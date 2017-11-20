public class CollisionData {
	private int damage;
	private CollisionType type;
	
	public CollisionData() {
		this.damage = 0;
		this.type = CollisionType.enemyShip;
	}
	public CollisionData(int damage, CollisionType type) {
		this.damage = damage;
		this.type = type;
	}
	public CollisionData(CollisionData toCopy) {
		if (toCopy != null) {
			this.damage = toCopy.getDamage();
			this.type = toCopy.getType();
		} else {
			this.damage = 0;
			this.type = CollisionType.enemyShip;
		}
	}
	public int getDamage() {
		return this.damage;
	}
	
	public void setDamage(int dmg) {
		this.damage = dmg;
	}
	
	public CollisionType getType() {
		return type;
	}
	
	public String toString() {
		return "Dam: "+ this.damage + " Type: " + this.type; 
	}
}