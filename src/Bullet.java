import java.awt.Color;

import acm.graphics.GOval;

public class Bullet implements Collision {
	private int bulletDamage;
	private int bulletSpeed;
	private BulletType bulletType;
	private float bulletDuration;
	private PhysXObject physObj;

	private Vector2 movementVector;
	private float bulletDX;
	private float bulletDY;
	private GOval sprite;
	private boolean dead;
	
	private Vector2 GOval_Pos;
	private Vector2 GOval_Size;
	
	private int steps = 0;
	
	public Bullet(int dmg, int spd, BulletType bullet, float time, PhysXObject obj, Vector2 movementVector) {
		this.bulletDamage = dmg;
		this.bulletSpeed = spd;
		this.bulletType = bullet;
		this.bulletDuration = time;
		this.physObj = obj;
		this.physObj.addSubscriber(this);
		this.movementVector = movementVector;
		physObj.setCollisionData(new CollisionData(bulletDamage, CollisionType.bullet));
//		physObj.setHost(this);
		this.bulletTrajectory();
		sprite = new GOval(0, 0, 10, 10);
		sprite.setFillColor(Color.yellow);
		sprite.setFilled(true);
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
	
	public void setBulletDXDY(float dx, float dy) {
		bulletDX = dx;
		bulletDY = dy;
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
	
	public void destroy() {
		dead = true;
	}
	
	public boolean checkIfDead() {
		return dead;
	}
	
	public int getSteps() {
		return this.steps;
	}
	
	public void bulletTrajectory() {
		this.bulletDX = physObj.getPosition().normalize(movementVector).getX();
		this.bulletDY = physObj.getPosition().normalize(movementVector).getY();
	}
	
	public Vector2 getGOvalSize() {
		return this.GOval_Size;
	}
	
	public Vector2 getGOvalPos() {
		return this.GOval_Pos;
	}
	
	public GOval getSprite() {
		return sprite;
	}

	@Override
	public void onCollisionEvent(CollisionData data, Vector2 pos) {
		// TODO Auto-generated method stub
		destroy();
	}
	
	
}