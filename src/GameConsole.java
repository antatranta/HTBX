import java.awt.event.KeyEvent;
import java.util.*;

import acm.program.GraphicsProgram;

public class GameConsole extends GraphicsProgram{
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	private PlayerShip player;
	
	
	
	
	
	//
    public void keyPressed(KeyEvent e) {

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


