package ir.sharif.messages.tournament;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;
import ir.sharif.model.User;

public class JoinPlayerMessage extends ClientMessage {
    private User user;
    private String tournamentToken;

    public JoinPlayerMessage(User user, String tournamentToken) {
        this.user = user;
        this.tournamentToken = tournamentToken;
        this.type = ClientMessageType.JOIN_PLAYER_MESSAGE;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTournamentToken() {
        return tournamentToken;
    }

    public void setTournamentToken(String tournamentToken) {
        this.tournamentToken = tournamentToken;
    }
}
