package ir.sharif.model.game.abilities;

import ir.sharif.model.game.*;
import ir.sharif.service.GameService;

public class TheSiegeMaster implements Ability {
    @Override
    public void execute(Object... objs) {
        UserTable userTable = GameService.getInstance().getMatchTable().getCurrentUserTable();
        for(Card card: userTable.getDeck()){
            if(CardTypes.getCardType(card.getName()) == CardTypes.IMPENETRABLE_FOG){
                userTable.removeDeck(card);
                GameService.getInstance().getController().placeCard(card, -1);
                return;
            }
        }
        for(Card card: userTable.getHand()){
            if(CardTypes.getCardType(card.getName()) == CardTypes.IMPENETRABLE_FOG){
                userTable.removeHand(card);
                GameService.getInstance().getController().placeCard(card, -1);
                return;
            }
        }
    }

}
