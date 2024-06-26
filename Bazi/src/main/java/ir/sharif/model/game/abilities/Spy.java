package ir.sharif.model.game.abilities;

import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Row;
import ir.sharif.model.game.UserTable;
import ir.sharif.service.GameService;
import ir.sharif.utils.Random;

public class Spy implements Ability {
    @Override
    public void execute(Object... objs) {
        UserTable userTable = GameService.getInstance().getMatchTable().getCurrentUserTable();
        for(int i = 0; i < 2 && !userTable.getDeck().isEmpty(); i++) {
            Card randomCard = userTable.getDeck().get(Random.getRandomInt(userTable.getDeck().size()));
            userTable.getDeck().remove(randomCard);
            userTable.getHand().add(randomCard);
        }
    }
}
