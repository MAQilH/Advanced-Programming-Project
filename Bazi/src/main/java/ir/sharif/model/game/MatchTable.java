package ir.sharif.model.game;

import ir.sharif.model.User;

import java.util.ArrayList;

public class MatchTable {
    private final UserTable[] userTables;
    private final User[] users;
    private final ArrayList<Card> weatherCards;
    int turn, roundNumber, totalTurns;
    private String gameToken;

    private boolean previousRoundPassed;

    public MatchTable(User user0, User user1, String gameToken) {
        turn = 0;
        roundNumber = 0;
        totalTurns = 0;
        userTables = new UserTable[2];
        users = new User[2];
        weatherCards = new ArrayList<>();
        userTables[0] = new UserTable(user0.getDeckInfo());
        userTables[1] = new UserTable(user1.getDeckInfo());
        users[0] = user0;
        users[1] = user1;
        previousRoundPassed = false;
        this.gameToken = gameToken;
    }

    public int getTurn() {
        return turn;
    }

    public UserTable getUserTable(int playerNumber) {
        return userTables[playerNumber];
    }

    public UserTable getCurrentUserTable() {
        return userTables[turn];
    }
    public UserTable getOpponentUserTable() {
        return userTables[1 - turn];
    }

    public ArrayList<Card> getWeatherCards() {
        return weatherCards;
    }

    public void addWeatherCard(Card card) {
        weatherCards.add(card);
    }

    public void removeWeatherCard(Card card) {
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

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void changeRound() {
        roundNumber++;
    }

    public void setTotalTurns(int totalTurns) {
        this.totalTurns = totalTurns;
    }

    public int getTotalTurns() {
        return totalTurns;
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

    public ArrayList<Card> getCardsByPosition(int pos) {
        ArrayList<Card> cards = new ArrayList<>();
        switch (pos) {
            case 0, 1, 2:
                cards.addAll(userTables[1].getRowByNumber(2 - pos).getCards());
                break;
            case 3, 4, 5:
                cards.addAll(userTables[0].getRowByNumber(pos - 3).getCards());
                break;
            case 6, 7, 8:
                if (userTables[1].getRowByNumber(8 - pos).getSpell() != null)
                    cards.add(userTables[1].getRowByNumber(8 - pos).getSpell());
                break;
            case 9, 10, 11:
                if (userTables[0].getRowByNumber(pos - 9).getSpell() != null)
                    cards.add(userTables[0].getRowByNumber(0).getSpell());
                break;
            case 12:
                cards.addAll(weatherCards);
                break;
        }
        return cards;
    }

    public String getGameToken() {
        return gameToken;
    }
}
