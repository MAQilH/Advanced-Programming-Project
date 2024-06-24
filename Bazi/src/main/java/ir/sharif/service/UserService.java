package ir.sharif.service;

import ir.sharif.model.User;

import java.util.ArrayList;

public class UserService {
    private static UserService instance;
    private User currentUser;
    private boolean stayLoggedIn;
    private ArrayList<User> allUsers = new ArrayList<>();

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

    public User getUserByUsername(String username) {
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

    public boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }

    public void addUser(User user){
        allUsers.add(user);
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }
}
