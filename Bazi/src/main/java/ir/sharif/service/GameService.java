package ir.sharif.service;

import ir.sharif.client.TCPClient;
import ir.sharif.controller.GameController;
import ir.sharif.model.CommandResult;
import ir.sharif.model.game.MatchTable;
import ir.sharif.view.controllers.Game;

public class GameService {
    private GameService() {}

    private static GameService instance;
    private MatchTable matchTable;
	private GameController controller;

    public static GameService getInstance() {
        if (instance == null) {
            instance = new GameService();
        }
        return instance;
    }

    public MatchTable getMatchTable(){
        return matchTable;
    }

    public void setMatchTable(MatchTable matchTable){
        this.matchTable = matchTable;
    }

	public void createController() {
		controller = new GameController();
	}

	public GameController getController() {
		return controller;
	}

    public CommandResult sendAction(String action){
        TCPClient tcpClient = new TCPClient();
        return tcpClient.gameAction(action, matchTable.getGameToken());
    }
}
