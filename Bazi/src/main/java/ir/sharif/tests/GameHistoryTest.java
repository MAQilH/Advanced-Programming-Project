package ir.sharif.tests;

import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.GameHistory;
import ir.sharif.service.GameHistoryService;
import ir.sharif.view.controllers.Game;
import ir.sharif.view.terminal.Menu;
import ir.sharif.enums.Menus;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GameHistoryTest {

    private GameHistoryService gameHistoryService;
    @Before
    public void setUp() {
        gameHistoryService = GameHistoryService.getInstance();
    }

    @Test
    public void test() {
        ArrayList<GameHistory> gameHistories = gameHistoryService.getGameHistories();
        ArrayList<GameHistory> userHistory = gameHistoryService.getUserHistory("sohsoh");
        ArrayList<GameHistory> userHistory2 = new ArrayList<>();
        for(GameHistory gameHistory : gameHistories) {
            if(gameHistory.getUser1().getUsername().equals("sohsoh") || gameHistory.getUser2().getUsername().equals("sohsoh")) {
                userHistory2.add(gameHistory);
            }
        }
        assertEquals(userHistory, userHistory2);
    }
}