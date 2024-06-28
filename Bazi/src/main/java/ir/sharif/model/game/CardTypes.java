package ir.sharif.model.game;

import ir.sharif.model.game.abilities.*;

public enum CardTypes {

    MARDOEME("Mardoeme", -1, 3, CardPosition.SPELL, new Mardroeme(), Faction.SKELLIGE, false),
    BERSERKER("Berserker", 4, 1, CardPosition.CLOSE_COMBAT_UNIT, new Berserker(), Faction.SKELLIGE, false),
    VIDKAARL("Vidkaarl", 14, 1, CardPosition.CLOSE_COMBAT_UNIT, new MoraleBoost(), Faction.SKELLIGE, false),
    SVANRIGE("Svanrige", 4, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.SKELLIGE, false),
    UDALRYK("Udalryk", 4, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.SKELLIGE, false),
    DONAR_AN_HINDAR("Donar an Hindar", 4, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.SKELLIGE, false),
    CLAN_AN_CRAITE("Clan An Craite", 6, 3, CardPosition.CLOSE_COMBAT_UNIT, new TightBond(), Faction.SKELLIGE, false),
    BLUEBOY_LUGOS("Blueboy Lugos", 6, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.SKELLIGE, false),
    MADMAN_LUGOS("Madman Lugos", 6, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.SKELLIGE, false),
    CERYS("Cerys", 10, 1, CardPosition.CLOSE_COMBAT_UNIT, new Muster(), Faction.SKELLIGE, true),
    KAMBI("Kambi", 11, 1, CardPosition.CLOSE_COMBAT_UNIT, new Transformers(), Faction.SKELLIGE, true),
    BIRNA_BRAN("Birna Bran", 2, 1, CardPosition.CLOSE_COMBAT_UNIT, new Medic(), Faction.SKELLIGE, false),
    CLAN_DRUMMOND_SHIELDMAIDEN("Clan Drummond Shieldmaiden", 4, 3, CardPosition.CLOSE_COMBAT_UNIT, new TightBond(), Faction.SKELLIGE, false),
    CLAN_TORDARROCH_ARMORSMITH("Clan Tordarroch Armorsmith", 4, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.SKELLIGE, false),
    CLAN_DIMUN_PIRATE("Clan Dimun Pirate", 6, 1, CardPosition.RANGED_UNIT, new Scorch(), Faction.SKELLIGE, false),
    CLAN_BROKVAR_ARCHER("Clan Brokvar Archer", 6, 3, CardPosition.RANGED_UNIT, null, Faction.SKELLIGE, false),
    ERMION("Ermion", 8, 1, CardPosition.RANGED_UNIT, new Mardroeme(), Faction.SKELLIGE, true),
    HJALMAR("Hjalmar", 10, 1, CardPosition.RANGED_UNIT, null, Faction.SKELLIGE, true),
    YOUNG_BERSERKER("Young Berserker", 2, 3, CardPosition.RANGED_UNIT, new Berserker(), Faction.SKELLIGE, false),
    YOUNG_VIDKAARL("Young Vidkaarl", 8, 1, CardPosition.RANGED_UNIT, new TightBond(), Faction.SKELLIGE, false),
    LIGHT_LONGSHIP("Light Longship", 4, 3, CardPosition.RANGED_UNIT, new Muster(), Faction.SKELLIGE, false),
    HOLGER_BLACKHAND("Holger Blackhand", 4, 1, CardPosition.SIEGE_UNIT, null, Faction.SKELLIGE, false),
    WAR_LONGSHIP("War Longship", 6, 3, CardPosition.SIEGE_UNIT, new TightBond(), Faction.SKELLIGE, false),
    DRAIG_BON_DHU("Draig Bon-Dhu", 2, 1, CardPosition.SIEGE_UNIT, new CommandersHorn(), Faction.SKELLIGE, false),
    OLAF("Olaf", 12, 1, CardPosition.AGILE_UNIT, new MoraleBoost(), Faction.SKELLIGE, false),
    ELVEN_SKIRMISHER("Elven Skirmisher", 2, 3, CardPosition.RANGED_UNIT, new Muster(), Faction.SCOIATAEL, false),
    IORVETH("Iorveth", 10, 1, CardPosition.RANGED_UNIT, null, Faction.SCOIATAEL, true),
    YAEVINN("Yaevinn", 6, 1, CardPosition.AGILE_UNIT, null, Faction.SCOIATAEL, false),
    CIARAN_AEP("Ciaran aep", 3, 1, CardPosition.AGILE_UNIT, null, Faction.SCOIATAEL, false),
    DENNIS_CRANMER("Dennis Cranmer", 6, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.SCOIATAEL, false),
    DOL_BLATHANNA_SCOUT("Dol Blathanna Scout", 6, 3, CardPosition.AGILE_UNIT, null, Faction.SCOIATAEL, false),
    DOL_BLATHANNA_ARCHER("Dol Blathanna Archer", 4, 1, CardPosition.RANGED_UNIT, null, Faction.SCOIATAEL, false),
    DWARVEN_SKIRMISHER("Dwarven Skirmisher", 3, 3, CardPosition.CLOSE_COMBAT_UNIT, new Muster(), Faction.SCOIATAEL, false),
    FILAVANDREL("Filavandrel", 6, 1, CardPosition.AGILE_UNIT, null, Faction.SCOIATAEL, false),
    HAVEKAR_HEALER("Havekar Healer", 0, 3, CardPosition.RANGED_UNIT, new Medic(), Faction.SCOIATAEL, false),
    HAVEKAR_SMUGGLER("Havekar Smuggler", 5, 3, CardPosition.CLOSE_COMBAT_UNIT, new Muster(), Faction.SCOIATAEL, false),
    IDA_EMEAN_AEP("Ida Emean aep", 6, 1, CardPosition.RANGED_UNIT, null, Faction.SCOIATAEL, false),
    RIORDAIN("Riordain", 1, 1, CardPosition.RANGED_UNIT, null, Faction.SCOIATAEL, false),
    TORUVIEL("Toruviel", 2, 1, CardPosition.RANGED_UNIT, null, Faction.SCOIATAEL, false),
    VRIHEDD_BRIGADE_RECRUIT("Vrihedd Brigade Recruit", 4, 1, CardPosition.RANGED_UNIT, null, Faction.SCOIATAEL, false),
    MAHAKAMAN_DEFENDER("Mahakaman Defender", 5, 5, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.SCOIATAEL, false),
    VRIHEDD_BRIGADE_VETERAN("Vrihedd Brigade Veteran", 5, 2, CardPosition.AGILE_UNIT, null, Faction.SCOIATAEL, false),
    MILVA("Milva", 10, 1, CardPosition.RANGED_UNIT, new MoraleBoost(), Faction.SCOIATAEL, false),
    SEASENTHESSIS("Seasenthessis", 10, 1, CardPosition.RANGED_UNIT, null, Faction.SCOIATAEL, true),
    SCHIRRU("Schirru", 8, 1, CardPosition.SIEGE_UNIT, new Scorch(), Faction.SCOIATAEL, false),
    BARCLAY_ELS("Barclay Els", 6, 1, CardPosition.AGILE_UNIT, null, Faction.SCOIATAEL, false),
    EITHNE("Eithne", 10, 1, CardPosition.RANGED_UNIT, null, Faction.SCOIATAEL, true),
    ISENGRIM_FAOILTIARNA("Isengrim Faoiltiarna", 10, 1, CardPosition.CLOSE_COMBAT_UNIT, new MoraleBoost(), Faction.SCOIATAEL, false),
    BALLISTA("Ballista", 6, 2, CardPosition.SIEGE_UNIT, null, Faction.NORTHEN_REALMS, false),
    BLUE_STRIPES_COMMANDO("Blue Stripes Commando", 4, 3, CardPosition.CLOSE_COMBAT_UNIT, new TightBond(), Faction.NORTHEN_REALMS, false),
    CATAPULT("Catapult", 8, 2, CardPosition.SIEGE_UNIT, new TightBond(), Faction.NORTHEN_REALMS, false),
    DRAGON_HUNTER("Dragon Hunter", 5, 3, CardPosition.RANGED_UNIT, new TightBond(), Faction.NORTHEN_REALMS, false),
    DETHMOLD("Dethmold", 6, 1, CardPosition.RANGED_UNIT, null, Faction.NORTHEN_REALMS, false),
    DUN_BANNER_MEDIC("Dun Banner Medic", 5, 1, CardPosition.SIEGE_UNIT, new Medic(), Faction.NORTHEN_REALMS, false),
    ESTERAD_THYSSEN("Esterad Thyssen", 10, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.NORTHEN_REALMS, true),
    JOHN_NATALIS("John Natalis", 10, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.NORTHEN_REALMS, true),
    KAEDWENI_SIEGE_EXPERT("Kaedweni Siege Expert", 1, 3, CardPosition.SIEGE_UNIT, new MoraleBoost(), Faction.NORTHEN_REALMS, false),
    KEIRA_METZ("Keira Metz", 5, 1, CardPosition.RANGED_UNIT, null, Faction.NORTHEN_REALMS, false),
    PHILIPPA_EILHART("Philippa Eilhart", 10, 1, CardPosition.RANGED_UNIT, null, Faction.NORTHEN_REALMS, true),
    POOR_FUCKING_INFANTRY("Poor Fucking Infantry", 1, 4, CardPosition.CLOSE_COMBAT_UNIT, new TightBond(), Faction.NORTHEN_REALMS, false),
    PRINCE_STENNIS("Prince Stennis", 5, 1, CardPosition.CLOSE_COMBAT_UNIT, new Spy(), Faction.NORTHEN_REALMS, false),
    REDANIAN_FOOT_SOLDIER("Redanian Foot Soldier", 1, 2, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.NORTHEN_REALMS, false),
    SABRINA_GLEVISSING("Sabrina Glevissing", 4, 1, CardPosition.RANGED_UNIT, null, Faction.NORTHEN_REALMS, false),
    SHELDON_SKAGGS("Sheldon Skaggs", 4, 1, CardPosition.RANGED_UNIT, null, Faction.NORTHEN_REALMS, false),
    SIEGE_TOWER("Siege Tower", 6, 1, CardPosition.SIEGE_UNIT, null, Faction.NORTHEN_REALMS, false),
    SIEGFRIED_OF_DENESLE("Siegfried of Denesle", 5, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.NORTHEN_REALMS, false),
    SIGISMUND_DJIKSTRA("Sigismund Djikstra", 4, 1, CardPosition.CLOSE_COMBAT_UNIT, new Spy(), Faction.NORTHEN_REALMS, false),
    SILE_DE_TANSARVILLE("Síle de Tansarville", 5, 1, CardPosition.RANGED_UNIT, null, Faction.NORTHEN_REALMS, false),
    THALER("Thaler", 1, 1, CardPosition.SIEGE_UNIT, new Spy(), Faction.NORTHEN_REALMS, false),
    TREBUCHET("Trebuchet", 6, 2, CardPosition.SIEGE_UNIT, null, Faction.NORTHEN_REALMS, false),
    VERNON_ROCHE("Vernon Roche", 10, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.NORTHEN_REALMS, true),
    VES("Ves", 5, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.NORTHEN_REALMS, false),
    YARPEN_ZIRGRIN("Yarpen Zirgrin", 2, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.NORTHEN_REALMS, false),
    IMPERA_BRIGADE_GUARD("Impera Brigade Guard", 3, 4, CardPosition.CLOSE_COMBAT_UNIT, new TightBond(), Faction.NILFGAARDIAN_EMPIRE, false),
    STEFAN_SKELLEN("Stefan Skellen", 9, 1, CardPosition.CLOSE_COMBAT_UNIT, new Spy(), Faction.NILFGAARDIAN_EMPIRE, false),
    SHILARD_FITZ_OESTERLEN("Shilard Fitz-Oesterlen", 7, 1, CardPosition.CLOSE_COMBAT_UNIT, new Spy(), Faction.NILFGAARDIAN_EMPIRE, false),
    YOUNG_EMISSARY("Young Emissary", 5, 2, CardPosition.CLOSE_COMBAT_UNIT, new TightBond(), Faction.NILFGAARDIAN_EMPIRE, false),
    CAHIR_MAWR_DYFFRYN_AEP_CEALLACH("Cahir Mawr Dyffryn aep Ceallach", 6, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    VATTIER_DE_RIDEAUX("Vattier de Rideaux", 4, 1, CardPosition.CLOSE_COMBAT_UNIT, new Spy(), Faction.NILFGAARDIAN_EMPIRE, false),
    MENNO_COEHOORN("Menno Coehorn", 10, 1, CardPosition.CLOSE_COMBAT_UNIT, new Medic(), Faction.NILFGAARDIAN_EMPIRE, true),
    PUTTKAMMER("Puttkammer", 3, 1, CardPosition.RANGED_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    ASSIRE_VAR_ANAHID("Assire var Anahid", 6, 1, CardPosition.RANGED_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    BLACK_INFANTRY_ARCHER("Black Infantry Archer", 10, 2, CardPosition.RANGED_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    TIBOR_EGGEBRACHT("Tibor Eggebracht", 10, 1, CardPosition.RANGED_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, true),
    RENUALD_AEP_MATSEN("Renuald aep Matsen", 5, 1, CardPosition.RANGED_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    FRINGILLA_VIGO("Fringilla Vigo", 6, 1, CardPosition.RANGED_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    ROTTEN_MANGONEL("Rotten Mangonel", 3, 1, CardPosition.SIEGE_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    HEAVY_ZERRIKANIAN_FIRE_SCORPION("Heavy Zerrikanian Fire Scorpion", 10, 1, CardPosition.SIEGE_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    ZERRIKANIAN_FIRE_SCORPION("Zerrikanian Fire Scorpion", 5, 1, CardPosition.SIEGE_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    SIEGE_ENGINEER("Siege Engineer", 6, 1, CardPosition.SIEGE_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    MORVRAN_VOORHIS("Morvran Voorhis", 10, 1, CardPosition.SIEGE_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, true),
    ALBRICH("Albrich", 2, 1, CardPosition.RANGED_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    CYNTHIA("Cynthia", 4, 1, CardPosition.RANGED_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    ETOLIAN_AUXILIARY_ARCHERS("Etolian Auxiliary Archers", 1, 2, CardPosition.RANGED_UNIT, new Medic(), Faction.NILFGAARDIAN_EMPIRE, false),
    LETHO_OF_GULET("Letho of Gulet", 10, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    MENNO_COEHOORN_2("Menno Coehoorn", 10, 1, CardPosition.CLOSE_COMBAT_UNIT, new Medic(), Faction.NILFGAARDIAN_EMPIRE, false),
    MORTEISEN("Morteisen", 3, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    NAUSICAA_CAVALRY_RIDER("Nausicaa Cavalry Rider", 2, 3, CardPosition.CLOSE_COMBAT_UNIT, new TightBond(), Faction.NILFGAARDIAN_EMPIRE, false),
    RAINFARN("Rainfarn", 4, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    SIEGE_TECHNICIAN("Siege Technician", 0, 1, CardPosition.SIEGE_UNIT, new Medic(), Faction.NILFGAARDIAN_EMPIRE, false),
    SWEERS("Sweers", 2, 1, CardPosition.RANGED_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    VANHEMAR("Vanhemar", 4, 1, CardPosition.RANGED_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    VREEMDE("Vreemde", 2, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.NILFGAARDIAN_EMPIRE, false),
    DRAUG("Draug", 10, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.MONSTERS, true),
    IMLERITH("Imlerith", 10, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.MONSTERS, true),
    LESHEN("Leshen", 10, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.MONSTERS, true),
    KAYRAN("Kayran", 8, 1, CardPosition.AGILE_UNIT, new MoraleBoost(), Faction.MONSTERS, true),
    TOAD("Toad", 7, 1, CardPosition.RANGED_UNIT, new Scorch(), Faction.MONSTERS, false),
    ARACHAS_BEHEMOTH("Arachas Behemoth", 6, 1, CardPosition.SIEGE_UNIT, new Muster(), Faction.MONSTERS, false),
    CRONE_BREWESS("Crone: Brewess", 6, 1, CardPosition.CLOSE_COMBAT_UNIT, new Muster(), Faction.MONSTERS, false),
    CRONE_WEAVESS("Crone: Weavess", 6, 1, CardPosition.CLOSE_COMBAT_UNIT, new Muster(), Faction.MONSTERS, false),
    CRONE_WHISPESS("Crone: Whispess", 6, 1, CardPosition.CLOSE_COMBAT_UNIT, new Muster(), Faction.MONSTERS, false),
    EARTH_ELEMENTAL("Earth Elemental", 6, 1, CardPosition.SIEGE_UNIT, null, Faction.MONSTERS, false),
    FIEND("Fiend", 6, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.MONSTERS, false),
    FIRE_ELEMENTAL("Fire Elemental", 6, 1, CardPosition.SIEGE_UNIT, null, Faction.MONSTERS, false),
    FORKTAIL("Forktail", 5, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.MONSTERS, false),
    FRIGHTENER("Frightener", 5, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.MONSTERS, false),
    GRAVE_HAG("Grave Hag", 5, 1, CardPosition.RANGED_UNIT, null, Faction.MONSTERS, false),
    GRIFFIN("Griffin", 5, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.MONSTERS, false),
    ICE_GIANT("Ice Giant", 5, 1, CardPosition.SIEGE_UNIT, null, Faction.MONSTERS, false),
    PLAGUE_MAIDEN("Plague Maiden", 5, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.MONSTERS, false),
    VAMPIRE_KATAKAN("Vampire: Katakan", 5, 1, CardPosition.CLOSE_COMBAT_UNIT, new Muster(), Faction.MONSTERS, false),
    WEREWOLF("Werewolf", 5, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.MONSTERS, false),
    ARACHAS("Arachas", 4, 3, CardPosition.CLOSE_COMBAT_UNIT, new Muster(), Faction.MONSTERS, false),
    BOTCHLING("Botchling", 4, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.MONSTERS, false),
    VAMPIRE_BRUXA("Vampire: Bruxa", 4, 1, CardPosition.CLOSE_COMBAT_UNIT, new Muster(), Faction.MONSTERS, false),
    VAMPIRE_EKIMMARA("Vampire: Ekimmara", 4, 1, CardPosition.CLOSE_COMBAT_UNIT, new Muster(), Faction.MONSTERS, false),
    VAMPIRE_FLEDER("Vampire: Fleder", 4, 1, CardPosition.CLOSE_COMBAT_UNIT, new Muster(), Faction.MONSTERS, false),
    VAMPIRE_GARKAIN("Vampire: Garkain", 4, 1, CardPosition.CLOSE_COMBAT_UNIT, new Muster(), Faction.MONSTERS, false),
    CELAENO_HARPY("Celaeno Harpy", 2, 1, CardPosition.AGILE_UNIT, null, Faction.MONSTERS, false),
    COCKATRICE("Cockatrice", 2, 1, CardPosition.RANGED_UNIT, null, Faction.MONSTERS, false),
    ENDREGA("Endrega", 2, 1, CardPosition.RANGED_UNIT, null, Faction.MONSTERS, false),
    FOGLET("Foglet", 2, 1, CardPosition.CLOSE_COMBAT_UNIT, null, Faction.MONSTERS, false),
    GARGOYLE("Gargoyle", 2, 1, CardPosition.RANGED_UNIT, null, Faction.MONSTERS, false),
    HARPY("Harpy", 2, 1, CardPosition.AGILE_UNIT, null, Faction.MONSTERS, false),
    NEKKER("Nekker", 2, 3, CardPosition.CLOSE_COMBAT_UNIT, new Muster(), Faction.MONSTERS, false),
    WYVERN("Wyvern", 2, 1, CardPosition.RANGED_UNIT, null, Faction.MONSTERS, false),
    GHOUL("Ghoul", 1, 3, CardPosition.CLOSE_COMBAT_UNIT, new Muster(), Faction.MONSTERS, false),
    BITING_FROST("Biting Frost", 0, 3, CardPosition.WEATHER, null, null, false),
    IMPENETRABLE_FOG("Impenetrable fog", 0, 3, CardPosition.WEATHER, null, null, false),
    TORRENTIAL_RAIN("Torrential Rain", 0, 3, CardPosition.WEATHER, null, null, false),
    CLEAR_WEATHER("Clear Weather", 0, 3, CardPosition.WEATHER, null, null, false),
    SCORCH("Scorch", 0, 3, CardPosition.SPELL, null, null, false),
    COMMANDERS_HORN("Commander’s horn", 0, 3, CardPosition.SPELL, new CommandersHorn(), null, false),
    DECOY("Decoy", 0, 3, CardPosition.SPELL, null, null, false),
    DANDELION("Dandelion", 2, 1, CardPosition.CLOSE_COMBAT_UNIT, new CommandersHorn(), null, false),
    EMIEL_REGIS("Emiel Regis", 5, 1, CardPosition.CLOSE_COMBAT_UNIT, null, null, false),
    GAUNTER_ODIMM("Gaunter O’Dimm", 2, 1, CardPosition.SIEGE_UNIT, new Muster(), null, false),
    GAUNTER_ODIMM_DARKNESS("Gaunter O’DImm Darkness", 4, 3, CardPosition.RANGED_UNIT, new Muster(), null, false),
    GERALT_OF_RIVIA("Geralt of Rivia", 15, 1, CardPosition.CLOSE_COMBAT_UNIT, null, null, true),
    MYSTERIOUS_ELF("Mysterious Elf", 0, 1, CardPosition.CLOSE_COMBAT_UNIT, new Spy(), null, false),
    OLGIERD_VON_EVERC("Olgierd Von Everc", 6, 1, CardPosition.AGILE_UNIT, new MoraleBoost(), null, false),
    TRISS_MERIGOLD("Triss Merigold", 7, 1, CardPosition.CLOSE_COMBAT_UNIT, null, null, true),
    VESEMIR("Vesemir", 6, 1, CardPosition.CLOSE_COMBAT_UNIT, null, null, false),
    VILLENTRETENMERTH("Villentretenmerth", 7, 1, CardPosition.CLOSE_COMBAT_UNIT, new Scorch(), null, false),
    YENNEFER_OF_VENGERBERG("Yennefer of Vengerberg", 7, 1, CardPosition.RANGED_UNIT, new Medic(), null, false),
    ZOLTAN_CHIVAY("Zoltan Chivay", 5, 1, CardPosition.CLOSE_COMBAT_UNIT, null, null, false),
    COW("Cow", 2, 1, CardPosition.RANGED_UNIT, new Transformers(), null, false);



    private String name;
    private int power, noOfCards;
    private CardPosition cardPosition;
    private Ability ability;
    private Faction faction;
    private boolean isHero;
    CardTypes(String name, int power, int noOfCards, CardPosition cardType, Ability ability, Faction faction,
                     boolean isHero) {
        this.name = name;
        this.power = power;
        this.noOfCards = noOfCards;
        this.cardPosition = cardType;
        this.ability = ability;
        this.faction = faction;
        this.isHero = isHero;
    }

    public static CardTypes getCardType(String name) {
        for (CardTypes cardType : CardTypes.values()) {
            if (cardType.name.equals(name)) {
                return cardType;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public int getNoOfCards() {
        return noOfCards;
    }

    public CardPosition getCardPosition() {
        return cardPosition;
    }

    public Ability getAbility() {
        return ability;
    }

    public Faction getFaction() {
        return faction;
    }

    public boolean isHero() {
        return isHero;
    }

    public Card getInstance() {
        return new Card(name, power, noOfCards, cardPosition, ability, faction, isHero);
    }
}
