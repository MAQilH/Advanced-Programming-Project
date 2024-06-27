package ir.sharif.model.game;

public class Leader {
    private final String name;
    private final Faction faction;
    private final Ability ability;
    private int roundOfAbilityUsed = -1;

    private int disableRound = -1;

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

    public void setRoundOfAbilityUsed(int roundOfAbilityUsed) {
        this.roundOfAbilityUsed = roundOfAbilityUsed;
    }

    public int getRoundOfAbilityUsed() {
        return roundOfAbilityUsed;
    }
    public int getDisableRound(){
        return disableRound;
    }
    public void setDisableRound(int disableRound){
        this.disableRound = disableRound;
    }
}
