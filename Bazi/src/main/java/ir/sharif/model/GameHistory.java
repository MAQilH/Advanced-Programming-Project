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

    public User getUser1() {
        return user1;
    }
    public User getUser2(){
        return user2;
    }

    public User getWinner(){
        return winner;
    }

    public ArrayList<Pair<Integer, Integer>> getRoundScores(){
        return roundScores;
    }
}
