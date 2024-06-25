package ir.sharif.controller;

import ir.sharif.enums.Menus;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.User;
import ir.sharif.model.game.*;
import ir.sharif.service.AppService;
import ir.sharif.service.UserService;
import ir.sharif.utils.FileSaver;
import ir.sharif.view.terminal.Menu;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PreGameController {

    private User enemy;
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
        enemy = UserService.getInstance().getUserByUsername(username);
        if(enemy ==  null){
            return new CommandResult(ResultCode.NOT_FOUND, "user not found");
        }
        if(enemy == UserService.getInstance().getCurrentUser()){
            return new CommandResult(ResultCode.FAILED, "you can not play with yourself");
        }
        // TODO: create game
        return new CommandResult(ResultCode.ACCEPT, "game created successfully");
    }

    public CommandResult showFactions() {
        DeckInfo deckInfo = getDeck();
        if(deckInfo.getFaction() == null) {
            return new CommandResult(ResultCode.FAILED, "no faction already selected");
        }
        return new CommandResult(ResultCode.ACCEPT, deckInfo.getFaction().name());
    }

    public CommandResult selectFaction(String factionName) {
        Faction faction = Faction.findFaction(factionName);
        if(faction == null){
            return new CommandResult(ResultCode.NOT_FOUND, "faction not found");
        }
        DeckInfo deckInfo = getDeck();
        deckInfo.setFaction(faction);
        deckInfo.clearStorage();
        return new CommandResult(ResultCode.ACCEPT, "faction selected successfully");
    }

//    public CommandResult showCards() {
//        HashMap<Card, Integer> map = new HashMap<>();
//        DeckInfo deckInfo = getDeck();
//        for(CardTypes card: deckInfo.getStorage()){
//            map.put(card, map.getOrDefault(card, 0) + 1);
//        }
//        JSONArray jsonArray = new JSONArray();
//        for(Map.Entry<Card, Integer> entry: map.entrySet()){
//            JSONObject jsonObject = new JSONObject();
//            Card card = entry.getKey();
//            jsonObject.put("name", CardTypes.getCardType(card.getName()).name());
//            jsonObject.put("count", entry.getValue());
//            jsonArray.put(jsonObject);
//        }
//        return new CommandResult(ResultCode.ACCEPT, jsonArray.toString());
//    }

    public CommandResult showDeck() {
        return null;
    }

    public int numberOfSoldiers(DeckInfo deckInfo){
        int count = 0;
        for(CardTypes card: deckInfo.getStorage()){
            if(!card.isHero() && !isSpecialCard(card)){
                count++;
            }
        }
        return count;
    }

    public int numberOfSpecial(DeckInfo deckInfo){
        int count = 0;
        for(CardTypes card: deckInfo.getStorage()){
            if(!card.isHero() && isSpecialCard(card)){
                count++;
            }
        }
        return count;
    }

    public int numberOfHero(DeckInfo deckInfo){
        int count = 0;
        for(CardTypes card: deckInfo.getStorage()){
            if(card.isHero()){
                count++;
            }
        }
        return count;
    }

    public int getSumOfDeckPower(DeckInfo deckInfo){
        int sum = 0;
        for(CardTypes card: deckInfo.getStorage()){
            sum += card.getPower();
        }
        return sum;
    }

    private boolean isSpecialCard(CardTypes card){
        return card.getCardPosition() == CardPosition.WEATHER || card.getCardPosition() == CardPosition.SPELL;
    }

    public CommandResult showInfoCurrentUser() {
        DeckInfo deckInfo = getDeck();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", UserService.getInstance().getCurrentUser().getUsername());
        jsonObject.put("faction", deckInfo.getFaction().name());
        jsonObject.put("numberOfDeckCards", deckInfo.getStorage().size());
        jsonObject.put("numberOfSoldiers", numberOfSoldiers(deckInfo));
        jsonObject.put("numberOfSpecial", numberOfSpecial(deckInfo));
        jsonObject.put("numberOfHero", numberOfHero(deckInfo));
        jsonObject.put("powerSum", getSumOfDeckPower(deckInfo));
        return new CommandResult(ResultCode.ACCEPT, jsonObject.toString());
    }

    public CommandResult setDeck(DeckInfo deckInfo){
        User user = UserService.getInstance().getCurrentUser();
        user.setDeckInfo(deckInfo);
        return new CommandResult(ResultCode.ACCEPT, "deck set successfully");
    }

    public CommandResult saveDeck(String name) {
        name += ".deck";
        boolean result = FileSaver.saveObject(getDeck(), name);
        if(!result)
            return new CommandResult(ResultCode.FAILED, "error in saving deck");
        return new CommandResult(ResultCode.ACCEPT, "deck saved successfully");
    }

    public CommandResult saveDeck(Path path) {
        CommandResult commandResult = validateDeck(getDeck());
        if(commandResult.statusCode() == ResultCode.FAILED){
            return commandResult;
        }
        boolean result = FileSaver.saveObject(getDeck(), path);
        if(!result)
            return new CommandResult(ResultCode.FAILED, "error in saving deck");
        return new CommandResult(ResultCode.ACCEPT, "deck saved successfully");
    }

    public CommandResult loadDeck(String name) {
        name += ".deck";
        DeckInfo deckInfo;
        try{
            deckInfo = (DeckInfo) FileSaver.loadObject(name);
        } catch (Exception e){
            deckInfo = null;
        }
        if(deckInfo == null)
            return new CommandResult(ResultCode.FAILED, "error in loading deck");
        UserService.getInstance().getCurrentUser().setDeckInfo(deckInfo);
        return new CommandResult(ResultCode.ACCEPT, "deck loaded successfully");
    }

    public CommandResult loadDeck(Path path) {
        DeckInfo deckInfo = (DeckInfo) FileSaver.loadObject(path);
        try{
            deckInfo = (DeckInfo) FileSaver.loadObject(path);
        } catch (Exception e){
            deckInfo = null;
        }
        if(deckInfo == null)
            return new CommandResult(ResultCode.FAILED, "error in loading deck");
        UserService.getInstance().getCurrentUser().setDeckInfo(deckInfo);
        return new CommandResult(ResultCode.ACCEPT, "deck loaded successfully");
    }

    public CommandResult changeTurn() {
        User temp = UserService.getInstance().getCurrentUser();
        UserService.getInstance().setCurrentUser(enemy);
        enemy = temp;
        return new CommandResult(ResultCode.ACCEPT, "turn changed successfully");
    }

    public CommandResult validateDeck(DeckInfo deckInfo){
        if(numberOfSoldiers(deckInfo) < 22){
            return new CommandResult(ResultCode.FAILED, "you should have at least 22 soldiers in your deck");
        }
        if(numberOfSpecial(deckInfo) > 10){
            return new CommandResult(ResultCode.FAILED, "you should have at most 10 special cards in your deck");
        }
        return new CommandResult(ResultCode.ACCEPT, "deck is valid");
    }


    public CommandResult startGame() {
        CommandResult result = validateDeck(getDeck());
        if(result.statusCode() == ResultCode.FAILED){
            return result;
        }
        result = validateDeck(enemy.getDeckInfo());
        if(result.statusCode() == ResultCode.FAILED){
            return result;
        }
        return new CommandResult(ResultCode.ACCEPT, "game started successfully");
    }

    private DeckInfo getDeck(){
        return UserService.getInstance().getCurrentUser().getDeckInfo();
    }
}
