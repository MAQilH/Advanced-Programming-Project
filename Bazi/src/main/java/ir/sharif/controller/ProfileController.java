package ir.sharif.controller;

import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.GameHistory;
import ir.sharif.model.User;

import ir.sharif.service.AppService;
import ir.sharif.service.GameHistoryService;
import ir.sharif.service.UserService;
import ir.sharif.view.Regex;
import org.json.JSONObject;

import java.util.ArrayList;


public class ProfileController {

    public CommandResult changeUsername(String newUsername) {
        if(!Regex.USERNAME.matches(newUsername))
            return new CommandResult(ResultCode.FAILED, "new username is invalid");

        User user = UserService.getInstance().getCurrentUser();

        User userByUsername = UserService.getInstance().getUserByUsername(newUsername);
        if(userByUsername != null)
            return new CommandResult(ResultCode.FAILED, "username is already taken");


        user.setUsername(newUsername);
        return new CommandResult(ResultCode.ACCEPT, "Username changed successfully");
    }

    public CommandResult changeNickname(String newNickname) {
        User user = UserService.getInstance().getCurrentUser();
        user.setNickname(newNickname);
        return new CommandResult(ResultCode.ACCEPT, "Nickname changed successfully");
    }

    public CommandResult changeEmail(String newEmail) {
        if(!Regex.EMAIL.matches(newEmail)){
            return new CommandResult(ResultCode.FAILED, "new email is invalid");
        }
        User user = UserService.getInstance().getCurrentUser();
        user.setEmail(newEmail);
        return new CommandResult(ResultCode.ACCEPT, "Email changed successfully");
    }

    public CommandResult changePassword(String currentPassword, String newPassword) {
        if(!Regex.PASSWORD.matches(newPassword)){
            return new CommandResult(ResultCode.FAILED, "password is invalid");
        }
        if(!Regex.STRONG_PASSWORD.matches(newPassword)){
            return new CommandResult(ResultCode.FAILED, "password is weak");
        }

        User user = UserService.getInstance().getCurrentUser();
        if(!user.getPassword().equals(currentPassword))
            return new CommandResult(ResultCode.FAILED, "password is incorrect");

        user.setPassword(newPassword);
        return new CommandResult(ResultCode.ACCEPT, "password changed successfully");
    }

    public CommandResult showInfo() {
        User user = UserService.getInstance().getCurrentUser();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user.getUsername());
        jsonObject.put("nickname", user.getNickname());
        jsonObject.put("maxScore", GameHistoryService.getInstance().getHighestScore(user.getUsername()));
        jsonObject.put("rank", GameHistoryService.getInstance().getUserRank(user.getUsername()));
        jsonObject.put("numberOfGames", GameHistoryService.getInstance().getNumberOfGames(user.getUsername()));
        jsonObject.put("numberOfDraws", GameHistoryService.getInstance().getNumberOfDraws(user.getUsername()));
        jsonObject.put("numberOfWins", GameHistoryService.getInstance().getNumberOfWins(user.getUsername()));
        jsonObject.put("numberOfLoose", GameHistoryService.getInstance().getNumberOfLosses(user.getUsername()));

        return new CommandResult(ResultCode.ACCEPT, jsonObject.toString());
    }

    public CommandResult showGameHistories(int n) {
        if(n <= 1){
            return new CommandResult(ResultCode.FAILED, "number of games is invalid");
        }
        User currentUser = UserService.getInstance().getCurrentUser();
        ArrayList<GameHistory> histories = new ArrayList<>();
        ArrayList<GameHistory> allHistories = GameHistoryService.getInstance().getUserHistory(currentUser.getUsername());
        for(int i = Math.max(0, allHistories.size() - n); i < allHistories.size(); i++){
            GameHistory gameHistory = allHistories.get(i);
            if(gameHistory.getUser2().getUsername().equals(currentUser.getUsername())){
                User tmp = gameHistory.getUser1();
                gameHistory.setUser1(gameHistory.getUser2());
                gameHistory.setUser2(tmp);
            }
            histories.add(allHistories.get(i));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("histories", histories);
        return new CommandResult(ResultCode.ACCEPT, jsonObject.toString());
    }
}
