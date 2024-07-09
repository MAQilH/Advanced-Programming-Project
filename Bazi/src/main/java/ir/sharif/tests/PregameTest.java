package ir.sharif.tests;

import eu.hansolo.tilesfx.Command;
import ir.sharif.controller.LoginController;
import ir.sharif.controller.PreGameController;
import ir.sharif.controller.RegisterController;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.SecurityQuestion;
import ir.sharif.model.game.CardTypes;
import ir.sharif.model.game.DeckInfo;
import ir.sharif.model.game.Faction;
import ir.sharif.model.game.LeaderType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PregameTest {
	@Before
	public void setUp() {
		LoginController loginController = new LoginController();
		RegisterController controller = new RegisterController();
		controller.register("guest", "guest", "guest",
			new SecurityQuestion("fake", "fake"), "guest", "guest");
		CommandResult result = loginController.login("sohsoh", "Soheil@84", false);
		assertEquals(ResultCode.ACCEPT, result.statusCode());
	}

	@Test
	public void load() {
		PreGameController controller = new PreGameController();
		CommandResult result = controller.loadDeck("test");
		assertEquals(result.statusCode(), ResultCode.ACCEPT);
	}

	@Test
	public void loadFail() {
		PreGameController controller = new PreGameController();
		controller.loadDeck("someRandom");
		assertEquals(controller.loadDeck("someRandom").statusCode(), ResultCode.FAILED);
	}

	@Test
	public void nullDeck() {
		PreGameController controller = new PreGameController();
		CommandResult result = controller.setDeck(new DeckInfo());
		assertEquals(result.statusCode(), ResultCode.ACCEPT);
	}

	@Test
	public void setDeck() {
		PreGameController controller = new PreGameController();
		DeckInfo testInfo = new DeckInfo();
		testInfo.addCard(CardTypes.ALBRICH);
		testInfo.setFaction(Faction.MONSTERS);
		testInfo.setLeader(LeaderType.QUEEN_OF_DOL_BLATHANNA);
		CommandResult result = controller.setDeck(testInfo);
		assertEquals(result.statusCode(), ResultCode.ACCEPT);
	}

	@Test
	public void failedGameStart() {

		PreGameController controller = new PreGameController();
		DeckInfo testInfo = new DeckInfo();
		testInfo.addCard(CardTypes.ALBRICH);
		testInfo.setFaction(Faction.MONSTERS);
		testInfo.setLeader(LeaderType.QUEEN_OF_DOL_BLATHANNA);
		controller.setDeck(testInfo);
		CommandResult result = controller.changeTurn();
		assertEquals(result.statusCode(), ResultCode.FAILED);
	}

	@Test
	public void loadGame() {
		PreGameController controller = new PreGameController();
		controller.createGame("guest");
		CommandResult result = controller.loadDeck("test");
		assertEquals(result.statusCode(), ResultCode.ACCEPT);
		result = controller.changeTurn();
		assertEquals(result.statusCode(), ResultCode.ACCEPT);
		result = controller.loadDeck("test");
		assertEquals(result.statusCode(), ResultCode.ACCEPT);
		result = controller.startGame();
		assertEquals(result.statusCode(), ResultCode.ACCEPT);
	}
}
