import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class ScoresPane extends GraphicsPane{
	private MainApplication program;
	private GLabel back;
	private GObject obj;
	
	public ScoresPane(MainApplication app) {
		program = app;
		
		back = new GLabel("BACK");
		back.setFont(font);
		back.setColor(Color.black);
		back.setLocation(CENTER_WIDTH - (back.getWidth() / 2), CENTER_HEIGHT - (back.getHeight() / 2) + 205);
	}
	
	@Override
	public void showContents() {
		program.add(title());
		program.add(back);
		program.add(selection());
		selection.setLocation(back.getX() - 25, back.getY());
	}

	@Override
	public void hideContents() {
		program.removeAll();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
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
