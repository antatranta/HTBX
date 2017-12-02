import java.awt.*;

import acm.graphics.*;
import acm.program.*;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 900;
	public static final int WINDOW_HEIGHT = 600;
	public static final Vector2 WINDOW_CENTER = new Vector2(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);

	private StoryPane story;
	private GamePane game;
	private MenuPane menu;
	private SettingsPane setting;
	private ScoresPane score;
	private ControlsPane control;
	private PausePane pause;
	private GameOverPane gameOver;
	private CreditsPane credits;
	private GameConsole console;
	private GameTimer gameTimer;
	private boolean musicToggle;
	private boolean sfxToggle;
	private boolean isPaused;
	private boolean lookedAtControls;
	private boolean fromMenu;
	private boolean fromCredits;

	public void init() {
		setTitle("HTBX");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setBackground(Color.white);
	}

	public void run() {
		musicToggle = true;
		sfxToggle = true;
		isPaused = false;
		lookedAtControls = false;
		fromMenu = true;
		fromCredits = false;
		menu = new MenuPane(this);
		setting = new SettingsPane(this);
		control = new ControlsPane(this);
		pause = new PausePane(this);
		

		switchToMenu();
	}

	public void sfxToggle(boolean toggle) {
		sfxToggle = toggle;
		AudioPlayer myAudio = AudioPlayer.getInstance();
		myAudio.sfxToggle(toggle);
	}

	public void setLookedAtControls(boolean looked) {
		lookedAtControls = looked;
	}
	
	public void setPaused(boolean paused) {
		isPaused = paused;
	}

	public boolean getSfxToggle() {
		return sfxToggle;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public boolean getMusicToggle() {
		return musicToggle;
	}

	public boolean lookedAtControls() {
		return lookedAtControls;
	}

	public boolean getFromMenu() {
		return fromMenu;
	}
	
	public boolean getFromCredits() {
		return fromCredits;
	}

	public void switchToMenu() {
		story = new StoryPane(this);
		fromMenu = true;
		isPaused = false;

		AudioPlayer audio = AudioPlayer.getInstance();
		if(musicToggle) {
			audio.stopSound("sounds", "bensound-ofeliasdream.mp3");
			audio.stopSound("sounds", "bensound-sadday.mp3");
			audio.playSound("sounds", "3A1W - Menu.wav", true, SoundType.Music);
			audio.updatePlayer();
		}

		switchToScreen(menu);
	}

	public void setStory(int num) {
		story.setSwitchStory(num);
	}
	
	public void switchToStory() {
		fromMenu = false;
		if(gameTimer != null) {
			gameTimer.stopTimer();
		}

		switchToScreen(story);
	}

	public void switchToGame() {
		if(!isPaused) {
			removeAll();
			console = new GameConsole();
			gameTimer = console.getTimer();
			game = new GamePane(this);
			gameTimer.addListener(game);
			console.addGraphicsSubscriber(game);
		}
		AudioPlayer audio = AudioPlayer.getInstance();

		if(musicToggle) {
			audio.stopSound("sounds", "bensound-ofeliasdream.mp3");
			audio.stopSound("sounds", "bensound-sadday.mp3");
			audio.stopSound("sounds", "3A1W - Menu.wav");
			audio.playSound("sounds", "Stone_1.mp3", true, SoundType.Music);
		}

		gameTimer.startTimer();
		switchToScreen(game);
	}

	public void switchToGameOver() {
		isPaused = false;
		gameTimer.stopTimer();
		gameOver = new GameOverPane(this);
		
		AudioPlayer audio = AudioPlayer.getInstance();
		if(musicToggle) {

			if(!getFromCredits()) {
				audio.stopSound("sounds", "Stone_1.mp3");
				audio.playSound("sounds", "bensound-ofeliasdream.mp3", true, SoundType.Music);	
			}
		}
		switchToScreen(gameOver);
	}
	
	public void switchToCredits() {
		fromCredits = true;
		gameTimer.stopTimer();
		credits = new CreditsPane(this);
		
		AudioPlayer audio = AudioPlayer.getInstance();
		if(musicToggle) {
			audio.stopSound("sounds", "Stone_1.mp3");
			audio.playSound("sounds", "bensound-sadday.mp3", SoundType.Music);
		}
		switchToScreen(credits);
	}

	public void switchToSettings() {
		switchToScreen(setting);
	}

	public void switchToScores() {
		score = new ScoresPane(this);
		switchToScreen(score);
	}

	public void switchToControls() {
		switchToScreen(control);
	}

	public void switchToPause() {
		isPaused = true;
		fromMenu = true;

		AudioPlayer audio = AudioPlayer.getInstance();
		audio.stopSound("sounds", "Stone_1.mp3");
		audio.stopSound("sounds", "Credits.mp3");
		if(musicToggle) {
			audio.playSound("sounds", "3A1W - Menu.wav", true, SoundType.Music);
		}
		gameTimer.stopTimer();
		switchToScreen(pause);
	}

	public GameConsole getGameConsole() {
		return console;
	}

	public void setMusicToggle(boolean toggle) {
		musicToggle = toggle;
		
		AudioPlayer audio = AudioPlayer.getInstance();

		if(musicToggle) {
			audio.playSound("sounds", "3A1W - Menu.wav", true, SoundType.Music);
		}
		else {
			audio.stopSound("sounds", "3A1W - Menu.wav");
		}
	}
	
	public int getPlayerScore() {
		return console.getScore();
	}
}
