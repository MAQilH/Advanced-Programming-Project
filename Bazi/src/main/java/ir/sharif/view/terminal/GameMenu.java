package ir.sharif.view.terminal;

import ir.sharif.controller.GameController;
import ir.sharif.model.CommandResult;

import java.util.regex.Matcher;

public class GameMenu extends Menu {
    private GameController controller;

    public GameMenu() {
        super();
    }
    @Override
    protected CommandResult runCommand(String command, Matcher matcher) {
        return null;
    }
}
