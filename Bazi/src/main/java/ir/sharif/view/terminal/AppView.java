package ir.sharif.view.terminal;

import ir.sharif.enums.Menus;
import ir.sharif.model.CommandResult;
import ir.sharif.service.AppService;

import java.util.Scanner;

public class AppView {

    public static CommandResult run(String command) {
        return AppService.getCurrentMenu().checkCommand(command);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            String command = scanner.nextLine();
            run(command);
        } while(AppService.getCurrentMenu() != Menus.ExitMenu);

    }

}
