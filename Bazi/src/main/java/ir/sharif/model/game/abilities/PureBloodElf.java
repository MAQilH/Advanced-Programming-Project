package ir.sharif.model.game.abilities;

import ir.sharif.model.game.*;
import ir.sharif.service.GameService;

public class PureBloodElf implements Ability {

    @Override
    public void execute(Object... objs) {
        UserTable userTable = GameService.getInstance().getMatchTable().getCurrentUserTable();
        for (Card card : userTable.getHand()) {
            if(CardTypes.getCardType(card.getName()) == CardTypes.BITING_FROST){
                userTable.getHand().remove(card);
                GameService.getInstance().getController().forcePlaceCard(card, 12);
                return;
            }
        }
    }

}
