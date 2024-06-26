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
        UserTable userTable = GameService.getInstance().getMatchTable().getCurrentUserTable();
        GameController gameController = GameService.getInstance().getController();

        ArrayList<Card> handClone = new ArrayList<>(userTable.getHand());

        for(Card handCard: handClone){
            if(handCard.getFaction() == card.getFaction() && handCard.getAbility() instanceof MoraleBoost){
                userTable.getHand().remove(handCard);

                Row insertedRow;
                if(handCard.getCardPosition() == CardPosition.AGILE_UNIT){
                    if(Random.getRandomInt(2) == 0) insertedRow = userTable.getRanged();
                    else insertedRow = userTable.getCloseCombat();
                } else insertedRow = gameController.getRowByPosition(handCard.getCardPosition());

                insertedRow.addCard(handCard);
            }
        }


        ArrayList<Card> deckClone = new ArrayList<>(userTable.getDeck());
        for(Card deckCard: deckClone){
            if(deckCard.getFaction() == card.getFaction() && deckCard.getAbility() instanceof MoraleBoost){
                userTable.getDeck().remove(deckCard);

                Row insertedRow;
                if(deckCard.getCardPosition() == CardPosition.AGILE_UNIT){
                    if(Random.getRandomInt(2) == 0) insertedRow = userTable.getRanged();
                    else insertedRow = userTable.getCloseCombat();
                } else insertedRow = gameController.getRowByPosition(deckCard.getCardPosition());

                insertedRow.addCard(deckCard);
            }
        }
    }
}
