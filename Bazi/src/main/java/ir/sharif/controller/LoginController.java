package ir.sharif.controller;

import ir.sharif.model.CommandResult;
import ir.sharif.service.UserService;
import ir.sharif.view.terminal.Menu;

public class LoginController {
    private UserService userService;
    public CommandResult menuEnter(Menu menu) {
        return null;
    }

    public CommandResult menuExit() {
        return null;
    }

    public CommandResult showCurrentMenu() {
        return null;
    }

    public CommandResult login(String username, String password, boolean stayLoggedIn) {
        return null;
    }

    public CommandResult forgotPassword(String username) {
        return null;
    }

    public CommandResult answerQuestion(int questionNumber, String answer) {
        return null;
    }

    public CommandResult setPassword(String password) {
        return null;
    }
}
