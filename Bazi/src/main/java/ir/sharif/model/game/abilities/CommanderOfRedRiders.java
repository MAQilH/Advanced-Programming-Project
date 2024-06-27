package ir.sharif.model.game.abilities;

import ir.sharif.model.game.*;
import ir.sharif.service.GameService;
import ir.sharif.utils.Random;
import ir.sharif.view.controllers.Game;

import java.util.ArrayList;
import java.util.Objects;

public class CommanderOfRedRiders implements Ability {

    @Override
    public void execute(Object... objs) {
        ArrayList<Card> weathers = new ArrayList<>();
        UserTable userTable = GameService.getInstance().getMatchTable().getCurrentUserTable();
        for(Card card: userTable.getDeck()){
            if(card.getCardPosition() == CardPosition.WEATHER){
                weathers.add(card);
            }
        }
        if(!weathers.isEmpty()){
            Card card = weathers.get(Random.getRandomInt(weathers.size()));
            GameService.getInstance().getController().forcePlaceCard(card, 12);
            userTable.removeDeck(card);
        }
    }
}
