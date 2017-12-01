import java.util.ArrayList;

import rotations.GameImage;

public class BossRoomManager {
	
	private static final double ACTIVATION_DISTANCE = 2000;
	
	private Boss bossShip;
	private int currentStage = 0;
	private ArrayList<BulletEmitter> bulletEmitters;
	private String bulletEmitterSprite = "";
	
	private GameConsoleEvents 	gameConsole_ref;
	private GamePaneEvents		gamePane_ref;
	
	private final double stg_0_be_delta = 1;
	
	// - - - - - - - - - - - - - 
	// - - - - - TIMING - - - - -
	// - - - - - - - - - - - - - 
	private int count = 0;
	private int stateCount = 0;
	private int currentState = -1;
	private int numStates = 3  -1;
	private boolean started = false;
	private boolean started_0 = false;
	private boolean started_1 = false;
	private boolean started_2 = false;
	
	// Settings
		// Use '-3' to set the trigger flag as bypass
		// Use '-2' to set the trigger flag as OR
		// Use '-1' to set the trigger flag as AND
		// Use 'X' to set the trigger flag as  NULL
	
	/* trigger flag : instead of waiting
	 * for the count the trigger bypasses
	 * this and moves the state.
	 */
	
	// STAGE 0
	private static final int BARRIER_SHIELD_COUNT = 16;
	private static final int BARRIER_SHIELD_RADIUS = 300;
	private static final double BARRIER_SHIELD_SPEED = 0.5;
	private int bossState_0_barriers_left = -1;
	private int bossState_0_emitter_time = 0;
	private int bossState_0_Setting = 0;
	private int bossState_0_Duration = 100;
	private boolean bossState_0_CountTrigger;
	
	
	// STAGE 1
	private int bossState_1_Setting = 0;
	private int bossState_1_Duration = 100;
	private boolean bossState_1_CountTrigger;
	
	// STAGE 2
	private int bossState_2_Setting = 0;
	private int bossState_2_Duration = 100;
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
				ShipStats.bossBulletEmitter_0(),			// ShipStats
				new BulletEmitterData(),					// Bullet bank
				BulletEmitterBehavior.SHOOT_CLOCKWISE,	// Firing pattern
				stg_0_be_delta,							// Angle delta
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
		if (!started) {
			double dist = PhysXLibrary.distance(bossShip.getPhysObj().getPosition(), gameConsole_ref.physXRequest_getPlayerPhysObj().getPosition());
			if (dist < ACTIVATION_DISTANCE) {
				started = true;
				System.out.println("Fight started!");
			}
		}
		else if (started) {
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
	}
	
	private void testStates() {
		
		// This needs to be set first. 
		stateCount ++;
		
		int state = currentState;
		switch(currentState) {

		case -1:			// Ready
			state += 1;
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
			// OR - either condition
			if (trigger || stateCount >= stateDuration) {
				result++;
			}
			break;
			
		case -1:
			// AND - both conditions
			if (trigger && stateCount >= stateDuration) {
				result++;
			}
			break;
			
		default:
			// AND - both conditions
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
		if (!started_0) {
			started_0 = true;
			init_state_0();
		}
		else {
			if (this.bossState_0_emitter_time == 0) {
				this.bossState_0_emitter_time = 360;
				double delta = 0.5;
				PhysXObject po = new PhysXObject(new QuadrantID(bossShip.getPhysObj().getQUID()),
						new Vector2(bossShip.getPhysObj().getPosition()),
						new CircleCollider(0));
				BulletEmitterData bed = new BulletEmitterData(1000, 0, 0, 0, 0);
				BulletEmitter be = new BulletEmitter(po, "BulletEmitter.png", ShipStats.bossBulletEmitter_0(),
						bed, BulletEmitterBehavior.SHOOT_CLOCKWISE , delta, BulletType.STRAIGHT, true);
				be.addSubscriber(gameConsole_ref.programRequest_getBulletManager());
				be.HAX_setInfiniteBullets(true);
				gameConsole_ref.programRequest_makeEnemy(be);

			}
		}
	}
	
	// Decrement the barriers
	public void stage0_decrementBarriers() {
		this.bossState_0_barriers_left -= 1;
	}
	
	// Initialize stage 0
 	private void init_state_0() {
 		double delta = 360 / (BARRIER_SHIELD_COUNT - 1);
 		double ang = 0;
 		this.bossState_0_barriers_left = 0;
		for (int i = 0; i <= BARRIER_SHIELD_COUNT; i++) {
			this.bossState_0_barriers_left += 1;
			double x = Math.cos(Math.toRadians(ang));
			double y = Math.sin(Math.toRadians(ang));
			PhysXObject po = new PhysXObject(new QuadrantID(bossShip.getPhysObj().getQUID()), new Vector2(bossShip.getPhysObj().getPosition().add( new Vector2((float)(x * BARRIER_SHIELD_RADIUS), (float)(y * BARRIER_SHIELD_RADIUS)))), new CircleCollider(25));
			ShipStats ss = ShipStats.EnemyStats_BossBarrier();
			BossBarrier rock = new BossBarrier(po, "Boss_Barrier_Small.png", ss.getHealthMax(), ss,
					0, EnemyType.BARRIER, ang, BARRIER_SHIELD_RADIUS, bossShip.getPhysObj().getPosition());
			rock.addGameConsole(gameConsole_ref);
			rock.setOrbitSpeed(BARRIER_SHIELD_SPEED);
			rock.setManagerRef(this);
			gameConsole_ref.programRequest_makeEnemy(rock);
			// Create shields
			ang = delta * i;
		}
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
