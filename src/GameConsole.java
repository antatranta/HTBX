import java.awt.event.KeyEvent;
import java.util.*;

import acm.program.GraphicsProgram;

public class GameConsole extends GraphicsProgram{
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	private PlayerShip player;
	private PhysX physx; // The controller for all things
	private int skillPoints;
	
	public GameConsole() {
		System.out.println("Made a new game console");
		physx = new PhysX();
		Quadrant h = new Quadrant(new QuadrantID(0,0,0));
		physx.addQuadrant(h);
		// Create the universe. For now, only a single quadrant
	}
	
	//
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        
       //if (key == KeyEvent.VK_ESCAPE) 
        //if (key == KeyEvent.VK_ENTER) 
        	
        if (key == KeyEvent.VK_A) {
            player.setDx(-1);
            System.out.println("Pressed A");
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


