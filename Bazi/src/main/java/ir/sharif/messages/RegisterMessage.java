package ir.sharif.messages;

import ir.sharif.model.User;

public class RegisterMessage extends ClientMessage{
    private User user;

    public RegisterMessage(User user) {
        this.user = user;
        this.type = ClientMessageType.REGISTER_MESSAGE;
    }

    public User getUser(){
        return user;
    }
}
