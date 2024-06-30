package ir.sharif.service.storage;

import ir.sharif.model.GameHistory;
import ir.sharif.model.User;
import ir.sharif.model.server.GameRecord;
import ir.sharif.utils.FileSaver;
import ir.sharif.view.controllers.Game;

import java.io.Serializable;
import java.util.ArrayList;

public class Storage implements Serializable {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<GameHistory> gameHistories = new ArrayList<>();
    private ArrayList<GameRecord> gameRecords = new ArrayList<>();

    public static Storage loadStorage(){
        Storage storage;
        try {
            storage = (Storage) FileSaver.loadObject("storage.stg");
            if(storage == null) storage = new Storage();
        } catch (Exception e){
            storage = new Storage();
        }
        return storage;
    }

    public static void saveStorage(Storage storage){
        FileSaver.saveObject(storage, "storage.stg");
    }

    public ArrayList<User> getUsers(){
        return users;
    }

    public ArrayList<GameHistory> getGameHistories(){
        return gameHistories;
    }

    public ArrayList<GameRecord> getGameRecords(){
        return gameRecords;
    }
}
