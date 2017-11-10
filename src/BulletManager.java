import java.util.*;

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
	}
}
