package ir.sharif.messages.Game;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;
import ir.sharif.model.User;

public class GameRequestMessage extends ClientMessage {
    private User user;
    private String receiver;

    private boolean isPrivate;

    public GameRequestMessage(User user, String receiver, boolean isPrivate) {
        this.user = user;
        this.receiver = receiver;
		this.isPrivate = isPrivate;
        this.type = ClientMessageType.GAME_REQUEST_MESSAGE;
    }

    public User getUser() {
        return user;
    }

    public String getReceiver() {
        return receiver;
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
