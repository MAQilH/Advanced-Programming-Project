package ir.sharif.view.terminal;

import ir.sharif.controller.LoginController;
import ir.sharif.model.CommandResult;

import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    private LoginController controller;
    public LoginMenu() {
        super();
    }

    @Override
    protected CommandResult runCommand(String command, Matcher matcher) {
        return null;
    }
}
