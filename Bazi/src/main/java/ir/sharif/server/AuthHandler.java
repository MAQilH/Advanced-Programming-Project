package ir.sharif.server;

import ir.sharif.enums.ResultCode;
import ir.sharif.messages.LoginMessage;
import ir.sharif.messages.RegisterMessage;
import ir.sharif.messages.ServerMessage;
import ir.sharif.model.User;
import ir.sharif.service.storage.Database;

public class AuthHandler {
    Database database;

    public AuthHandler(){
        database = Database.getInstance();
    }
    public ServerMessage register(RegisterMessage registerMessage) {
        User user = database.getUserWithUsername(registerMessage.getUser().getUsername());
        if(user != null)
            return new ServerMessage(ResultCode.FAILED, "User already exists");
        database.addUser(registerMessage.getUser());
        return new ServerMessage(ResultCode.ACCEPT, "User registered successfully");
    }

    public ServerMessage login(LoginMessage loginMessage) {
        User user = Database.getInstance().getUserWithUsername(loginMessage.getUsername());
        if(user == null)
            return new ServerMessage(ResultCode.FAILED, "User not found");
        if(!user.getPassword().equals(loginMessage.getPassword()))
            return new ServerMessage(ResultCode.FAILED, "Password is incorrect");
        return new ServerMessage(ResultCode.ACCEPT, "Login successful");
    }
}
