package ir.sharif.tests;

import ir.sharif.controller.GameController;
import ir.sharif.controller.PreGameController;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
	@Before
	public void setUp() {
		PreGameController controller = new PreGameController();
		controller.createGame("guest");
		controller.loadDeck("test");
		controller.changeTurn();
		controller.loadDeck("test");
		controller.startGame();
	}

	@Test
	public void vetoCard() {

	}
}
