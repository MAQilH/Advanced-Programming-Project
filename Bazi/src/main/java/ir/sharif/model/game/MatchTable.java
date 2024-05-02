package ir.sharif.model.game;

import java.util.ArrayList;

public class MatchTable {
    private UserTable[] userTables;
    private ArrayList<Card> specialCards;
    int turn, roundNumber;

    public MatchTable() {
        turn = 0;
        roundNumber = 0;
        userTables = new UserTable[2];
    }

    public int getTurn() {
        return 0;
    }

    public UserTable getUserTable(int playerNumber) {
        return userTables[playerNumber];
    }

    public ArrayList<Card> getSpecialCards() {
        return specialCards;
    }

    public void addSpecialCard(Card card) {
        specialCards.add(card);
    }

    public void removeSpecialCard(Card card) {
        specialCards.remove(card);
    }

    public Card getSpecialCard(int index) {
        return specialCards.get(index);
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }
}
