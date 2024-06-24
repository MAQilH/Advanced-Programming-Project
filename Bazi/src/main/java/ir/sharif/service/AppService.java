package ir.sharif.service;

import ir.sharif.view.terminal.Menu;
import ir.sharif.enums.Menus;

public class AppService {
    private Menus currentMenu;
    private static AppService instance;

    private AppService() {
        currentMenu = Menus.RegisterMenu;
    }

	public void setCurrentMenu(Menus menu) {
		currentMenu = menu;
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
