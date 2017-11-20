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
		return new ShipStats(3, 0, 50, 1);
	}
	
	public float getTurningSpeed() {
		switch(this.speed) {
		case 0:
			return 1f;
		case 1:
			return 2f;
		case 2:
			return 5f;
		case 3:
			return 10f;
		case 4:
			return 15f;
		case 5:
			return 20f;
		default:
			return 1;
		}
	}
	
	public int getSpeedSetting() {
		return this.speed;
	}
	
	public float getSpeedValue() {
		switch(this.speed) {
		case 0:
			return 1;
		case 1:
			return 1.75f;
		case 2:
			return 2.5f;
		case 3:
			return 3f;
		default:
			return 1;
		}
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