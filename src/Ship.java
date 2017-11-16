public class Ship {

	private PhysXObject physObj;
	private CollisionData collisionData;
	private int current_health;
	private ShipStats stats;
	private double dir = 90;

	
	private float dx = 0;// 1 to right, -1 to left.
	private float dy = 0;// 1 to up, -1 to down.

	
	public Ship(PhysXObject physObj, int current_health, ShipStats stats) {
		this.physObj = physObj;
		this.setCurrentHealth(current_health);
		this.stats = stats;//speed, shield_max, health_max, damage
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
	
	public PhysXObject getPhysObj() {
		return this.physObj;
	}
	
	public CollisionData getCollisionData() {
		return new CollisionData(collisionData);
	}
	
	public void sendCollisionMessage(CollisionData data) {
		takeDamage(data.getDamage());
	}
	
	public void takeDamage(int damage) {
		if(getCurrentHealth() > 0) {
			setCurrentHealth(getCurrentHealth() - damage);
		} else {
			setCurrentHealth(0);
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
			physObj.setPosition(newPosition);
		}
		
		
	}
	
	public void moveVector2(Vector2 dir) {
		if(getCurrentHealth()>0) {
			Vector2 currentPosition = physObj.getPosition();
			physObj.setPosition(currentPosition.add(dir));
		}
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
	/*
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	*/
}