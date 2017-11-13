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
	
	private Vector2 GOval_Pos;
	private Vector2 GOval_Size;
	
	private int steps = 0;
	
	public Bullet(int dmg, int spd, BulletType bullet, float time, PhysXObject obj, Vector2 movementVector) {
		this.bulletDamage = dmg;
		this.bulletSpeed = spd;
		this.bulletType = bullet;
		this.bulletDuration = time;
		this.physObj = obj;
		this.movementVector = movementVector;
		this.collisionData = new CollisionData(bulletDamage, CollisionType.enemyShip);
		this.bulletTrajectory();
	}
	
	public void setBulletDamage(int dmg) {
		this.bulletDamage = dmg;
	}
	
	public void setBulletSpeed(int spd) {
		this.bulletSpeed = spd;
	}
	
	public void setBulletType(BulletType bullet) {
		this.bulletType = bullet;
	}
	
	public void setBulletDuration(float time) {
		this.bulletDuration = time;
	}
	
	public void setPhysObj(PhysXObject obj) {
		this.physObj = obj;
	}
	
	public int getBulletDamage() {
		return this.bulletDamage;
	}
	
	public int getBulletSpeed() {
		return this.bulletSpeed;
	}
	
	public BulletType getBulletType() {
		return this.bulletType;
	}
	
	public float getBulletDuration() {
		return this.bulletDuration;
	}
	
	public PhysXObject getPhysObj() {
		return this.physObj;
	}
	
	public CollisionData getCollisionData() {
		return this.collisionData;
	}
	
	public Vector2 getMovementVector() {
		return this.movementVector;
	}
	
	public float getBulletDX() {
		return this.bulletDX * this.bulletSpeed;
	}
	
	public float getBulletDY() {
		return this.bulletDY * this.bulletSpeed;
	}
	
	public void move() {
		Vector2 movement = new Vector2(physObj.getPosition().getX() + getBulletDX(), physObj.getPosition().getY() + getBulletDY());
		this.physObj.setPosition(movement);
		steps ++;
	}
	
	public int getSteps() {
		return this.steps;
	}
	
	private void bulletTrajectory() {
		this.bulletDX = physObj.getPosition().normalize(movementVector).getX();
		this.bulletDY = physObj.getPosition().normalize(movementVector).getY();
	}
	
	public Vector2 getGOvalSize() {
		return this.GOval_Size;
	}
	
	public Vector2 getGOvalPos() {
		return this.GOval_Pos;
	}
}