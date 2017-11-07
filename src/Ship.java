public class Ship {

	private PhysXObject physObj;
	private CollisionData collisionData;
	private int current_health;
	private ShipStats stats;
	private double dir = 90;
	
	private int dx = 0;// 1 to right, -1 to left.
	private int dy = 0;// 1 to up, -1 to down.

	
	public Ship(PhysXObject physObj,int current_health, ShipStats stats) {
		this.physObj = new PhysXObject();
		this.setCurrent_health(current_health);
		this.stats = new ShipStats(1, 1, 1, 1);//speed, shield_max, health_max, damage
	}
	
	public double getAngle() {
		return dir;
	}
	
	public void adjustAngle(double degree) {
		dir += degree;
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
		if(getCurrent_health() > 0) {
			setCurrent_health(getCurrent_health() - damage);
		} else {
			setCurrent_health(0);
		}
	}	

	public int getCurrent_health() {
		return current_health;
	}

	public void setCurrent_health(int current_health) {
		if (current_health > 0)
			this.current_health = current_health;
		else
			this.current_health = 0;
	}
	
	public void Move() {
		while(getCurrent_health()>0) {
			Vector2 currentPosition = physObj.getPosition();
			physObj.setPosition(currentPosition.add(new Vector2(dx, dy)));
		}
	}
	
	public int getDx() {
		return dx;
	}
	public void setDx(int dx) {
		this.dx = dx;
	}
	public int getDy() {
		return dy;
	}
	public void setDy(int dy) {
		this.dy = dy;
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