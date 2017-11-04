import java.util.ArrayList;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Font;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private static final Font font = new Font("", Font.BOLD, 20);	
	
	private double yPos = MainApplication.WINDOW_HEIGHT / 2; // used for keeping a yPos for main menu and incrementing for other options in the main menu
	private MainApplication program; //you will use program to get access to all of the GraphicsProgram calls
	private ArrayList<GLabel> mainMenu = new ArrayList<GLabel>();
	// TODO possible constant of xPos
	
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
		
		for(GLabel menu:mainMenu) {
			menu.setFont(font);
			menu.setLocation(MainApplication.WINDOW_WIDTH / 2, yPos);
			menu.setColor(Color.white);
			yPos = 50 + yPos;
		}
	}
	
	@Override
	public void showContents() {
		for(GLabel menu:mainMenu) {
			program.add(menu);
		}
	}

	@Override
	public void hideContents() {
		for(GLabel menu:mainMenu) {
			program.remove(menu);
		}
	}
	
	// Main menu options switches panels and/or quits - Anthony
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		for(GLabel menu:mainMenu) {
			if(obj == menu) {
				switch(menu.getLabel()) {
					case "PLAY":
						program.switchToSome();
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
	
	// When you hover the mouse over the text it highlights :D - Anthony
	@Override
	public void mouseMoved(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		for(GLabel menu:mainMenu) {
			if(obj == menu) {
				menu.setColor(Color.white);
			}
			else {
				menu.setColor(Color.gray);
			}
		}
	}
}
