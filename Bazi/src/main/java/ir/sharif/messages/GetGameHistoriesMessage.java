package ir.sharif.messages;

public class GetGameHistoriesMessage extends ClientMessage{
    public GetGameHistoriesMessage(){
        this.type = ClientMessageType.GET_GAME_HISTORIES_MESSAGE;
    }
}
