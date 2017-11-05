import java.awt.*;

import acm.graphics.*;
import acm.program.*;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	
	private GamePane game_pane;
	private MenuPane menu;
	private GameConsole console;
	private int count = 0;
	
	
	public void init() {
		console = new GameConsole();
		setTitle("HTBX");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setBackground(Color.black);
	}
	
	public void run() {
		game_pane = new GamePane(this);
		menu = new MenuPane(this);
		switchToMenu();
	}
	
	public void switchToMenu() {
		AudioPlayer audio = AudioPlayer.getInstance();
		/*switch(count % 2) {
			case 0: audio.stopSound("sounds", "r2d2.mp3"); break;
			case 1: audio.stopSound("sounds", "somethinlikethis.mp3"); break;
		}*/
		count++;
		switchToScreen(menu);
	}
	
	public void switchToSome() {
		AudioPlayer audio = AudioPlayer.getInstance();
		/*switch(count % 2) {
			case 0: audio.playSound("sounds", "r2d2.mp3"); break;
			case 1: audio.playSound("sounds", "somethinlikethis.mp3"); break;
		}*/
		switchToScreen(game_pane);
	}
	
	public GameConsole getGameConsole() {
		return console;
	}
}
