package ir.sharif.messages.Game;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

public class GetActionsMessage extends ClientMessage {
    int buffer;
    String gameToken;
    public GetActionsMessage(int buffer, String gameToken){
        this.buffer = buffer;
        this.gameToken = gameToken;
        this.type = ClientMessageType.GAME_ACTION_MESSAGE;
    }

    public int getBuffer(){
        return buffer;
    }

    public String getGameToken() {
        return gameToken;
    }
}
