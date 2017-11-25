import java.util.ArrayList;

import javafx.scene.shape.Line;
import rotations.GameImage;

public class Fencer extends EnemyShip{
	
	private LaserLine laser;

	public Fencer(PhysXObject physObj, String sprite, int current_health, ShipStats stats, int aggression) {
		super(physObj, sprite, current_health, stats, aggression);
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<GameImage> fireLaser(Vector2 origin, Vector2 endPoint) {
		return laser.updateBeam(origin, endPoint);
	}
	
	@Override
	public void AIUpdate(Vector2 playerPos) {
		
		// Remove old laser
		for(GameImage sprite : laser.getSprites()) {
			this.gameConsoleSubscriber.programRequest_removeDrawnObject(sprite);
		}
		
		// Draw
		for(GameImage sprite : fireLaser(this.physObj.getPosition(), playerPos)) {
			this.gameConsoleSubscriber.programRequest_drawObject(sprite);
		}
	}

}
