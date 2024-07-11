package ir.sharif.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.sharif.client.TCPClient;
import ir.sharif.enums.Menus;
import ir.sharif.enums.ResultCode;
import ir.sharif.messages.ServerMessage;
import ir.sharif.model.CommandResult;
import ir.sharif.model.User;
import ir.sharif.service.AppService;
import ir.sharif.service.UserService;
import ir.sharif.utils.FileSaver;
import ir.sharif.view.Regex;
import ir.sharif.view.terminal.Menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

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

        UserService.getInstance().setCurrentUser(user);

        if(stayLoggedIn){
                stayLogin();
        }

        return new CommandResult(ResultCode.ACCEPT, "login successful");
    }

    public void stayLogin(){
        FileSaver.saveObject(UserService.getInstance().getCurrentUser(), "local_storage.stg");
    }

    public CommandResult isLogin(){
        try {
            User user = (User) FileSaver.loadObject("local_storage.stg");
            if(user == null) return new CommandResult(ResultCode.FAILED, "user not logged in");
            UserService.getInstance().setCurrentUser(user);
            return new CommandResult(ResultCode.ACCEPT, "user logged in");
        } catch (Exception e) {
            return  new CommandResult(ResultCode.FAILED, "user not logged in");
        }
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
