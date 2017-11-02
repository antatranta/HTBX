public class Ship {

	private PhysXObject physObj;
	private CollisionData collisionData;
	private int current_health;
	private ShipStats stats;
	
	private int dx = 0;// 1 to right, -1 to left.
	private int dy = 0;// 1 to up, -1 to down.
	private int x;// ship x coordinate
	private int y;// ship y coordinate
	
	public Ship(PhysXObject physObj,int current_health, ShipStats stats,int x,int y) {
		this.physObj = new PhysXObject();
		this.setCurrent_health(current_health);
		this.stats = new ShipStats(1, 1, 1, 1);//speed, shield_max, health_max, damage
		this.x = x;
		this.y = y;
	}
	
	public PhysXObject getPhysObj() {
		return this.physObj;
	}
	
	public CollisionData getCollisionData() {
		return new CollisionData(collisionData);
	}
	
	public void sendCollisionMessage(CollisionData data) {
		
	}
	
	public void takeDamage(int damage) {
		if(getCurrent_health()>0) {
			setCurrent_health(getCurrent_health() - damage);
		}else {
			setCurrent_health(0);
		}
	}	

	public int getCurrent_health() {
		return current_health;
	}

	public void setCurrent_health(int current_health) {
		this.current_health = current_health;
	}
	
	public void Move() {
		while(getCurrent_health()>0) {
	        x += dx;
	        y += dy;
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
}