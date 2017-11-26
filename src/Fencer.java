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

	public Fencer(PhysXObject physObj, String sprite, int current_health, ShipStats stats, int aggression) {
		super(physObj, sprite, current_health, stats, aggression);
		this.count = 0;
		this.shots = 1;
		this.shotCount = 0;
		this.laserDuration = 500;
		this.laserDelay = 100;
	}
	
	/*
	private void removeLasers() {		
		for(int i =0; i < numLasers; ++i) {
			for(LaserLine laser : lasers) {
				this.gameConsoleSubscriber.programRequest_removeDrawnObjects(laser.getSprites());
			}
		}
	}
	
	private void calculateTrajectories(Vector2 pos) {
		
		positions = new Vector2[numLasers + 1];
		for(int i =0; i < numLasers + 1; ++i) {
			positions[i] = new Vector2(this.getPhysObj().getPosition());
		}
		
		double theta_deg = Math.toDegrees(Math.atan2(pos.getY() - this.getPhysObj().getPosition().getY(), pos.getX() - this.getPhysObj().getPosition().getX()));
		int deg_spread = 100;
		theta_deg -= deg_spread;
		double unit_x = Math.cos(Math.toRadians(theta_deg));
		double unit_y = Math.sin(Math.toRadians(theta_deg));
		
		trajectories = new Vector2[numLasers + 1];
		for (int i = 0; i < numLasers + 1; i++) {
			trajectories[i] = new Vector2((float)(this.getPhysObj().getPosition().getX() + unit_x), (float)(this.getPhysObj().getPosition().getY() + unit_y));
			theta_deg += deg_spread;
			unit_x = Math.cos(Math.toRadians(theta_deg));
			unit_y = Math.sin(Math.toRadians(theta_deg));
		}
	}
	
	private void moveProjectiles() {
		
		for(int i =0; i < positions.length; ++i) {
			positions[i] = positions[i].add(trajectories[i].mult(new Vector2(1f,1f)));
		}
	}
	
	private void moveSprites() {
		int i = 0;
		int curr_laser = 0;
		while(i < numLasers) {
			if(i >= positions.length) {
				break;
			}
			System.out.println(curr_laser+" < curr_laser | lasers.length: "+ lasers.length);
			System.out.println(i+" < i | positions.length: "+ positions.length);

			this.gameConsoleSubscriber.programRequest_drawObjects(lasers[curr_laser].updateBeam(this.positions[i], this.positions[i+1]));
			
			lasers[curr_laser].updatePositions();
			curr_laser ++;
			i++;
		}
	}
	*/
	
	@Override
	public void AIUpdate(Vector2 playerPos) {
		
		if(PhysXLibrary.distance(this.physObj.getPosition(), playerPos) > 500) {
			count = laserDelay - 10;
			return;
		}
		
		// If the laser is supposed to be active
		if(count > laserDelay) { 
			
//			if(shotCount > shots) {
//				
//				// Blink to a new pos
////				blink(playerPos);
//				
//				// Reset the counter
//				shotCount = 0;
//				
//				// Reset the counter
//				count = 0;
//				
//				return;
//			}
			if(count <= laserDelay + laserDuration && isAiming) {
				PhysXObject obj = new PhysXObject(physObj.getQUID(), physObj.getPosition(), new CircleCollider(4));
				shoot(4, 25, CollisionType.enemy_bullet, 5, obj, "Bullet Large.png", playerPos);
				count = 0;
				isAiming = false;
			} else if(!isAiming) {
				isAiming = true;
				laserManagerSubscriber.createAdvancedLaserAtPlayer(this.physObj, laserDuration);
//				shotCount ++;
//				count = 0;
			}
			
		}
		
		count++;
	}

}
