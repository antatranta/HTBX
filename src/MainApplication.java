import java.awt.*;

import acm.graphics.*;
import acm.program.*;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
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
	private GameConsole console;
	private GameTimer gameTimer;
	private AudioPlayer audio;
	private boolean musicToggle;
	private boolean sfxToggle;
	private boolean isPaused;
	private boolean lookedAtControls;
	private boolean fromMenu;
	
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
		menu = new MenuPane(this);
		setting = new SettingsPane(this);
		control = new ControlsPane(this);
		score = new ScoresPane(this);
		pause = new PausePane(this);
		
		audio = AudioPlayer.getInstance();
		
		switchToMenu();
	}
	
	public void sfxToggle(boolean toggle) {
		sfxToggle = toggle;
	}
	
	public void setLookedAtControls(boolean looked) {
		lookedAtControls = looked;
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
	
	public void pauseGameTimer() {
		gameTimer.stopTimer();
	}
	
	public void startGameTimer() {
		gameTimer.startTimer();
	}
	
	public void switchToMenu() {
		story = new StoryPane(this);
		isPaused = false;
		
		//audio.stopSound("sounds", "Credits.mp3");
		if(musicToggle) {
			audio.playSound("sounds", "3A1W - Menu.wav", true);
			audio.updatePlayer();
		}
		
		switchToScreen(menu);
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
		
		if(musicToggle) {
			audio.stopSound("sounds", "3A1W - Menu.wav");
//			audio.playSound("sounds", "01 Misconection_1.mp3", true);
			audio.playSound("sounds", "Stone_1.mp3", true);
		}
		
		gameTimer.startTimer();
		switchToScreen(game);
	}
	
	public void switchToGameOver() {
		gameOver = new GameOverPane(this);
		gameTimer.stopTimer();
		isPaused = false;
//		audio.stopSound("sounds", "01 Misconection_1.mp3");
		audio.stopSound("sounds", "Stone_1.mp3");
		switchToScreen(gameOver);
	}
	
	public void switchToSettings() {
		switchToScreen(setting);
	}
	
	public void switchToScores() {
		switchToScreen(score);
	}
	
	public void switchToControls() {
		switchToScreen(control);
	}
	
	public void switchToPause() {
		isPaused = true;
		
//		audio.stopSound("sounds", "01 Misconection_1.mp3");
		audio.stopSound("sounds", "Stone_1.mp3");
		audio.stopSound("sounds", "Credits.mp3");
		if(musicToggle) {
			audio.playSound("sounds", "3A1W - Menu.wav", true);
		}
		gameTimer.stopTimer();
		switchToScreen(pause);
	}
		
	public GameConsole getGameConsole() {
		return console;
	}
	
	public void setMusicToggle(boolean toggle) {
		musicToggle = toggle;
		
		if(musicToggle) {
			audio.playSound("sounds", "3A1W - Menu.wav", true);
		}
		else {
			audio.stopSound("sounds", "3A1W - Menu.wav");
		}
	}
	
	public int getPlayerScore() {
		return console.getScore();
	}
}
