package ir.sharif.model.game.abilities;

import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Row;
import ir.sharif.service.GameService;
import ir.sharif.view.controllers.Game;

public class EmperorOfNilfGaard implements Ability {
    @Override
    public void execute(Object... objs) {
        GameService.getInstance().getMatchTable().getOpponentUserTable().getLeader().setDisableRound(
                GameService.getInstance().getMatchTable().getRoundNumber()
        );
    }
}
