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
        for(int cardIndex = 0; cardIndex < 2 && !userTable.getDeck().isEmpty(); cardIndex++) {
            Card randomCard = Random.getRandFromArrayListCard(userTable.getDeck());
            userTable.getDeck().remove(randomCard);
            userTable.getHand().add(randomCard);
        }
    }
}
