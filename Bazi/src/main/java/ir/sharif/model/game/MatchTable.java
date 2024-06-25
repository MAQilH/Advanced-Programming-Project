package ir.sharif.model.game;

import ir.sharif.model.User;

import java.util.ArrayList;

public class MatchTable {
    private final UserTable[] userTables;
    private final User[] users;
    private int[] lives;
    private int[] vetoesLeft;
    private final ArrayList<Card> weatherCards;
    int turn, roundNumber;

    private boolean previousRoundPassed;

    public MatchTable(User user0, User user1) {
        turn = 0;
        roundNumber = 0;
        userTables = new UserTable[2];
        users = new User[2];
        lives = new int[2];
        vetoesLeft = new int[2];
        weatherCards = new ArrayList<>();
        userTables[0] = new UserTable(user0.getDeckInfo());
        userTables[1] = new UserTable(user1.getDeckInfo());
        users[0] = user0;
        users[1] = user1;
        lives[0] = lives[1] = 2;
        vetoesLeft[0] = vetoesLeft[1] = 2;
        previousRoundPassed = false;
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

    public void decreaseVetoesLeft(int playerNumber) {
        vetoesLeft[playerNumber]--;
    }

    public int getVetoesLeft(int playerNumber) {
        return vetoesLeft[playerNumber];
    }

    public User getUser(int playerNumber) {
        return users[playerNumber];
    }

    public boolean isPreviousRoundPassed() {
        return previousRoundPassed;
    }

    public void setPreviousRoundPassed(boolean previousRoundPassed) {
        this.previousRoundPassed = previousRoundPassed;
    }
}
