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
	private GObject obj;
	
	public ControlsPane(MainApplication app) {
		program = app;
		
		controls = new GImage("controls.gif", 0, 0);
		controls.setLocation((CENTER_WIDTH - (controls.getWidth() / 2)), (CENTER_HEIGHT - (controls.getHeight() / 2) + 60));
		
		back = new GLabel("BACK");
		back.setFont(font);
		back.setColor(Color.black);
		back.setLocation(CENTER_WIDTH - (back.getWidth() / 2), CENTER_HEIGHT - (back.getHeight() / 2) + 250);
	}
	
	@Override
	public void showContents() {
		program.setLookedAtControls(true);
		program.add(whiteBG());
		program.add(controls);
		program.add(title());
		program.add(selection());
		if(program.getFromMenu()) {
			program.add(back);
			selection.setLocation(back.getX() - 25, back.getY());
		}
		else {
			program.add(clickToContinue());
			startFadingLabel();
		}
	}

	@Override
	public void hideContents() {
		program.remove(whiteBG());
		program.remove(title());
		if(program.getFromMenu()) {
			program.remove(back);
		}
		else {
			program.remove(clickToContinue());
			stopFadingLabel();
		}
		program.remove(selection());
		program.remove(controls);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		if(obj == back) {
			back.setColor(Color.gray);
		}
		
		if(!program.getFromMenu()) {
			program.switchToGame();
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
}
