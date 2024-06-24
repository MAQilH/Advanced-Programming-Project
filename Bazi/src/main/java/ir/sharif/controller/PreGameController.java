package ir.sharif.controller;

import ir.sharif.enums.Menus;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.User;
import ir.sharif.model.game.DeckInfo;
import ir.sharif.model.game.Faction;
import ir.sharif.service.AppService;
import ir.sharif.service.UserService;
import ir.sharif.view.terminal.Menu;

import java.nio.file.Path;

public class PreGameController {
    public CommandResult showCurrentMenu() {
        return new CommandResult(ResultCode.ACCEPT, "pre game");
    }

    public CommandResult menuEnter(Menus menu) {
        switch (menu){
            case Menus.GameMenu:
                AppService.getInstance().setCurrentMenu(Menus.GameMenu);
                return new CommandResult(ResultCode.ACCEPT, "enter game menu");
            case Menus.ExitMenu:
                AppService.getInstance().setCurrentMenu(Menus.MainMenu);
                return new CommandResult(ResultCode.ACCEPT, "enter main menu");
            default:
                return new CommandResult(ResultCode.FAILED, "you can access to this menu");
        }
    }

    public CommandResult createGame(String username) {
        User enemy = UserService.getInstance().getUserByUsername(username);
        if(enemy ==  null){
            return new CommandResult(ResultCode.NOT_FOUND, "user not found");
        }
        if(enemy == UserService.getInstance().getCurrentUser()){
            return new CommandResult(ResultCode.FAILED, "you can not play with yourself");
        }
        // TODO: create game
        return null;
	}

    public CommandResult showFactions() {
        return null;
    }

    public CommandResult selectFaction(String factionName) {
        Faction faction = Faction.findFaction(factionName);
        if(faction == null){
            return new CommandResult(ResultCode.NOT_FOUND, "faction not found");
        }
        DeckInfo deckInfo = UserService.getInstance().getCurrentUser().getDeckInfo();
        deckInfo.setFaction(faction);
        deckInfo.clearStorage();
        return new CommandResult(ResultCode.ACCEPT, "faction selected successfully");
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
