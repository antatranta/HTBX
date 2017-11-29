import java.util.ArrayList;

public class BulletEmitter extends EnemyShip {
	// TODO: Amount of bullets, angle delta, controllable emitter spray
	
	private BulletEmitterData bank;
	private BulletEmitterBehavior behavior;
	private BulletType bullet_style;
	private boolean damageable;
	private double angle_delta;

	private ArrayList<ShipTriggers> subscribers;

	public BulletEmitter(PhysXObject obj, String sprite, ShipStats stats, BulletEmitterData bullet_data, BulletEmitterBehavior beh, double delta, BulletType type, boolean can_hurt) {
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
		int current_index = BulletEmitterData.TOTAL_TYPES;
		
		// CHECK IF THE PRIORITIZED BULLETS ARE USED UP
		int p_size = bank.getPriorities().length;
		for (int i = 0; i < p_size; i++) {
			int b_type = bank.getIndex(bank.getPriorities()[i]);
			if (b_type < current_index && bank.getIndex(i) > 0) {
				current_index = b_type;
			}
		}
		
		// SHOOT!
		PhysXObject po = new PhysXObject(physObj.getQUID(), physObj.getPosition(), new CircleCollider(1));

		BulletFireEventData bfe = new BulletFireEventData(1, 4, BulletType.STRAIGHT, CollisionType.enemy_bullet, (float) 5, po, "RedCircle.png", currentTarget, FXManager.colorParticle(PaintToolbox.RED));

		if (behavior != BulletEmitterBehavior.SHOOT_TARGET) {
			for (int i = 0; i < 1; i++) {
				int neg = (-1) ^ i;
				double unit_x = Math.cos(Math.toRadians(dir)) * neg;
				double unit_y = Math.sin(Math.toRadians(dir)) * neg;
				currentTarget = new Vector2((float)(physObj.getPosition().getX() + unit_x), (float)(physObj.getPosition().getY() + unit_y));
				bfe.setMovementVector(currentTarget);
				shoot(bfe);
			}
		}
		else {
			bfe.setSpeed(6);
			shoot(bfe);
		}
		bank.addIndex(current_index, -1); // Subtract a bullet after shooting
		updateAngle();
	}
	
	private void updateAngle() {
		switch(behavior) {
		case SHOOT_CLOCKWISE:
			dir += angle_delta;
			break;
		case SHOOT_COUNTER_CLOCKWISE:
			dir -= angle_delta;
			break;
		default: // Shoot at target
			angleToTarget(currentTarget);
			break;
		}
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
