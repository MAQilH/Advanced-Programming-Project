package ir.sharif.view.terminal;

import ir.sharif.controller.MainMenuController;
import ir.sharif.model.CommandResult;

import java.util.regex.Matcher;

public class MainMenu extends Menu {
    private MainMenuController controller;
    public MainMenu() {
        super();
    }

    @Override
    protected CommandResult runCommand(String command, Matcher matcher) {
        return null;
    }

}
