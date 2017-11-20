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
		retryGame.setLocation(gameOver.getX(), gameOver.getY() + 50);
		exitToMenu.setFont(font);
		exitToMenu.setColor(Color.black);
		exitToMenu.setLocation(retryGame.getX() + 50, retryGame.getY());
	}
	
	@Override
	public void showContents() {
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
		
	}
	
}
