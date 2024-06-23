package ir.sharif.view.terminal;

import ir.sharif.enums.Menus;
import ir.sharif.service.AppService;

import java.util.Scanner;

public class AppView {

    public static void giveCommands() {
        Scanner scanner = new Scanner(System.in);
        do {
            String command = scanner.nextLine();
            AppService.getCurrentMenu().checkCommand(command);
        } while(AppService.getCurrentMenu() != Menus.ExitMenu);
    }

}
