import java.util.ArrayList;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Font;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private static final Font font = new Font("", Font.BOLD, 24);	
	private MainApplication program; //you will use program to get access to all of the GraphicsProgram calls
	private GLabel play;
	private ArrayList<GLabel> mainMenu;
	
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

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		
		if(obj == play) {
			program.switchToSome();
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == play) {
			play.setColor(Color.white);
		}
		else {
			play.setColor(Color.gray);
		}
	}
}
