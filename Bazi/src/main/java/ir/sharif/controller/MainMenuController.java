package ir.sharif.controller;

import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.service.AppService;
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
        AppService.getInstance().setCurrentMenu(new ProfileMenu());
        return new CommandResult(ResultCode.ACCEPT, "enter profile menu");
    }

    public CommandResult menuRegister() {
        AppService.getInstance().setCurrentMenu(new GameMenu());
        return new CommandResult(ResultCode.ACCEPT, "enter game menu");
    }

    public CommandResult menuExit() {
        AppService.getInstance().setCurrentMenu(new LoginMenu());
        return new CommandResult(ResultCode.ACCEPT, "enter login menu");
    }

}

