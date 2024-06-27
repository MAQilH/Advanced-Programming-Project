package ir.sharif.model.game.abilities;

import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Row;

public class Transformers implements Ability {
    boolean converted = false;
    @Override
    public void execute(Object... objs) {
        converted = true;
        Card card = (Card) objs[0];
        card.setPower(8);
    }

    public boolean isConverted() {
        return converted;
    }
}
