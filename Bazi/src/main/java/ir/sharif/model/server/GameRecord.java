package ir.sharif.model.server;

import ir.sharif.model.User;

import java.util.ArrayList;

public class GameRecord {
    private User user1, user2;
    private String gameToken;
    private boolean gameIsPrivate;
    ArrayList<String> commands;

    public GameRecord(User user1, User user2, String gameToken, boolean gameIsPrivate){
        this.user1 = user1;
        this.user2 = user2;
        this.gameToken = gameToken;
        this.gameIsPrivate = gameIsPrivate;
        commands = new ArrayList<>();
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public String getGameToken() {
        return gameToken;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public void setGameToken(String gameToken) {
        this.gameToken = gameToken;
    }

    public void setCommands(ArrayList<String> commands) {
        this.commands = commands;
    }
}
