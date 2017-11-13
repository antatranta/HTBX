import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import acm.graphics.*;

public class BulletManager {
	private ArrayList<Bullet> bullets;
	private ArrayList<GOval> gOvals;
	
	public BulletManager() {
		this.bullets = new ArrayList<Bullet>();
		this.gOvals = new ArrayList<GOval>();
	}
	
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	public GOval onShootEvent(int dmg, int spd, BulletType bullet, float time, PhysXObject obj, Vector2 movementVector) {
		this.bullets.add(new Bullet(dmg, spd, bullet, time, obj, movementVector));
		this.gOvals.add(new GOval(obj.getPosition().getX(), obj.getPosition().getY(), 10, 10));
		return this.gOvals.get(gOvals.size() -1);
	}
	
	public void moveBullets() {
		for(Bullet shoot : this.bullets) {
			shoot.move();
			
			for(GOval bullet : this.gOvals) {
				Vector2 pos = Camera.backendToFrontend(shoot.getPhysObj().getPosition());
				bullet.setLocation(pos.getX(), pos.getY());
			}
		}

		
		/*
		for(Bullet shoot:bullets) {
			Timer delete = new Timer((int)shoot.getBulletDuration() * 1000, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					bullets.remove(shoot);
				}
			});
			delete.setRepeats(false);
			delete.start();
		}*/
	}
}