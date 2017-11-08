import java.util.ArrayList;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Font;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GImage;

public class MenuPane extends GraphicsPane {
	private static final Font font = new Font("", Font.BOLD, 20);
	private static final double CENTER_WIDTH = MainApplication.WINDOW_WIDTH / 2;
	private static final double CENTER_HEIGHT = MainApplication.WINDOW_HEIGHT / 2;
	
	private MainApplication program; //you will use program to get access to all of the GraphicsProgram calls
	private ArrayList<GLabel> mainMenu = new ArrayList<GLabel>();
	private GLabel selection;
	private GImage title;
	private GObject obj;
	// TODO possible constant of xPos
	
	public MenuPane(MainApplication app) {
		program = app;
		title = new GImage("HTBX_Title.png");
		selection = new GLabel(">");
		title.setLocation(CENTER_WIDTH - (title.getWidth() / 2), 50);
		selection.setFont(font);
		selection.setColor(Color.white);
		
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
			menu.setLocation((MainApplication.WINDOW_WIDTH / 2) - (menu.getWidth() / 2), yPos);
			menu.setColor(Color.black);
			yPos += 50;
		}
	}
	
	@Override
	public void showContents() {
		for(GLabel menu:mainMenu) {
			program.add(menu);
		}
		program.add(title);
		program.add(selection);
	}

	@Override
	public void hideContents() {
		program.removeAll();
	}
	
	// Main menu options switches panels and/or quits - Anthony
	@Override
	public void mousePressed(MouseEvent e) {
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
	
	// When you hover the mouse over the text it highlights :D - Anthony
	@Override
	public void mouseMoved(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		for(GLabel menu:mainMenu) {
			if(obj == menu) {
				selection.setColor(Color.black);
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
				selection.setColor(Color.white);
			}
		}
	}
}
