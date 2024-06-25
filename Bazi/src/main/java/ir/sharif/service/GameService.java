package ir.sharif.service;

import ir.sharif.model.game.MatchTable;

public class GameService {
    private GameService() {}

    private static GameService instance;
    private MatchTable matchTable;

    public static GameService getInstance() {
        if (instance == null) {
            instance = new GameService();
        }
        return instance;
    }

    public MatchTable getMatchTable(){
        return matchTable;
    }

}
