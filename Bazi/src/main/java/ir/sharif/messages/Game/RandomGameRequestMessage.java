package ir.sharif.messages.Game;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;
import ir.sharif.model.User;
import ir.sharif.utils.Random;

public class RandomGameRequestMessage extends ClientMessage {
    private User user;
    public RandomGameRequestMessage(User user){
        this.user = user;
        this.type = ClientMessageType.RANDOM_GAME_REQUEST_MESSAGE;
    }

    public User getUser() {
        return user;
    }
}
