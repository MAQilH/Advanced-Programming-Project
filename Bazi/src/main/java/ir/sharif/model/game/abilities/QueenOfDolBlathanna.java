package ir.sharif.model.game.abilities;

import ir.sharif.model.game.Ability;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Row;
import ir.sharif.model.game.UserTable;
import ir.sharif.service.GameService;
import ir.sharif.utils.Random;

import java.util.ArrayList;

public class QueenOfDolBlathanna implements Ability {

    @Override
    public void execute(Object... objs) {
        UserTable opponentTable = GameService.getInstance().getMatchTable().getOpponentUserTable();

        int maxPowerNonHeroCloseCombatCard = 0;
        for (Card card : opponentTable.getCloseCombat().getCards()) {
            if(card.isHero()) continue;
            maxPowerNonHeroCloseCombatCard = Math.max(maxPowerNonHeroCloseCombatCard, card.calculatePower());
        }

        if(maxPowerNonHeroCloseCombatCard <= 10) return;

        int maxPowerNonHeroRangeCard = 0;
        for (Card card : opponentTable.getRanged().getCards()) {
            if(card.isHero()) continue;
            maxPowerNonHeroRangeCard = Math.max(maxPowerNonHeroRangeCard, card.calculatePower());
        }

        ArrayList<Card> removedCandidate = new ArrayList<>();
        for (Card card : opponentTable.getRanged().getCards()) {
            if(card.isHero()) continue;
            if(card.calculatePower() == maxPowerNonHeroRangeCard)
                removedCandidate.add(card);
        }

        if(removedCandidate.isEmpty()) return;

        Card card = Random.getRandFromArrayListCard(removedCandidate);
        opponentTable.getRanged().removeCard(card);
    }

}
