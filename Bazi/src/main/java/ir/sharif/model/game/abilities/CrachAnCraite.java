package ir.sharif.model.game.abilities;

import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Row;
import ir.sharif.model.game.UserTable;
import ir.sharif.service.GameService;

import java.util.ArrayList;
import java.util.Collections;

public class CrachAnCraite implements Ability {
    @Override
    public void execute(Object... objs) {
        returnDeadCardToDeck(GameService.getInstance().getMatchTable().getCurrentUserTable());
        returnDeadCardToDeck(GameService.getInstance().getMatchTable().getOpponentUserTable());
    }

    private void returnDeadCardToDeck(UserTable userTable){
        ArrayList<Card> nonHeroOutOfPlayCards = new ArrayList<>();
        ArrayList<Card> outOfPlaysClone = userTable.getOutOfPlays();
        for (Card outOfPlayCard : outOfPlaysClone) {
            if(!outOfPlayCard.isHero()){
                nonHeroOutOfPlayCards.add(outOfPlayCard);
                userTable.removeOutOfPlay(outOfPlayCard);
            }
        }
        Collections.shuffle(nonHeroOutOfPlayCards);
        userTable.getDeck().addAll(nonHeroOutOfPlayCards);
    }
}
