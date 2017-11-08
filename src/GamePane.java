import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.*;
import rotations.GameImage;

public class GamePane extends GraphicsPane implements ActionListener, KeyListener {
	private static final int CURSOR_DIST = 50;
	private static final int CURSOR_SIZE = 10;
	private static final int TURN_POWER = 6;
	private MainApplication program; //you will use program to get access to all of the GraphicsProgram calls
	private GameConsole console; // Not a new one; just uses the one from MainApplication
	private GameImage player_img;
	private PlayerShip player;
	private Vector2 last_mouse_loc;

//	private GameTimer gameTimer;
	private Timer gameTimer;

	
	private boolean CAN_MOVE = false;
	private boolean MOVEMENT_LOCK = false;
	private float MOVEMENT_CONSTANT = .0000001f;
	
	private boolean CAN_ALIGN = true;
	private boolean ALIGNMENT_LOCK = false;
	
	private ArrayList <GOval> cursor_dots;
//	private ArrayList <Integer> pressed_keys;
	private float xAxis = 0;
	private float yAxis = 0;
	private int track_amount = 0;
	//private Vector2 combat_offset = new Vector2(0,0); Unused for now; planned for centering player post combat smoothly
	
	// Nevermind, scratch the idea of game loops we cannot use it here
	// if we do everything becomes really buggy so timers are okay
	// but if you still want to study game loops feel free to
	// just lets get this game working with what we got w/ ACM
	public GamePane(MainApplication app) {
		this.program = app;
		CAN_MOVE = false;
		
		last_mouse_loc = new Vector2(0,0);

//		gameTimer = new GameTimer();
//		gameTimer.setupTimer(TIMER_INTERVAL, INITIAL_DELAY);

		cursor_dots = new ArrayList <GOval>();
		console = program.getGameConsole();
		player = console.getPlayer();

		Vector2 pos = player.getPhysObj().getPosition();
		player_img = new GameImage("PlayerShip_Placeholder.png", pos.getX(), pos.getY());
//		auto_fire = new Timer(250, this);

		if (console.getPlayer() != null && player != null) {
			System.out.println("GamePane successfully accessed GameConsole's Player ship");
		}
		centerPlayer();
		player.setDxDy(Vector2.Zero());
		CAN_MOVE = true;
	}
	
	private void setupTimer(int interval, int initialDelay) {
		gameTimer = new Timer(interval, this);
		gameTimer.setInitialDelay(initialDelay);
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

		//auto_fire.setInitialDelay(0);
		//auto_fire.start();

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
//		auto_fire.stop();
		System.out.println("Stopped shooting");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Player shoot every tick
//		System.out.println("Fired");
		if (CAN_ALIGN && !ALIGNMENT_LOCK) {
			ALIGNMENT_LOCK = true;
			alignReticle(last_mouse_loc);
		}
		
		if(CAN_MOVE) {
			
			movementLoop();
		}
		
	}
	
	private void movementLoop() {
		
		if(MOVEMENT_LOCK)
			return;
		
		MOVEMENT_LOCK = true;
		
		if (yAxis > 0 + MOVEMENT_CONSTANT) {
			double angle = -Math.toRadians(player.getAngle());
			float speed = (float) player.getStats().getSpeed() * 5;
			float cos = (float) Math.cos(angle) * speed;
			float sin = (float) Math.sin(angle) * speed;
			
			player_img.move(cos, sin);
			player.getPhysObj().getPosition().add(new Vector2(cos, sin));
		} else if (yAxis < 0 - MOVEMENT_CONSTANT) {
    			double angle2 = -Math.toRadians(player.getAngle());
			float speed2 = (float) player.getStats().getSpeed() * -5;
			float cos2 = (float) Math.cos(angle2) * speed2;
			float sin2 = (float) Math.sin(angle2) * speed2;
		
			player_img.move(cos2, sin2);
			player.getPhysObj().getPosition().add(new Vector2(cos2, sin2));
		}
		
		
		if (xAxis > 0 + MOVEMENT_CONSTANT) {
    			player.adjustAngle(-TURN_POWER);
    			player_img.rotate(TURN_POWER);

		} else if (xAxis < 0 - MOVEMENT_CONSTANT) {
			player.adjustAngle(TURN_POWER);
			player_img.rotate(-TURN_POWER);
		}

		/*
		for (int key : pressed_keys) {
	        	switch(key) {
	        	case (int) 'W':
	        		break;
	        	case (int) 'A':
	        		player.adjustAngle(TURN_POWER);
	        		player_img.rotate(-TURN_POWER);
	        		break;
	        	case (int) 'S':
	        		/*
	        		// TODO: Make this not janky hardcode. Kek
	        		double angle2 = -Math.toRadians(player.getAngle());
	    			float speed2 = (float) player.getStats().getSpeed() * -5;
	    			float cos2 = (float) Math.cos(angle2) * speed2;
	    			float sin2 = (float) Math.sin(angle2) * speed2;
	    		
	    			player_img.move(cos2, sin2);
	    			player.getPhysObj().getPosition().add(new Vector2(cos2, sin2));
	    			*/
		/*
	        		break;
	        	case (int) 'D':
	        		player.adjustAngle(-TURN_POWER);
	        		player_img.rotate(TURN_POWER);
	        		break;
	        	}
        }
*/
		
		player.setDx((float) player.getStats().getSpeed() * 5 * xAxis);
		player.setDy((float) player.getStats().getSpeed() * 5 * yAxis);
		/*
		for(int key : pressed_keys) {
			switch(key) {
			case KeyEvent.VK_A:
				player.setDx(-5);
				break;
			case KeyEvent.VK_D:
				player.setDx(5);
				break;
			case KeyEvent.VK_W:
				player.setDy(-5);
				break;
			case KeyEvent.VK_S:
				player.setDy(5);
				break;
			}
		}
		*/
		player.Move();
		
		// this has to be the last call!
		MOVEMENT_LOCK = false;
	}
	
	// Might be a very taxing method. We can change to having a simple cursor at the mouse pointer. Luckily, won't draw more than 5 dots
	public void alignReticle(Vector2 coord) {
		//Vector2 root = player.getPhysObj().getPosition();
		Vector2 visual_root = new Vector2((float)(player_img.getX() + (player_img.getWidth()/2)), (float)(player_img.getY() + (player_img.getHeight()/2)));
		int distance = (int)Math.floor(PhysXLibrary.distance(visual_root, new Vector2(coord.getX(), coord.getY())));
		int dots = (distance / CURSOR_DIST) + 1;
		if (cursor_dots.size() < dots) {
			for (int i = 0; i < dots - cursor_dots.size(); i++) {
				GOval dot = new GOval(10, 10, CURSOR_SIZE, CURSOR_SIZE);
				dot.setColor(Color.black);
				cursor_dots.add(dot);
				program.add(dot);
			}
		}
		if (cursor_dots.size() > dots) {
			for (int i = 0; i < cursor_dots.size() - dots; i++) {
				program.remove(cursor_dots.get(0));
				cursor_dots.remove(0);
			}
		}
		
		// Align them properly
		double off_x = (coord.getX() - visual_root.getX());
		double off_y = (coord.getY() - visual_root.getY());
		double theta_rad = Math.atan2(off_y, off_x);
		double unit_x = (Math.cos(theta_rad) * CURSOR_DIST);
		double unit_y = (Math.sin(theta_rad) * CURSOR_DIST);
		cursor_dots.get(0).setLocation(coord.getX() - (CURSOR_SIZE / 2), coord.getY() - (CURSOR_SIZE / 2));
		for (int i = 1; i < cursor_dots.size(); i++) {
			cursor_dots.get(i).setLocation(visual_root.getX() - (CURSOR_SIZE / 2) + (unit_x * i), visual_root.getY() - (CURSOR_SIZE / 2) + (unit_y * i));
		}
		//System.out.println("Distance: " + distance + ", Drawn: " + dots);
		ALIGNMENT_LOCK = false;
	}
	

	@Override
	public void mouseMoved(MouseEvent e) {
		last_mouse_loc.setXY(e.getX(), e.getY()); 
//		alignReticle(last_mouse_loc);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		last_mouse_loc.setXY(e.getX(), e.getY()); 
//		alignReticle(last_mouse_loc);
	}
	

	
	// Key Presses work; the println statements were removed to prevent clutter in the console as I test
	@Override
    public void keyPressed(KeyEvent e) {
		System.out.print("Press");
        int key = e.getKeyCode();
        switch(key) {
        case KeyEvent.VK_A:
    			System.out.print("ed : A");
    			xAxis = -(1 + MOVEMENT_CONSTANT);
    			break;
        case KeyEvent.VK_D:
        		System.out.print("ed : D");
        		xAxis = (1 + MOVEMENT_CONSTANT);
        		break;
        case KeyEvent.VK_W:
        		System.out.print("ed : W");
        		yAxis = (1 + MOVEMENT_CONSTANT);
        		break;
        case KeyEvent.VK_S:
    			System.out.print("ed : S");
    			yAxis = -(1 + MOVEMENT_CONSTANT);
    			break;
    		default:
    			System.out.print("ed : NONE");
    			break;
        }
        System.out.println("");
    }

	@Override
    public void keyReleased(KeyEvent e) {
		System.out.print("Release");
		int key = e.getKeyCode();
		switch(key) {
			case KeyEvent.VK_A:
//				player.setDx(0);
				System.out.print("d: A");
				
				if (xAxis + MOVEMENT_CONSTANT < 0) {
					xAxis = 0;
				}
				break;
			case KeyEvent.VK_D:
//				player.setDx(0);
				System.out.print("d: D");
				if (xAxis + MOVEMENT_CONSTANT > 0) {
					xAxis = 0;
				}
				break;
			case KeyEvent.VK_W:
//				player.setDy(0);
				System.out.print("d: W");
				if (yAxis + MOVEMENT_CONSTANT > 0) {
					yAxis = 0;
				}
				break;
			case KeyEvent.VK_S:
//				player.setDy(0);
				System.out.print("d: S");
				if (yAxis + MOVEMENT_CONSTANT < 0) {
					yAxis = 0;
				}
				break;
			default:
				System.out.print("d: NONE");
				break;
		}
		System.out.println("");
    }
	
	
}