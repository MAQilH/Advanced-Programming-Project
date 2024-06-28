package ir.sharif.messages;

import ir.sharif.model.User;

public class LoginMessage extends ClientMessage {
    private String username;
    private String password;

    public LoginMessage(String username, String password) {
        this.username = username;
        this.password = password;
        this.type = ClientMessageType.LOGIN_MESSAGE;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
