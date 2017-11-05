import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class GamePane extends GraphicsPane implements ActionListener {
	private MainApplication program; //you will use program to get access to all of the GraphicsProgram calls
	private GameConsole console; // Not a new one; just uses the one from MainApplication
	private GImage player;
	private Timer auto_fire;
	
	// Anthony: For now a timer is fine but later on when we are going into the big picture of things game loops are going to be
	// a big thing and I will do the game loop coding, if you want to help then google game loops
	// the reasoning for this is because game loops are more akin to the usage of games while timers
	// can easily hinder frames if there are alot of objects on screen
	// https://stackoverflow.com/questions/17440555/using-timer-and-game-loop
	// http://gameprogrammingpatterns.com/game-loop.html
	public GamePane(MainApplication app) {
		this.program = app;
		console = program.getGameConsole();
		player = new GImage("PlayerShip_Placeholder.png", 100, 100);
		auto_fire = new Timer(250, this);
	}
	
	@Override
	public void showContents() {
		program.add(player);
	}

	@Override
	public void hideContents() {
		program.remove(player);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// Timer should start here
		auto_fire.setInitialDelay(0);
		auto_fire.start();
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == player) {
			program.switchToMenu();
		}
		else {
			System.out.println("Clicked empty space");
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		auto_fire.stop();
		System.out.println("Stopped shooting");
	}

	@Override
	public void actionPerformed(ActionEvent e) { // Player shoot every tick
		System.out.println("Fired");
		
	}
	

}
