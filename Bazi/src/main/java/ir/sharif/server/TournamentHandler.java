package ir.sharif.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.sharif.enums.ResultCode;
import ir.sharif.messages.ServerMessage;
import ir.sharif.messages.StartNewGameMessage;
import ir.sharif.messages.tournament.*;
import ir.sharif.model.GameHistory;
import ir.sharif.model.User;
import ir.sharif.model.server.Tournament;
import ir.sharif.model.server.TournamentMatchOpponentResult;
import ir.sharif.model.server.TournamentPlayer;
import ir.sharif.model.server.TournamentState;
import ir.sharif.utils.Random;

import java.util.ArrayList;
import java.util.HashMap;

public class TournamentHandler {

    private final HashMap<String, Tournament> tournaments = new HashMap<>();

    private static TournamentHandler instance;

    private final Gson gson;

    private TournamentHandler() {
        gson = new GsonBuilder().create();
    }

    public static TournamentHandler getInstance() {
        if (instance == null)
            instance = new TournamentHandler();
        return instance;
    }

    public ServerMessage createTournament(CreateTournamentMessage createTournamentMessage) {
        User owner = createTournamentMessage.getOwner();
        String token = Random.generateNewToken();
        Tournament tournament = new Tournament(owner, token);
        tournaments.put(token, tournament);
        return new ServerMessage(ResultCode.ACCEPT, token);
    }

    public ServerMessage joinTournament(JoinPlayerMessage joinPlayerMessage) {
        String token = joinPlayerMessage.getTournamentToken();
        User user = joinPlayerMessage.getUser();

        if(!tournaments.containsKey(token))
            return new ServerMessage(ResultCode.NOT_FOUND, "doesnt exist such tournaments");

        Tournament tournament = tournaments.get(token);
        if(tournament.getTournamentState() == TournamentState.STARTED)
            return new ServerMessage(ResultCode.FAILED, "tournament is started!");
        if(tournament.getTournamentState() == TournamentState.FINISHED)
            return new ServerMessage(ResultCode.FAILED, "tournament ended!");

        TournamentPlayer player = new TournamentPlayer(user, token);
        tournament.getPlayers().add(player);
        tournament.getQueuedPlayer().add(player);

        return new ServerMessage(ResultCode.ACCEPT, "player joined successfully!");
    }

    public ServerMessage readyPlayer(ReadyPlayerMessage readyPlayerMessage){
        String token = readyPlayerMessage.getTournamentToken();
        String username = readyPlayerMessage.getUsername();

        if(!tournaments.containsKey(token))
            return new ServerMessage(ResultCode.NOT_FOUND, "doesnt exist such tournaments");

        Tournament tournament = tournaments.get(token);
        TournamentPlayer player = findTournamentPlayerWithUsername(username, tournament.getPlayers());

        if(player == null)
            return new ServerMessage(ResultCode.NOT_FOUND, "doesnt exist such player");

        player.setReady(readyPlayerMessage.isReady());

        boolean allReady = true;
        for (TournamentPlayer tournamentPlayer : tournament.getPlayers()) {
            if(!tournamentPlayer.isReady()){
                allReady = false;
                break;
            }
        }

        if(allReady){
            tournament.setTournamentState(TournamentState.STARTED);
        }

        return new ServerMessage(ResultCode.ACCEPT, "ready state change successfully");
    }

    public TournamentPlayer findTournamentPlayerWithUsername(String username, ArrayList<TournamentPlayer> players){
        for (TournamentPlayer player : players) {
            if(player.getUser().getUsername().equals(username))
                return player;
        }
        return null;
    }

    public ServerMessage getTournament(GetTournamentMessage getTournamentMessage){
        String token = getTournamentMessage.getTournamentToken();

        if(!tournaments.containsKey(token))
            return new ServerMessage(ResultCode.NOT_FOUND, "doesnt exist such tournaments");

        return new ServerMessage(ResultCode.ACCEPT, gson.toJson(tournaments.get(token)));
    }


    public ServerMessage getTournamentState(GetTournamentStateMessage getTournamentStateMessage){
        String token = getTournamentStateMessage.getTournamentToken();

        if(!tournaments.containsKey(token))
            return new ServerMessage(ResultCode.NOT_FOUND, "doesnt exist such tournaments");

        return new ServerMessage(ResultCode.ACCEPT, gson.toJson(tournaments.get(token).getTournamentState()));
    }

    public ServerMessage getOpponent(GetOpponentMessage getOpponentMessage){
        String token = getOpponentMessage.getTournamentToken();
        String username = getOpponentMessage.getUsername();

        if(!tournaments.containsKey(token))
            return new ServerMessage(ResultCode.NOT_FOUND, "doesnt exist such tournaments");

        Tournament tournament = tournaments.get(token);

        if(tournament.getMatchedOpponent().getOrDefault(username, null) != null){
            return new ServerMessage(ResultCode.ACCEPT, gson.toJson(tournament.getMatchedOpponent().get(username)));
        }

        TournamentPlayer currentPlayer = findTournamentPlayerWithUsername(username, tournament.getQueuedPlayer());

        if(currentPlayer == null){
            return new ServerMessage(ResultCode.FAILED, "you are a loser!");
        }

        int minDist = Integer.MAX_VALUE;
        TournamentPlayer candidateOpponent = null;
        for (TournamentPlayer player : tournament.getQueuedPlayer()) {
            if(player == currentPlayer) continue;
            int dist = Math.abs(player.getRate() - currentPlayer.getRate());
            if(dist < minDist){
                minDist = dist;
                candidateOpponent = player;
            }
        }

        if(minDist > 2) return new ServerMessage(ResultCode.NOT_FOUND, "not found any opponent!");

        ServerMessage result = GameHandler.getInstance().startNewGame(new StartNewGameMessage(
                currentPlayer.getUser(),
                candidateOpponent.getUser(),
                false,
                token
        ));
        if(!result.wasSuccessfull()) return result;
        String gameToken = result.getAdditionalInfo();

        tournament.getMatchedOpponent().put(username, new TournamentMatchOpponentResult(candidateOpponent.getUser(), gameToken));
        tournament.getMatchedOpponent().put(candidateOpponent.getUser().getUsername(), new TournamentMatchOpponentResult(currentPlayer.getUser(), gameToken));

        return new ServerMessage(ResultCode.ACCEPT, gson.toJson(tournament.getMatchedOpponent().get(username)));
    }

    public void finishGame(GameHistory gameHistory){
        String token = gameHistory.getTournamentToken();
        if(token == null || !tournaments.containsKey(token)) return;

        Tournament tournament = tournaments.get(token);

        String username1 = gameHistory.getUser1().getUsername();
        String username2 = gameHistory.getUser2().getUsername();

        TournamentPlayer player1 = findTournamentPlayerWithUsername(username1, tournament.getPlayers());
        TournamentPlayer player2 = findTournamentPlayerWithUsername(username2, tournament.getPlayers());
        TournamentPlayer winner = null;

        if(gameHistory.getWinner() != null)
            winner = findTournamentPlayerWithUsername(gameHistory.getWinner().getUsername(), tournament.getPlayers());

        if(winner == player1)
            player1.setRate(player1.getRate() + 1);
        if(winner == player2)
            player2.setRate(player2.getRate() + 1);

        tournament.getMatchedOpponent().put(username1, null);
        tournament.getMatchedOpponent().put(username2, null);

        if(winner == null || player1 == winner)
            tournament.getQueuedPlayer().add(player1);
        else player1.setLoss(true);

        if(winner == null || player2 == winner)
            tournament.getQueuedPlayer().add(player2);
        else player2.setLoss(true);

        tournament.getGameHistories().add(gameHistory);

        boolean finishTournament = true;
        for (TournamentPlayer player : tournament.getPlayers()) {
            if(!player.isLoss() && player != winner) {
                finishTournament = false;
                break;
            }
        }

        if(finishTournament) {
            tournament.setWinner(winner.getUser());
            tournament.setTournamentState(TournamentState.FINISHED);
            // TODO: save to database
        }
    }

}
