package ir.sharif.tests;

import ir.sharif.controller.MainMenuController;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainTest {

    @Before
    public void setUp() {

    }

    @Test
    public void test() {
        MainMenuController controller = new MainMenuController();
        CommandResult result = controller.showCurrentMenu();
        assertEquals(result.statusCode(), ResultCode.ACCEPT);
    }

    @Test
    public void test2() {
        MainMenuController controller = new MainMenuController();
        CommandResult result = controller.userLogout();
        assertEquals(result.statusCode(), ResultCode.ACCEPT);
    }

}
