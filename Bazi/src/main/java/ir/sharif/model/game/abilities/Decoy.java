package ir.sharif.model.game.abilities;

import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Row;
import ir.sharif.service.GameService;

public class Decoy implements Ability {
    @Override
    public void execute(Object... objs) {
        Row row = (Row) objs[0];
        Card card = (Card) objs[1];

        row.removeCard(card);
        GameService.getInstance().getMatchTable().getCurrentUserTable().addDeck(card);
    }
}
