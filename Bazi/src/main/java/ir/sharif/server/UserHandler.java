package ir.sharif.server;

import ir.sharif.enums.ResultCode;
import ir.sharif.messages.GetUserStatusMessage;
import ir.sharif.messages.ServerMessage;
import ir.sharif.messages.SetUserStatusMessage;

import java.util.HashMap;

public class UserHandler {
    HashMap<String, Boolean> userStatus = new HashMap<>();

    private static UserHandler instance;

    private UserHandler() {}

    public static UserHandler getInstance(){
        if(instance == null) instance = new UserHandler();
        return instance;
    }

    public ServerMessage getUserStatus(GetUserStatusMessage getUserStatusMessage){
        String username = getUserStatusMessage.getUsername();
        Boolean status = userStatus.getOrDefault(username, false);
        return new ServerMessage(ResultCode.ACCEPT, status.toString());
    }

    public ServerMessage setUserStatus(SetUserStatusMessage setUserStatusMessage){
        String username = setUserStatusMessage.getUsername();
        Boolean status = setUserStatusMessage.getStatus();

        userStatus.put(username, status);
        return new ServerMessage(ResultCode.ACCEPT, "user status set successfully");
    }

    public HashMap<String, Boolean> getUserStatus() {
        return userStatus;
    }
}
