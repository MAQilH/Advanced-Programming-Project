package ir.sharif.model.game.abilities;

import ir.sharif.controller.GameController;
import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Row;
import ir.sharif.model.game.UserTable;
import ir.sharif.service.GameService;

public class LordCommanderOfTheNorth implements Ability {

    @Override
    public void execute(Object... objs) {
        UserTable opponentTable = GameService.getInstance().getMatchTable().getOpponentUserTable();
        GameController gameController = GameService.getInstance().getController();
        int opponentPlayerNumber = GameService.getInstance().getMatchTable().getTurn() ^ 1;
        int maxPower = 0;
        for(Card card: opponentTable.getSiege().getCards()){
            if(card.isHero()) continue;
            maxPower = Math.max(maxPower, gameController.calculatePower(opponentPlayerNumber, 2, card));
        }
        if(maxPower > 10){
            for(Card card: opponentTable.getSiege().getCards()){
                if(card.isHero()) continue;
                if(gameController.calculatePower(opponentPlayerNumber, 2, card) == maxPower){
                    opponentTable.getSiege().removeCard(card);
                    opponentTable.addOutOfPlay(card);
                    return;
                }
            }
        }
    }

}
