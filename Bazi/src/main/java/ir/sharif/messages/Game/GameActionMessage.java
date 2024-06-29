package ir.sharif.messages.Game;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

public class GameActionMessage extends ClientMessage {
    private String action;
    private String gameToken;

    public GameActionMessage(String action, String gameToken){
        this.action = action;
        this.gameToken = gameToken;
        this.type = ClientMessageType.GAME_ACTION_MESSAGE;
    }

    public String getAction() {
        return action;
    }

    public String getGameToken() {
        return gameToken;
    }
}
