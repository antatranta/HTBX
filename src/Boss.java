import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import acm.graphics.GOval;
import rotations.GameImage;

public class Boss extends EnemyShip {

	private LaserLine laser;
	private int currentStage = 0;
	private static final int Stage2Health = 10;
	private int count;
	private int laserDuration;
	private int laserDelay;
	private int shots;
	private int shotCount;
	
	
	private GamePaneEvents gamePane_ref;
	private final String mainFaceFile = "Face.png";
	private final String mouthFile = "Mouth.png";
	private final String leftEyebrowFile = "eyebrow_0.png";
	private final String rightEyebrowFile = "eyebrow_1.png";
	
	private GameImage face;
	private GameImage leftBrow;
	private GameImage rightBrow;
	private GameImage mouth;
	
	private final Vector2 leftBrowOffset = new Vector2(-20,-10);
	private final Vector2 rightBrowOffset = new Vector2(30,-10);
	
	private final Vector2 browUp = new Vector2(0, 10);
	private final Vector2 browDown = new Vector2(0, -10);
	
	private final Vector2 mouthOffset = new Vector2(7,80);
	
	private Vector2 mouthPosition;
	private boolean isMouthOpen = false;
	
	private final Vector2 mouthUp = new Vector2(7,80);
	private final Vector2 mouthDown = new Vector2(7,95);
	
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

	public Boss(PhysXObject physObj, int current_health, ShipStats stats,GamePaneEvents gamePane_ref) {
		super(physObj, "Face.png", current_health, stats, 5, EnemyType.BOSS, 0);
		// TODO Auto-generated constructor stub
		this.gamePane_ref = gamePane_ref;
		setupFace();
		setupEyes();
	}

	public ArrayList<GameImage> fireLaser(Vector2 origin, Vector2 endPoint) {
		return laser.updateBeam(origin, endPoint);
	}
	
	private void setupFace() {
		face = new GameImage(mainFaceFile);
		leftBrow = new GameImage(leftEyebrowFile);
		rightBrow = new GameImage(rightEyebrowFile);
		mouth = new GameImage(mouthFile);
		
		face.setLocationRespectSize(physObj.getPosition().getX(), physObj.getPosition().getY());
		leftBrow.setLocationRespectSize(physObj.getPosition().getX() + leftBrowOffset.getX(), physObj.getPosition().getY() + leftBrowOffset.getY());
		rightBrow.setLocationRespectSize(physObj.getPosition().getX() + rightBrowOffset.getX(), physObj.getPosition().getY() + rightBrowOffset.getY());
		mouth.setLocationRespectSize(physObj.getPosition().getX() + mouthOffset.getX(), physObj.getPosition().getY() + mouthOffset.getY());
		mouthPosition = physObj.getPosition().add(mouthOffset);
		ArrayList<GameImage> images = new ArrayList<GameImage>();
		images.add(face);
		images.add(leftBrow);
		images.add(rightBrow);
		images.add(mouth);
		gamePane_ref.eventRequest_addObjects(images);
	}
	
	private void updateFace() {
		updatePosition(physObj.getPosition(), face);
		
		
		// Create the new offsets
		Vector2 newLeftBrowOffset = new Vector2(physObj.getPosition().getX() + leftBrowOffset.getX(), physObj.getPosition().getY() + leftBrowOffset.getY());
		Vector2 newRightBrowOffset = new Vector2(physObj.getPosition().getX() + rightBrowOffset.getX(), physObj.getPosition().getY() + rightBrowOffset.getY());
		
		if(currentEyeState == EyeState.Angry) {
			newLeftBrowOffset.add(browUp);
			newRightBrowOffset.add(browUp);
		} else {
			newLeftBrowOffset.add(browDown);
			newLeftBrowOffset.add(browDown);
		}
		
		updatePosition(newLeftBrowOffset, leftBrow);
		updatePosition(newRightBrowOffset, rightBrow);
		
		if(this.isMouthOpen) {
			mouthPosition = PhysXLibrary.lerpBetweenPoints(mouthPosition, mouthDown.add(this.physObj.getPosition()), .5f);
		} else {
			mouthPosition = PhysXLibrary.lerpBetweenPoints(mouthPosition, mouthUp.add(this.physObj.getPosition()), .5f);
		}
		updatePosition(mouthPosition, mouth);
		
		openMouth();
		
		currentEyeState = EyeState.Angry;
	}
	
	public void openMouth() {
		isMouthOpen = true;
	}
	
	public void closeMouth() {
		isMouthOpen = false;
	}
	
	private void updatePosition(Vector2 b_pos, GameImage sprite) {
		Vector2 f_pos = Camera.backendToFrontend(b_pos);
		sprite.setLocationRespectSize(f_pos.getX(), f_pos.getY());
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
			// trackingPosition
			break;
		case Angry:
			
			currentEyeColor = angryColor;
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
		
		updateFace();
		updateEyes();

		count++;
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