package ir.sharif.view.terminal;

import ir.sharif.controller.RegisterController;
import ir.sharif.model.CommandResult;

import java.util.regex.Matcher;

public class RegisterMenu extends Menu {
    private RegisterController controller;
    RegisterMenu() {
        super();
    }

    @Override
    protected CommandResult runCommand(String command, Matcher matcher) {
        return null;
    }
}
