package ir.sharif.model.game;

import java.util.ArrayList;

public class UserTable {
    private final Leader leader;
    private final Row siege, ranged, closeCombat;
    private final ArrayList<Card> outOfPlays, hand, deck;
    private final int Score;

    public UserTable(Leader leader) {
        this.leader = leader;
        this.siege = new Row();
        this.ranged = new Row();
        this.closeCombat = new Row();
        this.outOfPlays = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.deck = new ArrayList<>();
        this.Score = 0;
    }

    public Leader getLeader() {
        return leader;
    }

    public void addSiege(Card card) {
        siege.addCard(card);
    }

    public void addRanged(Card card) {
        ranged.addCard(card);
    }

    public void addCloseCombat(Card card) {
        closeCombat.addCard(card);
    }

    public void setSpecialSiege(Card card) {
        siege.setSpecialCard(card);
    }

    public void setSpecialRanged(Card card) {
        ranged.setSpecialCard(card);
    }

    public void setSpecialCloseCombat(Card card) {
        closeCombat.setSpecialCard(card);
    }

    public Card getSpecialSiege() {
        return siege.getSpecialCard();
    }

    public Card getSpecialRanged() {
        return ranged.getSpecialCard();
    }

    public Card getSpecialCloseCombat() {
        return closeCombat.getSpecialCard();
    }

    public ArrayList<Card> getSiege() {
        return siege.getCards();
    }

    public ArrayList<Card> getRanged() {
        return ranged.getCards();
    }

    public ArrayList<Card> getCloseCombat() {
        return closeCombat.getCards();
    }

    public void removeSiege(Card card) {
        siege.removeCard(card);
    }

    public void removeRanged(Card card) {
        ranged.removeCard(card);
    }

    public void removeCloseCombat(Card card) {
        closeCombat.removeCard(card);
    }
    public void addOutOfPlay(Card card) {
        outOfPlays.add(card);
    }

    public void addHand(Card card) {
        hand.add(card);
    }

    public void addDeck(Card card) {
        deck.add(card);
    }

    public void removeOutOfPlay(Card card) {
        outOfPlays.remove(card);
    }

    public void removeHand(Card card) {
        hand.remove(card);
    }

    public void removeDeck(Card card) {
        deck.remove(card);
    }

    public int getScore() {
        return Score;
    }

    public ArrayList<Card> getOutOfPlays() {
        return outOfPlays;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public Card getOutOfPlay(int index) {
        return outOfPlays.get(index);
    }

    public Card getHand(int index) {
        return hand.get(index);
    }

    public Card getDeck(int index) {
        return deck.get(index);
    }

    public void setScore(int score) {
    }

}
