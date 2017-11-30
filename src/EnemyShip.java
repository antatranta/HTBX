public class EnemyShip extends Ship {
	protected EnemyType type;

	private static int min_dist = 250;
	private static int max_dist = 350;
	protected EnemyShipStats stats;

	private int weapon_cd;
	private int checkpoint;
	private int auto_reset = 0;

	protected Vector2 currentTarget = new Vector2(-99, -99);

	public EnemyShip(PhysXObject physObj, String sprite, int current_health, ShipStats stats, int aggression,
			EnemyType type, int exp) {
		super(physObj, current_health, stats, sprite, CollisionType.enemyShip, exp);
		this.stats = new EnemyShipStats(stats, aggression, type);
		this.weapon_cd = 60;
		this.type = type;
	}

	public EnemyType getEnemyType() {
		return type;
	}

	protected boolean checkInteractionDistance(Vector2 playerPos) {
		if (PhysXLibrary.distance(this.physObj.getPosition(), playerPos) > stats.getInteractionDistance()) {
			return false;
		}
		return true;
	}

	public void AIUpdate(Vector2 playerPos) {

		// Is the player within range?
		if (!checkInteractionDistance(playerPos)) {
			currentTarget = physObj.getPosition();
		} else {
			gameConsoleSubscriber.UIRequest_addThreat(physObj.getPosition());
			if (checkpoint == 0) {
				checkpoint = 1;
				double theta_deg = -Math.toDegrees(Math.atan2(playerPos.getY() - physObj.getPosition().getY(),
						playerPos.getX() - physObj.getPosition().getX()));
				theta_deg += LavaLamp.randomRange(-45, 45);
				double unit_x = -Math.cos(Math.toRadians(theta_deg)) * LavaLamp.randomRange(min_dist, max_dist);
				double unit_y = Math.sin(Math.toRadians(theta_deg)) * LavaLamp.randomRange(min_dist, max_dist);
				currentTarget = playerPos.add(new Vector2((float) unit_x, (float) unit_y));
			} else if (checkpoint == 1) {
				auto_reset += 1;
				if (auto_reset >= 300) {
					auto_reset = 0;
					checkpoint = 0;
				}
				if (PhysXLibrary.distance(physObj.getPosition(), currentTarget) <= 15) {
					checkpoint = 0;
				}

			}

			rotateToPoint(currentTarget);

			this.sprite.setDegrees(dir + 90);
			if (checkpoint < 2) {
				this.physObj.setPosition(this.physObj.getPosition()
						.add(new Vector2((float) Math.cos(Math.toRadians(dir)) * stats.getSpeedSetting(),
								(float) Math.sin(Math.toRadians(dir)) * stats.getSpeedSetting())));
			}

			moveExternalForce();

			Vector2 weapon_target = playerPos;
			if (weapon_cd > 0) {
				weapon_cd -= 1;
			}
			if (weapon_cd == 0) {
				weapon_cd = stats.getFireRateValue() + LavaLamp.randomRange(-15, 15);
				PhysXObject po = new PhysXObject(physObj.getQUID(), physObj.getPosition(), new CircleCollider(1));
				BulletFireEventData bfe = new BulletFireEventData(1, 4, BulletType.STRAIGHT, CollisionType.enemy_bullet,
						(float) 5, po, "RedCircle.png", weapon_target, FXManager.colorParticle(PaintToolbox.RED));
				switch (type) {
				case LEVEL_1:
					bfe.setSpeed(6);
					shoot(bfe);
					break;
				case LEVEL_2:
					shootSpread(bfe, 3, 45);
					break;
				case LEVEL_3:
					bfe.setBulletType(BulletType.OSCILLATE);
					shootSpread(bfe, 5, 90);
					break;
				case BOSS:
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	protected void destroyShip() {
		setCurrentHealth(0);

		gameConsoleSubscriber.onShipDeath(physObj.getPosition(), exp_value);
		// TEMPORARY solution to "kill" enemies
		physObj.setPosition(new Vector2(-1000, -1000));

		sprite.rotate(0);
	}

	@Override
	protected void playDamageSound() {
		AudioPlayer myAudio = AudioPlayer.getInstance();
		myAudio.playSound("sounds", "Gun_Fire.wav");
	}

	// Thanks Wenrui
	public void rotateToPoint(Vector2 target) {
		Vector2 origin = physObj.getPosition();
		double target_angle = Math.atan2(target.getY() - origin.getY(), target.getX() - origin.getX());
		int target_degree = (int) (target_angle * 57.32);
		int different = (int) (dir - target_degree);
		if (Math.abs(different) > 180) {
			different += different > 0 ? -360 : 360;
		}
		if (different < 0) {
			adjustAngle(1);
		} else if (different > 0) {
			adjustAngle(-1);
		}
	}

	@Override
	public void onCollisionEvent(CollisionData data, Vector2 pos) {
		if (data.getType() == CollisionType.playerShip) {
			external_force = PhysXLibrary.calculateCollisionForce(pos, this.physObj, KB_FORCE);
		}
		if (data.getType() == CollisionType.asteroid || data.getType() == CollisionType.player_bullet) {
			takeDamage(data.getDamage());
		}
	}
}