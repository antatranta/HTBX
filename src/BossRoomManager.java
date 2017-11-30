import java.util.ArrayList;

import rotations.GameImage;

public class BossRoomManager {
	
	private Boss bossShip;
	private int currentStage = 0;
	private ArrayList<BulletEmitter> bulletEmitters;
	private String bulletEmitterSprite = "";
	
	private GameConsoleEvents 	gameConsole_ref;
	private GamePaneEvents		gamePane_ref;
	
	private final double stg_0_be_delta = 180;
	
	public BossRoomManager() {
		bulletEmitters = new ArrayList<BulletEmitter>();
	}

	private void createBulletEmitterCircle(int numEmitters) {
		// Create 'numEmitters' PhysXObjects in a evenly spaced circle around the boss
			// Use 'createBulletEmitter()' 
	}
	
	private BulletEmitter createBulletEmitter(PhysXObject obj, Vector2 pos, int health) {
		//PhysXObject obj, String sprite, ShipStats stats, BulletEmitterData bullet_data, BulletEmitterBehavior beh, double delta, BulletType type, boolean can_hurt
		return new BulletEmitter(
				obj,										// PhysXObject
				bulletEmitterSprite,						// Sprite
				ShipStats.bossBulletEmitter_0(),			// ??
				new BulletEmitterData(),					// ??
				BulletEmitterBehavior.SHOOT_CLOCKWISE,	// ??
				stg_0_be_delta,							// ??
				BulletType.STRAIGHT,   					// BulletType
				false); 									// Can hurt
	}
	
	private void drawBulletEmitters() {
		
		if(bulletEmitters == null || bulletEmitters.size() <= 0) {
			return;
		}
		
		ArrayList<GameImage> bulletEmitterGameImages = new ArrayList<GameImage>();
		for(BulletEmitter be : bulletEmitters) {
			if(be != null) {
				bulletEmitterGameImages.add(be.getSprite());
			}
		}
		
		if(bulletEmitterGameImages != null && bulletEmitterGameImages.size() > 0) {
			gamePane_ref.eventRequest_addObjects(bulletEmitterGameImages);
		} else {
			System.out.println("[WARN] Bullet Emitters appear to be missing sprites!");
		}
	}
	
	public Boss getBossShip() {
		return bossShip;
	}

	public void setBossShip(Boss bossShip) {
		this.bossShip = bossShip;
	}
	
	public int getCurrentStage() {
		return this.currentStage;
	}
	
	public void addBulletEmitter(BulletEmitter bulletEmitter) {
		if(bulletEmitter != null)
			this.bulletEmitters.add(bulletEmitter);
	}
	
	public void addBulletEmitters(ArrayList<BulletEmitter> bulletEmitters) {
		if(bulletEmitters != null && bulletEmitters.size() > 0)
			this.bulletEmitters.addAll(bulletEmitters);
	}
	
	public ArrayList<BulletEmitter> getBulletEmitters(){
		return this.bulletEmitters;
	}

	public void setGameConsole_ref(GameConsoleEvents gameConsole_ref) {
		this.gameConsole_ref = gameConsole_ref;
	}

	public void setGamePane_ref(GamePaneEvents gamePane_ref) {
		this.gamePane_ref = gamePane_ref;
	}
}
