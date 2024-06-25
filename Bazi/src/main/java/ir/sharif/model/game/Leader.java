package ir.sharif.model.game;

public class Leader {
    private final String name;
    private final Faction faction;
    private final LeadersAbility ability;

    public Leader(String name, Faction faction, LeadersAbility ability) {
        this.name = name;
        this.faction = faction;
        this.ability = ability;
    }
}
