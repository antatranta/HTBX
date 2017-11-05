import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.*;

import acm.program.GraphicsProgram;

public class GameConsole extends GraphicsProgram{
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	private PlayerShip player;
	private PhysX physx; // The controller for all things
	private int skillPoints;
	
	public GameConsole() {
		// Create the universe. For now, only a single quadrant
		System.out.println("Made a new game console");
		physx = new PhysX();
		Quadrant h = new Quadrant(new QuadrantID(0,0,0));
		physx.addQuadrant(h);
		player = new PlayerShip(new PhysXObject(new QuadrantID(0,0,0), new Vector2(100, 100)), 1, new ShipStats(1,1,1,1));
	}
	
	public PhysX physx() {
		return physx;
	}
	
	public PlayerShip getPlayer() {
		return player;
	}
	
	@Override
    public void keyPressed(KeyEvent e) {
    	System.out.println("Key Read");
        int key = e.getKeyCode();
        
       //if (key == KeyEvent.VK_ESCAPE) 
        //if (key == KeyEvent.VK_ENTER) 
        	
        if (key == KeyEvent.VK_A) {
            player.setDx(-1);
        }

        if (key == KeyEvent.VK_D) {
        	player.setDx(1);
        }

        if (key == KeyEvent.VK_W) {
        	player.setDy(-1);
        }

        if (key == KeyEvent.VK_S) {
        	player.setDy(1);
        }
    }

	@Override
    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();
        //if (key == KeyEvent.VK_ESCAPE) 
        //if (key == KeyEvent.VK_ENTER) 
        if (key == KeyEvent.VK_A) {
        	player.setDx(0);
        }

        if (key == KeyEvent.VK_D) {
        	player.setDx(0);
        }

        if (key == KeyEvent.VK_W) {
        	player.setDy(0);
        }

        if (key == KeyEvent.VK_S) {
        	player.setDy(0);
        }
    }
}


