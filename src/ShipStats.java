public class ShipStats {
	
	private int speed;
	private int shield_max;
	private int health_max;
	private int damage;
	
	public ShipStats(int speed, int shield_max, int health_max, int damage) {
		this.speed = speed;
		this.shield_max = shield_max;
		this.health_max = health_max;
		this.damage = damage;
	}
	
	public static ShipStats EnemyStats_01() {
		return new ShipStats(1, 0, 50, 1);
	}
	
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getShieldMax() {
		return shield_max;
	}
	public void setShieldMax(int shield_max) {
		this.shield_max = shield_max;
	}
	public int getHealthMax() {
		return health_max;
	}
	public void setHealthMax(int health_max) {
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
	public void incShieldMax(int update) {
		this.shield_max += update;
	}
	public void incHealthMax(int update) {
		this.health_max += update;
	}
	public void incDamage(int update) {
		this.damage += update;
	}
}