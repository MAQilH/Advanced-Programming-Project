package ir.sharif.model.game;

import java.util.ArrayList;

public class DeckInfo {

    private LeaderType leader;
    private Faction faction;
    private final ArrayList<CardTypes> storage;

    public DeckInfo() {
        leader = null;
        faction = null;
        storage = new ArrayList<>();
    }

    public void setLeader(LeaderType leader) {
        this.leader = leader;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public void addCard(CardTypes card) {
        storage.add(card);
    }

    public void removeCard(Card card) {
        storage.remove(card);
    }

    public LeaderType getLeader() {
        return leader;
    }

    public Faction getFaction() {
        return faction;
    }

    public ArrayList<CardTypes> getStorage() {
        return storage;
    }

    public void clearStorage() {
        storage.clear();
    }

}
