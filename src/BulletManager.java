import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class BulletManager {
	private ArrayList<Bullet> bullets;
	
	public BulletManager() {
		bullets = new ArrayList<Bullet>();
	}
	
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	public void onShootEvent(int dmg, int spd, BulletType bullet, float time, PhysXObject obj, Vector2 movementVector) {
		Bullet bulletShoot = new Bullet(dmg, spd, bullet, time, obj, movementVector);
		bullets.add(bulletShoot);
	}
	
	public void moveBullets() {
		for(Bullet shoot:bullets) {
			shoot.move();
		}
		
		for(Bullet shoot:bullets) {
			Timer delete = new Timer((int)shoot.getBulletDuration() * 1000, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					bullets.remove(shoot);
				}
			});
			delete.setRepeats(false);
			delete.start();
		}
	}
}
