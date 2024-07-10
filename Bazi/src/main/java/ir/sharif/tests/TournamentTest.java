package ir.sharif.tests;

import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.GameHistory;
import ir.sharif.service.GameHistoryService;
import ir.sharif.service.TournamentService;
import ir.sharif.view.controllers.Game;
import ir.sharif.view.terminal.Menu;
import ir.sharif.enums.Menus;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TournamentTest {

    private TournamentService tournamentService;
    @Before
    public void setUp() {
        tournamentService = TournamentService.getInstance();
    }

    @Test
    public void test() {
        String str = tournamentService.createTournament();
        assertNull(str);
    }
}