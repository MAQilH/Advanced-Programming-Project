package ir.sharif.model.game;

import java.io.Serializable;
import java.util.ArrayList;

public class DeckInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Leader leader;
    private Faction faction;
    private final ArrayList<Card> storage;

    public DeckInfo() {
        leader = null;
        faction = null;
        storage = new ArrayList<>();
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public void addCard(Card card) {
        storage.add(card);
    }

    public void removeCard(Card card) {
        storage.remove(card);
    }

    public Leader getLeader() {
        return leader;
    }

    public Faction getFaction() {
        return faction;
    }

    public ArrayList<Card> getStorage() {
        return storage;
    }

    public void clearStorage() {
        storage.clear();
    }

}
