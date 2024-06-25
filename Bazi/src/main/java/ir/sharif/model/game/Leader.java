package ir.sharif.model.game;

public class Leader {
    private final String name;
    private final Faction faction;
    private final Ability ability;
    private boolean isUsed = false;

    public Leader(String name, Faction faction, Ability ability) {
        this.name = name;
        this.faction = faction;
        this.ability = ability;
    }

    public String getName() {
        return name;
    }
    public Faction getFaction() {
        return faction;
    }
    public Ability getAbility() {
        return ability;
    }
    public boolean isUsed() {
        return isUsed;
    }
    public void setUsed(boolean used) {
        isUsed = used;
    }

}
