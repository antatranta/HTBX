import java.util.ArrayList;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

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
			pause.setLocation(CENTER_WIDTH - (pause.getWidth() / 2), yPos);
			yPos += 50;
		}
	}
	
	@Override
	public void showContents() {
		program.add(whiteBG());
		for(GLabel pause:pauseMenu) {
			program.add(pause);
		}
		program.add(title());
		program.add(selection());
	}
	
	@Override
	public void hideContents() {
		program.remove(whiteBG());
		for(GLabel pause:pauseMenu) {
			program.remove(pause);
		}
		program.remove(title());
		program.remove(selection());
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		for(GLabel pause:pauseMenu) {
			if(obj == pause) {
				switch(pause.getLabel()) {
					case "CONTINUE":
						program.switchToGame();
						break;
						
					case "SETTINGS":
						program.switchToSettings();
						break;
						
					case "SCORES":
						program.switchToScores();
						break;
						
					case "CONTROLS":
						program.switchToControls();
						break;
						
					case "EXIT TO MENU":
						program.switchToMenu();
						break;
						
					default:
						break;
				}
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		for(GLabel pause:pauseMenu) {
			if(obj == pause) {
				selection.setVisible(true);
				switch(pause.getLabel()) {
					case "CONTINUE":
						selection.setLocation(pause.getX() - 25, pause.getY());
						break;
						
					case "SETTINGS":
						selection.setLocation(pause.getX() - 25, pause.getY());
						break;
						
					case "SCORES":
						selection.setLocation(pause.getX() - 25, pause.getY());
						break;
						
					case "CONTROLS":
						selection.setLocation(pause.getX() - 25, pause.getY());
						break;
					
					case "EXIT TO MENU":
						selection.setLocation(pause.getX() - 25, pause.getY());
						break;
						
					default:
						break;
				}
			}
			else if(obj == null) {
				selection.setVisible(false);
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key) {
			case KeyEvent.VK_ESCAPE:
				program.switchToGame();
		}
	}
}
