package ir.sharif.controller;

import ir.sharif.enums.Menus;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.service.AppService;
import ir.sharif.service.UserService;
import ir.sharif.view.terminal.*;

public class MainMenuController {
    private UserService userService;

    public CommandResult userLogout() {
        AppService.getInstance().setCurrentMenu(Menus.LoginMenu);
        return new CommandResult(ResultCode.ACCEPT, "user logged out successfully");
    }

    public CommandResult showCurrentMenu() {
        return new CommandResult(ResultCode.ACCEPT, "enter main menu");
    }

    public CommandResult menuEnter(Menus menu) {
        switch (menu){
            case ExitMenu:
                AppService.getInstance().setCurrentMenu(Menus.LoginMenu);
                UserService.getInstance().setCurrentUser(null);
                return new CommandResult(ResultCode.ACCEPT, "enter login menu");
            case ProfileMenu:
                AppService.getInstance().setCurrentMenu(Menus.ProfileMenu);
                return new CommandResult(ResultCode.ACCEPT, "enter profile menu");
            case PreGameMenu:
                AppService.getInstance().setCurrentMenu(Menus.PreGameMenu);
                return new CommandResult(ResultCode.ACCEPT, "enter pre game menu");
            default:
                return new CommandResult(ResultCode.FAILED, "menu not found");
        }
    }

}

