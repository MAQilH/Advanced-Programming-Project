package ir.sharif.model.game.abilities;

import ir.sharif.model.game.*;
import ir.sharif.service.GameService;

import java.util.ArrayList;

public class HopeOfTheAenSeidhe implements Ability {
    @Override
    public void execute(Object... objs) {
        UserTable userTable = GameService.getInstance().getController().getCurrentUserTable();
        for(Row row: userTable.getRows()){
            ArrayList<Card> rowCardsClone = row.getCards();
            for(Card card: rowCardsClone){
                if(card.isHero()) continue;
                if(card.getCardPosition() == CardPosition.AGILE_UNIT){
                    row.removeCard(card);
                    userTable.getRanged().addCard(card);
                    int rangePower = card.calculatePower();
                    userTable.getRanged().removeCard(card);
                    userTable.getCloseCombat().addCard(card);
                    int closeCombatPower = card.calculatePower();
                    userTable.getCloseCombat().removeCard(card);
                    if(rangePower > closeCombatPower)
                        userTable.getRanged().addCard(card);
                    else
                        userTable.getCloseCombat().addCard(card);
                }
            }
        }
    }
}
