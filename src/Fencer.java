import java.util.ArrayList;

import javafx.scene.shape.Line;
import rotations.GameImage;

public class Fencer extends EnemyShip{
	
	private LaserLine laser;
	private int count;
	private int laserDuration;
	private int laserDelay;
	private int shots;
	
	private int shotCount;
	
	protected int minDist;
	protected int maxDist;

	public Fencer(PhysXObject physObj, String sprite, int current_health, ShipStats stats, int aggression) {
		super(physObj, sprite, current_health, stats, aggression);
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<GameImage> fireLaser(Vector2 origin, Vector2 endPoint) {
		return laser.updateBeam(origin, endPoint);
	}
	
	public void updateLaser(Vector2 pos) {
		// Remove old laser
		removeLaser();
		
		// Draw
		drawLaser(pos);
	}
	
	private void removeLaser() {
		// Remove old laser
		for(GameImage sprite : laser.getSprites()) {
			this.gameConsoleSubscriber.programRequest_removeDrawnObject(sprite);
		}
	}
	
	private void drawLaser(Vector2 pos) {
		// Draw
		for(GameImage sprite : fireLaser(this.physObj.getPosition(), pos)) {
			this.gameConsoleSubscriber.programRequest_drawObject(sprite);
		}
	}
	
	private void blink(Vector2 pos) {
		if (mangementSubscriber != null) {
			
			// Test and make sure it's safe
			Vector2 randomOffset = new Vector2(LavaLamp.randomSignedInt(minDist, maxDist), LavaLamp.randomSignedInt(minDist, maxDist));
			currentTarget = pos.add(randomOffset);
			int test = mangementSubscriber.isAreaSafe(currentTarget, this.getPhysObj().getColliders()[0].getRadius() * 2);
			
			while(test == 0) {
				randomOffset = new Vector2(LavaLamp.randomSignedInt(minDist, maxDist), LavaLamp.randomSignedInt(minDist, maxDist));
				currentTarget = pos.add(randomOffset);
				test = mangementSubscriber.isAreaSafe(currentTarget, this.getPhysObj().getColliders()[0].getRadius() * 2);
			}
			this.getPhysObj().setPosition(currentTarget);
		} else {
			
			// We gotta wing it!
			Vector2 randomOffset = new Vector2(LavaLamp.randomSignedInt(minDist, maxDist), LavaLamp.randomSignedInt(minDist, maxDist));
			currentTarget = pos.add(randomOffset);
			this.getPhysObj().setPosition(currentTarget);	
		}
		AudioPlayer myAudio = AudioPlayer.getInstance();
		myAudio.playSound("sounds", "BlinkerTeleport.wav");
	}
	
	@Override
	public void AIUpdate(Vector2 playerPos) {
		
		// If the laser is supposed to be active
		if(count > laserDelay) { 
			
			if(shotCount > shots) {
				
				// Blink to a new pos
				blink(playerPos);
				
				// Reset the counter
				shotCount = 0;
				
				// Reset the counter
				count = 0;
				
				return;
			}
			
			// If the laser is active, Draw
			if(count < laserDelay + laserDuration) {
				
				// Re-Draw the laser
				updateLaser(playerPos);
			} else {
				
				// Remove the laser
				removeLaser();
				
				// Reset the counter
				count = 0;
				
				// Increase the shot count
				shotCount++;
			}
		}
		
		count++;
	}

}
