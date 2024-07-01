package ir.sharif.messages.tournament;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;
import ir.sharif.model.User;

public class CreateTournamentMessage extends ClientMessage {
    private User owner;
    public CreateTournamentMessage(User owner){
        this.owner = owner;
        this.type = ClientMessageType.CREATE_TOURNAMENT_MESSAGE;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
