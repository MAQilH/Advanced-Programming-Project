package ir.sharif.model.game;

import java.util.ArrayList;

public class Row {
    private Card spell;
    private final ArrayList<Card> cards;

    public Row() {
        cards = new ArrayList<>();
        spell = null;
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

    public void setSpell(Card spell) {
        this.spell = spell;
    }

    public Card getSpell() {
        return spell;
    }

}
