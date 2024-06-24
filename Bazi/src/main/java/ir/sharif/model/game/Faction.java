package ir.sharif.model.game;

public enum Faction {
    MONSTERS, NILFGAARDIAN_EMPIRE, NORTHEN_REALMS, SCOIATAEL, SKELLIGE;

    public static Faction findFaction(String name) {
        for (Faction faction : Faction.values()) {
            if (faction.name().equals(name)) {
                return faction;
            }
        }
        return null;
    }
}
