import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import rotations.GameImage;

public class Boss extends EnemyShip implements ActionListener {

	private LaserLine laser;
	private int currentStage = 0;
	private static final int Stage2Health = 10;
	private int count;
	private int laserDuration;
	private int laserDelay;
	private int shots;
	private int shotCount;

	public Boss(PhysXObject physObj, int current_health, ShipStats stats) {
		super(physObj, "Enemy_1.png", current_health, stats, 5, EnemyType.BOSS, 0);
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
		/*
		for(GameImage sprite : laser.getSprites()) {
			this.gameConsoleSubscriber.programRequest_removeDrawnObject(sprite);
		}
		 */
	}

	private void drawLaser(Vector2 pos) {
		// Draw
		/*
		for(GameImage sprite : fireLaser(this.physObj.getPosition(), pos)) {
			this.gameConsoleSubscriber.programRequest_drawObject(sprite);
		}
		 */
	}

	@Override
	public void AIUpdate(Vector2 playerPos) {

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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void increaseStage() {
		if(this.getCurrentHealth()<Stage2Health){
			currentStage++;
		}
	}

	public void deathAnimation() {

	}

	public boolean startFight() {

		return false;
	}
}