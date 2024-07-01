package ir.sharif.messages.tournament;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

public class ReadyPlayerMessage extends ClientMessage {
    private String username;
    private boolean ready;
    private String tournamentToken;

    public ReadyPlayerMessage(String username, boolean ready, String tournamentToken) {
        this.username = username;
        this.ready = ready;
        this.tournamentToken = tournamentToken;
        this.type = ClientMessageType.READY_PLAYER_MESSAGE;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getTournamentToken() {
        return tournamentToken;
    }

    public void setTournamentToken(String tournamentToken) {
        this.tournamentToken = tournamentToken;
    }
}
