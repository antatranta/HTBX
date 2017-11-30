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
	private FXManager fx;
	private ShipManagement shipManager;
	private GamePaneEvents gamePane_ref;
	private TeleportWaypoint bossRoomTrigger;
	
	//Score
	private int score=0;
	private int enemiesKilled=0;
	private final int scorePerEnemy=100;
	private final int scorePerDamage = 50;

	public GameConsole() {
		endDebugView();
		skill_points = 0;
		exp = 0;
		level = 0;
		calculateNeededExp();
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
		physx.addQuadrants(mapCreator.createBossRoom());
		
		//TODO: STUB
		if(physx.getQuadrants() != null && physx.getQuadrants().size() > 0) {
			physx.getQuadrants().get(0).addEnemyShip(/*BOSS*/ new Boss(new PhysXObject(), 100, ShipStats.EnemyStats_01()));
		}
	}
	
	public void SetScore() {
		score = (enemiesKilled * scorePerEnemy) - (player.getDamageTaken() * scorePerDamage);
		//System.out.println("Score: "+ score);
	}
	
	public int getScore() {
		return score;
	}
	
	public void HAX_SetSkillPoints(int x) {
		this.skill_points = x;
	}

}


