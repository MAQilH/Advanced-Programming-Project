package ir.sharif.view.terminal;

import ir.sharif.model.CommandResult;

import java.util.Scanner;

public interface MenuInterface {

    CommandResult checkCommand(String command);

}
