package ir.sharif.model.game;

import ir.sharif.model.User;

import java.util.ArrayList;

public class MatchTable {
    private final UserTable[] userTables;
    private final User[] users;
    private int[] lives;
    private ArrayList<Card> weatherCards;
    int turn, roundNumber;

    public MatchTable(User user1, User user2) {
        turn = 0;
        roundNumber = 0;
        userTables = new UserTable[2];
        users = new User[2];
        lives = new int[2];
        users[0] = user1;
        users[1] = user2;
        lives[0] = lives[1] = 2;
    }

    public int getTurn() {
        return turn;
    }

    public UserTable getUserTable(int playerNumber) {
        return userTables[playerNumber];
    }

    public ArrayList<Card> getWeatherCards() {
        return weatherCards;
    }

    public void addWeatherCard(Card card) {
        weatherCards.add(card);
    }

    public void removeweatherCard(Card card) {
        weatherCards.remove(card);
    }

    public Card getweatherCard(int index) {
        return weatherCards.get(index);
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public void changeTurn() {
        turn = 1 - turn;
    }

    public void changeRound() {
        roundNumber++;
    }

    public void decreaseLife(int playerNumber) {
        lives[playerNumber]--;
    }

    public int getLife(int playerNumber) {
        return lives[playerNumber];
    }

    public User getUser(int playerNumber) {
        return users[playerNumber];
    }

}
