import java.util.ArrayList;

public class BulletEmitter extends EnemyShip {
	
	private BulletEmitterData bank;
	private BulletEmitterBehavior behavior;
	
	private int bullet_spd = 4;
	private float bullet_time = 10;
	private static final int WEAPON_CAP = 20;
	private String bullet_sprite = "RedCircle.png";
	
	private BulletType bullet_style;
	private boolean damageable;
	private double angle_delta;
	private boolean infinite_bullets = false;
	private int neg = 1;

	//private ArrayList<ShipTriggers> subscribers;

	public BulletEmitter(PhysXObject obj, String sprite, ShipStats stats,
			BulletEmitterData bullet_data, BulletEmitterBehavior beh, double delta, 
			BulletType type, boolean can_hurt) {
		super(obj, sprite, stats.getHealthMax(), stats, 0, EnemyType.EMITTER, 0);
		// Emitter does not need aggression, nor EXP value.
		this.bank = bullet_data;
		this.behavior = beh;
		this.angle_delta = delta;
		this.damageable = can_hurt;
		this.bullet_style = type;
	}

	protected void destroy() {
		this.physObj = null;
	}
	
	public void setBulletData(int spd, float time, String sprite) {
		this.bullet_spd = spd;
		this.bullet_time = time;
		this.bullet_sprite = sprite;
	}
	
	public void setTangibility(boolean tf) {
		this.damageable = tf;
	}
	
	public void takeDamage(int damage) {
		if (!damageable) {
			return;
		}
		this.current_health -= damage;
		if (current_health < 0) {
			current_health = 0;
		}
	}
	
	public void changeBehavior(BulletEmitterBehavior beh) {
		this.behavior = beh;
	}
	
	public void angleToTarget(Vector2 pos) {
		this.dir = Math.toDegrees(Math.atan2(pos.getY() - physObj.getPosition().getY(), pos.getX() - physObj.getPosition().getX()));
	}
	
	@Override
	public void AIUpdate(Vector2 playerPos) {
		// Check if there are even any bullets to fire
		updateAngle();
		this.sprite.setDegrees(dir);
		if (!bank.checkBank()) {
			return;
		}
		if (weapon_cd > 0) {
			weapon_cd -= 1;
			return;
		}
		else {
			weapon_cd = WEAPON_CAP;
			int current_index = BulletEmitterData.TOTAL_TYPES;
	
			// CHECK IF THE PRIORITIZED BULLETS ARE USED UP
			int p_size = bank.getPriorities().length;
			for (int i = 0; i < p_size; i++) {
				int b_type = bank.getPriorities()[i];
				if (b_type < current_index && bank.getIndex(i) > 0) {
					current_index = b_type;
				}
			}
			
			// SHOOT!
			PhysXObject po = new PhysXObject(physObj.getQUID(), physObj.getPosition(), new CircleCollider(1));
	
			BulletFireEventData bfe = new BulletFireEventData(1, bullet_spd, bullet_style, CollisionType.enemy_bullet, bullet_time, po, bullet_sprite, currentTarget, FXManager.colorParticle(PaintToolbox.RED));
	
			if (behavior != BulletEmitterBehavior.SHOOT_TARGET) {
				neg *= (-1);
					double unit_x = Math.cos(Math.toRadians(dir));
					double unit_y = Math.sin(Math.toRadians(dir));
					Vector2 w = new Vector2((float)(physObj.getPosition().getX() + unit_x * neg),
							(float)(physObj.getPosition().getY() + unit_y * neg));
					bfe.setMovementVector(w);
					shoot(bfe);
			}
			else {
				bfe.setSpeed(6);
				shoot(bfe);
			}
			if (!infinite_bullets) {
				bank.addIndex(current_index, -1); // Subtract a bullet after shooting
			}
		}

	}
	
	private void updateAngle() {
		switch(behavior) {
		case SHOOT_CLOCKWISE:
			dir -= angle_delta;
			break;
		case SHOOT_COUNTER_CLOCKWISE:
			dir += angle_delta;
			break;
		default: // Shoot at target
			angleToTarget(currentTarget);
			break;
		}
		if (dir < 0) {
			dir += 360;
		}
		else if (dir < 360) {
			dir -= 360;
		}
	}
	
	public void reverseDirection() {
		if (this.behavior == BulletEmitterBehavior.SHOOT_COUNTER_CLOCKWISE) {
			behavior = BulletEmitterBehavior.SHOOT_CLOCKWISE;
		}
		else {
			behavior = BulletEmitterBehavior.SHOOT_COUNTER_CLOCKWISE;
		}
	}
	
	public void HAX_setInfiniteBullets(boolean tf) {
		this.infinite_bullets = tf;
	}

	@Override
	public void onCollisionEvent(CollisionData data, Vector2 pos) {
		if (data.getType() == CollisionType.player_bullet) {
			takeDamage(data.getDamage());
		}
	}

	public void addSubscriber(ShipTriggers sub) {
		if(sub != null && this.subscribers != null && !this.subscribers.contains(sub)) {
			this.subscribers.add(sub);
		}
	}
}
