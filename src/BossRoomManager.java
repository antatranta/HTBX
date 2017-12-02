import java.util.ArrayList;

import rotations.GameImage;

public class BossRoomManager {
	
	// That's a lot of damage!
	private static final int TONS_OF_DAMAGE = 1000000;
	private static final double ACTIVATION_DISTANCE = 2000;
	
	private Boss bossShip;
	private int currentStage = -1;
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
	private boolean finished_setting_1 = false;
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
	private static final int STAGE_0_BARRIER_SHIELD_COUNT = 16;
	private static final int STAGE_0_BARRIER_SHIELD_RADIUS = 150;
	private static final double STAGE_0_BARRIER_SHIELD_SPEED = 0.75;
	
	private int bossStage_0_barriers_left = -1;
	private boolean bossStage_0_emitter_made = false;
	private int bossStage_0_emitter_reverse_cap = 600;
	private int bossStage_0_emitter_time = 0;
	private BulletEmitter bossStage_0_bullet_emitter = null;
	private ArrayList<BossBarrier> stage_0_barriers = new ArrayList<BossBarrier>();
	
	// STAGE 1
	private static final int STAGE_1_BARRIER_SHIELD_COUNT = 4;
	private static final int STAGE_1_BARRIER_SHIELD_RADIUS = 0;
	private static final double STAGE_1_BARRIER_SHIELD_SPEED = 0.1;
	
	private ArrayList<BossBarrier> stage_1_barriers = new ArrayList<BossBarrier>();
	private ArrayList<BulletEmitter> stage_1_emitters = new ArrayList<BulletEmitter>();
	
	// STAGE 2

	
	public BossRoomManager() {
		currentStage = 0;
		count = 0;
	}
	
	/* * * * * * * * * * * * * * * 
				TIMING 
	* * * * * * * * * * * * * * */
	
	public void Update() {
		if (!started) {
			double dist = PhysXLibrary.distance(bossShip.getPhysObj().getPosition(), gameConsole_ref.physXRequest_getPlayerPhysObj().getPosition());
			if (dist < ACTIVATION_DISTANCE) {
				started = true;
				currentStage = 0;
				gamePane_ref.tell_HUDBossIsActive(true);
				System.out.println("Fight started!");
			}
		}
		else if (started) {
			// Increment the count
			gamePane_ref.request_HUDtoDrawBossHP(bossShip.getCurrentHealth(), bossShip.getStats().getHealthMax());
			count++;
			// Do this first
			switch(currentStage) {
			case 0:
				stage_0();
				break;
			case 1:
				stage_1();
				break;
			case 2:
				stage_2();
				break;
			}

		}
	}
	
	private BulletEmitter makeBulletEmitter(Vector2 pos) {
		double delta = 0.1;
		PhysXObject po = new PhysXObject(new QuadrantID(bossShip.getPhysObj().getQUID()),
				pos,
				new CircleCollider(0));
		BulletEmitterData bed = new BulletEmitterData(1000, 0, 0, 0, 0);
		BulletEmitter be = new BulletEmitter(po, "BulletEmitter.png", ShipStats.bossBulletEmitter_0(),
				bed, BulletEmitterBehavior.SHOOT_CLOCKWISE , delta, BulletType.STRAIGHT, true);
		be.addSubscriber(gameConsole_ref.programRequest_getBulletManager());
		be.HAX_setInfiniteBullets(true);
		gameConsole_ref.programRequest_makeEnemy(be);
		return be;
	}
	
 	private void createBarriers(int stage) {
 		int amount = 0;
 		int radius = 0;
 		double speed = 0;
 		switch (stage) {
 		case 0:
 			amount = BossRoomManager.STAGE_0_BARRIER_SHIELD_COUNT;
 			radius = BossRoomManager.STAGE_0_BARRIER_SHIELD_RADIUS;
 			speed = BossRoomManager.STAGE_0_BARRIER_SHIELD_SPEED;
 			this.bossStage_0_barriers_left = amount;
 			break;
 		case 1:
 			amount = BossRoomManager.STAGE_1_BARRIER_SHIELD_COUNT;
 			radius = BossRoomManager.STAGE_1_BARRIER_SHIELD_RADIUS;
 			speed = BossRoomManager.STAGE_1_BARRIER_SHIELD_SPEED;
 		default:
 			break;
 		}
 		double delta = 360 / (amount - 1);
 		double ang = 0;
		for (int i = 0; i < amount; i++) {
			double x = Math.cos(Math.toRadians(ang));
			double y = Math.sin(Math.toRadians(ang));

			PhysXObject po = new PhysXObject(new QuadrantID(bossShip.getPhysObj().getQUID()), new Vector2(bossShip.getPhysObj().getPosition().add( new Vector2((float)(x * radius), (float)(y * radius)))), new CircleCollider(25));
			ShipStats ss = ShipStats.EnemyStats_BossBarrier();
			BossBarrier shield = new BossBarrier(po, "Boss_Barrier_Small.png", ss.getHealthMax() + ((ss.getHealthMax() / 4) * i), ss,
					0, EnemyType.BARRIER, ang, radius, bossShip.getPhysObj().getPosition());
			shield.addGameConsole(gameConsole_ref);
			shield.setOrbitSpeed(speed);
			shield.setManagerRef(this);
			switch (stage) {
			case 0:
				stage_0_barriers.add(shield);
				break;
			case 1:
				stage_1_barriers.add(shield);
				break;
			}
			gameConsole_ref.programRequest_makeEnemy(shield);
			// Create shields
			ang = delta * i;
		}
	}
	
	/* * * * * * * * * * * * * * * 
				STATES 
	* * * * * * * * * * * * * * */
	
	private void stage_0() {
		if (!started_0) {
			started_0 = true;
			
			System.out.println("Starting stage 0!");
			init_stage_0();
		}
		else {
			if (!bossStage_0_emitter_made) {
				bossStage_0_emitter_made = true;

				BulletEmitter be = makeBulletEmitter(bossShip.getPhysObj().getPosition());
				this.bossStage_0_bullet_emitter = be;
			}
			// Reverse weapon attacks periodically
			if (bossStage_0_emitter_time == 0) {
				this.bossStage_0_emitter_time = bossStage_0_emitter_reverse_cap;
				this.bossStage_0_bullet_emitter.reverseDirection();
			}
			this.bossStage_0_emitter_time -= 1;
			if (bossStage_0_barriers_left == 0) {
				currentStage = 1;
				this.bossStage_0_bullet_emitter.takeDamage(TONS_OF_DAMAGE);
			}
		}
	}
	
	// Decrement the barriers
	public void stage0_decrementBarriers() {
		this.bossStage_0_barriers_left -= 1;
	}
	
	// Initialize stage 0
	private void init_stage_0() {
		createBarriers(0);
	}

	
	private void stage_1() {
		if (!started_1) {
			started_1 = true;
			
			System.out.println("Starting stage 1!");
			init_stage_1();
			bossShip.setStage(1);
		}
		else if (started_1 && !finished_setting_1) {
			if (stage_1_barriers.get(0).reachedDestination()) {
				finished_setting_1 = true;
				for (BossBarrier b: stage_1_barriers) {
					BulletEmitter be = makeBulletEmitter(b.getPhysObj().getPosition());
					be.setWeaponCooldown(40);
					stage_1_emitters.add(be);
					b.takeDamage(TONS_OF_DAMAGE);
				}
			}
		}
		else {
			
		}
	}
	
	private void init_stage_1() {
		createBarriers(1);
		for (BossBarrier b: stage_1_barriers) {
			b.moveToDistance(600);
		}
	}
	
	private void stage_2() {
		
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
