package ir.sharif.messages.tournament;

import ir.sharif.messages.ClientMessage;

public class GetTournamentStateMessage extends ClientMessage {
    private String tournamentToken;

    public GetTournamentStateMessage(String tournamentToken){
        this.tournamentToken = tournamentToken;
    }

    public String getTournamentToken() {
        return tournamentToken;
    }

    public void setTournamentToken(String tournamentToken) {
        this.tournamentToken = tournamentToken;
    }
}
