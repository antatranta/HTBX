public class ShipStats {
	
	private int speed;
	private int shield_max;
	private int health_max;
	private int damage;
	
	public ShipStats(int speed, int damage, int health_max, int shield_max) {
		this.speed = speed;
		this.damage = damage;
		this.health_max = health_max;
		this.shield_max = shield_max;
	}
	
	public static ShipStats EnemyStats_01() {
		return new ShipStats(1, 1, 15, 0);
	}
	
	public static ShipStats EnemyStats_Banshe() {
		return new ShipStats(5, 1, 15, 0);
	}
	
	
	public float getTurningSpeed() {
		return 5f;
//		switch(this.speed) {
//		case 0:
//			return 5f;
//		case 1:
//			return 5f;
//		case 2:
//			return 5f;
//		case 3:
//			return 5;
//		case 4:
//			return 5;
//		case 5:
//			return 5;
//		default:
//			return 1;
//		}
	}
	
	public int getSpeedSetting() {
		return this.speed;
	}
	
	public float getSpeedValue() {
		switch(this.speed) {
		case 0:
			return 5;
		case 1:
			return 5.5f;
		case 2:
			return 6f;
		case 3:
			return 6.5f;
		default:
			return 7;
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