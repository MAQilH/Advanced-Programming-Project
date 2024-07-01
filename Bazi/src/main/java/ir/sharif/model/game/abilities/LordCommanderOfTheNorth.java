package ir.sharif.model.game.abilities;

import ir.sharif.controller.GameController;
import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Row;
import ir.sharif.model.game.UserTable;
import ir.sharif.service.GameService;

import java.util.ArrayList;

public class LordCommanderOfTheNorth implements Ability {

    @Override
    public void execute(Object... objs) {
        UserTable opponentTable = GameService.getInstance().getMatchTable().getOpponentUserTable();
        int maxPower = 0, totalPower = 0;
        for(Card card: opponentTable.getSiege().getCards()){
            totalPower += card.calculatePower();
            if(card.isHero()) continue;
            maxPower = Math.max(maxPower, card.calculatePower());
        }
        if(totalPower <= 10) return;
        ArrayList<Card> toBeDeleted = new ArrayList<>();
        for(Card card: opponentTable.getSiege().getCards()){
            if(card.isHero()) continue;
            if(card.calculatePower() == maxPower){
                toBeDeleted.add(card);
            }
        }
        for(Card card : toBeDeleted) {
            opponentTable.getSiege().removeCard(card);
            opponentTable.addOutOfPlay(card);
        }
    }
}
