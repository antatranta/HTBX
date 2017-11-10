public class ShipStats {
	
	public ShipStats(int speed, int shield_max, int health_max, int damage) {
		super();
		this.speed = speed;
		this.shield_max = shield_max;
		this.health_max = health_max;
		this.damage = damage;
	}
	
	private static int speed;
	private static int shield_max;
	private static int health_max;
	private static int damage;
	
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getShield_max() {
		return shield_max;
	}
	public void setShield_max(int shield_max) {
		this.shield_max = shield_max;
	}
	public int getHealth_max() {
		return health_max;
	}
	public void setHealth_max(int health_max) {
		this.health_max = health_max;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public void incSpeed(int update) {
		this.speed += update;
	}
	public void incShield_max(int update) {
		this.shield_max += update;
	}
	public void incHealth_max(int update) {
		this.health_max += update;
	}
	public void incDamage(int update) {
		this.damage += update;
	}
}