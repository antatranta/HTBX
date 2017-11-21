import java.awt.Color;

import acm.graphics.GObject;
import acm.graphics.GOval;
import rotations.GameImage;

public class Bullet extends Entity {
	public static final int OSCILLATION_OFFSET = 5;
	public static final int WAVE_OFFSET = 50;
	public static final int WAVE_DELTA = 5;
	
	private int bulletSpeed;
	private int originalSpeed;
	private BulletType bulletType;
	private float bulletDuration;		// Time in SECONDS; BulletManager checks for 60 ticks per
	private int bulletOscIts;		// Used for oscillation and wave
	private int bulletWaveIts;

	private Vector2 movementVector;
	private double originalAngle;
	private double angle;
	private float bulletDX;
	private float bulletDY;
	private GOval oval;
	private boolean dead;
	
	private Vector2 GOval_Pos;
	private Vector2 GOval_Size;
	
	private int steps = 0;
	
	public Bullet(int dmg, int spd, BulletType pattern, CollisionType bullet, float time, PhysXObject physObj, String sprite, Vector2 movementVector) {
		super(physObj, sprite, new CollisionData(10, bullet));
		
		this.originalSpeed = spd;
		this.bulletSpeed = originalSpeed;
		this.bulletType = pattern;
		this.bulletDuration = time;
		this.physObj.addSubscriber(this);
		this.movementVector = movementVector;
		this.originalAngle = Math.atan2(movementVector.getY() - physObj.getPosition().getY(), movementVector.getX() - physObj.getPosition().getX());
		this.angle = originalAngle;
		this.physObj.addSubscriber(this);
		this.bulletOscIts = 0;
		this.bulletWaveIts = 0;
		
		if (bulletType == BulletType.ACCEL) {
			bulletSpeed = 1;
		}
		else if (bulletType == BulletType.WAVE) {
			angle = originalAngle;
		}
		
		physObj.setCollisionData(new CollisionData(dmg, bullet));
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
		steps ++;
		// DO NOT need a case for straight bullets
		if (bulletType != BulletType.STRAIGHT) {
			// ACCEL: Bullet increases in speed (self explanatory)
			if (bulletType == BulletType.ACCEL) {
				bulletSpeed += 1;
			}
			// OSCILLATE: Bullet fluctuates in its path
			else if (bulletType == BulletType.OSCILLATE) {
				if (bulletSpeed > -OSCILLATION_OFFSET && bulletOscIts == 0) {
					bulletSpeed -= 1;
				}
				if (bulletSpeed == 0) {
					bulletOscIts = 1;
				}
				if (bulletSpeed < originalSpeed + OSCILLATION_OFFSET && bulletOscIts == 1) {
					bulletSpeed += 1;
				}
				if (bulletSpeed >= originalSpeed + OSCILLATION_OFFSET) {
					bulletOscIts = 0;
				}
			}
			// WAVE: Bullet moves back and forth in its path
			else if (bulletType == BulletType.WAVE) {
				if (bulletOscIts == 0) {
					bulletWaveIts += WAVE_DELTA ;
					if (bulletWaveIts > WAVE_OFFSET) {
						bulletOscIts = 1;
					}
				}
				else {
					bulletWaveIts -= WAVE_DELTA ;
					if (bulletWaveIts < -WAVE_OFFSET) {
						bulletOscIts = 0;
					}
				}
			
				angle = originalAngle + Math.toRadians(bulletWaveIts);
				this.bulletTrajectory();
			}
			// SWERVE_CLOCKWISE: Bullet will swerve clockwise
			else if (bulletType == BulletType.SWERVE_CW) {
				angle -= 1;
				this.bulletTrajectory();
			}
			// SWERVE_COUNTER_CLOCKWISE: Bullet will serve counter clockwise
			else if (bulletType == BulletType.SWERVE_CCW) {
				angle += 1;
				this.bulletTrajectory();
			}
			
		}
		Vector2 movement = new Vector2(physObj.getPosition().getX() + getBulletDX(), physObj.getPosition().getY() + getBulletDY());
		this.physObj.setPosition(movement);

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
		// Calculate unit x and y
		this.bulletDX = (float)Math.cos(angle);
		this.bulletDY = (float)Math.sin(angle);
//		this.bulletDX = physObj.getPosition().normalize(movementVector).getX();
//		this.bulletDY = physObj.getPosition().normalize(movementVector).getY();
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