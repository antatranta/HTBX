public class Bullet {
	private int bulletDamage;
	private int bulletSpeed;
	private BulletType bulletType;
	private float bulletDuration;
	private PhysXObject physObj;
	private PhysXObject initialLocation;
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
		this.initialLocation = obj;
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
		bulletDX = bulletTrajectory().getX();
		return bulletDX * bulletSpeed;
	}
	
	public float getBulletDY() {
		bulletDY = bulletTrajectory().getY();
		return bulletDY * bulletSpeed;
	}
	
	public void move() {
		Vector2 movement = new Vector2(physObj.getPosition().getX() + getBulletDX(), physObj.getPosition().getY() + getBulletDY());
		physObj.setPosition(movement);
	}
	
	private Vector2 bulletTrajectory() {
		Vector2 movement = null;
		
		if(movementVector.getX() < initialLocation.getPosition().getX() && movementVector.getY() < initialLocation.getPosition().getY()) {
			movement = this.initialLocation.getPosition().minus(movementVector);
			movement.setXY(movement.getX() * -1, movement.getY() * -1);
		}
		else if(movementVector.getX() < initialLocation.getPosition().getX() && movementVector.getY() > initialLocation.getPosition().getY()) {
			movement = this.initialLocation.getPosition().minusXAddY(movementVector);
			movement.setXY(movement.getX() * -1, movement.getY());
		}
		else if(movementVector.getX() > initialLocation.getPosition().getX() && movementVector.getY() < initialLocation.getPosition().getY()) {
			movement = this.initialLocation.getPosition().addXMinusY(movementVector);
			movement.setXY(movement.getX(), movement.getY() * - 1);
		}
		else {
			movement = this.initialLocation.getPosition().add(movementVector);
		}
		
		return movement.normalize();
	}
}
