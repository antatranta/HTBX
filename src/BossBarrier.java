
public class BossBarrier extends EnemyShip {

	double angle_from_source;
	double source_radius;
	double orbit_delta;
	Vector2 source;
	BossRoomManager manager_ref;
	Vector2 player_pos;
	
	public BossBarrier(PhysXObject physObj, String sprite, int current_health, ShipStats stats, int aggression,
			EnemyType type, double angle, double radius, Vector2 source) {
		super(physObj, sprite, current_health, stats, aggression, EnemyType.BARRIER, 0);
		this.angle_from_source = angle;
		this.source_radius = radius;
		this.source = source;
		this.orbit_delta = 0;
		this.manager_ref = null;
		this.stats.setFireRate(EnemyType.BARRIER);
	}
	
	// Orbit around the source physObject Vector2
	@Override
	public void AIUpdate(Vector2 playerPos) {
		player_pos = playerPos;
		if (current_health > 0) {
			double x = Math.cos(Math.toRadians(angle_from_source)) * source_radius;
			double y = Math.sin(Math.toRadians(angle_from_source)) * source_radius;
			this.physObj.setPosition(new Vector2((float)(source.getX() + x), (float)(source.getY() + y)));
			adjustAngle(orbit_delta);
			this.sprite.setDegrees((int) angle_from_source);
			Vector2 weapon_target = playerPos;
			if (weapon_cd > 0) {
				weapon_cd -= 1;
			}
			if (weapon_cd == 0) {
				weapon_cd = 600;
				PhysXObject po = new PhysXObject(physObj.getQUID(), physObj.getPosition(), new CircleCollider(1));
				BulletFireEventData bfe = new BulletFireEventData(1, 4, BulletType.ACCEL, CollisionType.enemy_bullet,
						(float) 5, po, "RedCircle.png", weapon_target, FXManager.colorParticle(PaintToolbox.RED));
				shoot(bfe);
			}
		}
	}
	
	@Override
	protected void destroyShip() {
		PhysXObject po = new PhysXObject(physObj.getQUID(), physObj.getPosition(), new CircleCollider(1));
		BulletFireEventData bfe = new BulletFireEventData(1, 4, BulletType.STRAIGHT, CollisionType.enemy_bullet,
				(float) 5, po, "RedCircle.png", player_pos, FXManager.colorParticle(PaintToolbox.RED));
		shootSpread(bfe, 9, 180);
		super.destroyShip();
		manager_ref.stage0_decrementBarriers();
	}
	
	public void setBossPos(Vector2 boss) {
		this.source = boss;
	}
	
	public void setDirFromSource(double degrees) {
		this.angle_from_source = degrees;
	}
	
	public void adjustAngle(double degrees) {
		this.angle_from_source += degrees;
		if (angle_from_source < 0) {
			angle_from_source += 360;
		}
		else if (angle_from_source > 360) {
			angle_from_source -= 360;
		}
		
	}
	
	public void setManagerRef(BossRoomManager man) {
		this.manager_ref = man;
	}
	public void setOrbitSpeed(double spd) {
		this.orbit_delta = spd;
	}
	
	// Asteroid enemy simply rotates; it will not ever suffer knockback, it only takes damage
	@Override
	public void onCollisionEvent(CollisionData data, Vector2 pos) {
		if (data.getType() == CollisionType.player_bullet) {
			takeDamage(data.getDamage());
		}
	}	

}
