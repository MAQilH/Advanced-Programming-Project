package ir.sharif.model.game.abilities;

import ir.sharif.controller.GameController;
import ir.sharif.model.User;
import ir.sharif.model.game.*;
import ir.sharif.service.GameService;

import java.util.ArrayList;

public class Scorch implements Ability {
    @Override
    public void execute(Object... objs) {
        Card card = (Card) objs[0];
        UserTable opponentTable = GameService.getInstance().getMatchTable().getOpponentUserTable();
        UserTable currentPlayerTable = GameService.getInstance().getMatchTable().getCurrentUserTable();
        GameController gameController = GameService.getInstance().getController();
        int opponentPlayer = GameService.getInstance().getMatchTable().getTurn() ^ 1;
        if(CardTypes.getCardType(card.getName()) == CardTypes.CLAN_DIMUN_PIRATE ||
                card.getCardPosition() == CardPosition.SPELL) {
            int maxPower = 0;
            ArrayList<Row> opponentRows = opponentTable.getRows();
            ArrayList<Row> currentPlayerRows = currentPlayerTable.getRows();
            for (int rowIndex = 0; rowIndex < opponentRows.size(); rowIndex++) {
                Row row = opponentRows.get(rowIndex);
                for(Card rowCard: row.getCards()){
                    if(rowCard.isHero()) continue;
                    if(rowCard.calculatePower() > maxPower) maxPower = rowCard.calculatePower();
                }
                row = currentPlayerRows.get(rowIndex);
                for(Card rowCard: row.getCards()){
                    if(rowCard.isHero()) continue;
                    if(rowCard.calculatePower() > maxPower) maxPower = rowCard.calculatePower();
                }
            }
            for (int rowIndex = 0; rowIndex < opponentRows.size(); rowIndex++) {
                Row row = opponentRows.get(rowIndex);
                delete(row, maxPower, opponentTable);
                row = currentPlayerRows.get(rowIndex);
                delete(row, maxPower, currentPlayerTable);
            }
            return;
        }
        int rowNumber = switch (card.getCardPosition()) {
            case CardPosition.CLOSE_COMBAT_UNIT -> 0;
            case CardPosition.RANGED_UNIT -> 1;
            default -> 2;
        };
        Row row = gameController.getRowByPositionCurrentPlayer(opponentPlayer, card.getCardPosition());
        int rowPower = gameController.calculateNonHeroPower(opponentPlayer, rowNumber);
        if(rowPower >= 10){
            int maxPower = 0;
            for(Card rowCard: row.getCards()){
                if(rowCard.isHero()) continue;
                if(maxPower < rowCard.calculatePower())
                    maxPower = rowCard.calculatePower();
            }
            delete(row, maxPower, opponentTable);
        }
    }

    public void delete(Row row, int maxPower, UserTable opponentTable) {
        ArrayList<Card> toBeDeleted = new ArrayList<>();
        for(Card cards : row.getCards()) {
            if(cards.calculatePower() == maxPower && !cards.isHero()) {
                toBeDeleted.add(cards);
            }
        }
        for(Card toBeDeletedCards : toBeDeleted) {
            row.getCards().remove(toBeDeletedCards);
        }
    }
}
