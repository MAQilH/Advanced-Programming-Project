package ir.sharif.view.terminal;

import ir.sharif.model.CommandResult;

public abstract class Menu {
    public abstract CommandResult checkCommand(String command);
}
