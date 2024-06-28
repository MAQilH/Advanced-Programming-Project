package ir.sharif.controller;

import ir.sharif.client.TCPClient;
import ir.sharif.enums.Menus;
import ir.sharif.enums.ResultCode;
import ir.sharif.messages.ServerMessage;
import ir.sharif.model.CommandResult;
import ir.sharif.model.User;
import ir.sharif.service.AppService;
import ir.sharif.service.UserService;
import ir.sharif.view.Regex;
import ir.sharif.view.terminal.Menu;

public class LoginController {
    public CommandResult menuEnter(Menus menu) {
        switch (menu){
            case RegisterMenu:
                AppService.getInstance().setCurrentMenu(Menus.LoginMenu);
                return new CommandResult(ResultCode.ACCEPT, "enter register menu");
            case ExitMenu:
                AppService.getInstance().setCurrentMenu(Menus.ExitMenu);
                return new CommandResult(ResultCode.ACCEPT, "exit from game");
            default:
                return new CommandResult(ResultCode.FAILED, "menu not found");
        }
    }

    public CommandResult showCurrentMenu() {
        return new CommandResult(ResultCode.ACCEPT, "login menu");
    }

    public CommandResult login(String username, String password, boolean stayLoggedIn) {
        User user = UserService.getInstance().getUserByUsername(username);
        if(user == null)
            return new CommandResult(ResultCode.NOT_FOUND, "user not found");
        if(!user.getPassword().equals(password))
            return new CommandResult(ResultCode.FAILED, "password is incorrect");

        UserService.getInstance().setStayLoggedIn(stayLoggedIn);
        UserService.getInstance().setCurrentUser(user);
        return new CommandResult(ResultCode.ACCEPT, "login successful");
    }

    public CommandResult forgotPassword(String username) {
        // TODO: forget password
        return null;
    }

    public CommandResult answerQuestion(int questionNumber, String answer) {
        // TODO: answer question
        return null;
    }

    public CommandResult setPassword(String password) {
        if(!Regex.PASSWORD.matches(password))
            return new CommandResult(ResultCode.FAILED, "password is invalid");
        if(!Regex.STRONG_PASSWORD.matches(password))
            return new CommandResult(ResultCode.FAILED, "password is weak");

        User user = UserService.getInstance().getCurrentUser();
        user.setPassword(password);
        UserService.getInstance().changeUser(user.getUsername(), user);

        return new CommandResult(ResultCode.ACCEPT, "password changed successfully");
    }
}
