import java.util.ArrayList;

import javafx.scene.shape.Line;
import rotations.GameImage;

public class Fencer extends EnemyShip{

	private int count;
	private int laserDuration;
	private int laserDelay;
	private int shots;
	private int shotCount;
	private boolean isAiming;

	public Fencer(PhysXObject physObj, String sprite, int current_health, ShipStats stats, int aggression, int exp) {
		super(physObj, sprite, current_health, stats, aggression, EnemyType.FENCER, exp);
		this.count = 0;
		this.shots = 1;
		this.shotCount = 0;
		this.laserDuration = 500;
		this.laserDelay = 100;
	}

	@Override
	public void AIUpdate(Vector2 playerPos) {

		if(PhysXLibrary.distance(this.physObj.getPosition(), playerPos) > 500) {
			count = laserDelay - 10;
			return;
		}

		// If the laser is supposed to be active
		if(count > laserDelay) { 
			if(count <= laserDelay + laserDuration && isAiming) {
				PhysXObject obj = new PhysXObject(physObj.getQUID(), physObj.getPosition(), new CircleCollider(4));
				BulletFireEventData bfe = new BulletFireEventData(4, 25, BulletType.STRAIGHT, CollisionType.enemy_bullet, 5, obj, "Bullet Large.png", playerPos, FXManager.colorParticle(PaintToolbox.RED));
				shoot(bfe);
				count = 0;
				isAiming = false;
			} else if(!isAiming) {
				isAiming = true;
				laserManagerSubscriber.createAdvancedLaserAtPlayer(this.physObj, laserDuration);
			}

		}

		count++;
	}

}
