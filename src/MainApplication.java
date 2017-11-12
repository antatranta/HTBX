import java.awt.*;

import acm.graphics.*;
import acm.program.*;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final Vector2 WINDOW_CENTER = new Vector2(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);
	
	private GamePane game;
	private MenuPane menu;
	private SettingsPane setting;
	private ScoresPane score;
	private ControlsPane control;
	private PausePane pause;
	private GameConsole console;
	private GameTimer gameTimer;
	private int TIMER_INTERVAL = 20;
	private int INITIAL_DELAY = 0;
	private AudioPlayer audio;
	private boolean musicToggle;
	private boolean sfxToggle;
	private boolean isPaused;
	//private int count = 0;
	
	
	public void init() {
		console = new GameConsole();
		gameTimer = new GameTimer();
		gameTimer.setupTimer(TIMER_INTERVAL, INITIAL_DELAY);
		setTitle("HTBX");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setBackground(Color.white);
	}
	
	public void run() {
		game = new GamePane(this);
		menu = new MenuPane(this);
		setting = new SettingsPane(this);
		control = new ControlsPane(this);
		score = new ScoresPane(this);
		pause = new PausePane(this);
		audio = AudioPlayer.getInstance();
		gameTimer.addListener(game);
		musicToggle = true;
		sfxToggle = true;
		isPaused = false;
		
		switchToMenu();
	}
	
	public void sfxToggle(boolean toggle) {
		sfxToggle = toggle;
	}
	
	public boolean getSfxToggle() {
		return sfxToggle;
	}
	
	public boolean isPaused() {
		return isPaused;
	}
	
	public void switchToMenu() {
		isPaused = false;
		audio.stopSound("sounds", "Credits.mp3");
		if(musicToggle) {
			audio.playSound("sounds", "3A1W - Menu.wav", true);
		}
		/*switch(count % 2) {
			case 0: audio.stopSound("sounds", "r2d2.mp3"); break;
			case 1: audio.stopSound("sounds", "somethinlikethis.mp3"); break;
		}*/
		//count++;
		switchToScreen(menu);
	}
	
	public void switchToGame() {
		if(musicToggle) {
			audio.stopSound("sounds", "3A1W - Menu.wav");
			audio.playSound("sounds", "01 Misconection_1.mp3", true);
		}
		/*switch(count % 2) {
			case 0: audio.playSound("sounds", "r2d2.mp3"); break;
			case 1: audio.playSound("sounds", "somethinlikethis.mp3"); break;
		}*/
		gameTimer.startTimer();
		switchToScreen(game);
	}
	
	public void switchToSettings() {
		if(musicToggle) {
			audio.stopSound("sounds", "3A1W - Menu.wav");
			audio.playSound("sounds", "Credits.mp3", true);
		}
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
		
		audio.stopSound("sounds", "01 Misconection_1.mp3");
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
	
	public void musicToggle(boolean toggle) {
		musicToggle = toggle;
		
		if(musicToggle) {
			audio.playSound("sounds", "Credits.mp3", true);
		}
		else {
			audio.stopSound("sounds", "Credits.mp3");
		}
	}
}
