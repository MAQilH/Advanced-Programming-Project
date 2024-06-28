package ir.sharif.server;

import com.almasb.fxgl.net.Server;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.sharif.enums.ResultCode;
import ir.sharif.messages.Game.*;
import ir.sharif.messages.ServerMessage;
import ir.sharif.messages.StartNewGameMessage;
import ir.sharif.model.User;
import ir.sharif.model.server.GameRecord;
import ir.sharif.service.storage.Database;
import ir.sharif.utils.Random;
import ir.sharif.view.GameGraphics;
import ir.sharif.view.controllers.Game;

import java.util.HashMap;

public class GameHandler {

    private Gson gson;

    private GameHandler(){
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }

    private static GameHandler instance = null;

    public static GameHandler getInstance(){
        if(instance == null) instance = new GameHandler();
        return instance;
    }



    HashMap<String, String> queuedGame = new HashMap<>(); // key username, value gameToken

    HashMap<String, GameRecord> pendingGames = new HashMap<>(); // key gameToken, value gameRecord
    HashMap<String, GameRecord> liveGames = new HashMap<>(); // key gameToken, value gameRecord
    public synchronized ServerMessage startNewGame(StartNewGameMessage startNewGameMessage) {
        Database database = Database.getInstance();

        GameRecord gameRecord = new GameRecord(startNewGameMessage.getUser1(), startNewGameMessage.getUser2(), Random.generateNewToken());
        database.addGameRecord(gameRecord);
        return new ServerMessage(ResultCode.ACCEPT, "game added successfully");
    }

    public synchronized ServerMessage gameRequest(GameRequestMessage gameRequestMessage) {
        String token = Random.generateNewToken();
        GameRecord gameRecord = new GameRecord(gameRequestMessage.getUser(), null, token);
        pendingGames.put(token, gameRecord);
        queuedGame.put(gameRequestMessage.getReceiver(), token);
        return new ServerMessage(
                ResultCode.ACCEPT,
                token
        );
    }

    public synchronized ServerMessage gameIsAccepted(GameIsAcceptedMessage gameIsAcceptedMessage){
        String token = gameIsAcceptedMessage.getGameToken();
        if(liveGames.containsKey(token)){
            return new ServerMessage(ResultCode.ACCEPT, gson.toJson(liveGames.get(token).getUser2()));
        }
        return new ServerMessage(ResultCode.FAILED, "game doesnt accepted");
    }

    public synchronized ServerMessage getQueuedGame(GetQueuedGameMessage getQueuedGameMessage){
        if(queuedGame.containsKey(getQueuedGameMessage.getUsername())){
            return new ServerMessage(ResultCode.ACCEPT, queuedGame.get(getQueuedGameMessage.getUsername()));
        }
        return new ServerMessage(ResultCode.NOT_FOUND, "not pending game exits for this user");
    }

    public synchronized ServerMessage gameAcceptRequest(GameAcceptRequestMessage gameAcceptRequestMessage) {
        String token = gameAcceptRequestMessage.getGameToken();
        User user2 = gameAcceptRequestMessage.getUser();
        GameRecord gameRecord = pendingGames.get(token);
        pendingGames.remove(token);
        queuedGame.remove(user2.getUsername());
        gameRecord.setUser2(user2);
        liveGames.put(token, gameRecord);
        return new ServerMessage(ResultCode.ACCEPT, gson.toJson(gameRecord.getUser1()));
    }

    public synchronized ServerMessage getGameRecord(GetGameRecordMessage getGameRecordMessage) {
        String token = getGameRecordMessage.getGameToken();
        if(liveGames.containsKey(token)){
            return new ServerMessage(ResultCode.ACCEPT, gson.toJson(liveGames.get(token)));
        }
        GameRecord gameRecord = Database.getInstance().getGameRecordWithId(token);
        if(gameRecord != null){
            return new ServerMessage(ResultCode.ACCEPT, gson.toJson(gameRecord));
        }
        return new ServerMessage(ResultCode.NOT_FOUND, "dont exist such game with this token = " + token);
    }
}
