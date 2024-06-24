package ir.sharif.model.game;

import java.util.ArrayList;

public class Row {

    private Card specialCard;
    private final ArrayList<Card> cards;

    public Row() {
        cards = new ArrayList<>();
        specialCard = null;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setSpecialCard(Card specialCard) {
        this.specialCard = specialCard;
    }

    public Card getSpecialCard() {
        return specialCard;
    }

}
