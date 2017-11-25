import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnemyShip extends Ship implements ActionListener {
	protected static EnemyType type;

	private static int min_dist = 250;
	private static int max_dist = 350;
	protected EnemyShipStats stats;
	
	private static int max_cd = 180;
	private int weapon_cd;
	private int checkpoint;
	private int auto_reset = 0;
	
	protected Vector2 currentTarget = new Vector2(-99, -99);
	
	public EnemyShip(PhysXObject physObj, String sprite, int current_health, ShipStats stats, int aggression) {
		super(physObj, current_health, stats, sprite, CollisionType.enemyShip);
		this.stats = new EnemyShipStats(stats, aggression);
		System.out.println("Stats: " + stats);
		System.out.println("Aggression: " +aggression);
		System.out.println("Int. Dist: " + this.stats.getInteractionDistance());
		System.out.println("Stp. Dist: " + this.stats.getStoppingDistance());
		weapon_cd = 90;
	}
	
	
	public EnemyType getEnemyType() {
		return type;
	}
	
	public void AIUpdate(Vector2 playerPos) {

		// Is the player within range?
		if(PhysXLibrary.distance(this.physObj.getPosition(), playerPos) > stats.getInteractionDistance()) {
			currentTarget = physObj.getPosition();
			return;
		}
		else {
			if (checkpoint == 0) {
				checkpoint = 1;
				double theta_deg = -Math.toDegrees(Math.atan2(playerPos.getY() - physObj.getPosition().getY(), playerPos.getX() - physObj.getPosition().getX()));
				theta_deg += randomRange(-15, 15);
				double unit_x = -Math.cos(Math.toRadians(theta_deg)) * randomRange(min_dist, max_dist);
				double unit_y = Math.sin(Math.toRadians(theta_deg)) * randomRange(min_dist, max_dist);
				currentTarget = playerPos.add(new Vector2((float)unit_x, (float)unit_y));
			}
			else if (checkpoint == 1) {
				auto_reset += 1;
				if (auto_reset >= 300) {
					auto_reset = 0;
					checkpoint = 0;
				}
				if (PhysXLibrary.distance(physObj.getPosition(), currentTarget) <= 5) {
					checkpoint = 0;
				}
				
			}
			
			rotateToPoint(currentTarget);
			
			this.sprite.setDegrees(dir + 90);
			if (checkpoint < 2) {
				this.physObj.setPosition(this.physObj.getPosition().add(new Vector2((float)Math.cos(Math.toRadians(dir)) * stats.getSpeedSetting(), (float)Math.sin(Math.toRadians(dir)) * stats.getSpeedSetting())));
			}
			
			moveExternalForce();
			
			Vector2 weapon_target = playerPos;
			if (weapon_cd > 0) {
				weapon_cd -= 1;
			}
			if (weapon_cd == 0) {
				weapon_cd = max_cd + randomRange(-15, 15);
				double theta_deg = Math.toDegrees(Math.atan2(weapon_target.getY() - physObj.getPosition().getY(), weapon_target.getX() - physObj.getPosition().getX()));
				int deg_spread = 25;
				theta_deg -= deg_spread;
				double unit_x = Math.cos(Math.toRadians(theta_deg));
				double unit_y = Math.sin(Math.toRadians(theta_deg));
				for (int i = 0; i < 3; i++) {
					PhysXObject obj = new PhysXObject(physObj.getQUID(), physObj.getPosition(), new CircleCollider(1));
					shoot(1, 3, CollisionType.enemy_bullet, 5, obj, "RedCircle.png", new Vector2((float)(physObj.getPosition().getX() + unit_x), (float)(physObj.getPosition().getY() + unit_y)));
					theta_deg += deg_spread;
					unit_x = Math.cos(Math.toRadians(theta_deg));
					unit_y = Math.sin(Math.toRadians(theta_deg));
				}
				
				// Use these two lines if you just want one bullet shot
//				PhysXObject obj = new PhysXObject(physObj.getQUID(), physObj.getPosition(), new CircleCollider(1));
//				shoot(1, 4, CollisionType.enemy_bullet, 3, obj, "RedCircle.png", weapon_target);
			}
		}
	}
	
	// Thanks Wenrui
	public void rotateToPoint(Vector2 target) {
		Vector2 origin = physObj.getPosition();
		double target_angle = Math.atan2(target.getY() - origin.getY(), target.getX() - origin.getX());
		int target_degree = (int) (target_angle * 57.32);
		int different = (int) (dir - target_degree);
		if(Math.abs(different)>180) {
			different+=different>0?-360:360;
		}
		if(different<0) {
			adjustAngle(1);
		}
		else if(different>0) {
			adjustAngle(-1);
		}
	}
	
	@Override
	public void onCollisionEvent(CollisionData data, Vector2 pos) {
//		System.out.println("Dam: "+data.getDamage());
		if (data.getType() == CollisionType.playerShip) {
			external_force = PhysXLibrary.calculateCollisionForce(pos, this.physObj, KB_FORCE);
		}
		if (data.getType() == CollisionType.asteroid || data.getType() == CollisionType.player_bullet) {
			AudioPlayer myAudio = AudioPlayer.getInstance();
			myAudio.stopSound("sounds", "BlinkerHit.wav");
			myAudio.playSound("sounds", "BlinkerHit.wav");
			takeDamage(data.getDamage());
		}
	}
	
	
	// WARNING: THIS IS NEVER ACCESSED IN THE GAME LOOP (GAMEPANE CALLS AIUPDATE)
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Stub
	}
	
	// WARNING: THIS IS NEVER ACCESSED IN THE GAME LOOP (GAMEPANE CALLS AIUPDATE)
	@Override
	public void Move() {
		// Stub
	}

	public static int randomRange(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
	
}