package ir.sharif.controller;

import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.User;
import ir.sharif.model.game.*;
import ir.sharif.model.game.abilities.Spy;
import ir.sharif.model.game.abilities.Transformers;
import ir.sharif.service.GameService;
import ir.sharif.utils.ConstantsLoader;
import ir.sharif.utils.Random;
import javafx.beans.binding.StringBinding;
import java.util.ArrayList;

public class GameController {
    private final MatchTable matchTable;

    public GameController() {
        this.matchTable = GameService.getInstance().getMatchTable();
        startGame();
    }

    public CardPosition getCardPositionByRowNumber(int rowNumber) {
        return switch (rowNumber) {
            case 0 -> CardPosition.CLOSE_COMBAT_UNIT;
            case 1 -> CardPosition.RANGED_UNIT;
            case 2 -> CardPosition.SIEGE_UNIT;
            case -1 -> CardPosition.WEATHER;
            default -> null;
        };
    }

    public Row getRowByPosition(int player, CardPosition cardPosition) {
        return switch (cardPosition) {
            case CLOSE_COMBAT_UNIT -> matchTable.getUserTable(player).getCloseCombat();
            case RANGED_UNIT -> matchTable.getUserTable(player).getRanged();
            case SIEGE_UNIT -> matchTable.getUserTable(player).getSiege();
            default -> null;
        };
    }

    public int weatherCardsOnTable() {
        int answer = 0;
        for(Card card : matchTable.getWeatherCards()) {
            if(card.getName().equals("Torrential rain")) {
                answer |= 4;
            }
            if(card.getName().equals("Impenetrable fog")) {
                answer |= 2;
            }
            if(card.getName().equals("Biting frost")) {
                answer |= 1;
            }
        }
        return answer;
    }

    public int calculatePower(int player, int rowNumber, Card card) {
        double cofficient = 1;
        int weather = weatherCardsOnTable();
        if(card.isHero()) {
            return card.getPower();
        }
        if(((1 << rowNumber) & weather) != 0) {
            if(matchTable.getUserTable(player).getLeader().getName().equals("King Bran")) {
                //TODO: complete it
            }
        }

        return (int)(cofficient * card.getPower());
    }

    public CommandResult vetoCard(int cardNumber) {
        int player = matchTable.getTurn();
        if(matchTable.getUserTable(player).getVetoesLeft() == 0) {
            return new CommandResult(ResultCode.FAILED, "You don't have any vetoes left");
        }
        if(matchTable.getUserTable(player).getDeck().isEmpty())
            return new CommandResult(ResultCode.FAILED, "deck is empty");
        Card card = matchTable.getUserTable(player).getHand().get(cardNumber);
        matchTable.getUserTable(player).getHand().remove(cardNumber);
        matchTable.getUserTable(player).getDeck().add(card);
        int randomNumber = Random.getRandomInt(matchTable.getUserTable(player).getDeck().size());
        Card randomCard = matchTable.getUserTable(player).getDeck().get(randomNumber);
        matchTable.getUserTable(player).getDeck().remove(randomNumber);
        matchTable.getUserTable(player).getHand().add(randomCard);
        matchTable.getUserTable(player).decreaseVetoesLeft();
        return new CommandResult(ResultCode.ACCEPT, "Card vetoed successfully");
    }

    public CommandResult inHandDeck(int cardNumber) {
        // Implement the logic for handling in-hand deck
        return null;
    }

    public CommandResult remainingCardsToPlay() {
        int player = matchTable.getTurn();
        return new CommandResult(ResultCode.ACCEPT, "Remaining cards to play: " + matchTable.getUserTable(player).getHand().size());
    }

    public CommandResult outOfPlayCards() {
        // Implement the logic for out of play cards
        return null;
    }

    public CommandResult cardsInRow(int rowNumber) {
        // Implement the logic for cards in a specific row
        return null;
    }

    public CommandResult spellsInPlay() {
        // Implement the logic for spells in play
        return null;
    }

    public CommandResult placeCard(int cardNumber, int rowNumber) {
        //TODO: do the abilities when they are placed
        if(matchTable.getUserTable(matchTable.getTurn()).getHand().size() <= cardNumber) {
            return new CommandResult(ResultCode.FAILED, "Invalid card number");
        }
        if(getCardPositionByRowNumber(rowNumber) == null) {
            return new CommandResult(ResultCode.FAILED, "Invalid row number");
        }
        int player = matchTable.getTurn();
        CardPosition cardPosition = getCardPositionByRowNumber(rowNumber);
        Card card = matchTable.getUserTable(player).getHand().get(cardNumber);
        matchTable.getUserTable(player).getHand().remove(cardNumber);
        if(cardPosition == CardPosition.WEATHER) {
            return placeWeatherCard(card);
        }
        if(cardPosition == CardPosition.SPELL) {
            return placeSpellCard(card, rowNumber);
        }
        if(card.getAbility() instanceof Spy) {
            return placeSpyCard(card, rowNumber);
        }
        return placeUnitCard(card, rowNumber);
    }

    public CommandResult placeWeatherCard(Card card) {
        matchTable.addWeatherCard(card);
        return new CommandResult(ResultCode.ACCEPT, "Weather card placed successfully");
        //done here
    }

    public CommandResult placeSpellCard(Card card, int rowNumber) {
        //TODO: execute what is does(maybe it is not needed)
        int player = matchTable.getTurn();
        matchTable.getUserTable(player).getRowByNumber(rowNumber).setSpell(card);
        return new CommandResult(ResultCode.ACCEPT, "Spell card placed successfully");
        //done here
    }

    public CommandResult placeSpyCard(Card card, int rowNumber) {
        //TODO: I can do its action by executing the ability
        int player = 1 - matchTable.getTurn();
        matchTable.getUserTable(player).getRowByNumber(rowNumber).addCard(card);
        player = 1 - player;
        for(int i = 0; i < 2 && !matchTable.getUserTable(player).getDeck().isEmpty(); i++) {
            int randomNumber = Random.getRandomInt(matchTable.getUserTable(player).getDeck().size());
            Card randomCard = matchTable.getUserTable(player).getDeck().get(randomNumber);
            matchTable.getUserTable(player).getDeck().remove(randomNumber);
            matchTable.getUserTable(player).getHand().add(randomCard);
        }
        return new CommandResult(ResultCode.ACCEPT, "Spy card placed successfully");
        //done here
    }

    public CommandResult placeUnitCard(Card card, int rowNumber) {
        //TODO: execute ability
        int player = matchTable.getTurn();
        Row row = matchTable.getUserTable(player).getRowByNumber(rowNumber);
        row.addCard(card);
        return new CommandResult(ResultCode.ACCEPT, "Unit card placed successfully");
        //done here
    }

    public CommandResult showCommander() {
        // Implement the logic for showing the commander
        return null;
    }

    public CommandResult commanderPowerPlay() {
        // Implement the logic for playing the commander's power
        return null;
    }

    public CommandResult showPlayersInfo() {
        String playersInfo = matchTable.getUser(0).getUsername() +
                " ~ Fraction: " +
                matchTable.getUserTable(0).getLeader().getFaction().name() +
                "\n" +
                matchTable.getUser(1).getUsername() +
                " ~ Fraction: " +
                matchTable.getUserTable(1).getLeader().getFaction().name();
        return new CommandResult(ResultCode.ACCEPT, playersInfo);
    }

    public CommandResult showPlayersLives() {
        // Implement the logic for showing players' lives
        String playersLives = matchTable.getUser(0).getUsername() +
                " ~ Lives: " +
                matchTable.getUserTable(0).getLife() +
                "\n" +
                matchTable.getUser(1).getUsername() +
                " ~ Lives: " +
                matchTable.getUserTable(1).getLife();
        return null;
    }

    public CommandResult showNumberOfCardsInHand() {
        // Implement the logic for showing the number of cards in hand
        int numberOfCardsInHand = matchTable.getUserTable(matchTable.getTurn()).getHand().size();
        int numberofCardsInOpponentHand = matchTable.getUserTable(1 - matchTable.getTurn()).getHand().size();
        return new CommandResult(ResultCode.ACCEPT, "Number of cards in hand: "
                + numberOfCardsInHand + "\nNumber of cards in opponent's hand: " + numberofCardsInOpponentHand);
    }

    public CommandResult showTurnInfo() {
        // Implement the logic for showing turn info
        String response = "It's " + matchTable.getUser(matchTable.getTurn()).getUsername() + "'s turn";
        return new CommandResult(ResultCode.ACCEPT, response);
    }

    public int getScoreOfRow(int player, int rowNumber) {
        Row row = getRowByPosition(player, getCardPositionByRowNumber(rowNumber));
        int score = 0;
        for(Card card : row.getCards()) {
            score += calculatePower(player, rowNumber, card);
        }
        return score;
    }

    public CommandResult showTotalScore() {
        String response = "Total score of " + matchTable.getUser(0).getUsername() + ": " +
                (getScoreOfRow(0, 0) + getScoreOfRow(0, 1) +
                        getScoreOfRow(0, 2)) +
                "\n" +
                "Total score of " + matchTable.getUser(1).getUsername() + ": " +
                (getScoreOfRow(1, 0) + getScoreOfRow(1, 1) +
                        getScoreOfRow(1, 2));
        return new CommandResult(ResultCode.ACCEPT, response);
    }

    public CommandResult showTotalScoreOfRow(int rowNumber) {
        String response = "Total score of row " + rowNumber + " of " + matchTable.getUser(0).getUsername() + ": " +
                getScoreOfRow(0, rowNumber) +
                "\n" +
                "Total score of row " + rowNumber + " of " + matchTable.getUser(1).getUsername() + ": " +
                getScoreOfRow(1, rowNumber);
        return new CommandResult(ResultCode.ACCEPT, response);
    }

    public CommandResult passTurn() {

        matchTable.changeTurn();
        if(matchTable.isPreviousRoundPassed()){
            finishRound();
            return new CommandResult(ResultCode.ACCEPT, "Round finished successfully");
        }
        matchTable.setPreviousRoundPassed(true);
        return new CommandResult(ResultCode.ACCEPT, "Turn passed successfully");
    }

    private void finishRound(){
        int winner = getRoundWinner();
        if(winner != 0) matchTable.getUserTable(0).decreaseLife();
        if(winner != 1) matchTable.getUserTable(1).decreaseLife();
        if(matchTable.getUserTable(0).getLife() == 0 || matchTable.getUserTable(1).getLife() == 0){
            finishGame();
            return;
        }
        matchTable.changeRound();
        startRound(winner);
    }

    private void startRound(int winner){
        // TODO: call graphical controller for this changes
        if(matchTable.getRoundNumber() == 0){
            ArrayList<Integer> scoiataelUsers = new ArrayList<>();
            for (int userIndex = 0; userIndex < 2; userIndex++) {
                if(matchTable.getUserTable(userIndex).getFaction() == Faction.SCOIATAEL) scoiataelUsers.add(userIndex);
            }
            if(scoiataelUsers.isEmpty() || scoiataelUsers.size() == 2){
                matchTable.setTurn(Random.getRandomInt(2));
            } else {
                matchTable.setTurn(scoiataelUsers.get(0));
            }
        }

        for (int userIndex = 0; userIndex < 2; userIndex++) {
            UserTable userTable = matchTable.getUserTable(userIndex);
            Card heroRemain = null;
            if(userTable.getFaction() == Faction.MONSTERS) {
                ArrayList<Card> heroCards = userTable.getHeroesCard();
                if(!heroCards.isEmpty()) heroRemain = heroCards.get(Random.getRandomInt(heroCards.size()));
            }

            for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
                Row row = userTable.getRowByNumber(rowIndex);
                ArrayList<Card> rowCards = new ArrayList<>(row.getCards());
                for (Card card : rowCards) {
                    boolean canDelete = true;
                    if(card == heroRemain) canDelete = false;
                    if(card.getAbility() instanceof Transformers){
                        Transformers transformers = (Transformers) card.getAbility();
                        if(!transformers.isConverted()) {
                            canDelete = false;
                            transformers.execute();
                        }
                    }
                    if(canDelete){
                        userTable.addOutOfPlay(card);
                        row.removeCard(card);
                    }
                }
                if(row.getSpell() != null) {
                    userTable.addOutOfPlay(row.getSpell());
                    row.setSpell(null);
                }
            }
            if(userTable.getFaction() == Faction.NORTHEN_REALMS && winner == userIndex){
                Card newAddedCard = null;
                if(!userTable.getDeck().isEmpty())
                    newAddedCard = userTable.getDeck().get(Random.getRandomInt(userTable.getDeck().size()));
                if(newAddedCard != null){
                    userTable.getDeck().remove(newAddedCard);
                    userTable.addHand(newAddedCard);
                }
            }

            if(userTable.getFaction() == Faction.SKELLIGE && matchTable.getRoundNumber() == 2){
                for (int addedCardIndex = 0; addedCardIndex < 2; addedCardIndex++) {
                    Card newAddedCard = null;
                    if(!userTable.getOutOfPlays().isEmpty())
                        newAddedCard = userTable.getOutOfPlays().get(Random.getRandomInt(userTable.getOutOfPlays().size()));
                    if(newAddedCard != null){
                        userTable.getOutOfPlays().remove(newAddedCard);
                        userTable.addHand(newAddedCard);
                    }
                }
            }
        }
    }

    private void startGame(){
        startRound(-1);

        for (int playerIndex = 0; playerIndex < 2; playerIndex++) {
            int startNumberOfCard = Integer.parseInt(ConstantsLoader.getInstance().getProperty("game.start_number_card"));
            for(int cardIndex = 0; cardIndex < startNumberOfCard; cardIndex++){
                if(matchTable.getUserTable(playerIndex).getDeck().isEmpty()) break;
                int randomNumber = Random.getRandomInt(matchTable.getUserTable(playerIndex).getDeck().size());
                Card randomCard = matchTable.getUserTable(playerIndex).getDeck().get(randomNumber);
                matchTable.getUserTable(playerIndex).getDeck().remove(randomNumber);
                matchTable.getUserTable(playerIndex).getHand().add(randomCard);
            }
        }
    }

    private void finishGame(){
        int gameWinner;
        if(matchTable.getUserTable(0).getLife() == matchTable.getUserTable(1).getLife()) gameWinner = -1;
        else if(matchTable.getUserTable(0).getLife() > matchTable.getUserTable(1).getLife()) gameWinner = 0;
        else gameWinner = 1;

        // TODO: return winner to the graphical controller
    }

    private int getRoundWinner(){
        int firstUserPower = matchTable.getUserTable(0).getPower();
        int secondUserPower = matchTable.getUserTable(1).getPower();
        if(firstUserPower > secondUserPower) return firstUserPower;
        if(secondUserPower > firstUserPower) return secondUserPower;
        Faction firstUserFaction = matchTable.getUserTable(0).getFaction();
        Faction secondUserFaction = matchTable.getUserTable(1).getFaction();
        if(firstUserFaction == secondUserFaction) return -1;
        if(firstUserFaction == Faction.NILFGAARDIAN_EMPIRE) return 0;
        if(secondUserFaction == Faction.NILFGAARDIAN_EMPIRE) return 1;
        return -1;
    }

    public UserTable getCurrentUserTable() {
        return matchTable.getUserTable(matchTable.getTurn() ^ 1);
    }

	public UserTable getOpponentUserTable() {
		return matchTable.getUserTable(matchTable.getTurn());
	}

    public UserTable getUserUserTable(int playerNumber) {
        return matchTable.getUserTable(playerNumber);
    }
}