import java.awt.Color;

import acm.graphics.GObject;
import acm.graphics.GOval;
import rotations.GameImage;

public class Bullet extends Entity {
	private int bulletSpeed;
	private BulletType bulletType;
	private float bulletDuration;
	

	private Vector2 movementVector;
	private float bulletDX;
	private float bulletDY;
	private GOval oval;
	private boolean dead;
	
	private Vector2 GOval_Pos;
	private Vector2 GOval_Size;
	
	private int steps = 0;
	

	public Bullet(int dmg, int spd, BulletType bullet, float time, PhysXObject physObj, String sprite, Vector2 movementVector) {
		super(physObj, sprite, new CollisionData(10, CollisionType.blank));
		
		this.bulletSpeed = spd;
		this.bulletType = bullet;
		this.bulletDuration = time;
		this.physObj.addSubscriber(this);
		this.movementVector = movementVector;
		
		this.physObj.addSubscriber(this);
		
		CollisionType collType = CollisionType.enemy_bullet;
		if (bullet == BulletType.PLAYER_BULLET) {
			collType = CollisionType.player_bullet;
		}
		
		physObj.setCollisionData(new CollisionData(dmg, collType));
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
		this.setCollisionData(CollisionData.Blank());
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
	
	public GameImage getSprite() {
		return sprite;
	}	
	
	public GOval getGOval() {
		return oval;
	}
	

	@Override
	public void onCollisionEvent(CollisionData data, Vector2 pos) {
		// TODO Auto-generated method stub
		handleCollision(data);
	}
	
	protected void handleCollision(CollisionData data) {
		if(data.getType() != CollisionType.player_bullet || data.getType() != CollisionType.blank
				|| data.getType() == CollisionType.enemy_bullet || data.getType() == CollisionType.enemyShip) {
			return;
		} else {
			destroy();
		}
	}
	
}