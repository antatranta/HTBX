public class EnemyShipStats extends ShipStats {
	private int aggression = 1;
	private int fireRate = 0;
	
	public EnemyShipStats(int speed, int damage, int health_max, int shield_max, int aggression, int fireRate) {
		super(speed, damage, health_max, shield_max);
		this.aggression = aggression;
	}
	
	public EnemyShipStats(ShipStats stats, int aggression) {
		super(stats.getSpeedSetting(), stats.getDamage(), stats.getHealthMax(), stats.getShieldMax());
		this.aggression = aggression;
	}

	public float getStoppingDistance() {
		switch(this.aggression) {
		case 0:
			return 275f;
		case 1:
			return 250f;
		case 2:
			return 225f;
		case 3:
			return 200f;
		case 4:
			return 150f;
		case 5:
			return 100f;
		default:
			return 50f;
		}
	}
	
	public float getInteractionDistance() {
		switch(this.aggression) {
		case 0:
			return 500f;
		case 1:
			return 600f;
		case 2:
			return 700f;
		case 3:
			return 400f;
		case 4:
			return 450f;
		case 5:
			return 500f;
		default:
			return 200f;
		}
	}
	
	@Override
	public float getTurningSpeed() {
		switch(this.aggression) {
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
	
	public int getAggresionSetting() {
		return this.aggression;
	}
	
	public int getFireRateValue() {
		switch(this.fireRate) {
		case 0:
			return 300;
		case 1:
			return 150;
		case 2:
			return 240;
		case 3:
			return 300;
		case 4:
			return 60;
		case 5:
			return 30;
		default:
			return 1;
		}
	}
	
	public int getFireRateSetting() {
		return this.fireRate;
	}

}
