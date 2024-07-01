package ir.sharif.model.game;

import java.util.ArrayList;
import java.util.Collection;

public class UserTable {
    private final Leader leader;
    private final Faction faction;
    private final Row siege, ranged, closeCombat;
    private final ArrayList<Card> outOfPlays, hand, deck;
    private int life = 2, vetoesLeft = 2;

    public UserTable(DeckInfo deckInfo) {
        this.leader = deckInfo.getLeader().getInstance();
        faction = deckInfo.getFaction();
        this.siege = new Row();
        this.ranged = new Row();
        this.closeCombat = new Row();
        this.outOfPlays = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.deck = new ArrayList<>();
        for (CardTypes cardTypes : deckInfo.getStorage()) {
            deck.add(cardTypes.getInstance());
        }

        //TODO: add random cards to hand using deckInfo
    }

    public int getPower(){
        return getRowPower(closeCombat) + getRowPower(ranged) + getRowPower(siege);
    }

    public int getRowPower(Row row){
        int rowPower = 0;
        for(Card rowCard: row.getCards()){
            rowPower += rowCard.calculatePower();
        }
        return rowPower;
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

    public void setSpellSiege(Card card) {
        siege.setSpell(card);
    }

    public void setSpellRanged(Card card) {
        ranged.setSpell(card);
    }

    public void setSpellCloseCombat(Card card) {
        closeCombat.setSpell(card);
    }

    public Card getSpellSiege() {
        return siege.getSpell();
    }

    public Card getSpellRanged() {
        return ranged.getSpell();
    }

    public Card getSpellCloseCombat() {
        return closeCombat.getSpell();
    }

    public Row getSiege() {
        return siege;
    }

    public Row getRanged() {
        return ranged;
    }

    public Row getCloseCombat() {
        return closeCombat;
    }

    public Row getRowByNumber(int rowNumber) {
        return switch (rowNumber) {
            case 0 -> closeCombat;
            case 1 -> ranged;
            case 2 -> siege;
            default -> null;
        };
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

    public Faction getFaction() {
        return faction;
    }

    public ArrayList<Card> getAllPlayedCard(){
        ArrayList<Card> allPlayedCard = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
            Row row = getRowByNumber(rowIndex);
            allPlayedCard.addAll(row.getCards());
            if (row.getSpell() != null)
                allPlayedCard.add(row.getSpell());
        }
        return allPlayedCard;
    }

    public ArrayList<Card> getHeroesCard(){
        ArrayList<Card> heroes = new ArrayList<>();
        for (Card card : getAllPlayedCard()) {
            if (card.isHero())
                heroes.add(card);
        }
        return heroes;
    }

    public int getLife() {
        return life;
    }

    public void decreaseLife() {
        life--;
    }

    public int getVetoesLeft() {
        return vetoesLeft;
    }

    public void decreaseVetoesLeft() {
        vetoesLeft--;
    }


    public ArrayList<Row> getRows(){
        ArrayList<Row> rows = new ArrayList<>();
        rows.add(closeCombat);
        rows.add(ranged);
        rows.add(siege);
        return rows;
    }

    public void setLife(int x) {
        life = x;
    }

	public ArrayList<Card> getDiscardPile() {
		return outOfPlays;
	}

}
