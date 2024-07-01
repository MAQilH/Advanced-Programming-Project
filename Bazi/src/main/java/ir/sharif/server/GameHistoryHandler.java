package ir.sharif.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.sharif.enums.ResultCode;
import ir.sharif.messages.AddGameHistoryMessage;
import ir.sharif.messages.GetGameHistoriesMessage;
import ir.sharif.messages.ServerMessage;
import ir.sharif.model.GameHistory;
import ir.sharif.service.GameHistoryService;
import ir.sharif.service.storage.Database;
import ir.sharif.view.controllers.Game;

import java.util.ArrayList;

public class GameHistoryHandler {
    private Gson gson;

    GameHistoryHandler() {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }

    public ServerMessage addGameHistory(AddGameHistoryMessage addGameHistoryMessage) {
        Database.getInstance().addGameHistories(addGameHistoryMessage.getGameHistory());
        return new ServerMessage(ResultCode.ACCEPT, "Game history added successfully");
    }

    public ServerMessage getGameHistories(GetGameHistoriesMessage getGameHistoriesMessage) {
        ArrayList<GameHistory> gameHistories = Database.getInstance().getGameHistories();
        return new ServerMessage(ResultCode.ACCEPT, gson.toJson(gameHistories));
    }
}
