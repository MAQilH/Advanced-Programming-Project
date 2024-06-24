package ir.sharif.service;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class BackgroundMusicService {
	private static BackgroundMusicService instance;
	private MediaPlayer player;

	private BackgroundMusicService() {
		Media media = new Media(BackgroundMusicService.class.getResource("/sounds/background.mp3").toExternalForm());
		player = new MediaPlayer(media);
		player.setCycleCount(MediaPlayer.INDEFINITE);
	}

	public static BackgroundMusicService getInstance() {
		if (instance == null)
			instance = new BackgroundMusicService();
		return instance;
	}

	public void playMusic() {
		player.play();
	}
}
