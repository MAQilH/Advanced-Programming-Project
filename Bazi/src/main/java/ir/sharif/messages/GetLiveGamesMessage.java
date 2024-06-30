package ir.sharif.messages;

public class GetLiveGamesMessage extends ClientMessage{
    public GetLiveGamesMessage (){
        this.type = ClientMessageType.GET_LIVE_GAMES_MESSAGE;
    }
}
