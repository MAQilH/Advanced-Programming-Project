package ir.sharif.service;

import ir.sharif.model.GameHistory;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameHistoryService {

    private ArrayList<GameHistory> gameHistories = new ArrayList<>();
    private GameHistoryService() {

    }


    static GameHistoryService instance;
    public static GameHistoryService getInstance() {
        if (instance == null) {
            instance = new GameHistoryService();
        }
        return instance;
    }

    public void addGameHistory(GameHistory gameHistory) {
        gameHistories.add(gameHistory);
    }

    public ArrayList<GameHistory> getUserHistory(String username){
        ArrayList<GameHistory> userHistory = new ArrayList<>();
        for (GameHistory gameHistory : gameHistories) {
            if (gameHistory.getUser1().getUsername().equals(username) || gameHistory.getUser2().getUsername().equals(username)) {
                userHistory.add(gameHistory);
            }
        }
        return userHistory;
    }

    public int getUserRank(String username){
        return 0;
    }

    public int getNumberOfWins(String username){
        ArrayList<GameHistory> gameHistories = getUserHistory(username);
//        for(GameHistory gameHistory: )
        return 0;
    }

    public int getNumberOfLosses(String username){
        return 0;
    }

    public int getNumberOfDraws(String username){
        return 0;
    }

    public int getNumberOfGames(String username){
        return 0;
    }

    public int getHighestScore(String username){
        return 0;
    }
}
