import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GImage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class ControlsPane extends GraphicsPane implements ActionListener {
	private MainApplication program;
	private GImage controls;
	private GLabel back;
	private GLabel clickToContinue;
	private GObject obj;
	private int colorRed;
	private int colorBlue;
	private int colorGreen;
	private int i;
	private int j;
	private int k;
	
	public ControlsPane(MainApplication app) {
		program = app;
		colorRed = 0;
		colorBlue = 0;
		colorGreen = 0;
		i = 1;
		j = 1;
		k = 1;
		
		controls = new GImage("controls.gif", 0, 0);
		controls.setLocation((CENTER_WIDTH - (controls.getWidth() / 2)), (CENTER_HEIGHT - (controls.getHeight() / 2) + 60));
		
		back = new GLabel("BACK");
		back.setFont(font);
		back.setColor(Color.black);
		back.setLocation(CENTER_WIDTH - (back.getWidth() / 2), CENTER_HEIGHT - (back.getHeight() / 2) + 250);
		
//		continueToGame = new GLabel("CONTINUE");
//		continueToGame.setFont(font);
//		continueToGame.setColor(Color.black);
//		continueToGame.setLocation(CENTER_WIDTH - (continueToGame.getWidth() / 2), CENTER_HEIGHT - (continueToGame.getHeight() / 2) + 250);
		
		clickToContinue = new GLabel("CLICK TO CONTINUE...", 0, 0);
		clickToContinue.setFont(font);
		clickToContinue.setColor(new Color(colorRed, colorBlue, colorGreen));
		clickToContinue.setLocation(5, MainApplication.WINDOW_HEIGHT - 10);
	}
	
	@Override
	public void showContents() {
		program.add(whiteBG());
		program.add(controls);
		program.add(title());
		program.add(selection());
		if(program.getFromMenu() || program.isPaused()) {
			program.add(back);
			selection.setLocation(back.getX() - 25, back.getY());
		}
		else {
			Timer fadingLabel = new Timer(5, this);
			fadingLabel.start();
			program.add(clickToContinue);
		}
	}

	@Override
	public void hideContents() {
		program.remove(whiteBG());
		program.remove(title());
		if(program.getFromMenu() || program.isPaused()) {
			program.remove(back);
		}
		else {
			program.remove(clickToContinue);
		}
		program.remove(selection());
		program.remove(controls);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!program.getFromMenu() && !program.isPaused()) {
			program.switchToGame();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		if(obj == back) {
			back.setColor(Color.gray);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		back.setColor(Color.black);
		
		obj = program.getElementAt(e.getX(), e.getY());
		if(obj == back) {
			if(!program.isPaused()) {
				program.switchToMenu();
			}
			else {
				program.switchToPause();
			}
		}
		
		program.setLookedAtControls(true);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		if(obj == back) {
			selection.setVisible(true);
		}
		else {
			selection.setVisible(false);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		clickToContinue.setColor(new Color(colorRed, colorGreen, colorBlue));
        
		
		switch(colorRed % 256) {
			case 0:
				i = 1;
				break;
				
			case 255:
				i = -1;
				break;
				
			default:
				break;
		}
		switch(colorGreen % 256) {
			case 0:
				j = 1;
				break;
			
			case 255:
				j = -1;
				break;
				
			default:
				break;
		}
		switch(colorBlue % 256) {
			case 0:
				k = 1;
				break;
				
			case 255:
				k = -1;
				break;
				
			default:
				break;
		}
        
        colorRed += i;
        colorGreen += j;
        colorBlue += k;
	}
}
