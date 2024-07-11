package ir.sharif.tests;

import ir.sharif.controller.MainMenuController;
import ir.sharif.enums.Menus;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.service.AppService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppTest {

    private Menus menu;
    private AppService appService;
    @Before
    public void setUp() {
        appService = AppService.getInstance();
        menu = Menus.LoginMenu;
    }

    @Test
    public void test() {
        CommandResult result = appService.setCurrentMenu(menu);
        assertEquals(ResultCode.ACCEPT, result.statusCode());
    }

}
