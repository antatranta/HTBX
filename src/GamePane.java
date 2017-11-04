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
	
	public GamePane(MainApplication app) {
		this.program = app;
		console = program.getGameConsole();
		player = new GImage("PlayerShip_Placeholder.png", 100, 100);
		auto_fire = new Timer(250, this);
		if (console.getPlayer() != null) {
			System.out.println("GamePane reads GameConsole Player ship");
		}
		
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
