import java.awt.Color;

import acm.graphics.GOval;

public class Bullet extends Entity {
	private int bulletSpeed;
	private BulletType bulletType;
	private float bulletDuration;

	private Vector2 movementVector;
	private float bulletDX;
	private float bulletDY;
	private boolean dead;
	
	private Vector2 GOval_Pos;
	private Vector2 GOval_Size;
	
	private int steps = 0;
	
	public Bullet(int dmg, int spd, CollisionType bullet, float time, PhysXObject obj, Vector2 movementVector) {
		super (obj, "RedCircle.png", new CollisionData(dmg, bullet));
		this.bulletSpeed = spd;
		this.bulletDuration = time;
		this.physObj.addSubscriber(this);
		this.movementVector = movementVector;
//		physObj.setHost(this);
		this.bulletTrajectory();
	}
	
	public void setBulletDamage(int dmg) {
		this.physObj.getCollisionData().setDamage(dmg);
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
		return this.physObj.getCollisionData().getDamage();
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
	

	@Override
	public void onCollisionEvent(CollisionData data, Vector2 pos) {
		// TODO Auto-generated method stub
		if (physObj.getCollisionData().getType() == CollisionType.enemy_bullet && data.getType() == CollisionType.enemyShip) {
			return;
		}
		else {
			destroy();
		}

	}
	
	
}