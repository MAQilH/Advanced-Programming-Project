package ir.sharif.model.game.abilities;

import ir.sharif.model.game.*;
import ir.sharif.service.GameService;
import ir.sharif.view.controllers.Game;

import java.util.ArrayList;
import java.util.Collections;

public class CrachAnCraite implements Ability {
    @Override
    public void execute(Object... objs) {
        MatchTable matchTable = GameService.getInstance().getMatchTable();
        ArrayList<Card> allDeadCards = new ArrayList<>();
        ArrayList<Card> toBeReturnedCardsP0 = new ArrayList<>();
        ArrayList<Card> toBeReturnedCardsP1 = new ArrayList<>();
        allDeadCards.addAll(matchTable.getUserTable(0).getOutOfPlays());
        allDeadCards.addAll(matchTable.getUserTable(1).getOutOfPlays());
        Collections.shuffle(allDeadCards);
        for(int i = 0; i < allDeadCards.size(); i++) {
            if(i < allDeadCards.size() / 2) {
                toBeReturnedCardsP0.add(allDeadCards.get(i));
            } else {
                toBeReturnedCardsP1.add(allDeadCards.get(i));
            }
        }
        matchTable.getUserTable(0).getOutOfPlays().clear();
        matchTable.getUserTable(1).getOutOfPlays().clear();
        matchTable.getUserTable(0).getHand().addAll(toBeReturnedCardsP0);
        matchTable.getUserTable(1).getHand().addAll(toBeReturnedCardsP1);
    }

}
