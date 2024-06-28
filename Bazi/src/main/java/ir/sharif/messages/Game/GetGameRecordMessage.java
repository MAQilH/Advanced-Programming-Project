package ir.sharif.messages.Game;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

public class GetGameRecordMessage extends ClientMessage {
    private String gameToken;

    public GetGameRecordMessage(String gameToken){
        this.gameToken = gameToken;
        this.type = ClientMessageType.GET_GAME_RECORD_MESSAGE;
    }

    public String getGameToken(){
        return gameToken;
    }
}
