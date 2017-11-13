import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.*;
import rotations.GameImage;

public class BulletTest extends GraphicsApplication implements ActionListener {
	private Vector2 last_mouse_loc;
	private BulletManager bulletStore;
	private ArrayList<GOval> bullets;
	private Timer gameTimer;
	private ArrayList<Bullet> storedBullets;
	private Camera camera;
	
	public void init() {

		setSize(800, 600);
		setBackground(Color.white);
	}
	
	public void run() {
		camera = new Camera (1, 1, 800, 600);
		Camera.setOffset(new Vector2(300, 400));
		bullets = new ArrayList<GOval>();
		bulletStore = new BulletManager();
		storedBullets = bulletStore.getBullets();
		
		gameTimer = new Timer(15, this);
		gameTimer.start();
	}
	
	public void moveBullets() {
		bulletStore.moveBullets();
		
		/*
		for(Bullet bulletStore:storedBullets) {
			for(GOval bullet:bullets) {
				bullet.setLocation(bulletStore.getPhysObj().getPosition().getX(), bulletStore.getPhysObj().getPosition().getY());
			}
		}*/
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		moveBullets();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		last_mouse_loc = new Vector2(e.getX(), e.getY());
		PhysXObject currentLocation = new PhysXObject();
		currentLocation.setPosition(new Vector2(400, 300));
		
		bulletStore.onShootEvent(1, 5, BulletType.PLAYER_BULLET, 4, new Vector2(400, 300), last_mouse_loc.minus(new Vector2(400, 300)));
		
//		bulletStore.onShootEvent(1, 5, BulletType.PLAYER_BULLET, 4, currentLocation, last_mouse_loc);
		//bulletStore.moveBullets();
		
		for(Bullet bullet:storedBullets) {
			GOval bulletSprite = new GOval(bullet.getPhysObj().getPosition().getX(), bullet.getPhysObj().getPosition().getY(), 10, 10);
			bullets.add(bulletSprite);
			add(bulletSprite);
		}
	}
}
