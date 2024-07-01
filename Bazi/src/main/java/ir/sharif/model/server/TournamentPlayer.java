package ir.sharif.model.server;

import ir.sharif.model.User;

public class TournamentPlayer {
    private User user;
    private boolean ready;
    private boolean loss;
    private int rate;
    private String tournamentToken;

    public TournamentPlayer(User user, String tournamentToken) {
        this.user = user;
        this.ready = false;
        this.loss = false;
        this.rate = 0;
        this.tournamentToken = tournamentToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isLoss() {
        return loss;
    }

    public void setLoss(boolean loss) {
        this.loss = loss;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getTournamentToken() {
        return tournamentToken;
    }

    public void setTournamentToken(String tournamentToken) {
        this.tournamentToken = tournamentToken;
    }
}
