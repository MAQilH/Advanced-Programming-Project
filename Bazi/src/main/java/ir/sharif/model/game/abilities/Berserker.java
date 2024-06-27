package ir.sharif.model.game.abilities;

import ir.sharif.model.game.*;
import ir.sharif.service.GameService;

public class Berserker implements Ability {
    @Override
    public void execute(Object... objs) {
        Row row = (Row) objs[0];
        Card card = (Card) objs[1];
        //TODO: check if card has mardroeme in row then transform it to vidkaar
        UserTable userTable = GameService.getInstance().getMatchTable().getCurrentUserTable();
        row.removeCard(card);
        Card newCard = CardTypes.VIDKAARL.getInstance();
        row.addCard(newCard);
        newCard.getAbility().execute(row, newCard);
    }
}
