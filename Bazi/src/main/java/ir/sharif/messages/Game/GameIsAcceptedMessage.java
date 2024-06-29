package ir.sharif.messages.Game;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

public class GameIsAcceptedMessage extends ClientMessage {
    private String gameToken;

    public GameIsAcceptedMessage(String gameToken){
        this.gameToken = gameToken;
        this.type = ClientMessageType.GAME_IS_ACCEPTED_MESSAGE;
    }

    public String getGameToken(){
        return gameToken;
    }
}
