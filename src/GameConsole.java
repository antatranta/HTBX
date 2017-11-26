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
	//private PlayerShip player;
	private PlayerShip player;
	private MapCreator mapCreator;
	private PhysX physx; // The controller for all things
	private int skill_points;
	private Camera camera;
	private GameTimer gameTimer;
	private BulletManager bulletStore;
	private LaserManager laserStore;
	
	private ShipManagement shipManager;
	private GamePaneEvents gamePane_ref;
//	private GameTimer clock = new GameTimer();
	
	public GameConsole() {
		endDebugView();
		skill_points = 16;
		// set up the clock for the game
		gameTimer = new GameTimer();
		gameTimer.setupTimer(TIMER_INTERVAL, INITIAL_DELAY);
		
		// create an object to handle physX
		physx = new PhysX(PhysXLibrary.QUADRANT_HEIGHT, PhysXLibrary.QUADRANT_WIDTH, PhysXLibrary.MAP_WIDTH, PhysXLibrary.MAP_HEIGHT);
		
		// create a new bullet manager
		bulletStore = new BulletManager();
		
		laserStore = new LaserManager(this);
		
		// setup a new camera
		camera = new Camera();
		camera.setupCamera(1, 1);
		
		// create a new map
		mapCreator = new MapCreator();
		
		// populate the PhysX sim
		physx.addQuadrants(mapCreator.createMap());
		
		/*
		// get the player spawn point
		Quadrant playerSpawn = mapCreator.getPlayerSpawn();
		float pos_x = Math.abs(((playerSpawn.getQUID().getX()) * PhysXLibrary.QUADRANT_WIDTH) - (PhysXLibrary.QUADRANT_WIDTH / 2));
		float pos_y = Math.abs(((playerSpawn.getQUID().getY())* PhysXLibrary.QUADRANT_HEIGHT) - (PhysXLibrary.QUADRANT_HEIGHT / 2));
//		System.out.println("(playerSpawn.getQUID().getX: "+playerSpawn.getQUID().getX()+" PhysXLibrary.QUADRANT_WIDTH): "+PhysXLibrary.QUADRANT_WIDTH);
//		System.out.println("(playerSpawn.getQUID().getY: "+playerSpawn.getQUID().getY()+" PhysXLibrary.QUADRANT_HEIGHT): "+PhysXLibrary.QUADRANT_HEIGHT);
		Vector2 pos = new Vector2(pos_x, pos_y);
		System.out.println("player starting position = " + pos_x + ", " + pos_y);
		
		// create a new collider for the player
		
		
		// create a new physXobject for the player
		PhysXObject playerPhysXobj = new PhysXObject(playerSpawn.getQUID(), pos, playerCollider);
		*/
		// create the player
//		player = new PlayerShip(playerPhysXobj, 1, new ShipStats(1,1,1,1), "PlayerShip-Small.png");
		CircleCollider playerCollider = new CircleCollider(Vector2.Zero(), 15);
		player = mapCreator.placePlayer(mapCreator.getPlayerSpawn().getQUID());
		player.physObj.removeColliders();
		player.physObj.addCollider(playerCollider);
		player.setDxDy(Vector2.Zero());
		player.addSubscriber(bulletStore);
		gameTimer.addListener(player);
		
		shipManager = new ShipManagement(this);
		
		for(EnemyShip ship: getAllShips()) {
			ship.addSubscriber(shipManager);
		}
		
		System.out.println("Player Pos before GamePane: " + player.getPhysObj().getPosition().getX() + ", " + player.getPhysObj().getPosition().getY());
		System.out.println("Made a new game console");
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
	
	public ArrayList<Asteroid> getActiveAsteroids() {
		
		ArrayList<Asteroid> Asteroids = new ArrayList<Asteroid>();
		ArrayList<Quadrant> quads = physx.getActiveQuadrants();
		for (Quadrant quad : quads) {
			Asteroids.addAll(quad.getAsteroids());
		}
		
		return Asteroids;
	}
	
	public ArrayList<EnemyShip> getActiveShips() {
		ArrayList<EnemyShip> EnemyShips = new ArrayList<EnemyShip>();
		ArrayList<Quadrant> quads = physx.getActiveQuadrants();
		for (Quadrant quad : quads) {
			EnemyShips.addAll(quad.getShips());
		}
		
		for(EnemyShip ship : EnemyShips) {
			ship.addGameConsole(this);
			ship.addLaserManager(laserStore);
		}
		return EnemyShips;
	}
	
	public ArrayList<EnemyShip> getAllShips() {
		ArrayList<EnemyShip> EnemyShips = new ArrayList<EnemyShip>();
		ArrayList<Quadrant> quads = physx.getQuadrants();
		for (Quadrant quad : quads) {
			EnemyShips.addAll(quad.getShips());
		}
		return EnemyShips;
	}
	
	public PlayerShip getPlayer() {
		return this.player;
	}
	

	public void Shoot (int dmg, int spd, CollisionType type, float time, PhysXObject obj, String sprite, Vector2 movementVector) {
//		this.bulletStore.onShipDeath(obj.getPosition(), obj.getQUID());
//		this.bulletStore.emitBurst(movementVector, obj.getQUID(), 25);
		bulletStore.onShootEvent(dmg,spd,type,time,obj,sprite,movementVector);
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
	
	public BulletEmitter createBulletEmitter(int health, int rate, PhysXObject physObj, String sprite, CollisionData data) {
		BulletEmitter be = new BulletEmitter(health, rate, physObj, sprite, data);
		be.addSubscriber(getBulletManager());
		emitters.add(be);
		return be;
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
	public void onShipDeath(Vector2 pos) {
		// TODO Auto-generated method stub
		skill_points += 1;
		System.out.println("SP: " + skill_points);
		createBulletEmitter(10, 5, new PhysXObject(player.getPhysObj().getQUID(), pos), "RedCircle.png", CollisionData.Blank());
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
		//System.out.println("Stats are now:\n Speed: " + player.getStats().getSpeedSetting() + "\n Damage: " + player.getStats().getDamage() + "\n Max_HP: " + player.getStats().getHealthMax() + "\n Max_Shield: " + player.getStats().getShieldMax());
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
	
	public void emitBurst(ShipDeathData data) {
		this.bulletStore.emitBurst(data.getPos(), data.getQUID(), 25);
	}

	@Override
	public void bulletRequest_burst(Vector2 pos, QuadrantID QUID) {
//		gamePane_ref.eventRequest_addDeathEvent(new ShipDeathData(pos,QUID));
		// TODO Auto-generated method stub
//		this.bulletStore.emitBurst(pos, new QuadrantID(), 25);
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
	public PhysXObject physXRequest_getPlayer() {
		return player.getPhysObj();
	}


}


