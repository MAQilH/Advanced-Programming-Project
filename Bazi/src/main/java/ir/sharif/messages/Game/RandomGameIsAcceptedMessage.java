package ir.sharif.messages.Game;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

public class RandomGameIsAcceptedMessage extends ClientMessage {
    private String username;
    private String gameToken;

    public RandomGameIsAcceptedMessage(String username, String gameToken){
        this.username = username;
        this.gameToken = gameToken;
        this.type = ClientMessageType.RANDOM_GAME_IS_ACCEPTED_MESSAGE;
    }

    public String getUsername() {
        return username;
    }

    public String getGameToken() {
        return gameToken;
    }
}
