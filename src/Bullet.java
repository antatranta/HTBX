public class Bullet {
	private int bulletDamage;
	private int bulletSpeed;
	private BulletType bulletType;
	private float bulletDuration;
	private PhysXObject physObj;
	private CollisionData collisionData;
	
	public Bullet(int dmg, int spd, BulletType bullet, float time, PhysXObject obj) {
		bulletDamage = dmg;
		bulletSpeed = spd;
		bulletType = bullet;
		bulletDuration = time;
		physObj = obj;
		collisionData = new CollisionData(bulletDamage, CollisionType.enemyShip);
	}
	
	public void setBulletDamage(int dmg) {
		bulletDamage = dmg;
	}
	
	public void setBulletSpeed(int spd) {
		bulletSpeed = spd;
	}
	
	public void setBulletType(BulletType bullet) {
		bulletType = bullet;
	}
	
	public void setBulletDuration(float time) {
		bulletDuration = time;
	}
	
	public void setPhysObj(PhysXObject obj) {
		physObj = obj;
	}
	
	public int getBulletDamage() {
		return bulletDamage;
	}
	
	public int getBulletSpeed() {
		return bulletSpeed;
	}
	
	public BulletType getBulletType() {
		return bulletType;
	}
	
	public float getBulletDuration() {
		return bulletDuration;
	}
	
	public PhysXObject getPhysObj() {
		return physObj;
	}
}
