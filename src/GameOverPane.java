import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GImage;

public class GameOverPane extends GraphicsPane {
	private MainApplication program;
	private GLabel gameOver;
	private GLabel retryGame;
	private GLabel exitToMenu;
	private GObject obj;
	private String filename = "Scores.txt";
	
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
	
	public void WriteScore() {
		Scanner reader = new Scanner(System.in);
		String player_input="";
		String in="";
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
			//FileWriter  fileWriter = new FileWriter("Scores.txt");
			//BufferedWriter buffer = new BufferedWriter(fileWriter);
			System.out.println("Enter your name: ");
			player_input = reader.nextLine();
			in = Integer.toString(program.getPlayerScore())+" "+player_input;
			System.out.println(in);
			out.write(in);
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
		program.add(retryGame);
		program.add(exitToMenu);
		WriteScore();
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
