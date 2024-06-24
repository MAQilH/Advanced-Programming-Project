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
        return null;
    }

    public CommandResult showCurrentMenu() {
        return null;
    }

    public CommandResult menuProfile() {
        AppService.setCurrentMenu(Menus.ProfileMenu);
        return new CommandResult(ResultCode.ACCEPT, "enter profile menu");
    }

    public CommandResult menuRegister() {
        AppService.setCurrentMenu(Menus.RegisterMenu);
        return new CommandResult(ResultCode.ACCEPT, "enter game menu");
    }

    public CommandResult menuExit() {
        AppService.setCurrentMenu(Menus.LoginMenu);
        return new CommandResult(ResultCode.ACCEPT, "enter login menu");
    }

}

