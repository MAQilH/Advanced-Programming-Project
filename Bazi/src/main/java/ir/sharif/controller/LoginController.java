package ir.sharif.controller;

import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.User;
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
        User user = UserService.getInstance().getUserByUsername(username);
        if(user == null)
            return new CommandResult(ResultCode.NOT_FOUND, "user not found");
        if(!user.getPassword().equals(password))
            return new CommandResult(ResultCode.NOT_FOUND, "password is incorrect");

        UserService.getInstance().setStayLoggedIn(stayLoggedIn);
        UserService.getInstance().setCurrentUser(user);
        return new CommandResult(ResultCode.ACCEPT, "login successful");
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
