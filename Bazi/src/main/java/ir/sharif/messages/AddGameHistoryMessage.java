package ir.sharif.messages;

import ir.sharif.model.GameHistory;
import ir.sharif.view.controllers.Game;

public class AddGameHistoryMessage extends ClientMessage{
    private GameHistory gameHistory;
    public AddGameHistoryMessage(GameHistory gameHistory){
        this.gameHistory = gameHistory;
        this.type = ClientMessageType.ADD_GAME_HISTORY_MESSAGE;
    }

    public GameHistory getGameHistory(){
        return gameHistory;
    }
}
