package ir.sharif.messages;

public class GetUserStatusMessage extends ClientMessage{
    private String username;

    public GetUserStatusMessage(String username){
        this.username = username;
        this.type = ClientMessageType.USER_GET_STATUS_MESSAGE;
    }

    public String getUsername(){
        return username;
    }
}
