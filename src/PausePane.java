import java.util.ArrayList;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

//TODO implement pause menu
public class PausePane extends MenuPane {
	private MainApplication program;
	private ArrayList<GLabel> pauseMenu;
	private GObject obj;
	
	public PausePane(MainApplication app) {
		super(app);
		program = app;
		
		pauseMenu = super.getMainMenu();
		
		GLabel continueGame = new GLabel("CONTINUE");
		GLabel exitToMenu = new GLabel("EXIT TO MENU");
		pauseMenu.remove(4);
		pauseMenu.remove(0);
		pauseMenu.add(0, continueGame);
		pauseMenu.add(exitToMenu);
		
		double yPos = CENTER_HEIGHT - (continueGame.getHeight() / 2);
		for(GLabel pause:pauseMenu) {
			pause.setFont(font);
			pause.setColor(Color.black);
			pause.setLocation(CENTER_WIDTH - pause.getWidth(), yPos);
			yPos += 50;
		}
	}
	
	@Override
	public void showContents() {
		for(GLabel pause:pauseMenu) {
			program.add(pause);
		}
		program.add(title());
		program.add(selection());
	}
	
	@Override
	public void hideContents() {
		program.removeAll();
	}
}
