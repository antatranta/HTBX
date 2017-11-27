
public class Banshe extends EnemyShip{

	private static int min_dist = 250;
	private static int max_dist = 350;
	protected EnemyShipStats stats;
	
	private static int max_cd = 180;
	private int weapon_cd;
	private int checkpoint;
	private int auto_reset = 0;
	
	public Banshe(PhysXObject physObj, String sprite, int current_health, ShipStats stats, int aggression) {
		super(physObj, sprite, current_health, stats, aggression, EnemyType.BANSHEE, aggression);
		this.stats = new EnemyShipStats(stats, aggression);
	}
	
	@Override
	public void AIUpdate(Vector2 playerPos) {

		// Is the player within range?
		if(PhysXLibrary.distance(this.physObj.getPosition(), playerPos) > stats.getInteractionDistance()) {
			return;
		}

		double theta_deg = -Math.toDegrees(Math.atan2(playerPos.getY() - physObj.getPosition().getY(), playerPos.getX() - physObj.getPosition().getX()));
//		theta_deg += randomRange(-45, 45);
//		double unit_x = -Math.cos(Math.toRadians(theta_deg)) * randomRange(min_dist, max_dist);
//		double unit_y = Math.sin(Math.toRadians(theta_deg)) * randomRange(min_dist, max_dist);
//		currentTarget = playerPos.add(new Vector2((float)unit_x, (float)unit_y));
		currentTarget = playerPos;
//		auto_reset += 1;
//		if (auto_reset >= 300) {
//			auto_reset = 0;
//			checkpoint = 0;
//		}
//		if (PhysXLibrary.distance(physObj.getPosition(), currentTarget) <= 15) {
//			checkpoint = 0;
//		}
//			
		rotateToPoint(currentTarget);
		
		this.sprite.setDegrees(dir + 90);
		this.physObj.setPosition(this.physObj.getPosition().add(new Vector2((float)Math.cos(Math.toRadians(dir)) * stats.getSpeedSetting(), (float)Math.sin(Math.toRadians(dir)) * stats.getSpeedSetting())));

		
		moveExternalForce();
	}

}
