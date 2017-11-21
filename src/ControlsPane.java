import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GImage;

public class ControlsPane extends GraphicsPane {
	private MainApplication program;
	private GImage controls;
	private GLabel back;
	private GObject obj;
	
	public ControlsPane(MainApplication app) {
		program = app;
		controls = new GImage("controls.gif", 0, 0);
		controls.setLocation((CENTER_WIDTH - (controls.getWidth() / 2) - 10), (CENTER_HEIGHT - (controls.getHeight() / 2) + 25));
		
		back = new GLabel("BACK");
		back.setFont(font);
		back.setColor(Color.black);
		back.setLocation(CENTER_WIDTH - (back.getWidth() / 2), CENTER_HEIGHT - (back.getHeight() / 2) + 205);
	}
	
	@Override
	public void showContents() {
		program.add(whiteBG());
		program.add(controls);
		program.add(title());
		program.add(back);
		program.add(selection());
		selection.setLocation(back.getX() - 25, back.getY());
	}

	@Override
	public void hideContents() {
		program.remove(whiteBG());
		program.remove(title());
		program.remove(back);
		program.remove(selection());
		program.remove(controls);
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
