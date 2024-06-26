package ir.sharif.model.game;

import ir.sharif.controller.GameController;
import ir.sharif.service.AppService;
import ir.sharif.service.GameService;

public class Card {
    private final String name;
    private final int power, noOfCards;
    private final CardPosition cardType;
    private final Ability ability;
    private final Faction faction;
    private final boolean isHero;

    public Card(String name, int power, int noOfCards, CardPosition cardType, Ability ability, Faction faction, boolean isHero) {
        this.name = name;
        this.power = power;
        this.noOfCards = noOfCards;
        this.cardType = cardType;
        this.ability = ability;
        this.faction = faction;
        this.isHero = isHero;
    }

	public String getName() {
		return name;
	}

	public int getPower() {
		return this.power;
	}

	public int getNoOfCards() {
		return noOfCards;
	}

	public CardPosition getCardType() {
		return cardType;
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

	public CardPosition getCardPosition() {
		return cardType;
	}

	public int findCardsPosition() {
		MatchTable matchTable = GameService.getInstance().getMatchTable();
		if(matchTable.getUserTable(1).getRowByNumber(2).getCards().contains(this)) {
			return 0;
		} if(matchTable.getUserTable(1).getRowByNumber(1).getCards().contains(this)) {
			return 1;
		} if(matchTable.getUserTable(1).getRowByNumber(0).getCards().contains(this)) {
			return 2;
		} if(matchTable.getUserTable(0).getRowByNumber(0).getCards().contains(this)) {
			return 3;
		} if(matchTable.getUserTable(0).getRowByNumber(1).getCards().contains(this)) {
			return 4;
		} if(matchTable.getUserTable(0).getRowByNumber(2).getCards().contains(this)) {
			return 5;
		} if(matchTable.getUserTable(1).getRowByNumber(2).getSpell() == this) {
			return 6;
		} if(matchTable.getUserTable(1).getRowByNumber(1).getSpell() == this) {
			return 7;
		} if(matchTable.getUserTable(1).getRowByNumber(0).getSpell() == this) {
			return 8;
		} if(matchTable.getUserTable(0).getRowByNumber(0).getSpell() == this) {
			return 9;
		} if(matchTable.getUserTable(0).getRowByNumber(1).getSpell() == this) {
			return 10;
		} if(matchTable.getUserTable(0).getRowByNumber(2).getSpell() == this) {
			return 11;
		} return -1;
	}

	public int calculatePower() {
		int pos = findCardsPosition();
		GameController gameController = GameService.getInstance().getController();
		return gameController.calculatePower(pos, this);
	}

}
