package ir.sharif.model.game;

import java.util.ArrayList;

public class UserTable {
    private final Leader leader;
    private final ArrayList<Card> siege, ranged, closeCombat, outOfPlays, hand, deck;
    private final int Score;

    public UserTable(Leader leader) {
        this.leader = leader;
        this.siege = new ArrayList<>();
        this.ranged = new ArrayList<>();
        this.closeCombat = new ArrayList<>();
        this.outOfPlays = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.deck = new ArrayList<>();
        this.Score = 0;
    }

    public Leader getLeader() {
        return leader;
    }

    public void addSiege(Card card) {
        siege.add(card);
    }

    public void addRanged(Card card) {
        ranged.add(card);
    }

    public void addCloseCombat(Card card) {
        closeCombat.add(card);
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

    public void removeSiege(Card card) {
        siege.remove(card);
    }

    public void removeRanged(Card card) {
        ranged.remove(card);
    }

    public void removeCloseCombat(Card card) {
        closeCombat.remove(card);
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

    public ArrayList<Card> getSiege() {
        return siege;
    }

    public ArrayList<Card> getRanged() {
        return ranged;
    }

    public ArrayList<Card> getCloseCombat() {
        return closeCombat;
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

    public void setScore(int score) {
    }

    public Card getSiege(int index) {
        return siege.get(index);
    }

    public Card getRanged(int index) {
        return ranged.get(index);
    }

    public Card getCloseCombat(int index) {
        return closeCombat.get(index);
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

}
