import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.*;

import acm.graphics.GImage;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;

public class GameConsole extends GraphicsProgram{
	
	public static boolean IS_DEBUGGING;
	private int TIMER_INTERVAL = 16;
	private int INITIAL_DELAY = 0;
	
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	//private PlayerShip player;
	private PlayerShip player;
	private MapCreator mapCreator;
	private PhysX physx; // The controller for all things
	private int skillPoints;
	private Camera camera;
	private GameTimer gameTimer;
	private BulletManager bulletStore;
//	private GameTimer clock = new GameTimer();
	
	public GameConsole() {
		endDebugView();
		
		// set up the clock for the game
		gameTimer = new GameTimer();
		gameTimer.setupTimer(TIMER_INTERVAL, INITIAL_DELAY);
		
		// create an object to handle physX
		physx = new PhysX(PhysXLibrary.QUADRANT_HEIGHT, PhysXLibrary.QUADRANT_WIDTH, PhysXLibrary.MAP_WIDTH, PhysXLibrary.MAP_HEIGHT);
		
		// create a new map
		mapCreator = new MapCreator();
		
		// populate the PhysX sim
		physx.addQuadrants(mapCreator.createMap());
		
		// setup a new camera
		camera = new Camera();
		camera.setupCamera(1, 1);
		
		// create a new bullet manager
		bulletStore = new BulletManager();
		
		// get the player spawn point
		Quadrant playerSpawn = mapCreator.getPlayerSpawn();
		float pos_x = ((playerSpawn.getQUID().getX()) * PhysXLibrary.QUADRANT_WIDTH) - (PhysXLibrary.QUADRANT_WIDTH / 2);
		float pos_y = ((playerSpawn.getQUID().getY())* PhysXLibrary.QUADRANT_HEIGHT) - (PhysXLibrary.QUADRANT_HEIGHT / 2);
		Vector2 pos = new Vector2(pos_x, pos_y);
		System.out.println("player starting position = " + pos_x + ", " + pos_y);
		
		// create a new collider for the player
		CircleCollider playerCollider = new CircleCollider(Vector2.Zero(), 25);
		
		// create a new physXobject for the player
		PhysXObject playerPhysXobj = new PhysXObject(playerSpawn.getQUID(), pos, playerCollider);
		
		// create the player
//		player = new PlayerShip(playerPhysXobj, 1, new ShipStats(1,1,1,1), "PlayerShip-Small.png");
		player = mapCreator.placePlayer(mapCreator.getPlayerSpawn().getQUID());
		player.physObj.removeColliders();
		player.physObj.addCollider(playerCollider);
		player.setDxDy(Vector2.Zero());
		gameTimer.addListener(player);
		
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
		if(player.getPhysObj() != null) {
			physx.checkForCollisions(player.getPhysObj());
		}
		if(bulletStore.getBullets() != null) {
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
		
//		Asteroids.add(new Asteroid(player.getPhysObj()));
		
		return Asteroids;
	}
	
	public ArrayList<EnemyShip> getActiveShips() {
		ArrayList<EnemyShip> EnemyShips = new ArrayList<EnemyShip>();
		ArrayList<Quadrant> quads = physx.getActiveQuadrants();
		for (Quadrant quad : quads) {
			EnemyShips.addAll(quad.getShips());
		}
		return EnemyShips;
	}
	
	public PlayerShip getPlayer() {
		return this.player;
	}
	
	public GImage Shoot (int dmg, int spd, BulletType bullet, float time, PhysXObject obj, Vector2 movementVector) {
		return this.bulletStore.onShootEvent(dmg,spd,bullet,time,obj,movementVector);
	}
	
	public void moveBullets() {
		this.bulletStore.moveBullets();
	}
	
	public ArrayList<GImage> cullBullets() {
		return this.bulletStore.getDeadBullets();
	}
	
	public GameTimer getTimer() {
		return gameTimer;
	}
	
}


