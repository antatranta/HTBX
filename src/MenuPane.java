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
		
		GLabel play = new GLabel("PLAY");
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
		for(GLabel menu:mainMenu) {
			program.add(menu);
		}
		program.add(title());
		program.add(selection());
	}

	@Override
	public void hideContents() {
		program.removeAll();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		if(obj == null) {
			return;
		}
		
		obj.setColor(Color.gray);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		for(GLabel menu:mainMenu) {
			menu.setColor(Color.black);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		for(GLabel menu:mainMenu) {
			if(obj == menu) {
				switch(menu.getLabel()) {
					case "PLAY":
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
						
					case "QUIT":
						System.exit(0);
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
		for(GLabel menu:mainMenu) {
			if(obj == menu) {
				selection.setVisible(true);
				switch(menu.getLabel()) {
					case "PLAY":
						selection.setLocation(menu.getX() - 25, menu.getY());
						break;
						
					case "SETTINGS":
						selection.setLocation(menu.getX() - 25, menu.getY());
						break;
						
					case "SCORES":
						selection.setLocation(menu.getX() - 25, menu.getY());
						break;
						
					case "CONTROLS":
						selection.setLocation(menu.getX() - 25, menu.getY());
						break;
					
					case "QUIT":
						selection.setLocation(menu.getX() - 25, menu.getY());
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
}
