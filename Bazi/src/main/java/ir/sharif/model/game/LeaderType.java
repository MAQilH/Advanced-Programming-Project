package ir.sharif.model.game;

public enum LeaderType {
    THE_SIEGEMASTER("The Siegemaster", Faction.NORTHEN_REALMS, null),
    THE_STEEL_FORGED("The Steel-Forged", Faction.NORTHEN_REALMS, null),
    KING_OF_TEMERIA("King of Temeria", Faction.NORTHEN_REALMS, null),
    LORD_COMMANDER_OF_THE_NORTH("Lord Commander of the North", Faction.NORTHEN_REALMS, null),
    SON_OF_MEDELL("Son of Medell", Faction.NORTHEN_REALMS, null),
    THE_WHITE_FLAME("The White Flame", Faction.NILFGAARDIAN_EMPIRE, null),
    HIS_IMPERIAL_MAJESTY("His Imperial Majesty", Faction.NILFGAARDIAN_EMPIRE, null),
    EMPEROR_OF_NILFGAARD("Emperor of Nilfgaard", Faction.NILFGAARDIAN_EMPIRE, null),
    THE_RELENTLESS("The Relentless", Faction.NILFGAARDIAN_EMPIRE, null),
    INVADER_OF_THE_NORTH("Invader of the North", Faction.NILFGAARDIAN_EMPIRE, null),
    BRINGER_OF_DEATH("Bringer of Death", Faction.MONSTERS, null),
    KING_OF_THE_WILD_HUNT("King of the wild Hunt", Faction.MONSTERS, null),
    DESTROYER_OF_WORLDS("Destroyer of Worlds", Faction.MONSTERS, null),
    COMMANDER_OF_THE_RED_RIDERS("Commander of the Red Riders", Faction.MONSTERS, null),
    THE_TREACHEROUS("The Treacherous", Faction.MONSTERS, null),
    QUEEN_OF_DOL_BLATHANNA("Queen of Dol Blathanna", Faction.SCOIATAEL, null),
    THE_BEAUTIFUL("The Beautiful", Faction.SCOIATAEL, null),
    DAISY_OF_THE_VALLEY("Daisy of the Valley", Faction.SCOIATAEL, null),
    PUREBLOOD_ELF("Pureblood Elf", Faction.SCOIATAEL, null),
    HOPE_OF_THE_AEN_SEIDHE("Hope of the Aen Seidhe", Faction.SCOIATAEL, null),
    CRACH_AN_CRAITE("Crach an Craite", Faction.SKELLIGE, null),
    KING_BRAN("King Bran", Faction.SKELLIGE, null);

    private final String name;
    private final Faction faction;
    private final Ability ability;

    LeaderType() { // TODO: remove that
        this.name = "";
        this.faction = null;
        this.ability = null;
    }
    LeaderType(String name, Faction faction, Ability ability) {
        this.name = name;
        this.faction = faction;
        this.ability = ability;
    }

    public Leader getInstance() {
        return new Leader(this.name(), this.faction, this.ability);
    }

	public Faction getFaction() {
		return faction;
	}

	public String getName() {
		return name;
	}
}
