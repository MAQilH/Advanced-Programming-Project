package ir.sharif.view.terminal;

import ir.sharif.model.CommandResult;

import java.util.Scanner;

public interface Menu {

    CommandResult checkCommand(Scanner scanner);
    CommandResult checkCommand(String command);

}
