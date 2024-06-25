package ir.sharif.model.game;

import ir.sharif.model.game.leadersAbillity.*;

public enum LeaderType {
    THE_SIEGEMASTER("The Siegemaster", Faction.NORTHEN_REALMS, new TheSiegeMaster()),
    THE_STEEL_FORGED("The Steel-Forged", Faction.NORTHEN_REALMS, new TheSteelForged()),
    KING_OF_TEMERIA("King of Temeria", Faction.NORTHEN_REALMS, new KingOfTemeria()),
    LORD_COMMANDER_OF_THE_NORTH("Lord Commander of the North", Faction.NORTHEN_REALMS, new LordCommanderOfTheNorth()),
    SON_OF_MEDELL("Son of Medell", Faction.NORTHEN_REALMS, new SonOfMedell()),
    THE_WHITE_FLAME("The White Flame", Faction.NILFGAARDIAN_EMPIRE, new TheWhiteFlame()),
    HIS_IMPERIAL_MAJESTY("His Imperial Majesty", Faction.NILFGAARDIAN_EMPIRE, new HisImperialMajesty()),
    EMPEROR_OF_NILFGAARD("Emperor of Nilfgaard", Faction.NILFGAARDIAN_EMPIRE, new EmperorOfNilfGaard()),
    THE_RELENTLESS("The Relentless", Faction.NILFGAARDIAN_EMPIRE, new TheRelentless()),
    INVADER_OF_THE_NORTH("Invader of the North", Faction.NILFGAARDIAN_EMPIRE, new InvaderOfTheNorth()),
    BRINGER_OF_DEATH("Bringer of Death", Faction.MONSTERS, new BringerOfDeath()),
    KING_OF_THE_WILD_HUNT("King of the wild Hunt", Faction.MONSTERS, new KingOfWildHunt()),
    DESTROYER_OF_WORLDS("Destroyer of Worlds", Faction.MONSTERS, new DestroyerOfWorlds()),
    COMMANDER_OF_THE_RED_RIDERS("Commander of the Red Riders", Faction.MONSTERS, new CommanderOfRedRiders()),
    THE_TREACHEROUS("The Treacherous", Faction.MONSTERS, new TheTreacherous()),
    QUEEN_OF_DOL_BLATHANNA("Queen of Dol Blathanna", Faction.SCOIATAEL, new QueenOfDolBlathanna()),
    THE_BEAUTIFUL("The Beautiful", Faction.SCOIATAEL, new TheBeautiful()),
    DAISY_OF_THE_VALLEY("Daisy of the Valley", Faction.SCOIATAEL, new DaisyOfTheValley()),
    PUREBLOOD_ELF("Pureblood Elf", Faction.SCOIATAEL, new PureBloodElf()),
    HOPE_OF_THE_AEN_SEIDHE("Hope of the Aen Seidhe", Faction.SCOIATAEL, new HopeOfTheAenSeidhe()),
    CRACH_AN_CRAITE("Crach an Craite", Faction.SKELLIGE, new CrachAnCraite()),
    KING_BRAN("King Bran", Faction.SKELLIGE, new KingBran());

    private final String name;
    private final Faction faction;
    private final LeadersAbility ability;

    LeaderType() { // TODO: remove that
        this.name = "";
        this.faction = null;
        this.ability = null;
    }
    LeaderType(String name, Faction faction, LeadersAbility ability) {
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
