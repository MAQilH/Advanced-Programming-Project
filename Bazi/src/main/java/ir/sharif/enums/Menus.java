package ir.sharif.enums;

import ir.sharif.view.terminal.*;

public enum Menus {
    LoginMenu(new LoginMenu()),
    RegisterMenu(new RegisterMenu()),
    MainMenu(new MainMenu()),
    ProfileMenu(new ProfileMenu()),
    PreGameMenu(new PreGameMenu()),
    GameMenu(new GameMenu()),
    ExitMenu(new ExitMenu());

    private final Menu menu;
    Menus(Menu menu) {
        this.menu = menu;
    }

    public void checkCommand(String command) {
        this.menu.checkCommand(command);
    }
}
