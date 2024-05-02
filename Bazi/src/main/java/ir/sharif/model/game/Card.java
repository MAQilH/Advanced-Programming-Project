package ir.sharif.model.game;

public class Card {
    private String name;
    private int power, noOfCards;
    private CardPosition cardType;
    private Ability ability;
    private Faction faction;
    private boolean isHero;

    public Card(String name, int power, int noOfCards, CardPosition cardType, Ability ability, Faction faction, boolean isHero) {
        this.name = name;
        this.power = power;
        this.noOfCards = noOfCards;
        this.cardType = cardType;
        this.ability = ability;
        this.faction = faction;
        this.isHero = isHero;
    }
}
