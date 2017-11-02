import java.awt.event.KeyEvent;
import java.util.*;

import acm.program.GraphicsProgram;

public class GameConsole extends GraphicsProgram{
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	private PlayerShip player;
	
	
	
	
	
	//
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            player.setDx(-1);
        }

        if (key == KeyEvent.VK_RIGHT) {
        	player.setDx(1);
        }

        if (key == KeyEvent.VK_UP) {
        	player.setDy(-1);
        }

        if (key == KeyEvent.VK_DOWN) {
        	player.setDy(1);
        }
    }

    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
        	player.setDx(0);
        }

        if (key == KeyEvent.VK_RIGHT) {
        	player.setDx(0);
        }

        if (key == KeyEvent.VK_UP) {
        	player.setDy(0);
        }

        if (key == KeyEvent.VK_DOWN) {
        	player.setDy(0);
        }
    }
}


