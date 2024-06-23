package ir.sharif.view.terminal;

import ir.sharif.model.CommandResult;
import ir.sharif.model.Pair;

import java.util.regex.Matcher;

public abstract class Menu {
    public void run(String command) {

    }

    protected abstract CommandResult runCommand(String command, Matcher matcher);

    private Pair<String, Matcher> getCommand(String input) {
        return null;
    }
}
