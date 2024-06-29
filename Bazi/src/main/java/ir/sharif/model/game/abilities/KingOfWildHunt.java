package ir.sharif.model.game.abilities;

import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Row;
import ir.sharif.model.game.UserTable;
import ir.sharif.service.GameService;
import ir.sharif.utils.Random;
import ir.sharif.view.controllers.Game;

import java.util.ArrayList;


public class KingOfWildHunt implements Ability {
    @Override
    public void execute(Object... objs) {
        // TODO: not be random
        UserTable userTable = GameService.getInstance().getMatchTable().getCurrentUserTable();
        ArrayList<Card> nonHeroCards = new ArrayList<>();
        for (Card outOfPlay : userTable.getOutOfPlays()) {
            if(!outOfPlay.isHero()) nonHeroCards.add(outOfPlay);
        }
        if(!nonHeroCards.isEmpty()){
            Card card = Random.getRandFromArrayListCard(nonHeroCards);
            userTable.getOutOfPlays().remove(card);
            userTable.addHand(card);
        }
    }

}
