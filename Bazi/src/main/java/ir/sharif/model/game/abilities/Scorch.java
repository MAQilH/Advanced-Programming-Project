package ir.sharif.model.game.abilities;

import ir.sharif.controller.GameController;
import ir.sharif.model.game.*;
import ir.sharif.service.GameService;
import ir.sharif.view.controllers.Game;

import java.util.ArrayList;

public class Scorch implements Ability {
    @Override
    public void execute(Object... objs) {
        Card card = (Card) objs[0];
        UserTable opponentTable = GameService.getInstance().getMatchTable().getOpponentUserTable();
        GameController gameController = GameService.getInstance().getController();
        int opponentPlayer = GameService.getInstance().getMatchTable().getTurn() ^ 1;

        if(CardTypes.getCardType(card.getName()) == CardTypes.CLAN_DIMUN_PIRATE){
            int maxPower = 0;
            ArrayList<Row> opponentRows = opponentTable.getRows();
            for (int rowIndex = 0; rowIndex < opponentRows.size(); rowIndex++) {
                Row row = opponentRows.get(rowIndex);
                for(Card rowCard: row.getCards()){
                    if(rowCard.isHero()) continue;
                    if(rowCard.getPower() > maxPower) maxPower = gameController.calculatePower(opponentPlayer, rowIndex, rowCard);
                }
            }
            for (int rowIndex = 0; rowIndex < opponentRows.size(); rowIndex++) {
                Row row = opponentRows.get(rowIndex);
                for(Card rowCard: row.getCards()){
                    if(rowCard.isHero()) continue;
                    if(rowCard.getPower() == maxPower) {
                        row.removeCard(rowCard);
                        opponentTable.addOutOfPlay(rowCard);
                    }
                }
            }
        } else{
            Row row = gameController.getRowByPosition(opponentPlayer, card.getCardPosition());
             // TODO: after kian finish his part
        }
    }
}
