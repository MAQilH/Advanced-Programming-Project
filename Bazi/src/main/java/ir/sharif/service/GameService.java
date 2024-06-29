package ir.sharif.service;

import ir.sharif.client.TCPClient;
import ir.sharif.controller.GameController;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.game.MatchTable;
import ir.sharif.view.controllers.Game;

import java.util.ArrayList;

public class GameService {
    private GameService() {}

    private static GameService instance;
    private MatchTable matchTable;
	private GameController controller;

    int bufferReading = 0;

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
        bufferReading = 0;
        this.matchTable = matchTable;
    }

	public void createController() {
        bufferReading = 0;
		controller = new GameController();
	}

	public GameController getController() {
		return controller;
	}

    public CommandResult sendAction(String action){
        TCPClient tcpClient = new TCPClient();
        CommandResult result = tcpClient.gameAction(action, matchTable.getGameToken());
        if(result.statusCode() == ResultCode.ACCEPT)
            increaseBufferReading(1);
        else System.err.println(result.message());
        return result;
    }

    public void increaseBufferReading(int value){
        bufferReading += value;
    }

    public ArrayList<String> getNewActions() {
        TCPClient tcpClient = new TCPClient();
        return tcpClient.getActions(bufferReading, matchTable.getGameToken());
    }
}
