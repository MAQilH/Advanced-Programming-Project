package ir.sharif.messages;

import ir.sharif.model.GameHistory;
import ir.sharif.view.controllers.Game;

public class FinishGameMessage extends ClientMessage {
    private String gameToken;
    private GameHistory gameHistory;

    public FinishGameMessage(GameHistory gameHistory, String gameToken){
        this.gameToken = gameToken;
        this.gameHistory = gameHistory;
        this.type = ClientMessageType.FINISH_GAME_MESSAGE;
    }

    public String getGameToken() {
        return gameToken;
    }

    public GameHistory getGameHistory(){
        return gameHistory;
    }
}
