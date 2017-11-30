
public class AsteroidEnemy extends EnemyShip {

	double angle_from_source;
	double source_radius;
	Vector2 source;
	
	public AsteroidEnemy(PhysXObject physObj, String sprite, int current_health, ShipStats stats, int aggression,
			EnemyType type, double angle, double radius) {
		super(physObj, sprite, current_health, stats, aggression, EnemyType.ROCK_SHIELD, 0);
		this.angle_from_source = angle;
		this.source_radius = radius;
	}
	
	@Override
	public void AIUpdate(Vector2 playerPos) {
		double x = Math.cos(Math.toRadians(angle_from_source)) * source_radius;
		double y = Math.cos(Math.toRadians(angle_from_source)) * source_radius;
		this.physObj.getPosition().setXY((float)(source.getX() + x), (float)(source.getY() + y));
		
	}
	
	public void setBossPos(Vector2 boss) {
		this.source = boss;
	}
	
	public void setDirFromSource(double degrees) {
		this.angle_from_source = degrees;
	}
	
	public void adjustAngle(double degrees) {
		this.angle_from_source += degrees;
	}

}
