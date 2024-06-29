package ir.sharif.model.game.abilities;

import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Row;

public class Mardroeme implements Ability {
    @Override
    public void execute(Object... objs) {
        Row row = (Row) objs[0];
        for(int i = row.getCards().size() - 1; i > -1; i--) {
            Card rowCard = row.getCards().get(i);
            if(rowCard.getAbility() instanceof Berserker){
                rowCard.getAbility().execute(row, rowCard);
            }
        }
    }
}
