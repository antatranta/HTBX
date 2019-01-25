import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.*;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;
import rotations.GameImage;

public class GameConsole extends GraphicsProgram implements GameConsoleEvents{

	public static boolean IS_DEBUGGING;
	private int TIMER_INTERVAL = 16;
	private int INITIAL_DELAY = 0;

	private ArrayList<Ship> ships = new ArrayList<Ship>();

	private ArrayList<BulletEmitter> emitters = new ArrayList<BulletEmitter>();
	private PlayerShip player;
	private MapCreator mapCreator;

	private PhysX physx; // The controller for all things
	private int level;
	private int next_level;
	private int skill_points;
	private int exp;
	private Camera camera;
	private GameTimer gameTimer;
	private BulletManager bulletStore;
	private LaserManager laserStore;
	private BossRoomManager bossRoomManager;
	private FXManager fx;
	private ShipManagement shipManager;
	private GamePaneEvents gamePane_ref;
	private TeleportWaypoint bossRoomTrigger;
	private boolean sfxToggle;
	// out of bounds
	private boolean isOutOfBounds = false;
	private long startTimeOutOfBounds;
	//Score
	private int score=0;
	private int enemiesKilled=0;
	private static final int scorePerEnemy = 100;
	private static final int scorePerDamage = 25;
	
	//For story
	private int storyProgression = enemiesKilled;

	public GameConsole() {
		endDebugView();
		skill_points = 0;
		exp = 0;
		level = 0;
		calculateNeededExp();
		
		sfxToggle = true;
		// set up the clock for the game
		gameTimer = new GameTimer();
		gameTimer.setupTimer(TIMER_INTERVAL, INITIAL_DELAY);

		// create an object to handle physX
		physx = new PhysX(PhysXLibrary.QUADRANT_HEIGHT, PhysXLibrary.QUADRANT_WIDTH, PhysXLibrary.MAP_WIDTH, PhysXLibrary.MAP_HEIGHT);

		// create a new bullet manager
		bulletStore = new BulletManager(this);

		laserStore = new LaserManager(this);
		fx = new FXManager();

		// setup a new camera
		camera = new Camera();
		camera.setupCamera(1, 1);

		// create a new map
		mapCreator = new MapCreator();

		// populate the PhysX sim
		physx.addQuadrants(mapCreator.createMap());

		CircleCollider playerCollider = new CircleCollider(Vector2.Zero(), 15);
		player = mapCreator.placePlayer(mapCreator.getPlayerSpawn().getQUID());
		player.physObj.removeColliders();
		player.physObj.addCollider(playerCollider);
		player.setDxDy(Vector2.Zero());
		player.addSubscriber(bulletStore);
		gameTimer.addListener(player);
		
		// create a new ship manager
		shipManager = new ShipManagement(this);
		
		// give all the ships refs
		for(EnemyShip ship: getAllShips()) {
			ship.addSubscriber(shipManager);
			ship.addGameConsole(this);
			ship.addLaserManager(laserStore);
		}
		
		bossRoomTrigger = new TeleportWaypoint(mapCreator.getBossSpawn().getQUID(), this);
		bossRoomTrigger.setActivationDistance(TeleportWaypoint.AURORA_DISTANCE);
		bossRoomTrigger.setTeleportDistance(TeleportWaypoint.AURORA_INNER);
		bossRoomTrigger.setInteractable(true);
		
		System.out.println("Player Pos before GamePane: " + player.getPhysObj().getPosition().getX() + ", " + player.getPhysObj().getPosition().getY());
		System.out.println("Made a new game console");
		
		startTimeOutOfBounds = System.nanoTime();
	}
	
	public void progressStory() {
		storyProgression++;
	}
	
	public int getEnemiesKilled() {
		return storyProgression;
	}
	
	public TeleportWaypoint getBossRoomTrigger() {
		return bossRoomTrigger;
	}

	public void startDebugView() {
		IS_DEBUGGING = true;
		System.out.println("- - - DEBUG ON - - -");
	}

	public void endDebugView() {
		IS_DEBUGGING = false;
		System.out.println("- - - DEBUG OFF - - -");
	}

	public void changeGraphicsRatio(float FR, float BR) {
		camera.setupCamera(FR, BR);
		if(IS_DEBUGGING) {
			System.out.println("- - - Changed Camera Ratios - - -");
			System.out.println("FORWARD -> "+FR);
			System.out.println("BACKWAR -> "+BR);
		}
	}

	public void testCollisions(PlayerShip player) {

		physx.checkForCollisionsInQuads();

		if(player.getPhysObj() != null) {
			physx.checkForCollisions(player.getPhysObj());

		}
		if(bulletStore.getBullets() != null) {
			physx.checkForCollisions(player.getPhysObj(),bulletStore.getPhysXObjects());
			for(Bullet bullet:bulletStore.getBullets()) {
				physx.checkForCollisions(bullet.getPhysObj());
			}
		}
	}

	public PhysX physx() {
		return physx;
	}
	
	public MapCreator getMapCreatorModule() {
		return this.mapCreator;
	}

	public ArrayList<Asteroid> getActiveAsteroids() {

		ArrayList<Asteroid> Asteroids = new ArrayList<Asteroid>();
		ArrayList<Quadrant> quads = physx.getActiveQuadrants();
		for (Quadrant quad : quads) {
			ArrayList<Asteroid> asteroids = quad.getAsteroids();
			if(asteroids != null && asteroids.size() > 0) {
				Asteroids.addAll(quad.getAsteroids());
			}
		}

		return Asteroids;
	}

	public ArrayList<EnemyShip> getActiveShips() {
		ArrayList<EnemyShip> EnemyShips = new ArrayList<EnemyShip>();
		ArrayList<Quadrant> quads = physx.getActiveQuadrants();
		for (Quadrant quad : quads) {
			ArrayList<EnemyShip> ships = quad.getShips();
			if(ships != null && ships.size() > 0) {
				EnemyShips.addAll(quad.getShips());
			}
		}
		return EnemyShips;
	}

	public ArrayList<EnemyShip> getAllShips() {
		ArrayList<EnemyShip> EnemyShips = new ArrayList<EnemyShip>();
		ArrayList<Quadrant> quads = physx.getQuadrants();
		for (Quadrant quad : quads) {
			ArrayList<EnemyShip> ships = quad.getShips();
			if(ships != null && ships.size() > 0) {
				EnemyShips.addAll(ships);
			}
		}
		return EnemyShips;
	}

	public PlayerShip getPlayer() {
		return this.player;
	}

	public void moveBullets() {
		this.bulletStore.moveBullets();
	}

	public ArrayList<GameImage> cullBullets() {
		return this.bulletStore.getDeadBullets();
	}

	public GameTimer getTimer() {
		return gameTimer;
	}

	public BulletManager getBulletManager() {
		return bulletStore;
	}

	public LaserManager getLaserManager() {
		return laserStore;
	}

	public FXManager getFXManager() {
		return fx;
	}
	
	public void createBulletEmitter(PhysXObject physObj, String sprite, ShipStats stats, BulletEmitterData data, BulletEmitterBehavior beh, double angle_delta, BulletType type, boolean can_hurt ) {
		BulletEmitter be = new BulletEmitter(physObj, sprite, stats, data, beh, angle_delta, type, can_hurt);
		be.addSubscriber(getBulletManager());
		emitters.add(be);
	}

	public ArrayList<BulletEmitter> getActiveBulletEmitters() {
		ArrayList<BulletEmitter> bulletEmitters = new ArrayList<BulletEmitter>(); 
		for(int i=0; i < emitters.size(); i++) {
			if(PhysXLibrary.arePositionsInXRange(emitters.get(i).getPhysObj().getPosition(), player.getPhysObj().getPosition(), 750)) {
				bulletEmitters.add(emitters.get(i));
			}
		}
		return bulletEmitters;
	}

	@Override
	public void onShipDeath(Vector2 pos, int ship_exp) {
		calculateNeededExp();
		enemiesKilled++;
		storyProgression = enemiesKilled;
		exp += ship_exp;
		if (exp >= next_level) {
			exp -= next_level;
			level += 1;
			skill_points += 1;
			calculateNeededExp();
		}
		SetScore();
		System.out.println("Score: "+score +"| EnemyKills: "+ enemiesKilled+"| Taken Damage: " + player.getDamageTaken());
		System.out.println("Level: " + level + "| Exp: " + exp + "| SP: " + skill_points);

		FXParticle particle = FXManager.deathFlash();
		particle.setPosition(pos);
		fx.makeDeathFlash(FXType.COLOR_CHANGE, particle);
	}

	private void calculateNeededExp() {
		int base = 20;
		next_level = (int)(base + (level * 20));
	}

	public int getSP() {
		return skill_points;
	}

	public void levelUpSkill(LevelUpEnum stat) {
		if (skill_points == 0) {
			return;
		}
		switch(stat) {
		case speed:
			if (player.getBonusStats().getSpeedSetting() < 4) {
				player.getBonusStats().incSpeed(1);
				skill_points -= 1;
			}
			break;
		case damage:
			if (player.getBonusStats().getDamage() < 4) {
				player.getBonusStats().incDamage(1);
				skill_points -= 1;
			}
			break;
		case health:
			if (player.getBonusStats().getHealthMax() < 4) {
				player.getBonusStats().incHealthMax(1);
				player.setCurrentHealth(player.getCurrentHealth() + 1);
				skill_points -= 1;
			}
			break;
		case shield:
			if (player.getBonusStats().getShieldMax() < 4) {
				player.getBonusStats().incShieldMax(1);
				skill_points -= 1;
			}
			break;
		default:
			break;
		}
	}

	@Override
	public boolean physXRequest_isAreaSafe(Vector2 pos, float range) {
		return physx.isPositionSafe(pos, range);
	}

	@Override
	public void programRequest_drawGOval(PhysXObject obj, GOval oval) {
		StaticGObject sgo = new StaticGObject(obj);
		sgo.setSingleObject(oval);
		sgo.setSize(new Vector2((float)oval.getWidth(), (float)oval.getHeight()));
		gamePane_ref.eventRequest_addStaticGObject(sgo);
	}

	public void addGraphicsSubscriber(GamePaneEvents game) {
		gamePane_ref = game;
	}

	@Override
	public void programRequest_removeObject(EnemyShip obj) {
		this.gamePane_ref.eventRequest_removeShip(obj);
	}

	@Override
	public void programRequest_removeDrawnObjects(ArrayList<GameImage> objects) {
		this.gamePane_ref.eventRequest_removeObjects(objects);
	}

	@Override
	public void programRequest_drawObjects(ArrayList<GameImage> objects) {
		this.gamePane_ref.eventRequest_addObjects(objects);
	}
	
	@Override
	public PlayerShip physXRequest_getPlayer() {
		return player;
	}

	@Override
	public PhysXObject physXRequest_getPlayerPhysObj() {
		return player.getPhysObj();
	}

	@Override
	public void programRequest_removeGOval(PhysXObject obj, GOval oval) {
		this.gamePane_ref.eventRequest_removeObject(oval);
	}

	@Override
	public void UIRequest_addThreat(Vector2 pos) {
		this.gamePane_ref.addThreat(pos);
	}

	@Override
	public void programRequest_makeFX(FXPattern pattern, FXType type, FXParticle particle) {
		switch(pattern) {
		case SPARKS_DEFLECT:
			fx.makeDeflectSparks(type, particle);
			break;
		case GROW_STATIONARY:
			fx.makeBulletFlash(type, particle);
		default:
			break;
		}

	}
	
	@Override
	public void programRequest_makeBossRoom() {
		
		// Delete the old PhysXObject and create a new one
		physx = new PhysX(PhysXLibrary.BOSS_QUADRANT_HEIGHT, PhysXLibrary.BOSS_QUADRANT_WIDTH, 1, 1);
		
		// Add in the new Quadrant
		physx.addQuadrant(mapCreator.createBossRoom().get(0));
		
		// Create a new PhysXObject
		QuadrantID qid = physx.getQuadrants().get(0).getQUID();
		PhysXObject boss_obj = new PhysXObject(qid, new CircleCollider(90));
		
		// Get the position of the new Object
		Vector2 pos = new Vector2((boss_obj.getQUID().getX() * PhysXLibrary.QUADRANT_WIDTH) + (PhysXLibrary.QUADRANT_WIDTH / 2), (boss_obj.getQUID().getY() * PhysXLibrary.QUADRANT_HEIGHT) + (PhysXLibrary.QUADRANT_HEIGHT / 2));
		boss_obj.setPosition(pos);
		
		// Create the boss
		Boss bossShip = new Boss(boss_obj, ShipStats.EnemyStats_Boss().getHealthMax(), ShipStats.EnemyStats_Boss(), this.gamePane_ref);
		bossShip.addGameConsole(this);
		
		// Add to PhysX
		if(physx.getQuadrants() != null && physx.getQuadrants().size() > 0) {
			physx.getQuadrants().get(0).addEnemyShip(bossShip);
		}
		
		// Create a new boss room manager
		bossRoomManager = new BossRoomManager();
		bossRoomManager.setGameConsole_ref(this);
		bossRoomManager.setGamePane_ref(gamePane_ref);
		bossRoomManager.setBossShip(bossShip);
		
		player.getPhysObj().setPosition(bossShip.getPhysObj().getPosition().add(new Vector2(0, 750)));
		gamePane_ref.request_RedrawGridLines();
	}
	
	@Override
	public <Enemy extends EnemyShip> void programRequest_makeEnemy(Enemy enemy) {
		QuadrantID QUID = enemy.getPhysObj().getQUID();
		for (Quadrant quad : physx.getActiveQuadrants()) {
			if(quad.getQUID().getX() == QUID.getX() && quad.getQUID().getY() == QUID.getY()) {
				quad.addEnemyShip(enemy);
			}
		}

	}
	
	@Override
	public BulletManager programRequest_getBulletManager() {
		return this.bulletStore;
	}
	
	public void SetScore() {
		score = (enemiesKilled * scorePerEnemy) - (player.getDamageTaken() * scorePerDamage);
	}
	
	public int getScore() {
		return score;
	}
	
	public void HAX_SetSkillPoints(int x) {
		this.skill_points = x;
	}

	public void updateBossRoom() {
		if(bossRoomManager != null)
			bossRoomManager.Update();
	}

	public ArrayList<Ship> getShips() {
		return ships;
	}

	public void setShips(ArrayList<Ship> ships) {
		this.ships = ships;
	}

	/* Beginning of Pseudocode of Out-Of-Bounds detection;
	 * This method is to detect when the player goes out of the play area
	 * of the game's map/design. It gives a warning and a countdown when
	 * the player is out of the play area telling the player to come back
	 * into the play area or be met with a "game over". 
	 */
	public boolean OutOfBounds() {
		// Set default status of out-of-bounds to "false" (done in the class variable)
		// If the player's position in the game is less than 0 or greater than the map's dimensions (x and y) and out-of-bounds status is "false"
		float playerXCoord = player.getPhysObj().getPosition().getX();
		float playerYCoord = player.getPhysObj().getPosition().getY();
		if ((playerXCoord < 0 && !isOutOfBounds) || (playerXCoord > PhysXLibrary.getMapWidth() && !isOutOfBounds) || 
			(playerYCoord < 0 && !isOutOfBounds) || (playerYCoord > PhysXLibrary.getMapHeight() && !isOutOfBounds)) {
			// Set status of out-of-bounds to "true"
			isOutOfBounds = true;
			// Start the 10 second countdown/timer and show timer on HUD
			startTimeOutOfBounds = System.nanoTime();
		}
		// If the player gets back in bounds into the play area
		if ((playerXCoord > 0 && playerXCoord < PhysXLibrary.getMapWidth() && isOutOfBounds) && 
			(playerYCoord > 0 && playerYCoord < PhysXLibrary.getMapHeight() && isOutOfBounds)) {
			// Set status of out-of-bounds to "false"
			isOutOfBounds = false;
		}
		
		return isOutOfBounds;
	}
	
	// Figure out the elapsed time that has passed since the player has been out of bounds
	public double ElapsedTimeOutOfBounds() {
		if (isOutOfBounds) {
			long endTimeOutOfBounds = System.nanoTime();
			long elapsedTime = endTimeOutOfBounds - startTimeOutOfBounds;
			// Nanosecond to second conversion
			double secondsElapsed = (double)elapsedTime / 1000000000;
			
			return secondsElapsed;
		}
		
		return 0;
	}
	
	// Kill the player if the player has been out of bounds for longer than 10 seconds
	public void KillPlayerOutOfBounds() {
		if (ElapsedTimeOutOfBounds() > 10 && isOutOfBounds) {
			player.setCurrentHealth(0);
		}
	}
}


