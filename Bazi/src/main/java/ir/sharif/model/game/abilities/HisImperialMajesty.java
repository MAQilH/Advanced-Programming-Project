package ir.sharif.model.game.abilities;

import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.MatchTable;
import ir.sharif.model.game.Row;
import ir.sharif.service.GameService;
import ir.sharif.utils.Random;

import java.util.ArrayList;

public class HisImperialMajesty implements Ability {

    @Override
    public void execute(Object... objs) {
        MatchTable matchTable = GameService.getInstance().getMatchTable();
        ArrayList<Card> toBeShown = new ArrayList<>();
        ArrayList<Card> cardsInHand = new ArrayList<>();
        cardsInHand.addAll(matchTable.getUserTable(matchTable.getTurn()).getHand());
        for(int i = 0; i < 3; i++) {
            if(cardsInHand.isEmpty()) break;
            Card card = Random.getRandFromArrayListCard(cardsInHand);
            cardsInHand.remove(card);
            toBeShown.add(card);
        }
        //TODO: call the graphic method to show this ArrayList
    }

}
