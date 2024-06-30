package ir.sharif.service.storage;

import ir.sharif.model.GameHistory;
import ir.sharif.model.User;
import ir.sharif.model.server.GameRecord;
import ir.sharif.view.controllers.Game;

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
	    System.err.println("fuckkkkkk");
	    System.err.println(gameHistory.getGameToken());
	    System.err.println(gameHistory.getUser1()   );
        storage.getGameHistories().add(gameHistory);
        Storage.saveStorage(storage);
    }

    public void addGameRecord(GameRecord gameRecord){
        Storage storage = Storage.loadStorage();
        storage.getGameRecords().add(gameRecord);
        Storage.saveStorage(storage);
    }

    public void updateGameRecord(GameRecord gameRecord){
        Storage storage = Storage.loadStorage();
        int gameIndex = 0;

        for (GameRecord record : storage.getGameRecords()) {
            if(record.getGameToken().equals(gameRecord.getGameToken())){
                break;
            }
            gameIndex++;
        }
        storage.getGameRecords().set(gameIndex, gameRecord);

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

    public ArrayList<GameRecord> getGameRecords(){
        Storage storage = Storage.loadStorage();
        return storage.getGameRecords();
    }

    public GameRecord getGameRecordWithId(String token){
        ArrayList<GameRecord> gameRecords = getGameRecords();
        for (GameRecord gameRecord : gameRecords) {
            if(gameRecord.getGameToken().equals(token)) return gameRecord;
        }
        return null;
    }

}
