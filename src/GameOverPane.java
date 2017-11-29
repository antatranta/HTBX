import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import acm.graphics.GLabel;
import acm.graphics.GObject;


public class GameOverPane extends GraphicsPane {
	private static final String FILE_NAME = "Scores.txt";
	
	private MainApplication program;
	private GLabel gameOver;
	private GLabel scoreDisplay;
	private GLabel retryGame;
	private GLabel exitToMenu;
	private GObject obj;
	
	public GameOverPane(MainApplication app) {
		program = app;
		gameOver = new GLabel("GAME OVER");
		scoreDisplay = new GLabel("YOUR SCORE: " + Integer.toString(program.getPlayerScore()));
		retryGame = new GLabel("RETRY");
		exitToMenu = new GLabel("EXIT TO MENU");

		gameOver.setFont(font);
		gameOver.setColor(Color.black);
		gameOver.setLocation(CENTER_WIDTH - (gameOver.getWidth() / 2), CENTER_HEIGHT - (gameOver.getHeight() / 2));
		scoreDisplay.setFont(font);
		scoreDisplay.setColor(Color.black);
		scoreDisplay.setLocation(CENTER_WIDTH - (scoreDisplay.getWidth() / 2), gameOver.getY() + 50);
		retryGame.setFont(font);
		retryGame.setColor(Color.black);
		retryGame.setLocation(gameOver.getX() - 75, scoreDisplay.getY() + 50);
		exitToMenu.setFont(font);
		exitToMenu.setColor(Color.black);
		exitToMenu.setLocation(gameOver.getX() + 75, retryGame.getY());
	}
	
	public void writeScore() {
		String in = "";
		try {
			PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME, true));
			
			in = Integer.toString(program.getPlayerScore());
			out.println(in);
			out.flush();
			out.close();
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file Scores.txt");
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("Done!");
	}
	
	@Override
	public void showContents() {
		program.removeAll();
		program.add(whiteBG());
		program.add(selection());
		program.add(gameOver);
		program.add(scoreDisplay);
		program.add(retryGame);
		program.add(exitToMenu);
		writeScore();
	}

	@Override
	public void hideContents() {
		program.remove(whiteBG());
		program.remove(selection());
		program.remove(gameOver);
		program.remove(scoreDisplay);
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
