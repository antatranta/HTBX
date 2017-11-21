import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import acm.graphics.*;
import rotations.GameImage;

public class BulletTest extends GraphicsApplication implements ActionListener {
	
	// To Anthony: Change the BulletType enum to get some funky bullet movement :)
	private static BulletType bulletType = BulletType.STRAIGHT;
	
	private Vector2 last_mouse_loc;
	private BulletManager bulletStore;
	private Timer gameTimer;
	private PhysXObject currentLocation;
	private boolean isShooting;
	private int shotCount;
	
	public void init() {
		setSize(800, 600);
		setBackground(Color.white);
		last_mouse_loc = new Vector2(0, 0);
		bulletStore = new BulletManager();
		currentLocation = new PhysXObject();
		currentLocation.setPosition(new Vector2(400, 300));
	}
	
	public void run() {
		isShooting = false;
		shotCount = 0;


		
		gameTimer = new Timer(15, this);
		gameTimer.start();
	}
	
	public void moveBullets() {
		//bulletStore.moveClockwiseSpiralPattern();
		bulletStore.moveSunBurstBottom();
		//bulletStore.moveSunBurstTop();
		//bulletStore.moveZigZagBottom();
		//bulletStore.moveZigZagTop();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(isShooting) {
			if(shotCount % 5 == 0) {
				shoot();
			}
			shotCount++;
		}
		
		moveBullets();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		last_mouse_loc.setXY(e.getX(), e.getY());
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		last_mouse_loc.setXY(e.getX(), e.getY()); 
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		isShooting = true;
		last_mouse_loc = new Vector2(e.getX(), e.getY());
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		isShooting = false;
	}
	
	public void shoot() {
		PhysXObject currentLocation = new PhysXObject();
		currentLocation.setPosition(new Vector2(400, 300));

		GameImage bullet = bulletStore.onShootEvent(1, 8, bulletType, CollisionType.player_bullet, 3, currentLocation, "RedCircle.png", Camera.frontendToBackend(last_mouse_loc) );
//		bullet.setFilled(true);
//		bullet.setFillColor(Color.orange);
//		bullet.setColor(Color.orange);
		add(bullet);
	}
}