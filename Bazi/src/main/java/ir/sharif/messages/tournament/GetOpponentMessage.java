package ir.sharif.messages.tournament;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

public class GetOpponentMessage extends ClientMessage {
    private String username;
    private String tournamentToken;

    public GetOpponentMessage(String username, String tournamentToken) {
        this.username = username;
        this.tournamentToken = tournamentToken;
        this.type = ClientMessageType.GET_OPPONENT_MESSAGE;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTournamentToken() {
        return tournamentToken;
    }

    public void setTournamentToken(String tournamentToken) {
        this.tournamentToken = tournamentToken;
    }
}
