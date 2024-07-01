package ir.sharif.model.server;

import ir.sharif.model.User;

public class TournamentMatchOpponentResult {
    private User opponent;
    private String gameToken;

    public TournamentMatchOpponentResult(User opponent, String gameToken) {
        this.opponent = opponent;
        this.gameToken = gameToken;
    }

    public User getOpponent() {
        return opponent;
    }

    public String getGameToken() {
        return gameToken;
    }
}
