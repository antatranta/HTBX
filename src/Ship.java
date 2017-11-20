import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import rotations.GameImage;

public class Ship extends Entity implements ActionListener {

	private static final int KB_FORCE = 7;
	private static final float FRICTION = (float) 1.1;
	protected int current_health;
	protected ShipStats stats;
	protected Vector2 external_force;
	protected double dir = 90;
	
	private float dx = 0;// 1 to right, -1 to left.
	private float dy = 0;// 1 to up, -1 to down.
	
	public Ship(PhysXObject physObj, int current_health, ShipStats stats, String sprite, CollisionType shipType) {
		super(physObj, sprite, new CollisionData(10, shipType));
//		this.physObj = physObj;
		this.external_force = new Vector2(0, 0);
		this.physObj.addSubscriber(this);
		this.setCurrentHealth(current_health);
		this.stats = stats;//speed, shield_max, health_max, damage
//		super.createSprite(sprite);
//		super.setCollisionData(new CollisionData(10, CollisionType.enemyShip));
	}
	
	public double getAngle() {
		return dir;
	}
	
	public void adjustAngle(double degree) {
		dir += degree;
		if (dir > 360) {
			dir -= 360;
		}
		else if (dir < 0) {
			dir += 360;
		}
	}
	
	protected void takeDamage(int damage) {
		if(getCurrentHealth() > 0) {
			setCurrentHealth(getCurrentHealth() - damage);
		} 
		else {
			setCurrentHealth(0);
			sprite.setImage("Aiming_Reticle.png");
			sprite.rotate(0);
		}
	}

	public int getCurrentHealth() {
		return current_health;
	}

	public void setCurrentHealth(int current_health) {
		if (current_health > 0)
			this.current_health = current_health;
		else
			this.current_health = 0;
	}
	
	public void Move() {
		if(getCurrentHealth()>0) {
			Vector2 currentPosition = physObj.getPosition();
			Vector2 newPosition = currentPosition.add(new Vector2(dx, dy));
			this.physObj.setPosition(newPosition);
		}
	}
	
	public void moveVector2(Vector2 dir) {
		if(getCurrentHealth()>0) {
			Vector2 currentPosition = physObj.getPosition();
			this.physObj.setPosition(currentPosition.add(dir));
			
		}
	}
	
	protected void moveExternalForce() {
		Vector2 currentPosition = physObj.getPosition();
		this.physObj.setPosition(currentPosition.add(external_force));
		external_force = external_force.div(new Vector2(FRICTION, FRICTION));
	}

	protected void calculateCollisionForce(Vector2 pos) {
		double theta_rad = Math.atan2(pos.getY() - physObj.getPosition().getY(), pos.getX() - physObj.getPosition().getX());
		float unit_x = (float)(Math.cos(theta_rad));
		float unit_y = (float)(Math.sin(theta_rad));
		external_force.setXY(unit_x * -KB_FORCE, unit_y * -KB_FORCE);

	}
	
	public float getDx() {
		return dx;
	}
	public void setDx(float dx) {
		this.dx = dx;
	}
	public float getDy() {
		return dy;
	}
	public void setDy(float dy) {
		this.dy = dy;
	}
	
	public void setDxDy(Vector2 DXDY) {
		this.dx = DXDY.getX();
		this.dy = DXDY.getY();
	}
	
	public ShipStats getStats() {
		return stats;
	}
	
	public void shoot() {
		// TODO Auto-generated method stub
		
	}
	
	public void onCollisionEvent(CollisionData data, Vector2 pos) {
		// TODO Auto-generated method stub
	}
	
	protected void handleCollision(CollisionData data) {
		takeDamage(data.getDamage());
	}

	
	public void actionPerformed(ActionEvent e) {
		// Stub
	}
}