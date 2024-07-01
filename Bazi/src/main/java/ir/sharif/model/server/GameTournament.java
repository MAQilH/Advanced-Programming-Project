package ir.sharif.model.server;

import ir.sharif.model.User;

import java.util.ArrayList;

public class GameTournament {
    private ArrayList<String> gameTokens;
    private ArrayList<User> users;
    private String tournamentToken;

    public GameTournament(ArrayList<String> gameTokens, ArrayList<User> users, String tournamentToken) {
        this.gameTokens = gameTokens;
        this.users = users;
        this.tournamentToken = tournamentToken;
    }

    public GameTournament(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<String> getGameTokens() {
        return gameTokens;
    }

    public void setGameTokens(ArrayList<String> gameTokens) {
        this.gameTokens = gameTokens;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getTournamentToken() {
        return tournamentToken;
    }

    public void setTournamentToken(String tournamentToken) {
        this.tournamentToken = tournamentToken;
    }
}
