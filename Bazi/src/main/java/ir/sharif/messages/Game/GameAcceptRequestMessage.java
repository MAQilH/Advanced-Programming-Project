package ir.sharif.messages.Game;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;
import ir.sharif.model.User;

public class GameAcceptRequestMessage extends ClientMessage {
    private String gameToken;
    private User user;

    public GameAcceptRequestMessage(String gameToken, User user) {
        this.gameToken = gameToken;
        this.user = user;
        this.type = ClientMessageType.GAME_ACCEPT_REQUEST_MESSAGE;
    }

    public String getGameToken() {
        return gameToken;
    }

    public User getUser() {
        return user;
    }
}
