import java.util.*;

public class BulletManager {
	private ArrayList<Bullet> bullets;
	
	public BulletManager() {
		bullets = new ArrayList<Bullet>();
	}
	
	public void onShootEvent(int dmg, int spd, BulletType bullet) {
		//TODO
		
	}
	
	public void onDeathEvent(Ship ship, int points) {
		//TODO
		
	}
	
	public void subscribeToShip(Ship ship) {
		//TODO
	}
}
