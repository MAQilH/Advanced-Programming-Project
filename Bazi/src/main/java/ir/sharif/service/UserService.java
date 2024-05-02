package ir.sharif.service;

import ir.sharif.model.User;

import java.util.ArrayList;

public class UserService {
    private static UserService instance;
    private User currentUser;
    private ArrayList<User> allUsers;

    private UserService() {

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

    private User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean isUsernameTaken(String username) {
        return getUserByUsername(username) != null;
    }
}
