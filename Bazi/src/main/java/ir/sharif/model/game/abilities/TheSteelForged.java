package ir.sharif.model.game.abilities;

import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Row;
import ir.sharif.service.GameService;
import ir.sharif.view.controllers.Game;

public class TheSteelForged implements Ability {
    @Override
    public void execute(Object... objs) {
        GameService.getInstance().getMatchTable().getWeatherCards().clear();
    }

}
