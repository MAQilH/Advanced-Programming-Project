package ir.sharif.view.terminal;

import ir.sharif.model.CommandResult;
import ir.sharif.model.Pair;

import java.util.regex.Matcher;

public abstract class Menu {
    public void run(String command) {
        CommandResult commandResult = new CommandResult(2, "salam");
        commandResult.message();

    }

    protected abstract CommandResult runCommand(String command, Matcher matcher);

    private Pair<String, Matcher> getCommand(String input) {
        return null;
    }
}
