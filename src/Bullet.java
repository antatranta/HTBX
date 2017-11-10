public class Bullet {
	private int bulletDamage;
	private int bulletSpeed;
	private BulletType bulletType;
	private float bulletDuration;
	private PhysXObject physObj;
	private CollisionData collisionData;
	private Vector2 movementVector;
	private float bulletDX;
	private float bulletDY;
	
	public Bullet(int dmg, int spd, BulletType bullet, float time, PhysXObject obj, Vector2 movementVector) {
		this.bulletDamage = dmg;
		this.bulletSpeed = spd;
		this.bulletType = bullet;
		this.bulletDuration = time;
		this.physObj = obj;
		this.movementVector = movementVector;
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
	
	public CollisionData getCollisionData() {
		return collisionData;
	}
	
	public Vector2 getMovementVector() {
		return movementVector;
	}
	
	public float getBulletDX() {
		Vector2 movement = this.physObj.getPosition().add(movementVector);
		bulletDX = movement.normalize().getX();
		return bulletDX * bulletSpeed;
	}
	
	public float getBulletDY() {
		Vector2 movement = this.physObj.getPosition().add(movementVector);
		bulletDY = movement.normalize().getY();
		return bulletDY * bulletSpeed;
	}
	
	public void move() {
		physObj.getPosition().setXY(physObj.getPosition().getX() + getBulletDX(), physObj.getPosition().getY() + getBulletDY());
	}
}
