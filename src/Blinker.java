import java.awt.Color;

import acm.graphics.GOval;

public class Blinker extends EnemyShip {
	protected int count;
	protected int blinkRate;
	protected int minDist;
	protected int maxDist;
	protected float blending;
	protected float blendStep;
	
	protected int bulletCount;
	protected int bulletCountTarget;
	
	protected Color targetColor;
	protected Color prevColor;
	
	protected GOval charger;
	
	private boolean BlinkerGOvalAdded = false;

	
	public Blinker(PhysXObject physObj, String sprite, int current_health, ShipStats stats, int aggression) {
		super(physObj, sprite, current_health, stats, aggression);
		
		this.blinkRate = getBlinkRate();
		this.count = 0;
		this.minDist = (int)(getInteractionDistance() / 4);
		this.maxDist = (int)(minDist * 1.25f);
		this.charger = new GOval(0,0,40,40);
		this.charger.setFilled(true);
		this.charger.setFillColor(Color.GREEN);
		Vector2 pos = Camera.backendToFrontend(this.physObj.getPosition());
		this.charger.setLocation(pos.getX() - (this.charger.getWidth() / 2 ), pos.getY() - (this.charger.getHeight()/ 2));
		
		this.targetColor = Color.WHITE;
		this.prevColor = Color.WHITE;
		this.BlinkerGOvalAdded = false;
		// TODO Auto-generated constructor stub
	}
	
	public GOval getCharger() {
		return this.charger;
	}
	
	protected int getBlinkRate() {
		switch(stats.getAggresionSetting()) {
		case 0:
			return 100;
		case 1:
			return 200;
		case 2:
			return 300;
		case 3:
			return 400;
		case 4:
			return 550;
		case 5:
			return 670;
		default:
			return 100;
		}
	}
	
	public float getInteractionDistance() {
		switch(stats.getAggresionSetting()) {
		case 0:
			return 275f *2;
		case 1:
			return 250f *2;
		case 2:
			return 225f *2;
		case 3:
			return 200f *2;
		case 4:
			return 150f *2;
		case 5:
			return 100f *2;
		default:
			return 50f *2;
		}
	}
	
	protected void updateCharger(int current, int goal) {
		Color chargerColor = PaintToolbox.blend(prevColor, this.targetColor, (float)current / (float)goal);
		this.charger.setFillColor(chargerColor);
		this.charger.setColor(chargerColor);
	}
	
	@Override
	public void AIUpdate(Vector2 playerPos) {
		
		if(!BlinkerGOvalAdded) {
			this.gameConsoleSubscriber.programRequest_drawGOval(this.getPhysObj(), this.charger);
			BlinkerGOvalAdded = true;
		}

		
		int chargeTime = 2;		
	
		// Current position
		// Is the player within range?
		if(PhysXLibrary.distance(this.physObj.getPosition(), playerPos) > getInteractionDistance()) {
			return;
		}
		
		if(count >= blinkRate) {
			if (mangementSubscriber != null) {
				
				// Test and make sure it's safe
				Vector2 randomOffset = new Vector2(LavaLamp.randomSignedInt(minDist, maxDist), LavaLamp.randomSignedInt(minDist, maxDist));
				currentTarget = playerPos.add(randomOffset);
				int test = mangementSubscriber.isAreaSafe(currentTarget, this.getPhysObj().getColliders()[0].getRadius() * 2);
				
				while(test == 0) {
					randomOffset = new Vector2(LavaLamp.randomSignedInt(minDist, maxDist), LavaLamp.randomSignedInt(minDist, maxDist));
					currentTarget = playerPos.add(randomOffset);
					test = mangementSubscriber.isAreaSafe(currentTarget, this.getPhysObj().getColliders()[0].getRadius() * 2);
				}
				this.getPhysObj().setPosition(currentTarget);
			} else {
				
				// We gotta wing it!
				Vector2 randomOffset = new Vector2(LavaLamp.randomSignedInt(minDist, maxDist), LavaLamp.randomSignedInt(minDist, maxDist));
				currentTarget = playerPos.add(randomOffset);
				this.getPhysObj().setPosition(currentTarget);
				
				
			}
			AudioPlayer myAudio = AudioPlayer.getInstance();
			myAudio.playSound("sounds", "BlinkerTeleport.wav");
			
			// Set the charger pos
			Vector2 pos = Camera.backendToFrontend(this.physObj.getPosition());
			this.charger.setLocation(pos.getX() - (this.charger.getWidth() / 2 ), pos.getY() - (this.charger.getHeight()/ 2));
			
			
			this.gameConsoleSubscriber.programRequest_removeObject(this);
			
			this.createSprite("BlinkerGifRev.gif");
			count = 0;
		} else if (count < blinkRate - (float)(blinkRate / chargeTime)){
			
			int numShots = 2;
			if(count % ((blinkRate - (float)(blinkRate / chargeTime)) / numShots) == 0) {
				
				// Shoot
				PhysXObject obj = new PhysXObject(physObj.getQUID(), physObj.getPosition(), new CircleCollider(4));
				shoot(1, 3, CollisionType.enemy_bullet, 5, obj, "RedCircle.png", playerPos);
				
				AudioPlayer myAudio = AudioPlayer.getInstance();
				myAudio.playSound("sounds", "BlinkerShoot.wav");
			} else {
				
				this.targetColor = new Color(205, 58, 42);
				this.prevColor = Color.WHITE;
				
				bulletCount = (count % ((int)(blinkRate - (float)(blinkRate / chargeTime)) / numShots));
				bulletCountTarget = ((int)(blinkRate - (float)(blinkRate / chargeTime)) / numShots);
				
				updateCharger(bulletCount, bulletCountTarget);
			}
		} else {
			this.targetColor = new Color(205, 48, 178);
			this.prevColor = Color.WHITE;
			updateCharger(count, blinkRate);
		}
		count++;
	}

}
