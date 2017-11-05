import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class GamePane extends GraphicsPane implements ActionListener, KeyListener {
	private MainApplication program; //you will use program to get access to all of the GraphicsProgram calls
	private GameConsole console; // Not a new one; just uses the one from MainApplication
	private GImage player_img;
	private PlayerShip player;
	private Timer auto_fire;
	//private Vector2 combat_offset = new Vector2(0,0); Unused for now; planned for centering player post combat smoothly
	
	public GamePane(MainApplication app) {
		this.program = app;
		console = program.getGameConsole();
		player = console.getPlayer();
		player_img = new GImage("PlayerShip_Placeholder.png", 0, 0);
		auto_fire = new Timer(250, this);
		if (console.getPlayer() != null && player != null) {
			System.out.println("GamePane reads GameConsole Player ship");
		}
		centerPlayer();
	}
	
	public void centerPlayer() {
		player_img.setLocation((MainApplication.WINDOW_WIDTH / 2) - (player_img.getWidth() / 2), (MainApplication.WINDOW_HEIGHT / 2) - (player_img.getHeight() /2));
	}
	
	@Override
	public void showContents() {
		program.add(player_img);
	}

	@Override
	public void hideContents() {
		program.remove(player_img);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// Timer should start here
		auto_fire.setInitialDelay(0);
		auto_fire.start();
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == player_img) {
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
	
	@Override
    public void keyPressed(KeyEvent e) {
    	
        int key = e.getKeyCode();
        
       //if (key == KeyEvent.VK_ESCAPE) 
        //if (key == KeyEvent.VK_ENTER) 
        	
        if (key == KeyEvent.VK_A) {
        	System.out.println("v A Key Pressed");
            player.setDx(-1);
        }

        if (key == KeyEvent.VK_D) {
        	System.out.println("v D Key Pressed");
        	player.setDx(1);
        }

        if (key == KeyEvent.VK_W) {
        	System.out.println("v W Key Pressed");
        	player.setDy(-1);
        }

        if (key == KeyEvent.VK_S) {
        	System.out.println("v S Key Pressed");
        	player.setDy(1);
        }
    }

	@Override
    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();
        //if (key == KeyEvent.VK_ESCAPE) 
        //if (key == KeyEvent.VK_ENTER) 
        if (key == KeyEvent.VK_A) {
        	System.out.println("^ A Key Released");
        	player.setDx(0);
        }

        if (key == KeyEvent.VK_D) {
        	System.out.println("^ D Key Released");
        	player.setDx(0);
        }

        if (key == KeyEvent.VK_W) {
        	System.out.println("^ W Key Released");
        	player.setDy(0);
        }

        if (key == KeyEvent.VK_S) {
        	System.out.println("^ S Key Released");
        	player.setDy(0);
        }
    }

}
