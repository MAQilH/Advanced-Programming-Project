package ir.sharif.service;

import ir.sharif.model.game.MatchTable;
import javafx.css.Match;

public class GameService {

    private static GameService instance;
    private MatchTable matchTable;

    private GameService() {

    }

    public static GameService getInstance() {
        if (instance == null) {
            instance = new GameService();
        }
        return instance;
    }

    public MatchTable getMatchTable() {
        return matchTable;
    }

    public void setMatchTable(MatchTable matchTable) {
        this.matchTable = matchTable;
    }
}
