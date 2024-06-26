package ir.sharif.model.game.abilities;

import ir.sharif.model.game.*;
import ir.sharif.service.GameService;
import ir.sharif.view.controllers.Game;

public class TheWhiteFlame implements Ability {

    @Override
    public void execute(Object... objs) {
        UserTable userTable = GameService.getInstance().getMatchTable().getCurrentUserTable();
        for (Card card : userTable.getHand()) {
            if(CardTypes.getCardType(card.getName()) == CardTypes.TORRENTIAL_RAIN){
                userTable.getHand().remove(card);
//                GameService.getInstance().getController().placeWeatherCard(card, Row.ENEMY_MELEE, false);
            }
        }
    }

}
