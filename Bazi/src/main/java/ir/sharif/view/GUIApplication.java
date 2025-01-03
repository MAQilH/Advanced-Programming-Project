package ir.sharif.view;

import ir.sharif.client.TCPClient;
import ir.sharif.controller.LoginController;
import ir.sharif.controller.PreGameController;
import ir.sharif.controller.RegisterController;
import ir.sharif.model.SecurityQuestion;
import ir.sharif.model.User;
import ir.sharif.server.TwoFactorAuth;
import ir.sharif.service.UserService;
import ir.sharif.utils.ConstantsLoader;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GUIApplication extends Application {
    private static String username;

    @Override
	public void start(Stage primaryStage) throws Exception {
//	    System.err.println(TwoFactorAuth.getInstance().sendAuthCode("sohsoh84@gmail.com"));
	    //Start.count++;
	    //System.err.println(TwoFactorAuth.getInstance().sendAuthCode("sohsoh84@gmail.com"));
//		test(primaryStage);
//	    runLobby();
	//	test(primaryStage);
	    ViewLoader.setStage(primaryStage);
	    Font.loadFont(getClass().getResource("/fonts/KingsCross.ttf").toExternalForm(), 10);
	    Font.loadFont(getClass().getResource("/fonts/Ancient.ttf").toExternalForm(), 10);

	    //BackgroundMusicService.getInstance().playMusic();
	    primaryStage.setTitle(ConstantsLoader.getInstance().getProperty("app.title"));
	    ViewLoader.newScene("start");
	}

	public void test(Stage primaryStage) {
		ViewLoader.setStage(primaryStage);
		Font.loadFont(getClass().getResource("/fonts/KingsCross.ttf").toExternalForm(), 10);
		Font.loadFont(getClass().getResource("/fonts/Ancient.ttf").toExternalForm(), 10);

		primaryStage.setTitle(ConstantsLoader.getInstance().getProperty("app.title"));
		// primaryStage.initStyle(StageStyle.UNDECORATED);
		ViewLoader.newScene("start");
		//Pane pane = (Pane) (ViewLoader.getStage().getScene().getRoot());
		//pane.getChildren().add(new CardGraphics(CardTypes.KAYRAN.getInstance(), 0.5));
//		BackgroundMusicService.getInstance().playMusic();
		new RegisterController().register("sohsoh", "Soheil@84", "Soheil@84", new SecurityQuestion("fuck", "fuck"), "sohsoh", "sohsoh84@gmail.com");
		new RegisterController().register("guest", "Soheil@84", "Soheil@84", new SecurityQuestion("fuck", "fuck"), "guest", "aqil@gmail.com");
		new RegisterController().register("aqil", "Soheil@84", "Soheil@84", new SecurityQuestion("fuck", "fuck"), "aqil", "sohsoh84@gmail.com");
        new RegisterController().register("liqa", "Soheil@84", "Soheil@84", new SecurityQuestion("fuck", "fuck"), "aqil", "sohsoh84@gmail.com");

        //ViewLoader.newScene("pregame");
		//		PreGameController preGameController = new PreGameController();
//		System.err.println(preGameController.createGame("guest").statusCode());
//		System.err.println(preGameController.loadDeck("test").statusCode());
//		System.err.println(preGameController.changeTurn().statusCode());
//		System.err.println(preGameController.loadDeck("test").statusCode());
//		System.err.println(preGameController.changeTurn().statusCode());
//		System.err.println(preGameController.startGame().statusCode());
//
//		GameService.getInstance().createController();
//		ViewLoader.newScene("game");

		//addAFriendRequest();
	//	ViewLoader.newScene("main");
		runLobby();
	}

	void addAFriendRequest() {
		new LoginController().login("sohsoh", "Soheil@84", true);
		TCPClient client = new TCPClient();
		client.sendFriendRequest("aqil");
		new LoginController().login("aqil", "Soheil@84", true);
		System.err.println(UserService.getInstance().getCurrentUser().getUsername());
		client.acceptFriendRequest("sohsoh");
	}

    void testStartGame(){
        User aqil = UserService.getInstance().getUserByUsername("aqil");
        User liqa = UserService.getInstance().getUserByUsername("liqa");
        TCPClient tcpClient = new TCPClient();
        String realToken = tcpClient.gameRequest(aqil, "liqa", false);
        String token;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String token;
                while(true){
                    TCPClient tcpClient = new TCPClient();
                    token = tcpClient.getQueuedGame("liqa");
                    if(token != null){
                        break;
                    }
                    System.out.println(tcpClient.getQueuedGame("liqa"));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                TCPClient tcpClient = new TCPClient();
                User user1 = tcpClient.gameAcceptRequest(token, liqa);
                System.err.println("game started by user 2, and name of user 1 is: " + user1.getUsername());
            }
        });

        thread.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String token;
                User opponent;
                while(true){
                    TCPClient tcpClient = new TCPClient();
                    opponent = tcpClient.gameIsAccepted(realToken);
                    if(opponent != null){
                        break;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.err.println("game started by user1");
            }
        });

        thread2.start();



//		thread.start();
    }

    public void runLobby() {
        new LoginController().login(username, "Soheil@84", true);

        PreGameController controller = new PreGameController();
        controller.loadDeck("test");
        controller.setDeck(controller.getDeck());
        ViewLoader.newScene("lobby");
    }

	public static void main(String[] args) {
		//username = args[0];
	  // String felan = TwoFactorAuth.getInstance().sendAuthCode("sohsoh84@gmail.com");
	//	System.err.println(felan);
		launch();
	}
}
