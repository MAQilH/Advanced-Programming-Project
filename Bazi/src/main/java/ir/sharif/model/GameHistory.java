package ir.sharif.model;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;

public class GameHistory {
    private User user1, user2;
    private Date date;
    private ArrayList<Pair<Integer, Integer>> roundScores;
    private User winner;
	private String gameToken;
	private String tournamentToken;

	public GameHistory(User user1, User user2, User winner, ArrayList<Pair<Integer, Integer>> roundScores, String gameToken, String tournamentToken) {
		this.user1 = user1;
		this.user2 = user2;
		this.date = new Date();
		this.roundScores = roundScores;
		this.winner = winner;
		this.gameToken = gameToken;
		this.tournamentToken = tournamentToken;
	}

	public GameHistory() {

	}

	public User getUser1() {
		return user1;
	}

	public User getUser2() {
		return user2;
	}

	public Date getDate() {
		return date;
	}

	public ArrayList<Pair<Integer, Integer>> getRoundScores() {
		return roundScores;
	}

	public User getWinner() {
		return winner;
	}

	public Pair<Integer, Integer> getRoundResult(){
		Pair<Integer, Integer> result = new Pair<>(0, 0);
		for (Pair<Integer, Integer> roundScore : getRoundScores()) {
			if(roundScore.getFirst() > roundScore.getSecond())
				result.setFirst(result.getFirst() + 1);
			else if(roundScore.getFirst() < roundScore.getSecond())
				result.setSecond(result.getSecond() + 1);
		}
		return result;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public String getGameToken(){
		return gameToken;
	}

	public String getTournamentToken() {
		return tournamentToken;
	}

	public void setTournamentToken(String tournamentToken) {
		this.tournamentToken = tournamentToken;
	}
}
