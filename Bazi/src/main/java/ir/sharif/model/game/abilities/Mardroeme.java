package ir.sharif.model.game.abilities;

import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Row;

public class Mardroeme implements Ability {
    @Override
    public void execute(Object... objs) {
        Row row = (Row) objs[0];

        for (Card rowCard : row.getCards()) {
            if(rowCard.getAbility() instanceof Berserker){
                rowCard.getAbility().execute(row, rowCard);
            }
        }
    }
}
