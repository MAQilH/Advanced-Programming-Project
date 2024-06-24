package ir.sharif.service;

import ir.sharif.model.GameHistory;
import ir.sharif.model.Pair;
import ir.sharif.model.User;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

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
        ArrayList<User> users = UserService.getInstance().getAllUsers();
        HashMap<User, Integer> numberOfWins = new HashMap<>();
        for(User user: users){
            numberOfWins.put(user, getNumberOfWins(user.getUsername()));
        }
        users.sort((User user1, User user2) -> numberOfWins.get(user2) - numberOfWins.get(user1));
        return users.indexOf(UserService.getInstance().getUserByUsername(username)) + 1;
    }

    public int getNumberOfWins(String username){
        ArrayList<GameHistory> gameHistories = getUserHistory(username);
        int numberOfWins = 0;
        for(GameHistory gameHistory: gameHistories){
            if(gameHistory.getWinner() != null && gameHistory.getWinner().getUsername().equals(username)){
                numberOfWins++;
            }
        }
        return numberOfWins;
    }

    public int getNumberOfLosses(String username){
        ArrayList<GameHistory> gameHistories = getUserHistory(username);
        int numberOfLoose = 0;
        for(GameHistory gameHistory: gameHistories){
            if(gameHistory.getWinner() != null && !gameHistory.getWinner().getUsername().equals(username)){
                numberOfLoose++;
            }
        }
        return numberOfLoose;
    }

    public int getNumberOfDraws(String username){
        ArrayList<GameHistory> gameHistories = getUserHistory(username);
        int numberOfDraws= 0;
        for(GameHistory gameHistory: gameHistories){
            if(gameHistory.getWinner() == null){
                numberOfDraws++;
            }
        }
        return numberOfDraws;
    }

    public int getNumberOfGames(String username){
        ArrayList<GameHistory> gameHistories = getUserHistory(username);
        return gameHistories.size();
    }

    public int getHighestScore(String username){
        int maxScore = 0;
        ArrayList<GameHistory> gameHistories = getUserHistory(username);
        for(GameHistory gameHistory: gameHistories){
            if(gameHistory.getUser1().getUsername().equals(username)){
                for (Pair<Integer, Integer> roundScore : gameHistory.getRoundScores()) {
                    maxScore = Math.max(maxScore, roundScore.getFirst());
                }
            } else {
                for (Pair<Integer, Integer> roundScore : gameHistory.getRoundScores()) {
                    maxScore = Math.max(maxScore, roundScore.getSecond());
                }
            }
        }
        return maxScore;
    }
}
