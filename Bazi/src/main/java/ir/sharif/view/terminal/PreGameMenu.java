package ir.sharif.view.terminal;

import ir.sharif.controller.PreGameController;
import ir.sharif.model.CommandResult;

import java.util.regex.Matcher;

public class PreGameMenu extends Menu {
    private PreGameController controller;

    public PreGameMenu() {
        super();
    }


    @Override
    protected CommandResult runCommand(String command, Matcher matcher) {
        return null;
    }
}
