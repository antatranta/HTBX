import java.util.ArrayList;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private MainApplication program;
	private ArrayList<GLabel> mainMenu = new ArrayList<GLabel>();
	private GObject obj;
	
	public MenuPane(MainApplication app) {
		program = app;
		
		GLabel play = new GLabel("NEW GAME");
		GLabel settings = new GLabel("SETTINGS");
		GLabel scores = new GLabel("SCORES");
		GLabel controls = new GLabel("CONTROLS");
		GLabel quit = new GLabel("QUIT");
		mainMenu.add(play);
		mainMenu.add(settings);
		mainMenu.add(scores);
		mainMenu.add(controls);
		mainMenu.add(quit);
		
		double yPos = CENTER_HEIGHT - (play.getHeight() / 2);
		for(GLabel menu:mainMenu) {
			menu.setFont(font);
			menu.setColor(Color.black);
			menu.setLocation((CENTER_WIDTH) - (menu.getWidth() / 2), yPos);
			yPos += 50;
		}
	}
	
	public ArrayList<GLabel> getMainMenu() {
		return mainMenu;
	}
	
	@Override
	public void showContents() {
		program.add(whiteBG());
		for(GLabel menu:mainMenu) {
			program.add(menu);
		}
		program.add(title());
		program.add(selection());
	}

	@Override
	public void hideContents() {
		program.remove(whiteBG());
		for(GLabel menu:mainMenu) {
			program.remove(menu);
		}
		program.remove(title());
		program.remove(selection());
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		if(obj == null || obj == whiteBG()) {
			return;
		}
		
		obj.setColor(Color.gray);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		for(GLabel menu:mainMenu) {
			menu.setColor(Color.black);
		}
		
		obj = program.getElementAt(e.getX(), e.getY());
		if(obj != whiteBG() || obj != title()) {
			for(GLabel menu:mainMenu) {
				if(obj == menu) {
					switch(menu.getLabel()) {
						case "NEW GAME":
							program.switchToStory();
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
							
						case "QUIT":
							System.exit(0);
							break;
							
						default:
							break;
					}
				}
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		
		if(obj != whiteBG() && obj != title()) {
			for(GLabel menu:mainMenu) {
				if(obj == menu) {
					selection.setVisible(true);
					selection.setLocation(menu.getX() - 25, menu.getY());
				}
			}
		}
		else {
			selection.setVisible(false);
		}
	}
}
