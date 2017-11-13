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
		bulletTrajectory();
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
		return bulletDX * bulletSpeed;
	}
	
	public float getBulletDY() {
		return bulletDY * bulletSpeed;
	}
	
	public void move() {
		Vector2 movement = new Vector2(physObj.getPosition().getX() + getBulletDX(), physObj.getPosition().getY() + getBulletDY());
		physObj.setPosition(movement);
	}
	
	private void bulletTrajectory() {
		bulletDX = physObj.getPosition().normalize(movementVector).getX();
		bulletDY = physObj.getPosition().normalize(movementVector).getY();
	}
}