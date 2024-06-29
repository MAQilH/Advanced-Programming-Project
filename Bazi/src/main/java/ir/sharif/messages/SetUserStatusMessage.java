package ir.sharif.messages;

public class SetUserStatusMessage extends ClientMessage {
    private String username;
    boolean status;

    public SetUserStatusMessage(String username, boolean status){
        this.username = username;
        this.status = status;
        this.type = ClientMessageType.USER_SET_STATUS_MESSAGE;
    }

    public String getUsername(){
        return username;
    }

    public boolean getStatus(){
        return status;
    }
}
