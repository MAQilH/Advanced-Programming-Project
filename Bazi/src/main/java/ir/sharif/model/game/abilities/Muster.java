package ir.sharif.model.game.abilities;

import ir.sharif.controller.GameController;
import ir.sharif.model.game.*;
import ir.sharif.service.GameService;
import ir.sharif.utils.Random;

import java.util.ArrayList;

public class Muster implements Ability {
    @Override
    public void execute(Object... objs) {
        Card card = (Card) objs[0];
        int rowNum = (int) objs[1];
        UserTable userTable = GameService.getInstance().getMatchTable().getCurrentUserTable();
        GameController gameController = GameService.getInstance().getController();
        for(int i = userTable.getHand().size() - 1; i > -1; i--){
            Card handCard = userTable.getHand().get(i);
            if(handCard == card) continue;
            if(handCard.getFaction() == card.getFaction() && handCard.getAbility() instanceof Muster){
                userTable.getHand().remove(i);
                Row insertedRow;
                if(handCard.getCardPosition() == CardPosition.AGILE_UNIT){
                    if(Random.getRandomInt(2) == 0) insertedRow = userTable.getRanged();
                    else insertedRow = userTable.getCloseCombat();
                }
                else insertedRow = gameController.getRowByPositionCurrentPlayer(handCard.getCardPosition());
                insertedRow.addCard(handCard);
            }
        }


        for(int i = userTable.getDeck().size() - 1; i > -1; i--) {
            Card deckCard = userTable.getDeck().get(i);
            if(deckCard.getFaction() == card.getFaction() && deckCard.getAbility() instanceof Muster){
                userTable.getDeck().remove(deckCard);
                Row insertedRow;
                if(deckCard.getCardPosition() == CardPosition.AGILE_UNIT){
                    if(Random.getRandomInt(2) == 0) insertedRow = userTable.getRanged();
                    else insertedRow = userTable.getCloseCombat();
                }
                else insertedRow = gameController.getRowByPositionCurrentPlayer(deckCard.getCardPosition());
                insertedRow.addCard(deckCard);
            }
        }
        userTable.getRowByNumber(rowNum).addCard(card);
    }
}
