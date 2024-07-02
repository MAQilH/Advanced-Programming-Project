package ir.sharif.controller;

import ir.sharif.client.TCPClient;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.GameHistory;
import ir.sharif.model.GameState;
import ir.sharif.model.User;
import ir.sharif.model.game.*;
import ir.sharif.model.game.abilities.*;
import ir.sharif.service.GameService;
import ir.sharif.service.UserService;
import ir.sharif.utils.ConstantsLoader;
import ir.sharif.utils.Random;
import ir.sharif.view.GameGraphics;
import ir.sharif.view.Regex;
import ir.sharif.view.controllers.Game;
import javafx.css.Match;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameController {
    private final MatchTable matchTable;

    private final GameState gameState;

	public GameState getGameState() {
		return gameState;
	}


	public boolean isOnline() {
		return gameState == GameState.ONLINE_OBSERVER || gameState == GameState.ONLINE_PLAYER;
	}

	public int getOnlineCurrentUser() {
		return UserService.getInstance().getCurrentUser().getUsername()
			.equals(getMatchTable().getUser(0).getUsername()) ? 0 : 1;
	}

    public GameController(GameState gameState) {
        this.matchTable = GameService.getInstance().getMatchTable();
        startGame();
        this.gameState = gameState;
        if(gameState != GameState.OFFLINE_PLAYER){
            runOnlineMode();
        }
    }

    private void runOnlineMode() {
        if(gameState == GameState.ONLINE_OBSERVER || gameState == GameState.ONLINE_PLAYER){
            if(gameState == GameState.ONLINE_OBSERVER) GameService.getInstance().setActionLock(true);
            Thread thread = new Thread(() -> {
                while (true){
                    ArrayList<String> newAction = GameService.getInstance().getNewActions();
                    GameService.getInstance().setActionLock(true);
                    for (String action : newAction) {
                        runne(action);
                    }
                    if(gameState != GameState.ONLINE_OBSERVER) GameService.getInstance().setActionLock(false);
                    GameService.getInstance().increaseBufferReading(newAction.size());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.start();
        }
    }

	public void offlineRunner(){
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				ArrayList<String> newAction = GameService.getInstance().getNewActions();
				GameService.getInstance().setActionLock(true);
				for (String action : newAction) {
					System.err.println("khar----" + action);
					runne(action);
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
				GameService.getInstance().increaseBufferReading(newAction.size());
			}
		});

		thread.start();
	}

    public void runne(String action){
		CommandResult result = null;
        Matcher matcher;
        if((matcher = Regex.VETO_CARD.getMatcher(action)).matches()){
            String username = matcher.group("username");
            if(gameState != GameState.OFFLINE_OBSERVER && username.equals(UserService.getInstance().getCurrentUser().getUsername())) return;
            result = vetoCard(Integer.parseInt(matcher.group("number")));
        } else if((matcher = Regex.PASS_TURN.getMatcher(action)).matches()){
            String username = matcher.group("username");
            if(gameState != GameState.OFFLINE_OBSERVER && username.equals(UserService.getInstance().getCurrentUser().getUsername())) return;
            result = passTurn();
        } else if((matcher = Regex.EXECUTE_LEADER.getMatcher(action)).matches()){
            String username = matcher.group("username");
            if(gameState != GameState.OFFLINE_OBSERVER && username.equals(UserService.getInstance().getCurrentUser().getUsername())) return;
            result = commanderPowerPlay();
        } else if((matcher = Regex.PLACE_CARD.getMatcher(action)).matches()){
            String username = matcher.group("username");
            if(gameState != GameState.OFFLINE_OBSERVER && username.equals(UserService.getInstance().getCurrentUser().getUsername())) return;
            int index = Integer.parseInt(matcher.group("index"));
            int pos = Integer.parseInt(matcher.group("pos"));
            Card card = GameService.getInstance().getMatchTable().getCurrentUserTable().getHand().get(index);
            result = placeCard(card, pos);
        }

	    System.err.println("khaar2: " + action);
	    System.err.println("Result: " + result.message());
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

    public int getRowNumberByCardPosition(CardPosition cardPosition) {
        return switch (cardPosition) {
            case CLOSE_COMBAT_UNIT -> 0;
            case RANGED_UNIT -> 1;
            case SIEGE_UNIT -> 2;
            default -> -1;
        };
    }

    public Row getRowByPositionCurrentPlayer(int player, CardPosition cardPosition) {
        return switch (cardPosition) {
            case CLOSE_COMBAT_UNIT -> matchTable.getUserTable(player).getCloseCombat();
            case RANGED_UNIT -> matchTable.getUserTable(player).getRanged();
            case SIEGE_UNIT -> matchTable.getUserTable(player).getSiege();
            default -> null;
        };
    }

    public int graphicRowToLogicRow(int rowNumber) {
        return switch (rowNumber) {
            case 0, 5, 6, 11 -> 2;
            case 1, 4, 7, 10 -> 1;
            case 2, 3, 8, 9 -> 0;
            case 12 -> -1;
            default -> -100;
        };
    }
    //    p1
    // 6|   0
    // 7|   1
    // 8|   2
    // 9|   3
    //10|   4
    //11|   5
    //   p0

    public int getPlayerByPos(int pos) {
        if(0 <= pos && pos < 3) return 1;
        if(3 <= pos && pos < 6) return 0;
        if(6 <= pos && pos < 9) return 1;
        if(9 <= pos && pos < 12) return 0;
        return -1;
    }

    public int getRealRowNumber(int player, int row){
        if(player == 0){
            return 2 - row;
        } else{
            return row - 3;
        }
    }

    public Row getRowByPositionCurrentPlayer(CardPosition cardPosition) {
        return getRowByPositionCurrentPlayer(matchTable.getTurn(), cardPosition);
    }

    public int weatherCardsOnTable() {
        int answer = 0;
        for(Card card : matchTable.getWeatherCards()) {
            if(card.getName().equals("Torrential Rain")) {
                answer |= 4;
            }
            if(card.getName().equals("Impenetrable fog")) {
                answer |= 2;
            }
            if(card.getName().equals("Biting Frost")) {
                answer |= 1;
            }
            if(card.getName().equals("Skellige Storm")) {
                answer |= 6;
            }
        }
        return answer;
    }

    public int calculatePower(int pos, Card card) {
        if(card.getCardPosition() == CardPosition.SPELL || card.getCardPosition() == CardPosition.WEATHER) {
            return 0;
        }
        if(pos < 0 || pos > 12) {
            return card.getPower();
        }
        int player = getPlayerByPos(pos);//pos: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
        int rowNumber = graphicRowToLogicRow(pos);//rowNum: 0, 1, 2, -1
        double cofficient = 1;
        int weather = weatherCardsOnTable(), counter2x = 0, constant = 0;
        Row row = getRowByPositionCurrentPlayer(player, getCardPositionByRowNumber(rowNumber));
        if(card.isHero()) {
            return card.getPower();
        }

        if (((1 << rowNumber) & weather) != 0) {
            if(LeaderType.getLeaderType(matchTable.getUserTable(player).getLeader().getName())
                    == LeaderType.KING_BRAN && matchTable.getUserTable(player).getLeader().
                        getRoundOfAbilityUsed() == matchTable.getRoundNumber()) {
                cofficient *= 0.5;
            }
            else if(LeaderType.getLeaderType(matchTable.getUserTable(player).getLeader().getName())
                    == LeaderType.THE_STEEL_FORGED && matchTable.getUserTable(player).getLeader().
                            getRoundOfAbilityUsed() == matchTable.getRoundNumber()) {
                cofficient *= 1;
            }
            else {
                return 1;
            }
        }

        if (row.getSpell() != null && row.getSpell().getAbility() instanceof CommandersHorn) {
            counter2x++;
        }

        for (Card card1 : row.getCards()) {
            if(card1.getAbility() instanceof CommandersHorn) {
                counter2x++;
            }
        }
        if(rowNumber == 2 && matchTable.getUserTable(player).getLeader().
                getRoundOfAbilityUsed() == matchTable.getRoundNumber()) {
            if(LeaderType.getLeaderType(matchTable.getUserTable(player).getLeader().getName())
                    == LeaderType.KING_OF_TEMERIA) {
                counter2x++;
            }
        }
        if(LeaderType.getLeaderType(matchTable.getUserTable(player).getLeader().getName())
                == LeaderType.BRINGER_OF_DEATH) {
            if(rowNumber == 0 && matchTable.getUserTable(player).getLeader().
                    getRoundOfAbilityUsed() == matchTable.getRoundNumber()) {
                counter2x++;
            }
        }
        if(LeaderType.getLeaderType(matchTable.getUserTable(player).getLeader().getName())
                == LeaderType.THE_BEAUTIFUL) {
            if(rowNumber == 1 && matchTable.getUserTable(player).getLeader().
                    getRoundOfAbilityUsed() == matchTable.getRoundNumber()) {
                counter2x++;
            }
        }
        if(LeaderType.getLeaderType(matchTable.getUserTable(player).getLeader().getName())
                == LeaderType.THE_TREACHEROUS) {
            if(card.getAbility() instanceof Spy && matchTable.getUserTable(player).getLeader().
                    getRoundOfAbilityUsed() == matchTable.getRoundNumber()) {
                counter2x++;
            }
        }
        if(LeaderType.getLeaderType(matchTable.getUserTable(1 - player).getLeader().getName())
                == LeaderType.THE_TREACHEROUS) {
            if(card.getAbility() instanceof Spy && matchTable.getUserTable(1 - player).getLeader().
                    getRoundOfAbilityUsed() == matchTable.getRoundNumber()) {
                counter2x++;
            }
        }
        if(card.getAbility() instanceof CommandersHorn) {
            counter2x--;
        }
        if(counter2x > 0) {
            cofficient *= 2;
        }
        if(card.getAbility() instanceof TightBond) {
            int counter = 0;
            for(Card card1 : row.getCards()) {
                if(card1.getAbility() instanceof TightBond) {
                    counter++;
                }
            }
            if(counter > 1) {
                cofficient *= counter;
            }
        }
        for(Card card1 : row.getCards()) {
            if(card1.getAbility() instanceof MoraleBoost) {
                constant += 1;
            }
        }
        if(card.getAbility() instanceof MoraleBoost) {
            constant -= 1;
        }
        return (int)(cofficient * card.getPower()) + constant;
    }

    public int calculateRowPower(int pos) {
        if(pos > 5) {
            return 0;
        }
        int player = getPlayerByPos(pos);
        int rowNumber = graphicRowToLogicRow(pos);
        Row row = getRowByPositionCurrentPlayer(player, getCardPositionByRowNumber(rowNumber));
        int power = 0;
        for(Card card : row.getCards()) {
            power += calculatePower(pos, card);
        }
        return power;
    }

    public int calculateNonHeroPower(int player, int rowNumber){
        Row row = getRowByPositionCurrentPlayer(player, getCardPositionByRowNumber(rowNumber));
        int power = 0;
        for(Card card : row.getCards()) {
            if(card.isHero()) continue;
            power += card.calculatePower();
        }
        return power;
		//TODO: fix it
    }

    public int calculateTotalPower(int player) {
        int power = 0;
        for(int i = 0; i < 3; i++) {
            power += calculateRowPower(3 * (1 - player) + i);
        }
        return power;
    }
    public boolean isVetoeTurn() {
        return matchTable.getTotalTurns() < 2 && matchTable.getRoundNumber() == 0;
    }

    public CommandResult vetoCard(int cardNumber) {
        int player = matchTable.getTurn();
        if(matchTable.getUserTable(player).getVetoesLeft() == 0) {
            return new CommandResult(ResultCode.FAILED, "You don't have any vetoes left");
        }
        if(matchTable.getUserTable(player).getDeck().isEmpty()) {
	        return new CommandResult(ResultCode.FAILED, "deck is empty");
        }
        GameService.getInstance().sendAction("veto card -number " + cardNumber);
        Card card = matchTable.getUserTable(player).getHand().get(cardNumber);
        matchTable.getUserTable(player).getHand().remove(cardNumber);
        matchTable.getUserTable(player).getDeck().add(card);
        Card randomCard = Random.getRandFromArrayListCard(matchTable.getUserTable(player).getDeck());
        matchTable.getUserTable(player).getDeck().remove(randomCard);
        matchTable.getUserTable(player).getHand().add(cardNumber, randomCard);
        matchTable.getUserTable(player).decreaseVetoesLeft();
        GameGraphics.getInstance().loadModel();
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

    public CommandResult forcePlaceCard(Card card, int pos){
        int rowNumber = graphicRowToLogicRow(pos);
        CardPosition cardPosition = card.getCardPosition();
        if(cardPosition == null) return new CommandResult(ResultCode.FAILED, "Invalid position");
        CommandResult result;
        if(cardPosition == CardPosition.WEATHER) {
            result = placeWeatherCard(card);
        } else if(cardPosition == CardPosition.SPELL) {
            result = placeSpellCard(card, pos);
        } else if(card.getAbility() instanceof Spy) {
            result = placeSpyCard(card, pos);
        } else if(card.getAbility() instanceof Muster) {
            result = placeMusterCard(card, pos);
        } else {
            result = placeUnitCard(card, pos);
        }
        matchTable.getUserTable(0).getHand().remove(card);
        matchTable.getUserTable(1).getHand().remove(card);
        return result;
    }

    public CommandResult placeCard(Card card, int pos) {
        if(isVetoeTurn()) return new CommandResult(ResultCode.FAILED, "You can't play cards in veto turn");
        GameService.getInstance().sendAction("place card -index " +
                GameService.getInstance().getMatchTable().getCurrentUserTable().getHand().indexOf(card) +
                " -pos " + pos);
        CommandResult result = forcePlaceCard(card, pos);
        if(result.statusCode() == ResultCode.ACCEPT){
            finishTurn();
        }
        return result;
    }

    public CommandResult leaderExecute(){
        Leader leader = matchTable.getCurrentUserTable().getLeader();
        if(leader.getDisableRound() == matchTable.getRoundNumber())
            return new CommandResult(ResultCode.FAILED, "Leader ability is disabled for this round");
        if(leader.getRoundOfAbilityUsed() != -1)
            return new CommandResult(ResultCode.FAILED, "Leader ability is before used");
        GameService.getInstance().sendAction("execute leader");
        matchTable.getCurrentUserTable().getLeader().getAbility().execute();
        leader.setRoundOfAbilityUsed(matchTable.getRoundNumber());
        finishTurn();
        return new CommandResult(ResultCode.ACCEPT, "Leader ability executed successfully");
    }

    public CommandResult placeWeatherCard(Card card) {
        matchTable.addWeatherCard(card); //abilities done
        Ability ability = card.getAbility();
        if(ability != null) ability.execute();
        if(card.getName().equals("Clear Weather")) {
            matchTable.getWeatherCards().clear();
        }
        return new CommandResult(ResultCode.ACCEPT, "Weather card placed successfully");
    }

    public CommandResult placeSpellCard(Card card, int pos) {
        int player = matchTable.getTurn();
        int rowNumber = graphicRowToLogicRow(pos);
        matchTable.getUserTable(player).getRowByNumber(rowNumber).setSpell(card);
        if (card.getAbility() instanceof Mardroeme) {
            Row row = matchTable.getUserTable(player).getRowByNumber(rowNumber);
            card.getAbility().execute(row);
        } //abilities done
        return new CommandResult(ResultCode.ACCEPT, "Spell card placed successfully");
    }

    public CommandResult placeSpyCard(Card card, int pos) {
        int player = 1 - matchTable.getTurn();
        int rowNumber = graphicRowToLogicRow(pos);
        matchTable.getUserTable(player).getRowByNumber(rowNumber).addCard(card);
        card.getAbility().execute();
        return new CommandResult(ResultCode.ACCEPT, "Spy card placed successfully");
        //done here
    }

    public CommandResult placeMusterCard(Card card, int pos) {
        int player = matchTable.getTurn();
        int rowNumber = graphicRowToLogicRow(pos);
        card.getAbility().execute(card, rowNumber);
        return new CommandResult(ResultCode.ACCEPT, "Muster card placed successfully");
    }

    public CommandResult placeUnitCard(Card card, int pos) {
        //TODO: execute ability
        int player = matchTable.getTurn();
        int rowNumber = graphicRowToLogicRow(pos);
        Row row = matchTable.getUserTable(player).getRowByNumber(rowNumber);
        row.addCard(card);
        Ability ability = card.getAbility();
        if(ability != null && ability instanceof Mardroeme) {
            ability.execute(row);
        }
        else if(ability != null && !(ability instanceof Berserker) && !(ability instanceof Transformers)) {
			ability.execute(card, rowNumber);
        }
        return new CommandResult(ResultCode.ACCEPT, "Unit card placed successfully");
        //done here
    }

    public CommandResult showCommander() {
        // Implement the logic for showing the commander
        return null;
    }

    public CommandResult commanderPowerPlay() {
        // Implement the logic for playing the commander's power
        //TODO: HisImperialMajesty, KingOfWildHunt
        if(isVetoeTurn()) {
            return new CommandResult(ResultCode.FAILED, "Can't play in vetoe round");
        }

        return leaderExecute();
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

    public CommandResult showTotalScoreOfRow(int pos) {
        String response = "Total score of row " + pos + " of " + matchTable.getUser(0).getUsername() + ": " +
                calculateRowPower(pos) +
                "\n" +
                "Total score of row " + pos + " of " + matchTable.getUser(1).getUsername() + ": " +
                calculateRowPower(pos);
        return new CommandResult(ResultCode.ACCEPT, response);
    }

    public void finishTurn(){
//        System.out.println("TotalTurn :" + matchTable.getTotalTurns() + " RoundNumber: " + matchTable.getRoundNumber() +
//                " Turn: " + matchTable.getTurn() + " PreviousRoundPassed: " + matchTable.isPreviousRoundPassed() + " isFinishTurn");
        if(!matchTable.isPreviousRoundPassed()){
            matchTable.changeTurn();
            GameGraphics.getInstance().preTurnLoading();
        }
        matchTable.setTotalTurns(matchTable.getTotalTurns() + 1);
        // TODO: call graphic
        GameGraphics.getInstance().loadModel();
    }

    public CommandResult passTurn() {
        GameService.getInstance().sendAction("pass turn");

        //System.out.println("TotalTurn :" + matchTable.getTotalTurns() + " RoundNumber: " + matchTable.getRoundNumber() +
        //" Turn: " + matchTable.getTurn() + " PreviousRoundPassed: " + matchTable.isPreviousRoundPassed() + " isPassTurn");
        matchTable.changeTurn();
        GameGraphics.getInstance().preTurnLoading();//todo: maybe should be deleted
        matchTable.setTotalTurns(matchTable.getTotalTurns() + 1);
        if(matchTable.isPreviousRoundPassed()){
            finishRound();
            GameGraphics.getInstance().preTurnLoading();
            GameGraphics.getInstance().loadModel();
            return new CommandResult(ResultCode.ACCEPT, "Round finished successfully");
        }
        GameGraphics.getInstance().preTurnLoading();
        if(matchTable.getTotalTurns() > 2) matchTable.setPreviousRoundPassed(true);
        return new CommandResult(ResultCode.ACCEPT, "Turn passed successfully");
    }

    private void finishRound() {
        int winner = getRoundWinner();
        if(winner != 0) matchTable.getUserTable(0).decreaseLife();
        if(winner != 1) matchTable.getUserTable(1).decreaseLife();
        if(matchTable.getUserTable(0).getLife() == 0 || matchTable.getUserTable(1).getLife() == 0){
            finishGame();
            return;
        }
        matchTable.setPreviousRoundPassed(false);
        matchTable.changeRound();
        startRound(winner);
    }

    private void startRound(int winner){
        // TODO: call graphical controller for this changes
        matchTable.getWeatherCards().clear();
        ArrayList<Integer> scoiataelUsers = new ArrayList<>();
        for (int userIndex = 0; userIndex < 2; userIndex++) {
            if(matchTable.getUserTable(userIndex).getFaction() == Faction.SCOIATAEL) scoiataelUsers.add(userIndex);
        }
        if(scoiataelUsers.isEmpty() || scoiataelUsers.size() == 2){
            matchTable.setTurn(0);
        } else {
            matchTable.setTurn(scoiataelUsers.get(0));
        }
        //Here above, we determine who should start the round

        for (int userIndex = 0; userIndex < 2; userIndex++) {
            UserTable userTable = matchTable.getUserTable(userIndex);
            Card heroRemain = null;
            if(userTable.getFaction() == Faction.MONSTERS) {
                ArrayList<Card> heroCards = userTable.getHeroesCard();
                if(!heroCards.isEmpty()) {
                    heroRemain = Random.getRandFromArrayListCard(heroCards);
                }
            }

            for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
                Row row = userTable.getRowByNumber(rowIndex);
                ArrayList<Card> rowCards = new ArrayList<>(row.getCards());
                for (int i = rowCards.size() - 1; i > -1; i--) {
                    Card card = rowCards.get(i);
                    boolean canDelete = true;
                    if(card == heroRemain) canDelete = false;
                    if(card.getAbility() instanceof Transformers){
                        if(card.getPower() != 8) {
                            canDelete = false;
                            card.getAbility().execute(card);
                        }
                    }
                    if(canDelete){
                        userTable.addOutOfPlay(card);
                        row.getCards().remove(i);
                    }
//                    else {
//                        System.out.println("player " + userIndex + ", row " +
//                                rowIndex + ". size: " + row.getCards().size() +
//                                " card: " + card.getName() + " is hero: " + card.isHero());
//                    }
                }
                if(row.getSpell() != null) {
                    userTable.addOutOfPlay(row.getSpell());
                    row.setSpell(null);
                }
//                System.out.println("player " + userIndex + ", row " + rowIndex +
//                        ". size: " + row.getCards().size());
            }
            //deletes cards from the round before
            if(userTable.getFaction() == Faction.NORTHEN_REALMS && winner == userIndex){
                Card newAddedCard = null;
                if(!userTable.getDeck().isEmpty())
                    newAddedCard = Random.getRandFromArrayListCard(userTable.getDeck());
                if(newAddedCard != null){
                    userTable.getDeck().remove(newAddedCard);
                    userTable.addHand(newAddedCard);
                }
            }
            //add cards if the Faction is NorthernRealms

            if(userTable.getFaction() == Faction.SKELLIGE &&
                    (matchTable.getRoundNumber() == 2 || ((matchTable.getUserTable(0).getLife() == 1) &&
                        matchTable.getUserTable(1).getLife() == 1))){
                for (int addedCardIndex = 0; addedCardIndex < 2; addedCardIndex++) {
                    Card newAddedCard = null;
                    if(!userTable.getOutOfPlays().isEmpty())
                        newAddedCard = Random.getRandFromArrayListCard(userTable.getOutOfPlays());
                    if(newAddedCard != null){
                        userTable.getOutOfPlays().remove(newAddedCard);
                        userTable.addHand(newAddedCard);
                    }
                }
            }
            //add cards to hand if it's the final round
        }
    }

    private void startGame(){
        startRound(-1);

        for (int playerIndex = 0; playerIndex < 2; playerIndex++) {
            int startNumberOfCard = Integer.parseInt(ConstantsLoader.getInstance().getProperty("game.start_number_card"));
            if(matchTable.getUserTable(playerIndex).getLeader().getAbility() instanceof DaisyOfTheValley) {
                startNumberOfCard++;
                matchTable.getUserTable(playerIndex).getLeader().setDisableRound(0);
            }
            for(int cardIndex = 0; cardIndex < startNumberOfCard; cardIndex++){
                if(matchTable.getUserTable(playerIndex).getDeck().isEmpty()) break;
                Card randomCard = Random.getRandFromArrayListCard(matchTable.getUserTable(playerIndex).getDeck());
                matchTable.getUserTable(playerIndex).getDeck().remove(randomCard);
                matchTable.getUserTable(playerIndex).getHand().add(randomCard);
            }
        }
    }

    private void finishGame(){
        int gameWinner;
        if(matchTable.getUserTable(0).getLife() == matchTable.getUserTable(1).getLife()) gameWinner = -1;
        else if(matchTable.getUserTable(0).getLife() > matchTable.getUserTable(1).getLife()) gameWinner = 0;
        else gameWinner = 1;
        GameGraphics.getInstance().showWinner(gameWinner);

        if(gameState == GameState.ONLINE_PLAYER){
            if(getOnlineCurrentUser() == 0) {
                User winner = null;
                if(gameWinner != -1) winner = GameService.getInstance().getMatchTable().getUser(gameWinner);
                sendGameHistories(winner);
            }
        }
        // TODO: return winner to the graphical controller
    }

    public void sendGameHistories(User winner){
	    System.err.println("game finished: " + winner);
        GameHistory gameHistory = new GameHistory(GameService.getInstance().getMatchTable().getUser(0),
                GameService.getInstance().getMatchTable().getUser(1),
                winner,
                new ArrayList<>(), GameService.getInstance().getMatchTable().getGameToken(),
                GameService.getInstance().getMatchTable().getTournamentToken()
        );

        TCPClient tcpClient = new TCPClient();
        CommandResult result = tcpClient.finishGame(gameHistory, gameHistory.getGameToken());
        if(result.statusCode() != ResultCode.ACCEPT){
            System.err.println("error: " + result.message());
        }
    }

    private int getRoundWinner(){
        int firstUserPower = matchTable.getUserTable(0).getPower();
        int secondUserPower = matchTable.getUserTable(1).getPower();
        if(firstUserPower > secondUserPower) return 0;
        if(secondUserPower > firstUserPower) return 1;
        Faction firstUserFaction = matchTable.getUserTable(0).getFaction();
        Faction secondUserFaction = matchTable.getUserTable(1).getFaction();
        if(firstUserFaction == secondUserFaction) return -1;
        if(firstUserFaction == Faction.NILFGAARDIAN_EMPIRE) return 0;
        if(secondUserFaction == Faction.NILFGAARDIAN_EMPIRE) return 1;
        return -1;
    }

    public UserTable getCurrentUserTable() {
        return matchTable.getUserTable(matchTable.getTurn());
    }

	public UserTable getOpponentUserTable() {
		return matchTable.getUserTable(matchTable.getTurn() ^ 1);
	}

    public UserTable getUserUserTable(int playerNumber) {
        return matchTable.getUserTable(playerNumber);
    }

	public MatchTable getMatchTable() {
		return matchTable;
	}

    public CommandResult instantWinCheat(int player) {
        matchTable.getUserTable(1 - player).setLife(0);
        finishGame();
        return new CommandResult(ResultCode.ACCEPT, "Instant win. cheat activated");
    }
    public CommandResult instantLoseCheat(int player) {
        matchTable.getUserTable(player).setLife(0);
        finishGame();
        return new CommandResult(ResultCode.ACCEPT, "Instant lose. cheat activated");
    }
    public CommandResult addCardToHandCheat(int player) {
        if(matchTable.getUser(player).getDeckInfo().getStorage().isEmpty()) {
            return new CommandResult(ResultCode.FAILED, "DeckInfo is empty");
        }
        CardTypes cardType = Random.getRandFromArrayListCardTypes(matchTable.getUser(player).getDeckInfo().getStorage());
        Card card = cardType.getInstance();
        matchTable.getUserTable(player).getHand().add(card);
        return new CommandResult(ResultCode.ACCEPT, "Random Card added to hand. cheat activated");
    }

    public CommandResult removeOpponentHandCheat() {
        matchTable.getUserTable(1 - matchTable.getTurn()).getHand().clear();
        return new CommandResult(ResultCode.ACCEPT, "Opponent hand removed. cheat activated");
    }

    public CommandResult addLifeCheat(int player) {
        matchTable.getUserTable(player).setLife(matchTable.getUserTable(player).getLife() + 1);
        return new CommandResult(ResultCode.ACCEPT, "Life added. cheat activated");
    }

    public CommandResult clearWeatherCheat() {
        matchTable.getWeatherCards().clear();
        return new CommandResult(ResultCode.ACCEPT, "Weather cleared. cheat activated");
    }

    public CommandResult leaderExecutionCheat(int player) {
        Leader leader = matchTable.getUserTable(player).getLeader();
        leader.getAbility().execute();
        return new CommandResult(ResultCode.ACCEPT, "Leader ability executed. cheat activated");
    }

}