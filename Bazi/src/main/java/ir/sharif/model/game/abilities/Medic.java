package ir.sharif.model.game.abilities;

import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Row;
import ir.sharif.model.game.UserTable;
import ir.sharif.service.GameService;
import ir.sharif.utils.Random;

public class Medic implements Ability {
    @Override
    public void execute(Object... objs) {
        UserTable userTable = GameService.getInstance().getMatchTable().getCurrentUserTable();
        if(!userTable.getOutOfPlays().isEmpty()){
            Card card = Random.getRandFromArrayListCard(userTable.getOutOfPlays());
            userTable.removeOutOfPlay(card);
            userTable.getHand().add(card);
        }
    }
}
