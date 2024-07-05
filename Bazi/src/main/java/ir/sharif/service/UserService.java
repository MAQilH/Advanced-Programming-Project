package ir.sharif.service;

import ir.sharif.model.User;
import ir.sharif.service.storage.Database;

import java.util.ArrayList;

public class UserService {
    private static UserService instance;
    private User currentUser;
    private Database database;
	private String tournamentToken = null;
    private UserService() {
        database = Database.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getUserByUsername(String username) {
        return database.getUserWithUsername(username);
    }

    public boolean isUsernameTaken(String username) {
        return getUserByUsername(username) != null;
    }

    public void addUser(User user){
        database.addUser(user);
    }

    public void changeUser(String username, User user){
        Database.getInstance().changeUserInfo(username, user);
    }

    public ArrayList<User> getUsers(){
        return Database.getInstance().getUsers();
    }

	public void setTournamentToken(String tournamentToken) {
		this.tournamentToken = tournamentToken;
	}

	public String getTournamentToken() {
		return tournamentToken;
	}
}
