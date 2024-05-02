package ir.sharif.controller;

import ir.sharif.model.CommandResult;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.MatchTable;
import ir.sharif.model.game.UserTable;

public class GameController {
    private MatchTable matchTable;

    public GameController(MatchTable matchTable) {
        this.matchTable = matchTable;
    }

    public CommandResult vetoCard(int cardNumber) {
        // Implement the logic for vetoing a card
        return null;
    }

    public CommandResult inHandDeck(int cardNumber) {
        // Implement the logic for handling in-hand deck
        return null;
    }

    public CommandResult remainingCardsToPlay() {
        // Implement the logic for remaining cards to play
        return null;
    }

    public CommandResult outOfPlayCards() {
        // Implement the logic for out of play cards
        return null;
    }

    public CommandResult cardsInRow(int rowNumber) {
        // Implement the logic for cards in a specific row
        return null;
    }

    public CommandResult spellsInPlay() {
        // Implement the logic for spells in play
        return null;
    }

    public CommandResult placeCard(int cardNumber, int rowNumber) {
        // Implement the logic for placing a card in a specific row
        return null;
    }

    public CommandResult showCommander() {
        // Implement the logic for showing the commander
        return null;
    }

    public CommandResult commanderPowerPlay() {
        // Implement the logic for playing the commander's power
        return null;
    }

    public CommandResult showPlayersInfo() {
        // Implement the logic for showing players' info
        return null;
    }

    public CommandResult showPlayersLives() {
        // Implement the logic for showing players' lives
        return null;
    }

    public CommandResult showNumberOfCardsInHand() {
        // Implement the logic for showing the number of cards in hand
        return null;
    }

    public CommandResult showTurnInfo() {
        // Implement the logic for showing turn info
        return null;
    }

    public CommandResult showTotalScore() {
        // Implement the logic for showing total score
        return null;
    }

    public CommandResult showTotalScoreOfRow(int rowNumber) {
        // Implement the logic for showing total score of a specific row
        return null;
    }

    public CommandResult passTurn() {
        // Implement the logic for passing the turn
        return null;
    }
}