package ir.sharif.model.game;

import ir.sharif.controller.GameController;
import ir.sharif.service.AppService;
import ir.sharif.service.GameService;

import java.util.ArrayList;

public class Card {
    private final String name;
    private final int power, noOfCards;
    private final CardPosition cardPosition;
    private final Ability ability;
    private final Faction faction;
    private final boolean isHero;

    public Card(String name, int power, int noOfCards, CardPosition cardPosition, Ability ability, Faction faction, boolean isHero) {
        this.name = name;
        this.power = power;
        this.noOfCards = noOfCards;
        this.cardPosition = cardPosition;
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
		return cardPosition;
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

	public ArrayList<Integer> validPositions() {
		MatchTable matchTable = GameService.getInstance().getMatchTable();
		ArrayList<Integer> validPositions = new ArrayList<>();
		int player = matchTable.getTurn();
		if(this.cardPosition == CardPosition.WEATHER) {
			validPositions.add(12);
			return validPositions;
		}
		if(this.cardPosition == CardPosition.SPELL) {
			validPositions.add((1 - player) * 3 + 6);
			validPositions.add((1 - player) * 3 + 7);
			validPositions.add((1 - player) * 3 + 8);
		}
		if(this.cardPosition == CardPosition.RANGED_UNIT) {
			validPositions.add((1 - player) * 3 + 1);
		}
		if(this.cardPosition == CardPosition.CLOSE_COMBAT_UNIT) {
			validPositions.add(3 - player);
		}
		if(this.cardPosition == CardPosition.SIEGE_UNIT) {
			validPositions.add(5 - 5 * player);
		}
		if(this.cardPosition == CardPosition.AGILE_UNIT) {
			validPositions.add((1 - player) * 3 + 1);
			validPositions.add(3 - player);
		}
		return validPositions;
	}
}
