import java.util.ArrayList;

import javafx.scene.shape.Line;
import rotations.GameImage;

public class Fencer extends EnemyShip{

	private int count;
	private int laserDuration;
	private int laserDelay;
	private int shots;
	private int shotCount;
	private boolean hasTrajectories;
	
	private int numLasers = 2;
	
	Vector2[] trajectories;
	Vector2[] positions;
	
	LaserLine[] lasers;

	public Fencer(PhysXObject physObj, String sprite, int current_health, ShipStats stats, int aggression) {
		super(physObj, sprite, current_health, stats, aggression);
		this.count = 0;
		this.shots = 1;
		this.shotCount = 0;
		this.laserDuration = 500;
		this.laserDelay = 100;
		this.hasTrajectories = false;
		
		trajectories = new Vector2[0];
		positions = new Vector2[0];
		lasers = new LaserLine[numLasers];
		for(int i =0; i< numLasers; i++) {
			lasers[i] = new LaserLine();
		}
	}
	
	private void removeLasers() {		
		for(int i =0; i < numLasers; ++i) {
			for(LaserLine laser : lasers) {
				for(GameImage sprite : laser.getSprites()) {
					this.gameConsoleSubscriber.programRequest_removeDrawnObject(sprite);
				}
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
			for(GameImage sprite : lasers[curr_laser].updateBeam(this.positions[i], this.positions[i+1])) {
				this.gameConsoleSubscriber.programRequest_drawObject(sprite);
			}
			lasers[curr_laser].updatePositions();
			curr_laser ++;
			i++;
		}
	}
	
	@Override
	public void AIUpdate(Vector2 playerPos) {
		
//		moveProjectiles();
		
		// If the laser is supposed to be active
		if(count > laserDelay) { 
			
			if(shotCount > shots) {
				
				// Blink to a new pos
//				blink(playerPos);
				
				// Reset the counter
				shotCount = 0;
				
				// Reset the counter
				count = 0;
				
				return;
			}
			
			// If the laser is active, Draw
			if(count < laserDelay + laserDuration) {
				if(!hasTrajectories) {
					calculateTrajectories(playerPos);
					hasTrajectories = true;
				}
				
				if(count %5 == 0) {
					removeLasers();
					moveProjectiles();
					moveSprites();
				}
			} else {
				
				// Remove the laser
				removeLasers();
				
				hasTrajectories = false;
				
				// Reset the counter
				count = 0;
				
				// Increase the shot count
				shotCount++;
			}
		}
		
		count++;
	}

}
