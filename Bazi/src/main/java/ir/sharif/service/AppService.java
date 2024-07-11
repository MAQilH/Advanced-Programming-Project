package ir.sharif.service;

import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.view.terminal.Menu;
import ir.sharif.enums.Menus;

public class AppService {
    private Menus currentMenu;
    private static AppService instance;

    private AppService() {
        currentMenu = Menus.RegisterMenu;
    }

	public CommandResult setCurrentMenu(Menus menu) {
		currentMenu = menu;
        return new CommandResult(ResultCode.ACCEPT, "menu changed");
	}

    public static AppService getInstance() {
        if (instance == null) {
            instance = new AppService();
        }
        return instance;
    }

    public Menus getCurrentMenu() {
        return currentMenu;
    }
}
