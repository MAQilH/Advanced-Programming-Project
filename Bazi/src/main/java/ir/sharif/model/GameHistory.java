package ir.sharif.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;

public class GameHistory implements Savable {
    private User user1, user2;
    private Date date;
    private ArrayList<Pair<Integer, Integer>> roundScores;
    private User winner;

    @Override
    public void save(Path path) {

    }

    @Override
    public Savable load(Path path) {
        return null;
    }
}
