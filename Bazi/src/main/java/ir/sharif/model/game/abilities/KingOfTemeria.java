package ir.sharif.model.game.abilities;

import ir.sharif.controller.GameController;
import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.MatchTable;
import ir.sharif.model.game.Row;
import ir.sharif.service.GameService;
import ir.sharif.view.controllers.Game;

public class KingOfTemeria implements Ability {
    @Override
    public void execute(Object... objs) {
        MatchTable matchTable = GameService.getInstance().getMatchTable();
        matchTable.getCurrentUserTable().getLeader().setRoundOfAbilityUsed(
                matchTable.getRoundNumber()
        );
    }

}
