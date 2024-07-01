package ir.sharif.messages.tournament;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

public class GetTournamentMessage extends ClientMessage {
    private String tournamentToken;

    public GetTournamentMessage(String tournamentToken) {
        this.tournamentToken = tournamentToken;
        this.type = ClientMessageType.GET_TOURNAMENT_MESSAGE;
    }

    public String getTournamentToken() {
        return tournamentToken;
    }

    public void setTournamentToken(String tournamentToken) {
        this.tournamentToken = tournamentToken;
    }
}
