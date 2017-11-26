import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Ship extends Entity {

	protected int KB_FORCE = 10;
	protected int current_health;
	protected ShipStats stats;
	protected Vector2 external_force;
	protected double dir = 90;
	protected ArrayList<ShipTriggers> subscribers;
	protected ShipTriggers mangementSubscriber;
	protected ShipTriggers bulletSubscriber;
	protected GameConsoleEvents gameConsoleSubscriber;
	protected LaserManagerEvents laserManagerSubscriber;
	
	private float dx = 0;// 1 to right, -1 to left.
	private float dy = 0;// 1 to up, -1 to down.
	
	public Ship(PhysXObject physObj, int current_health, ShipStats stats, String sprite, CollisionType shipType) {
		super(physObj, sprite, new CollisionData(10, shipType));
		this.external_force = new Vector2(0, 0);
		this.physObj.addSubscriber(this);
		this.setCurrentHealth(current_health);
		this.stats = stats;
		this.subscribers = new ArrayList<ShipTriggers>();
	}
	
	public void identifySubscribers() {
		if(subscribers != null && subscribers.size() > 0) {
			for(ShipTriggers sub: subscribers) {
				int tag = sub.identify();
				if(tag == 10) {
					mangementSubscriber = sub;
				} else if (tag == 20) {
					bulletSubscriber = sub;
				}
			}
		}
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
	
	private void destroyShip() {
		setCurrentHealth(0);
		
		if(subscribers != null && subscribers.size() > 0) {
			for(ShipTriggers sub : subscribers) {
				sub.onShipDeath(physObj.getPosition(), physObj.getQUID());
			}
		}
		
		// TEMPORARY solution to "kill" enemies
		//physObj.setPosition(new Vector2(0, 0));
		physObj = new PhysXObject();
		
		sprite.rotate(0);
		

	}
	
	protected void takeDamage(int damage) {
		System.out.println("Health: " + getCurrentHealth() + " Dam: " + damage);
		if(getCurrentHealth() > 0) {
			setCurrentHealth(getCurrentHealth() - damage);
		} 
		if (getCurrentHealth() <= 0) {
			destroyShip();
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
		external_force = external_force.div(new Vector2(PhysXLibrary.FRICTION, PhysXLibrary.FRICTION));
	}

//	protected void calculateCollisionForce(Vector2 pos) {
//		double theta_rad = Math.atan2(pos.getY() - physObj.getPosition().getY(), pos.getX() - physObj.getPosition().getX());
//		float unit_x = (float)(Math.cos(theta_rad));
//		float unit_y = (float)(Math.sin(theta_rad));
//		external_force.setXY(unit_x * -KB_FORCE, unit_y * -KB_FORCE);
//
//	}
	
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
	
	protected void shoot(int damage, int speed, CollisionType enemyBullet, float time, PhysXObject obj, String sprite, Vector2 movementVector) {
		BulletFireEventData bfe = new BulletFireEventData(damage,speed, enemyBullet, time, obj, sprite, movementVector);
		
		if(bulletSubscriber == null) {
			if(subscribers != null && subscribers.size() > 0) {
				for(ShipTriggers sub: subscribers) {
					sub.onShipFire(bfe, enemyBullet);
				}
			}
		} else {
			bulletSubscriber.onShipFire(bfe, enemyBullet);
		}
	}
	
	public void onCollisionEvent(CollisionData data, Vector2 pos) {
		takeDamage(data.getDamage());
	}
	
	protected void handleCollision(CollisionData data) {
		takeDamage(data.getDamage());
	}
	
	public void addSubscriber(ShipTriggers sub) {
		if(sub != null && !this.subscribers.contains(sub)) {
			this.subscribers.add(sub);
		}
	}
	
	public void addGameConsole(GameConsoleEvents sub) {
		gameConsoleSubscriber = sub;
	}
	
	public void addLaserManager(LaserManagerEvents sub) {
		laserManagerSubscriber = sub;
	}
}