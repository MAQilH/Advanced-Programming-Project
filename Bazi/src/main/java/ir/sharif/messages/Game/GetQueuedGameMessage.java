package ir.sharif.messages.Game;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

public class GetQueuedGameMessage extends ClientMessage {
    private String username;
    public GetQueuedGameMessage(String username) {
        this.username = username;
        this.type = ClientMessageType.GET_QUEUED_GAME_MESSAGE;
    }

    public String getUsername() {
        return username;
    }
}
