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
	
	// - - - - - - - - - - - - - 
	// - - - - - TIMING - - - - -
	// - - - - - - - - - - - - - 
	private int count = 0;
	private int stateCount = 0;
	private int currentState = -1;
	private int numStates = 3  -1;
	
	// Settings
		// Use '-3' to set the trigger flag as bypass
		// Use '-2' to set the trigger flag as OR
		// Use '-1' to set the trigger flag as AND
		// Use 'X' to set the trigger flag as  NULL
	
	/* trigger flag : instead of waiting
	 * for the count the trigger bypasses
	 * this and moves the state.
	 */
	
	private int bossState_0_Setting = 0;
	private int bossState_0_Duration = 0;
	private boolean bossState_0_CountTrigger;
	
	private int bossState_1_Setting = 0;
	private int bossState_1_Duration = 0;
	private boolean bossState_1_CountTrigger;
	
	private int bossState_2_Setting = 0;
	private int bossState_2_Duration = 0;
	private boolean bossState_2_CountTrigger;
	
	public BossRoomManager() {
		currentStage = 0;
		count = 0;
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
	
	/* * * * * * * * * * * * * * * 
				TIMING 
	* * * * * * * * * * * * * * */
	
	public void Update() {
		
		// Increment the count
		count++;
		
		// Do this first
		testStates();
		
		switch(currentState) {
		case -1:
			break;
		case 0:
			state_0();
			break;
		case 1:
			state_1();
			break;
		case 2:
			state_2();
			break;
		default:
			break;
		}
	}
	
	private void testStates() {
		
		// This needs to be set first. 
		stateCount ++;
		
		int state = currentState;
		switch(currentState) {

		case -1:			// Ready
			break;
		case 0:			// State 0
			state += testState(bossState_0_Setting, bossState_0_Duration, bossState_0_CountTrigger);
			break;
		case 1:
			state += testState(bossState_1_Setting, bossState_1_Duration, bossState_1_CountTrigger);
			break;	
		case 2:
			state += testState(bossState_2_Setting, bossState_2_Duration, bossState_2_CountTrigger);
			break;	
		}
		
		if (state != currentState) {
			
			// Loop the states
			if (state > numStates) {
				state = 0;
				onStateLoop();
			}
			
			// New state new count
			stateCount = 0;
		}

		currentState = state;
	}
	
	private int testState(int setting, int stateDuration, boolean trigger) {
		int result = 0;
		switch(setting) {
		case -3:
			// Bypass - count means nothing
			if (trigger) {
				result++;
			}
			break;
			
		case -2:
			// OR - either condtion
			if (trigger || stateCount >= stateDuration) {
				result++;
			}
			break;
			
		case -1:
			// AND - both condtions
			if (trigger && stateCount >= stateDuration) {
				result++;
			}
			break;
			
		default:
			// AND - both condtions
			if (trigger && stateCount >= stateDuration) {
				result++;
			}
			break;
		}
		
		// Reset the state
		if(result > 0) {
			trigger = false;
		}
		
		return result;
	}
	
	private void onStateLoop() {
		
	}
	
	
	/* * * * * * * * * * * * * * * 
				STATES 
	* * * * * * * * * * * * * * */
	
	private void state_0() {
		
	}
	
	private void state_1() {
		
	}
	
	private void state_2() {
		
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
