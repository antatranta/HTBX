public class Ship {

	private PhysXObject physObj;
	private CollisionData collisionData;
	private int current_health;
	private ShipStats stats;
	
	public Ship(PhysXObject physObj,int current_health, ShipStats stats) {
		this.physObj = new PhysXObject();
		this.setCurrent_health(current_health);
		this.stats = new ShipStats(1, 1, 1, 1);//speed, shield_max, health_max, damage
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
	
	public void Move() {

	}

	public int getCurrent_health() {
		return current_health;
	}

	public void setCurrent_health(int current_health) {
		this.current_health = current_health;
	}
}