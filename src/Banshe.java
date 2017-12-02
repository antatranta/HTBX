
public class Banshe extends EnemyShip{

	private static int min_dist = 250;
	private static int max_dist = 350;
	protected EnemyShipStats stats;

	private static int max_cd = 180;
	private int weapon_cd;
	private int checkpoint;
	private int auto_reset = 0;
	
	private static float screamDist = 300f;

	public Banshe(PhysXObject physObj, String sprite, int current_health, ShipStats stats, int aggression) {
		super(physObj, sprite, current_health, stats, aggression, EnemyType.BANSHEE, 30);
		this.stats = new EnemyShipStats(stats, aggression);
	}

	@Override
	public void AIUpdate(Vector2 playerPos) {

		// Is the player within range?
		if(PhysXLibrary.distance(this.physObj.getPosition(), playerPos) > stats.getInteractionDistance()) {
			return;
		}
		
		if(PhysXLibrary.distance(this.physObj.getPosition(), playerPos) < screamDist) {
			AudioPlayer myAudio = AudioPlayer.getInstance();
			myAudio.playSound("sounds", "Banshe.wav", SoundType.SFX);
		}

		double theta_deg = -Math.toDegrees(Math.atan2(playerPos.getY() - physObj.getPosition().getY(), playerPos.getX() - physObj.getPosition().getX()));
		currentTarget = playerPos;
		rotateToPoint(currentTarget);

		this.sprite.setDegrees(dir + 90);
		this.physObj.setPosition(this.physObj.getPosition().add(new Vector2((float)Math.cos(Math.toRadians(dir)) * stats.getSpeedSetting(), (float)Math.sin(Math.toRadians(dir)) * stats.getSpeedSetting())));


		moveExternalForce();
	}

}
