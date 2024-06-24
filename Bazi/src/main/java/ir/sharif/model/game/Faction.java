package ir.sharif.model.game;

public enum Faction {
    MONSTERS("monster"), NILFGAARDIAN_EMPIRE("nilfgaardian_empire"), NORTHEN_REALMS("northen_realms"), SCOIATAEL("scoiatael"), SKELLIGE("skellige");

    private final String name;

    Faction(String name) {
        this.name = name;
    }

    public static Faction findFaction(String name) {
        for (Faction faction : Faction.values()) {
            if (faction.name.equals(name)) {
                return faction;
            }
        }
        return null;
    }

}
