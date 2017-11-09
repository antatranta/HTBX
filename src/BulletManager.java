import java.util.*;

public class BulletManager {
	private ArrayList<Bullet> bullets;
	
	public BulletManager() {
		bullets = new ArrayList<Bullet>();
	}
	
	public void onShootEvent(int dmg, int spd, BulletType bullet, float time, PhysXObject obj, Vector2 movementVector) {
		//TODO
		Bullet bulletShoot = new Bullet(dmg, spd, bullet, time, obj, movementVector);
		bullets.add(bulletShoot);
	}
	
	public void onDeathEvent(Ship ship, int points) {
		//TODO
		
	}
	
	public void subscribeToShip(Ship ship) {
		//TODO
		for(Bullet bullet:bullets) {
			switch(bullet.getBulletType()) {
				case PLAYER_BULLET:
					//TODO
					break;
					
				case ENEMY_BULLET:
					//TODO
					break;
					
				default:
					break;
			}
		}
	}
}
