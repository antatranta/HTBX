import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GImage;

public class ControlsPane extends GraphicsPane {
	private MainApplication program;
	private GLabel back;
	private GLabel selection;
	
	public ControlsPane(MainApplication app) {
		program = app;
		
		back = new GLabel("BACK");
		back.setFont(font);
		back.setColor(Color.black);
		back.setLocation(CENTER_WIDTH - (back.getWidth() / 2), CENTER_HEIGHT - (back.getHeight() / 2) + 205);
		
		selection = new GLabel(">");
		selection.setFont(font);
		selection.setColor(Color.black);
		selection.setVisible(false);
		selection.setLocation(back.getX() - 25, back.getY());
	}
	
	@Override
	public void showContents() {
		program.add(title());
		program.add(back);
		program.add(selection);
	}

	@Override
	public void hideContents() {
		program.removeAll();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == back) {
			program.switchToMenu();
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == back) {
			selection.setVisible(true);
		}
		else {
			selection.setVisible(false);
		}
	}
}
