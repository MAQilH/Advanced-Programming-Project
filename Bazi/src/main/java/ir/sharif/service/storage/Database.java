package ir.sharif.service.storage;

import ir.sharif.model.GameHistory;
import ir.sharif.model.User;

import java.util.ArrayList;

public class Database {

    private static Database instance;

    private Database() {}

    public static Database getInstance(){
        if(instance == null) instance = new Database();
        return instance;
    }

    public void addUser(User user){
        Storage storage = Storage.loadStorage();
        storage.getUsers().add(user);
        Storage.saveStorage(storage);
    }

    public void addGameHistories(GameHistory gameHistory){
        Storage storage = Storage.loadStorage();
        storage.getGameHistories().add(gameHistory);
        Storage.saveStorage(storage);
    }

    public void changeUserInfo(String username, User user){
        Storage storage = Storage.loadStorage();
        for(User u : storage.getUsers()){
            if(u.getUsername().equals(username)){
                u = user;
                break;
            }
        }
        Storage.saveStorage(storage);
    }

    public User getUserWithUsername(String username){
        Storage storage = Storage.loadStorage();
        for(User u : storage.getUsers()){
            if(u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }

    public ArrayList<GameHistory> getGameHistories(){
        Storage storage = Storage.loadStorage();
        return storage.getGameHistories();
    }

}
