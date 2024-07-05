package ir.sharif.model.server;

import ir.sharif.model.GameHistory;
import ir.sharif.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Tournament {
    private ArrayList<TournamentPlayer> players = new ArrayList<>();
    private User owner;
    private User winner = null;
    private ArrayList<GameHistory> gameHistories = new ArrayList<>();
    private TournamentState tournamentState;
    private String tournamentToken;
    private ArrayList<TournamentPlayer> queuedPlayer = new ArrayList<>();
    private HashMap<String, TournamentMatchOpponentResult> matchedOpponent;

    public Tournament(User owner, String tournamentToken) {
        this.owner = owner;
        this.tournamentToken = tournamentToken;
        tournamentState = TournamentState.PENDDING;
		matchedOpponent = new HashMap<>();
    }

    public ArrayList<TournamentPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<TournamentPlayer> players) {
        this.players = players;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public ArrayList<GameHistory> getGameHistories() {
        return gameHistories;
    }

    public void setGameHistories(ArrayList<GameHistory> gameHistories) {
        this.gameHistories = gameHistories;
    }

    public String getTournamentToken() {
        return tournamentToken;
    }

    public void setTournamentToken(String tournamentToken) {
        this.tournamentToken = tournamentToken;
    }

    public TournamentState getTournamentState() {
        return tournamentState;
    }

    public void setTournamentState(TournamentState tournamentState) {
        this.tournamentState = tournamentState;
    }

    public ArrayList<TournamentPlayer> getQueuedPlayer() {
        return queuedPlayer;
    }

    public void setQueuedPlayer(ArrayList<TournamentPlayer> queuedPlayer) {
        this.queuedPlayer = queuedPlayer;
    }

    public HashMap<String, TournamentMatchOpponentResult> getMatchedOpponent() {
        return matchedOpponent;
    }

	public User getWinner() {
		return winner;
	}

	public ArrayList<GameHistory> getHistories() {
		return gameHistories;
	}
}
