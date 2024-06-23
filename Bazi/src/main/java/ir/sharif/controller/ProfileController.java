package ir.sharif.controller;

import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.User;

import ir.sharif.service.UserService;
import org.json.JSONObject;


public class ProfileController {

    // TODO: regex checking for inputs
    public CommandResult changeUsername(String newUsername) {
        User user = UserService.getInstance().getCurrentUser();

        User userByUsername = UserService.getInstance().getUserByUsername(newUsername);
        if(userByUsername != null)
            return new CommandResult(ResultCode.FAILED, "username is already taken");


        user.setUsername(newUsername);
        // TODO: change in database
        return new CommandResult(ResultCode.ACCEPT, "Username changed successfully");
    }

    public CommandResult changeNickname(String newNickname) {
        User user = UserService.getInstance().getCurrentUser();
        user.setNickname(newNickname);
        // TODO: change in database
        return new CommandResult(ResultCode.ACCEPT, "Nickname changed successfully");
    }

    public CommandResult changeEmail(String newEmail) {
        User user = UserService.getInstance().getCurrentUser();
        user.setEmail(newEmail);
        // TODO: change in database
        return new CommandResult(ResultCode.ACCEPT, "Email changed successfully");
    }

    public CommandResult changePassword(String currentPassword, String newPassword) {
        User user = UserService.getInstance().getCurrentUser();
        if(!user.getPassword().equals(currentPassword))
            return new CommandResult(ResultCode.FAILED, "password is incorrect");

        user.setPassword(newPassword);
        // TODO: change in database
        return new CommandResult(ResultCode.ACCEPT, "Email changed successfully");
    }

    public CommandResult showInfo() {
        User user = UserService.getInstance().getCurrentUser();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user.getUsername());
        jsonObject.put("nickname", user.getNickname());
        jsonObject.put("email", user.getEmail());

        
        jsonObject.put("score", user);
        return new CommandResult(ResultCode.ACCEPT, jsonObject.toString());
    }

    public CommandResult showGameHistories(int n) {
        return null;
    }

}
