package ir.sharif.model.game.abilities;

import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Row;
import ir.sharif.model.game.UserTable;
import ir.sharif.service.GameService;
import ir.sharif.utils.Random;

public class InvaderOfTheNorth implements Ability {
    @Override
    public void execute(Object... objs) {
        addDeadCard(GameService.getInstance().getMatchTable().getCurrentUserTable());
        addDeadCard(GameService.getInstance().getMatchTable().getOpponentUserTable());
    }
    void addDeadCard(UserTable userTable){
        if(!userTable.getOutOfPlays().isEmpty()){
            Card card = Random.getRandFromArrayListCard(userTable.getOutOfPlays());
            userTable.getOutOfPlays().remove(card);
            userTable.addHand(card);
        }
    }
}
