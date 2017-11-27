import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GImage;

public class ControlsPane extends GraphicsPane {
	private MainApplication program;
	private GImage controls;
	private GLabel back;
	private GLabel continueToGame;
	private GObject obj;
	
	public ControlsPane(MainApplication app) {
		program = app;
		controls = new GImage("controls.gif", 0, 0);
		controls.setLocation((CENTER_WIDTH - (controls.getWidth() / 2)), (CENTER_HEIGHT - (controls.getHeight() / 2) + 60));
		
		back = new GLabel("BACK");
		back.setFont(font);
		back.setColor(Color.black);
		back.setLocation(CENTER_WIDTH - (back.getWidth() / 2), CENTER_HEIGHT - (back.getHeight() / 2) + 250);
		
		continueToGame = new GLabel("CONTINUE");
		continueToGame.setFont(font);
		continueToGame.setColor(Color.black);
		continueToGame.setLocation(CENTER_WIDTH - (continueToGame.getWidth() / 2), CENTER_HEIGHT - (continueToGame.getHeight() / 2) + 250);
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
			program.add(continueToGame);
			selection.setLocation(continueToGame.getX() - 25, continueToGame.getY());
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
			program.remove(continueToGame);
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
		else if(obj == continueToGame) {
			continueToGame.setColor(Color.gray);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		back.setColor(Color.black);
		continueToGame.setColor(Color.black);
		
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
		
		if(obj == continueToGame) {
			program.switchToGame();
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		if(obj == back || obj == continueToGame) {
			selection.setVisible(true);
		}
		else {
			selection.setVisible(false);
		}
	}
}
