package ir.sharif.controller;

import ir.sharif.model.CommandResult;
import ir.sharif.service.UserService;
import ir.sharif.view.terminal.Menu;

public class RegisterController {
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

    public CommandResult register(String username, String password, String passwordConfirm,
                                  String nickname, String email) {
        return null;
    }

    public CommandResult pickQuestion(int questionNumber, String answer, String answerConfirm) {
        return null;
    }
}
