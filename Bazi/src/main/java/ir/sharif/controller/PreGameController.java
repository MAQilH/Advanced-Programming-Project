package ir.sharif.controller;

import ir.sharif.model.CommandResult;
import ir.sharif.view.terminal.Menu;

import java.nio.file.Path;

public class PreGameController {
    public CommandResult showCurrentMenu() {
        return null;
    }

    public CommandResult menuEnter(Menu menu) {
        return null;
    }

    public CommandResult menuExit() {
        return null;
    }

    public CommandResult createGame(String username) {
        return null;
    }

    public CommandResult showFactions() {
        return null;
    }

    public CommandResult selectFaction(String faction) {
        return null;
    }

    public CommandResult showCards() {
        return null;
    }

    public CommandResult showDeck() {
        return null;
    }

    public CommandResult showInfoCurrentUser() {
        return null;
    }

    public CommandResult saveDeck(String name) {
        return null;
    }

    public CommandResult saveDeck(Path path) {
        return null;
    }

    public CommandResult loadDeck(String name) {
        return null;
    }

    public CommandResult loadDeck(Path path) {
        return null;
    }

    public CommandResult showLeaders() {
        return null;
    }

    public CommandResult selectLeader(int leaderNumber) {
        return null;
    }

    public CommandResult addToDeck(String cardName, int count) {
        return null;
    }

    public CommandResult removeFromDeck(String cardName, int count) {
        return null;
    }

    public CommandResult changeTurn() {
        return null;
    }

    public CommandResult startGame() {
        return null;
    }
}
