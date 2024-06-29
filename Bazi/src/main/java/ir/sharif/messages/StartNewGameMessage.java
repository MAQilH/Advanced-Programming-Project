package ir.sharif.messages;

import ir.sharif.model.User;
import ir.sharif.model.game.CardTypes;

public class StartNewGameMessage extends ClientMessage{
    private User user1, user2;
    public StartNewGameMessage(User user1, User user2){
        this.type = ClientMessageType.START_NEW_GAME_MESSAGE;
        this.user1 = user1;
        this.user2 = user2;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }
}
