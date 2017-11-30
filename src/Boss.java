import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import acm.graphics.GOval;
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
	private final String mainFace = "";
	private final String leftEyebrow = "";
	private final String rightEyebrow = "";
	
	private final double eyeSize = 20;
	private final double eyeBlinkSize = 5;
	private EyeState currentEyeState;
	
	private GOval leftEye;
	private GOval rightEye;
	private Color currentEyeColor;
	
	private int blinkDuration;
	private int blinkCounter;
	
	private final Color idleColor = PaintToolbox.WHITE;
	private final Color angryColor = PaintToolbox.RED;
	
	private Vector2 trackingPosition;

	public Boss(PhysXObject physObj, int current_health, ShipStats stats) {
		super(physObj, "Enemy_1.png", current_health, stats, 5, EnemyType.BOSS, 0);
		// TODO Auto-generated constructor stub
		
		setupEyes();
	}

	public ArrayList<GameImage> fireLaser(Vector2 origin, Vector2 endPoint) {
		return laser.updateBeam(origin, endPoint);
	}
	
	private void setupEyes() {
		currentEyeColor = idleColor;
		
		leftEye = new GOval(eyeSize, eyeSize);
		rightEye = new GOval(eyeSize, eyeSize);
		
		leftEye.setColor(PaintToolbox.TRANSPARENT);
		rightEye.setColor(PaintToolbox.TRANSPARENT);
		
		leftEye.setFillColor(currentEyeColor);
		rightEye.setFillColor(currentEyeColor);
		
		leftEye.setFilled(true);
		rightEye.setFilled(true);
	}
	
	public void updateEyes() {
		
		
		// Set the color
		leftEye.setFillColor(currentEyeColor);
		rightEye.setFillColor(currentEyeColor);
		
		// Determine the behaviour
		switch(currentEyeState) {
		case Idle:
			// Do nothing
		case Blink:
			// Blink
			if(blinkCounter == -77) {
				blinkCounter = 0;
				
				// Here we actually blink
				leftEye.setSize(eyeSize, eyeBlinkSize);
				rightEye.setSize(eyeSize, eyeBlinkSize);
				
			} else if (blinkCounter < blinkDuration){
				blinkCounter++;
			} else if (blinkCounter >= blinkDuration) {
				
				// Reset
				leftEye.setSize(eyeSize, eyeSize);
				rightEye.setSize(eyeSize, eyeSize);
				
				blinkCounter = -77;
				currentEyeState = EyeState.Idle;
			}
			break;
		case Track:
			// Track player pos
			break;
		case Angry:
			break;
		default:
			// Do nothing
			break;
		}
		
		
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