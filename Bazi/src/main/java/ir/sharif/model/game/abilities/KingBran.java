package ir.sharif.model.game.abilities;

import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Row;
import ir.sharif.service.GameService;

public class KingBran implements Ability {
    @Override
    public void execute(Object... objs) {
        GameService.getInstance().getMatchTable().getCurrentUserTable().getLeader().setRoundOfAbilityUsed(
                GameService.getInstance().getMatchTable().getRoundNumber()
        );
    }

}
