import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GImage;

//TODO: Implement Game Over Screen with a continue and exit to menu function
public class GameOverPane extends GraphicsPane {
	private MainApplication program;
	private GLabel gameOver;
	private GLabel retryGame;
	private GLabel exitToMenu;
	private GObject obj;
	
	public GameOverPane(MainApplication app) {
		program = app;
		gameOver = new GLabel("GAME OVER");
		retryGame = new GLabel("RETRY");
		exitToMenu = new GLabel("EXIT TO MENU");
		
		gameOver.setFont(font);
		gameOver.setColor(Color.black);
		gameOver.setLocation(CENTER_WIDTH - (gameOver.getWidth() / 2), CENTER_HEIGHT - (gameOver.getHeight() / 2));
		retryGame.setFont(font);
		retryGame.setColor(Color.black);
		retryGame.setLocation(gameOver.getX() - 75, gameOver.getY() + 50);
		exitToMenu.setFont(font);
		exitToMenu.setColor(Color.black);
		exitToMenu.setLocation(gameOver.getX() + 75, retryGame.getY());
	}
	
	@Override
	public void showContents() {
		program.removeAll();
		program.add(whiteBG());
		program.add(selection());
		program.add(gameOver);
		program.add(retryGame);
		program.add(exitToMenu);
	}

	@Override
	public void hideContents() {
		program.remove(whiteBG());
		program.remove(selection());
		program.remove(gameOver);
		program.remove(retryGame);
		program.remove(exitToMenu);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		if(obj == retryGame) {
			program.switchToGame();
		}
		else if(obj == exitToMenu) {
			program.switchToMenu();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		if(obj == null || obj == whiteBG()) {
			return;
		}
		
		obj.setColor(Color.gray);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		gameOver.setColor(Color.black);
		retryGame.setColor(Color.black);
		exitToMenu.setColor(Color.black);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		
		if(obj == retryGame) {
			selection.setVisible(true);
			selection.setLocation(retryGame.getX() - 25, retryGame.getY());
		}
		else if(obj == exitToMenu) {
			selection.setVisible(true);
			selection.setLocation(exitToMenu.getX() - 25, exitToMenu.getY());
		}
		else {
			selection.setVisible(false);
		}
	}
}
