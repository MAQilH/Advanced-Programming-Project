package ir.sharif.model.game.abilities;

import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Row;
import ir.sharif.model.game.UserTable;
import ir.sharif.service.GameService;
import ir.sharif.utils.Random;

import java.util.ArrayList;

public class TheRelentless implements Ability {
    @Override
    public void execute(Object... objs) {
//         TODO: its most be peek from out of plays card
        UserTable opponentTable = GameService.getInstance().getMatchTable().getOpponentUserTable();
        UserTable userTable = GameService.getInstance().getMatchTable().getOpponentUserTable();
        ArrayList<Card> peekCandidate = new ArrayList<>();

        for (Card outOfPlayCard : opponentTable.getOutOfPlays()) {
            if(!outOfPlayCard.isHero()) peekCandidate.add(outOfPlayCard);
        }
        if(peekCandidate.isEmpty()) return;

        Card peekCard = peekCandidate.get(Random.getRandomInt(peekCandidate.size()));
        opponentTable.removeOutOfPlay(peekCard);
        userTable.addHand(peekCard);
    }
}
