package ir.sharif.controller;

import ir.sharif.enums.Menus;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.User;
import ir.sharif.service.AppService;
import ir.sharif.service.UserService;
import ir.sharif.utils.Random;
import ir.sharif.view.Regex;
import ir.sharif.view.terminal.Menu;

public class RegisterController {
    public CommandResult menuEnter(Menus menu) {
        switch (menu){
            case LoginMenu:
                AppService.getInstance().setCurrentMenu(Menus.LoginMenu);
                return new CommandResult(ResultCode.ACCEPT, "enter login menu");
            case ExitMenu:
                AppService.getInstance().setCurrentMenu(Menus.ExitMenu);
                return new CommandResult(ResultCode.ACCEPT, "exit from game");
            default:
                return new CommandResult(ResultCode.FAILED, "menu not found");
        }
    }

    public CommandResult showCurrentMenu() {
        return new CommandResult(ResultCode.ACCEPT, "register menu");
    }

    public CommandResult register(String username, String password, String passwordConfirm,
                                  String nickname, String email) {
        if(!Regex.USERNAME.matches(username)){
            return new CommandResult(ResultCode.FAILED, "username is invalid");
        }
        if(!Regex.PASSWORD.matches(password)){
            return new CommandResult(ResultCode.FAILED, "password is invalid");
        }
        if(!Regex.STRONG_PASSWORD.matches(password)){
            return new CommandResult(ResultCode.FAILED, "password is weak");
        }
        if(!Regex.EMAIL.matches(email)){
            return new CommandResult(ResultCode.FAILED, "email is invalid");
        }
        if(!password.equals(passwordConfirm)){
            return new CommandResult(ResultCode.FAILED, "passwords do not match");
        }
        if(UserService.getInstance().getUserByUsername(username) != null){
            String randomUsername = createRandomUsername(username);
            return new CommandResult(ResultCode.FAILED, "username is already taken\n you can use " + randomUsername);
        }
        // TODO: email validation

        // TODO: show security question
        User user = new User(username, password, nickname, email, null);
        UserService.getInstance().addUser(user);
        return new CommandResult(ResultCode.ACCEPT, "user created successfully");
    }

    private String createRandomUsername(String username){
        StringBuilder usernameTaken = new StringBuilder();
        usernameTaken.append(username).append('-');
        do{
            usernameTaken.append(Random.getRandomInt(0, 9));
        } while(UserService.getInstance().getUserByUsername(usernameTaken.toString()) != null);
        return usernameTaken.toString();
    }

    public CommandResult pickQuestion(int questionNumber, String answer, String answerConfirm) {
        // TODO: pick question
        return null;
    }
}
