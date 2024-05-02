package ir.sharif.service;

import ir.sharif.view.terminal.Menu;

public class AppService {
    private Menu currentMenu;
    private static AppService instance;

    private AppService() {

    }

    public static AppService getInstance() {
        if (instance == null) {
            instance = new AppService();
        }
        return instance;
    }

    private Menu getCurrentMenu() {
        return currentMenu;
    }
}
