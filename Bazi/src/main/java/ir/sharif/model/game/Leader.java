package ir.sharif.model.game;

public class Leader {
    private final String name;
    private final Faction faction;
    private final Ability ability;

    public Leader(String name, Faction faction, Ability ability) {
        this.name = name;
        this.faction = faction;
        this.ability = ability;
    }

    public Ability getAbility() {
        return ability;
    }
    public Faction getFaction() {
        return faction;
    }
    public String getName() {
        return name;
    }
}
