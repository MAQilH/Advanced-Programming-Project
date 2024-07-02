package ir.sharif.service;

import ir.sharif.client.TCPClient;
import ir.sharif.messages.tournament.CreateTournamentMessage;

public class TournamentService {
    private static TournamentService instance;
    private TournamentService() {}

    public static TournamentService getInstance() {
        if(instance == null) instance = new TournamentService();
        return instance;
    }

    public String createTournament(){
        CreateTournamentMessage createTournamentMessage = new CreateTournamentMessage(UserService.getInstance().getCurrentUser());
        TCPClient tcpClient = new TCPClient();
//        tcpClient.
        return null;
    }
}
