import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import rotations.GameImage;

public class GamePane extends GraphicsPane implements ActionListener, KeyListener, GamePaneEvents {	

	private static final int AURORA_DRAW_DIST = 2000;
	private static final int PLAYER_FIRE_RATE = 6;
	
	private MainApplication program; // You will use program to get access to all of the GraphicsProgram calls
	private GameConsole console; // Not a new one; just uses the one from MainApplication
	private BulletManager bulletStore;
	private LaserManager laserStore;

	private PlayerShip player;
	private Vector2 last_mouse_loc;
	private GOval playerCollider;


	private boolean REQUEST_DEBUG_END = false;
	private GLabel CURRENT_QUID_LABEL;
	private GLabel CURRENT_PLAYER_POS_LABEL;
	private GLabel CURRENT_MOUSE_POS_LABEL;
	private GLabel CURRENT_ASTEROIDS_LABEL;
	private GRect DEBUGGING_BOX;

	private boolean DO_POINT_TEST = false;

	private ArrayList<GOval> POINT_TEST;

	private ArrayList<StaticRect> DEBUGGING_LINES;

	private ArrayList<StaticRect> DEBUGGING_ROWS;
	private ArrayList<StaticRect> DEBUGGING_COLS;


	private boolean DEBUGGING_MOVE_LOCK = false;
	private boolean CAN_MOVE = false;
	private boolean MOVEMENT_LOCK = false;

	private boolean CAN_ALIGN = true;
	private boolean ALIGNMENT_LOCK = false;
	private boolean DRAWING_LOCK = false;

	private boolean isShooting = false;
	private int shotCount = 0;

	// THINGS TO BE DRAWN
	private GameImage player_img;
	private GameImage aiming_head;
	private GameImage aiming_edge;
	private GameImage boss_aurora;
	
	private ArrayList <Integer> pressed_keys;
	private ArrayList <FXParticle> drawn_fx;
	private ArrayList <Asteroid> drawn_rocks;
	private ArrayList <EnemyShip> drawn_ships;
//	private ArrayList <EnemyShip> drawn_ship_gifs;
	private ArrayList <Bullet> drawn_bullets;

	private ArrayList <Asteroid> DEBUGGING_COLLIDERS_ASTEROIDS;
	private ArrayList <StaticGObject> DEBUGGING_COLLIDERS_OBJECTS_ref;
	private ArrayList <StaticGObject> DEBUGGING_COLLIDERS;

	private ArrayList <StaticGObject> BlinkerEyes;
	private ArrayList <StaticGObject> DrawnBlinkerEyes;

	private ArrayList <LaserSegment> drawnLaserSegments;

	private ArrayList <ShipDeathData> deathEvents;

	private ArrayList <EnemyShip> DEBUGGING_COLLIDERS_SHIPS;
	private boolean DEBUGGING_DRAW_BULLETS = false;
	private ArrayList <Bullet> DEBUGGING_COLLIDERS_BULLETS;

	private ArrayList <GLabel> DEBUGGING_QUID_LABELS;

	private DisplayableHUD HUD;

	private boolean TRACKING_SET = false;
	private Vector2 TRACKING_POSITION;
	private Vector2 lastTrackingPosition;
	private Vector2 tracking_offset;

	// THREAT LEVELS
	private float left_threat = 0;
	private float right_threat = 0;
	private float down_threat = 0;
	private float up_threat = 0;

	// Player STATUS HUD Stuffs
	ArrayList<StaticRect> GRID_ROWS = new ArrayList<StaticRect>();
	ArrayList<StaticRect> GRID_COLS = new ArrayList<StaticRect>();
	ArrayList<StaticRect> GRID_LINES = new ArrayList<StaticRect>();
	
	private GOval tele_flash;
	
	float GRID_LINE_WIDTH = 2f;
	float GRID_SQUARE_SIZE = 400f;
	
	public GamePane(MainApplication app) {
		this.program = app;
		init();
		TRACKING_POSITION = player.getPhysObj().getPosition();
		setOffset();
	}

	public ArrayList<Double> debuggingColliderSizes(PhysXObject object) {
		ArrayList<Double> sizes = new ArrayList<Double>();
		for(CircleCollider coll : object.getColliders()) {
			float size = coll.getRadius() * 2;

			Vector2 testPoint0 = Camera.backendToFrontend(object.getPosition());
			Vector2 testPoint1 = Camera.backendToFrontend(object.getPosition().add(new Vector2(0f, size)));
			sizes.add(PhysXLibrary.distance(testPoint0, testPoint1));
		}
		return sizes;
	}

	public void setupDebug() {

		ArrayList<Double> dist = debuggingColliderSizes(player.getPhysObj());
		playerCollider = new GOval(0,0,dist.get(0),dist.get(0));

		program.add(CURRENT_QUID_LABEL);
		program.add(DEBUGGING_BOX);
		program.add(CURRENT_ASTEROIDS_LABEL);
		program.add(CURRENT_PLAYER_POS_LABEL);
		program.add(CURRENT_MOUSE_POS_LABEL);

		program.add(playerCollider);

		DEBUGGING_BOX.setFillColor(Color.black);
		DEBUGGING_BOX.setFilled(true);

		CURRENT_QUID_LABEL.setColor(Color.white);
		CURRENT_QUID_LABEL.setFont("Arial");

		CURRENT_ASTEROIDS_LABEL.setColor(Color.white);
		CURRENT_ASTEROIDS_LABEL.setFont("Arial");

		CURRENT_PLAYER_POS_LABEL.setColor(Color.WHITE);
		CURRENT_PLAYER_POS_LABEL.setFont("Arial");

		CURRENT_MOUSE_POS_LABEL.setColor(Color.WHITE);
		CURRENT_MOUSE_POS_LABEL.setFont("Arial");

		playerCollider.setColor(Color.YELLOW);

		DEBUGGING_ROWS = new ArrayList<StaticRect>();
		DEBUGGING_COLS = new ArrayList<StaticRect>();

		DEBUGGING_LINES = new ArrayList<StaticRect>();

		DEBUGGING_QUID_LABELS = new ArrayList<GLabel>();

		DEBUGGING_COLLIDERS = new ArrayList<StaticGObject>();
		DEBUGGING_COLLIDERS_ASTEROIDS = new ArrayList<Asteroid>();
		DEBUGGING_COLLIDERS_SHIPS = new ArrayList<EnemyShip>();
		DEBUGGING_COLLIDERS_BULLETS = new ArrayList<Bullet>();
		DEBUGGING_COLLIDERS_OBJECTS_ref = new ArrayList<StaticGObject>();

		tracking_offset = new Vector2(0, 0);

		for(int i =0; i < PhysXLibrary.MAP_WIDTH; ++i) {
			DEBUGGING_ROWS.add(new StaticRect(new Vector2((PhysXLibrary.QUADRANT_WIDTH * i), 0), new Vector2(5, PhysXLibrary.getMapHeight())));
			program.add(DEBUGGING_ROWS.get(i).getRect());
			DEBUGGING_ROWS.get(i).getRect().setFillColor(Color.LIGHT_GRAY);
			DEBUGGING_ROWS.get(i).getRect().setColor(PaintToolbox.TRANSPARENT);
			DEBUGGING_ROWS.get(i).getRect().setFilled(true);
		}

		for(int i =0; i < PhysXLibrary.MAP_HEIGHT; ++i) {
			DEBUGGING_COLS.add(new StaticRect(new Vector2(0, (PhysXLibrary.QUADRANT_HEIGHT * i)), new Vector2(PhysXLibrary.getMapWidth(), 5)));
			program.add(DEBUGGING_COLS.get(i).getRect());
			DEBUGGING_COLS.get(i).getRect().setFillColor(Color.LIGHT_GRAY);
			DEBUGGING_COLS.get(i).getRect().setColor(PaintToolbox.TRANSPARENT);
			DEBUGGING_COLS.get(i).getRect().setFilled(true);
		}
	}

	public void drawGridLines() {
		GRID_ROWS = new ArrayList<StaticRect>();
		GRID_COLS = new ArrayList<StaticRect>();
		GRID_LINES = new ArrayList<StaticRect>();
		
		int i =0;
		while ((i * GRID_SQUARE_SIZE)< (PhysXLibrary.QUADRANT_WIDTH * PhysXLibrary.MAP_WIDTH)) {
			GRID_ROWS.add(new StaticRect(new Vector2((GRID_SQUARE_SIZE * i), 0), new Vector2(GRID_LINE_WIDTH, PhysXLibrary.getMapHeight())));
			program.add(GRID_ROWS.get(i).getRect());
			GRID_ROWS.get(i).getRect().setFillColor(PaintToolbox.VERYLIGHTGREY);
			GRID_ROWS.get(i).getRect().setColor(PaintToolbox.TRANSPARENT);
			GRID_ROWS.get(i).getRect().setFilled(true);
			i++;
		}
		int e =0;
		while ((e * GRID_SQUARE_SIZE)< (PhysXLibrary.QUADRANT_HEIGHT * PhysXLibrary.MAP_HEIGHT)) { 
//		for(int i =0; i < NUM_GRID_LINES; ++i) {
			GRID_COLS.add(new StaticRect(new Vector2(0, (GRID_SQUARE_SIZE * e)), new Vector2(PhysXLibrary.getMapWidth(), GRID_LINE_WIDTH)));
			program.add(GRID_COLS.get(e).getRect());
			GRID_COLS.get(e).getRect().setFillColor(PaintToolbox.VERYLIGHTGREY);
			GRID_COLS.get(e).getRect().setColor(PaintToolbox.TRANSPARENT);
			GRID_COLS.get(e).getRect().setFilled(true);
			e++;
		}
	}
	
	public void pointTest(Vector2 pos) {
		Vector2 player_pos = Camera.frontendToBackend(pos, new Vector2(25,25));
		POINT_TEST.add(new GOval(player_pos.getX(), player_pos.getY(), 25, 25));
		program.add(POINT_TEST.get(POINT_TEST.size() - 1));
		POINT_TEST.get(POINT_TEST.size() - 1).setFillColor(Color.blue);
		POINT_TEST.get(POINT_TEST.size() - 1).setFilled(true);

		Vector2 player_pos_to_front = Camera.backendToFrontend(player_pos, new Vector2(25,25));

		System.out.println("Does pos0 = pos2?: loss: " + (PhysXLibrary.distance(pos, player_pos_to_front)));

		POINT_TEST.add(new GOval(player_pos_to_front.getX(), player_pos_to_front.getY(), 25, 25));
		program.add(POINT_TEST.get(POINT_TEST.size() - 1));
		POINT_TEST.get(POINT_TEST.size() - 1).setFillColor(Color.red);
		POINT_TEST.get(POINT_TEST.size() - 1).setFilled(true);

		Vector2 player_pos_back = Camera.frontendToBackend(player_pos_to_front, new Vector2(25,25));

		System.out.println("Does pos1 = pos3? loss: " + (PhysXLibrary.distance(player_pos, player_pos_back)));

		POINT_TEST.add(new GOval(player_pos_back.getX(), player_pos_back.getY(), 25, 25));
		program.add(POINT_TEST.get(POINT_TEST.size() - 1));
		POINT_TEST.get(POINT_TEST.size() - 1).setFillColor(Color.green);
		POINT_TEST.get(POINT_TEST.size() - 1).setFilled(true);

	}

	public void drawPhysXObjects(ArrayList<StaticGObject> statics) {
		for(int i =0; i < statics.size(); ++i) {
			if (!DEBUGGING_COLLIDERS.contains(statics.get(i))) {
				DEBUGGING_COLLIDERS.add(statics.get(i));

				DEBUGGING_QUID_LABELS.add(new GLabel("QUID"));
				program.add(DEBUGGING_QUID_LABELS.get(DEBUGGING_QUID_LABELS.size() - 1));
				DEBUGGING_QUID_LABELS.get(DEBUGGING_QUID_LABELS.size() - 1).setColor(Color.pink);
				DEBUGGING_QUID_LABELS.get(DEBUGGING_QUID_LABELS.size() - 1).setLabel(statics.get(i).getPhysObj().getQUID().toString());

				statics.get(i).setup(debuggingColliderSizes(statics.get(i).getPhysObj()));


				for(GOval col : statics.get(i).getObjects()) {
					program.add(col);
					col.setColor(Color.MAGENTA);
				}
			}

			// Set its location according to the offset
			if (DEBUGGING_COLLIDERS.contains(statics.get(i))) {
				for(int f = 0; f < statics.get(i).getObjects().length; ++f) {
					float diameter = statics.get(i).getPhysObj().getColliders()[f].getRadius() * 2;
					Vector2 size = new Vector2(diameter, diameter);
					Vector2 frontEndPos = Camera.backendToFrontend(statics.get(i).getPhysObj().getColliders()[f].getCenter().add(statics.get(i).getPhysObj().getPosition()), size);
					statics.get(i).setLocationRespectSize(f, frontEndPos);
					DEBUGGING_QUID_LABELS.get(i).setLocation(frontEndPos.getX(), frontEndPos.getY());
				}
			}
		}

		ArrayList<StaticGObject> new_statics = new ArrayList<StaticGObject>();
		new_statics.addAll(statics);
		// Remove asteroids
		for (StaticGObject coll : new_statics) {
			if (!new_statics.contains(coll)) {
				DEBUGGING_COLLIDERS.remove(coll);
				for(GOval oval :  coll.getObjects()) {
					program.remove(oval);
				}
			}
		}
		DRAWING_LOCK = false;
	}

	public void init() {
		CAN_MOVE = false;

		last_mouse_loc = new Vector2(0,0);
		pressed_keys = new ArrayList <Integer>();
		drawn_fx = new ArrayList <FXParticle>();
		drawn_rocks = new ArrayList <Asteroid>();
		drawn_ships = new ArrayList <EnemyShip>();
//		drawn_ship_gifs = new ArrayList <EnemyShip>();
		drawn_bullets = new ArrayList <Bullet>();
		BlinkerEyes = new ArrayList <StaticGObject>();
		DrawnBlinkerEyes = new ArrayList<StaticGObject>();
		drawnLaserSegments = new ArrayList<LaserSegment>();

		deathEvents = new ArrayList <ShipDeathData>();

		DEBUGGING_COLLIDERS = new ArrayList<StaticGObject>();
		DEBUGGING_COLLIDERS_ASTEROIDS = new ArrayList<Asteroid>();
		DEBUGGING_COLLIDERS_OBJECTS_ref = new ArrayList<StaticGObject>();

		CURRENT_QUID_LABEL = new GLabel("Current QUID", 15, 25);
		CURRENT_ASTEROIDS_LABEL = new GLabel("Current ASTEROIDS", 15, 50);
		CURRENT_PLAYER_POS_LABEL = new GLabel("Current P Position", 15, 75);
		CURRENT_MOUSE_POS_LABEL = new GLabel("Current M Position", 15, 100);
		DEBUGGING_BOX = new GRect(10, 10, 300, 100);
		playerCollider = new GOval(0,0,0,0);

		console = program.getGameConsole();
		player = console.getPlayer();
		bulletStore = console.getBulletManager();
		laserStore = console.getLaserManager();
		console.getFXManager();

		aiming_edge = new GameImage("Player Rectile.png", 0, 0);
		aiming_head = new GameImage("Player Cursor.png", 0, 0);
		player_img = player.getSprite();

		centerPlayer();
		player.getPhysObj().setQUID(console.physx().assignQuadrant(player.getPhysObj().getPosition()));
		console.physx().setActiveQuadrant(console.physx().assignQuadrant(player.getPhysObj().getPosition()));

		HUD = new DisplayableHUD(program, player);
		boss_aurora = new GameImage("Boss_Aurora_Gray_Small.png", 1000, 1000);
		CAN_MOVE = true;
		
		lastTrackingPosition = player.getPhysObj().getPosition();
		
		tele_flash = new GOval(0,0,0,0);
		tele_flash.setFilled(true);
		tele_flash.setFillColor(PaintToolbox.WHITE);
		tele_flash.setColor(PaintToolbox.WHITE);
		
		drawGridLines();
	}

	public void centerPlayer() {
		Vector2 frontPos = Camera.backendToFrontend(player.getPhysObj().getPosition());
		player_img.setLocationRespectSize(frontPos.getX(), frontPos.getY());
	}

	public void setOffset() {
		Camera.setOffset(Vector2.Zero().minus(TRACKING_POSITION));
	}

	@Override
	public void showContents() {
		program.add(player_img);
		program.add(aiming_edge);
		program.add(aiming_head);
		HUD.showContents();
		program.add(boss_aurora);
		program.add(tele_flash);
	}

	@Override
	public void hideContents() {
		program.remove(player_img);
		program.remove(aiming_edge);
		program.remove(aiming_head);
		HUD.hideContents();
		program.remove(boss_aurora);
		program.remove(tele_flash);
	}

	private void playerShoot() {
		BulletFireEventData bfe = new BulletFireEventData(player.getStats().getDamage() + player.getBonusStats().getDamage(), 20, BulletType.STRAIGHT, CollisionType.player_bullet, 1, new PhysXObject(player.getPhysObj().getQUID(), player.getPhysObj().getPosition(), new CircleCollider(5)), "Player Bullet.png", Camera.frontendToBackend(last_mouse_loc), FXManager.colorParticle(PaintToolbox.BLUE));
		player.shoot(bfe);
		AudioPlayer myAudio = AudioPlayer.getInstance();
		myAudio.playSound("sounds", "Bow_Fire_Arrow.wav");
	}

	// Every tick of the global game clock calls all visual drawing necessary
	@Override
	public void actionPerformed(ActionEvent e) {

		// Audio
		AudioPlayer myAudio = AudioPlayer.getInstance();
		myAudio.updatePlayer();
		
		// Game over screen
		if(player.getCurrentHealth() <= 0) {
			console.SetScore();
			program.switchToGameOver();
		}
		
		// Reset death events
		if(deathEvents.size() > 1) {
			deathEvents = new ArrayList<ShipDeathData>();
		}
		
		trackingUpdate();
		
		// Align the rectile
		if (CAN_ALIGN && !ALIGNMENT_LOCK) {
			ALIGNMENT_LOCK = true;
			alignReticle(last_mouse_loc);
		}
		
		// Handle player shooting
		if(isShooting) {
			shotCount++;
			if(shotCount >= PLAYER_FIRE_RATE) {
				shotCount = 0;
				playerShoot();
			}
		}
		
		// Test collisions
		console.testCollisions(player);
		
		// Move projectiles

		laserStore.updateLasers();
		
		// Move particles
		console.getFXManager().moveParticles();

		
		// Remove Dead bullets
		for(GameImage bullet : console.cullBullets()) {
			program.remove(bullet);
		}
		
		// Update the debug screen
		debugUpdate();		
		
		// Update the HUD
		HUD.updateHUD();
		HUD.updateStats();
		
		drawStaticRect(GRID_ROWS, GRID_LINES);
		drawStaticRect(GRID_COLS, GRID_LINES);
		
		// Re-Layer objects
		layerSprites();
		
		// Reset threat levels
		left_threat = 0;
		right_threat = 0;
		up_threat = 0;
		down_threat = 0;
		console.moveBullets();

		// Move the player ship
		if(CAN_MOVE) {
			movementLoop();
		}
		
		// Move the enemy ships
		moveEnemyShips();
		
		// Test the boss room trigger
		drawSprites();
		console.getBossRoomTrigger().Update(player.getPhysObj().getPosition(), tele_flash);

	}
	
	public void trackingUpdate() {
		// Camera Tracking
		if(!TRACKING_SET) {
			lastTrackingPosition = PhysXLibrary.lerpBetweenPoints(player.getPhysObj().getPosition(),lastTrackingPosition, .1f);
			TRACKING_POSITION = lastTrackingPosition;
		}
		
		// Offset the camera
		setOffset();
	}
	

	public void debugUpdate() {
		if(console.IS_DEBUGGING) {
			if(REQUEST_DEBUG_END) {
				console.endDebugView();
				program.remove(CURRENT_QUID_LABEL);
				program.remove(DEBUGGING_BOX);

				for(StaticRect rect: DEBUGGING_ROWS) {
					program.remove(rect.getRect());
				}
				for(StaticRect rect: DEBUGGING_COLS) {
					program.remove(rect.getRect());
				}
				for(GLabel label: DEBUGGING_QUID_LABELS) {
					program.remove(label);
				}

				for(StaticGObject obj: DEBUGGING_COLLIDERS) {
					for (GOval oval : obj.getObjects()) {
						program.remove(oval);
					}
				}

				program.remove(playerCollider);

				drawPhysXObjects(new ArrayList<StaticGObject>());
				REQUEST_DEBUG_END = false;

			} else {

				CURRENT_QUID_LABEL.setLabel("Current QUID: " + player.getPhysObj().getQUID().toString());
				CURRENT_PLAYER_POS_LABEL.setLabel("Current Player V2: " + player.getPhysObj().getPosition().toString());
				CURRENT_MOUSE_POS_LABEL.setLabel("Current Mouse V2: " + Camera.frontendToBackend(last_mouse_loc).toString());

				float dia = player.getPhysObj().getColliders()[0].getRadius() * 2;
				Vector2 size = new Vector2(dia, dia);
				Vector2 newFEPOS = Camera.backendToFrontend(player.getPhysObj().getPosition(), size);
				playerCollider.setLocation(newFEPOS.getX(), newFEPOS.getY());

				drawStaticRect(DEBUGGING_ROWS, DEBUGGING_LINES);
				drawStaticRect(DEBUGGING_COLS, DEBUGGING_LINES);
				

				if (!DRAWING_LOCK) {
					try {
						DRAWING_LOCK = true;
						int MAXDRAW = 50;
						int draw = 0;
						for(Asteroid aster : drawn_rocks) {
							if(draw >= MAXDRAW) {
								break;
							}
							if(!DEBUGGING_COLLIDERS_ASTEROIDS.contains(aster)) {
								DEBUGGING_COLLIDERS_ASTEROIDS.add(aster);
								Vector2 imageSize = new Vector2((float)aster.getSprite().getWidth(), (float)aster.getSprite().getHeight());
								DEBUGGING_COLLIDERS_OBJECTS_ref.add(new StaticGObject(aster.getPhysObj(), imageSize));
								draw ++;
							}
						}

						for(EnemyShip ship : drawn_ships) {
							if(draw >= MAXDRAW) {
								break;
							}
							if(!DEBUGGING_COLLIDERS_SHIPS.contains(ship)) {
								DEBUGGING_COLLIDERS_SHIPS.add(ship);
								Vector2 imageSize = new Vector2((float)ship.getSprite().getWidth(), (float)ship.getSprite().getHeight());
								DEBUGGING_COLLIDERS_OBJECTS_ref.add(new StaticGObject(ship.getPhysObj(), imageSize));
								draw ++;
							}
						}
						if(DEBUGGING_DRAW_BULLETS) {
							for(Bullet bullet : drawn_bullets) {
								if(draw >= MAXDRAW) {
									break;
								}
								if(!DEBUGGING_COLLIDERS_BULLETS.contains(bullet)) {
									DEBUGGING_COLLIDERS_BULLETS.add(bullet);
									Vector2 imageSize = new Vector2((float)bullet.getSprite().getWidth(), (float)bullet.getSprite().getHeight());
									DEBUGGING_COLLIDERS_OBJECTS_ref.add(new StaticGObject(bullet.getPhysObj(), imageSize));
									draw ++;
								}
							}
						}

						drawPhysXObjects(DEBUGGING_COLLIDERS_OBJECTS_ref);
					} catch (java.lang.NullPointerException e1) {
						System.out.println("Dropped frame " + e1.getMessage());
						DRAWING_LOCK = false;
					}
				}
			}
		}
	}

	public void drawSprites() {
		drawFX(console.getFXManager().getParticles(), drawn_fx);
		drawSprites(console.getActiveAsteroids(), drawn_rocks);
		drawSprites(console.getActiveShips(), drawn_ships);
//		drawGifs(console.getActiveShips(), drawn_ship_gifs, ROCK_LAYER);
		drawBullets(bulletStore.getBullets(), drawn_bullets);
		drawSprites(laserStore.getSegments(), drawnLaserSegments);
		drawBlinkerEyes(BlinkerEyes);
		
		player_img.setDegrees(-player.getAngle() + 90);
		
		if (PhysXLibrary.distance(player.getPhysObj().getPosition(), HUD.getBossTelePos()) < AURORA_DRAW_DIST) {
			Vector2 lc = Camera.backendToFrontend(HUD.getBossTelePos());
			boss_aurora.setLocation(lc.getX() - (boss_aurora.getWidth() / 2), lc.getY() - (boss_aurora.getHeight() / 2));
			//boss_aurora.rotate(-1);
		}
		else {
			boss_aurora.setLocation(1000, 1000);
		}
	}

	public void moveEnemyShips() {
		ArrayList<EnemyShip> ships = console.getActiveShips();
		for(EnemyShip ship: ships) {
			ship.addSubscriber(bulletStore);
			ship.AIUpdate(player.physObj.getPosition());
		}
	}

	public void updateBulletEmitters () {
		ArrayList<BulletEmitter> bulletEmitters = console.getActiveBulletEmitters();
		for(BulletEmitter bulletEmitter: bulletEmitters) {
			bulletEmitter.addSubscriber(bulletStore);
			bulletEmitter.AIUpdate(player.physObj.getPosition());
		}
	}

	private void layerSprites() {
		// Then dynamic parts of the HUD
		HUD.layerSprites();
		tele_flash.sendToBack();
		aiming_edge.sendToBack();
		aiming_head.sendToBack();

		// FX Layer
		for (int i = 0; i < drawn_fx.size(); i++) {
			drawn_fx.get(i).getSprite().sendToBack();
		}

		// Player ship
		player_img.sendToBack();

		// Big rocks have priority next
		for (int i = 0; i < drawn_rocks.size(); i++) {
			drawn_rocks.get(i).getSprite().sendToBack();
		}

		// Enemy ships have priority next
		for (int i = 0; i < drawn_ships.size(); i++) {
			drawn_ships.get(i).getSprite().sendToBack();
		}

		for(int i =0; i < DrawnBlinkerEyes.size(); i++) {
			for(GOval oval : DrawnBlinkerEyes.get(i).getObjects()) {
				oval.sendToBack();
			}
		}

		// Finally, bullets
		for (int i = 0; i < drawn_bullets.size(); i++) {
			drawn_bullets.get(i).getSprite().sendToBack();
		}	
		
		// Grid
		for(int i =0; i < GRID_LINES.size(); i++) {
			GRID_LINES.get(i).getRect().sendToBack();
		}
		
		boss_aurora.sendToBack();
		HUD.getBackgroundOverlay().sendToBack();
		
		CURRENT_QUID_LABEL.sendToFront();

	}

	private void movementLoop() {
		if(MOVEMENT_LOCK)
			return;

		MOVEMENT_LOCK = true;
		if (console.getBossRoomTrigger().getPhase() <= 0) {
		int final_turn = 0;
		int final_forward = 0;

		// Set rotation
		if (pressed_keys.contains(KeyEvent.VK_A) && !pressed_keys.contains(KeyEvent.VK_D)) {
			final_turn = -1;
		}
		else if (!pressed_keys.contains(KeyEvent.VK_A) && pressed_keys.contains(KeyEvent.VK_D)) {
			final_turn = 1;
		}

		if (pressed_keys.contains(KeyEvent.VK_W) && !pressed_keys.contains(KeyEvent.VK_S)) {
			final_forward = 1;
		}
		else if (!pressed_keys.contains(KeyEvent.VK_W) && pressed_keys.contains(KeyEvent.VK_S)) {
			final_forward = -1;
		}

		double angle = -Math.toRadians(player.getAngle());

		float speed = (float) player.getStats().getSpeedValue() * final_forward;

		float cos = (float) Math.cos(angle) * speed;
		float sin = (float) Math.sin(angle) * speed;

		player.adjustAngle((int)player.getStats().getTurningSpeed() * -final_turn);


		player.moveVector2(new Vector2(cos, sin));

		player.getPhysObj().setQUID(console.physx().assignQuadrant(player.getPhysObj().getPosition()));
		console.physx().setActiveQuadrant(console.physx().assignQuadrant(player.getPhysObj().getPosition()));
		}
		
		Vector2 newFEPOS = Camera.backendToFrontend(player.getPhysObj().getPosition());

		if(!DEBUGGING_MOVE_LOCK) {
			player_img.setLocationRespectSize(newFEPOS.getX(), newFEPOS.getY());
		}
		
		MOVEMENT_LOCK = false;
	}

	
	private void drawStaticRect(ArrayList<StaticRect> lines, ArrayList<StaticRect> storage) {
		
		if(lines == null) {
			return;
		}

		for (int i = 0; i < lines.size(); i++) {
			// Get the offset
			StaticRect rect = lines.get(i);
			
			if(rect == null) {
				continue;
			}
			
			// Make a proper vector2 location according to the camera zoom scale
			Vector2 final_off = Camera.backendToFrontend(rect.getPhysObj().getPosition());

			// Are we already drawing that rect?
			if (!storage.contains(rect)) {
				storage.add(rect);
			}

			// Set its location according to the offset
			if (storage.contains(rect)) {
				rect.getRect().setLocation(final_off.getX(), final_off.getY());
			}
		}
	}

	private void drawBlinkerEyes(ArrayList<StaticGObject> objects) {
		for (int i = 0; i < objects.size(); i++) {
			// Get the offset
			StaticGObject rect = objects.get(i);

			// Are we already drawing that rect?
			if (!DrawnBlinkerEyes.contains(rect)) {
				DrawnBlinkerEyes.add(rect);
				for(GOval oval: rect.getObjects()) {
					program.add(oval);
				}	
			}
			// Set its location according to the offset
			if (DrawnBlinkerEyes.contains(rect)) {
				//				
				GOval[] ovals = rect.getObjects();
				for(int e =0; e < ovals.length; e++) {
					// Make a proper vector2 location according to the camera zoom scale
					Vector2 final_off = Camera.backendToFrontend(rect.getPhysObj().getPosition(), new Vector2((float)ovals[e].getWidth(), (float)ovals[e].getHeight()));
					rect.setLocationRespectSize(e, final_off);
				}
			}

		}
	}

	private void removeObjects(ArrayList<GameImage> objects) {
		for(GameImage sprite : objects) {
			program.remove(sprite);
		}
	}

	private void addObjects(ArrayList<GameImage> objects) {
		for(GameImage sprite : objects) {
			program.add(sprite);
		}
	}
	private void drawBullets(ArrayList<Bullet> objects, ArrayList<Bullet> storage) {
		for (int i = 0; i < objects.size(); i++) {
			Bullet bullet = objects.get(i);
			Vector2 frontEndPos = Camera.backendToFrontend(bullet.getPhysObj().getPosition());

			// Are we already drawing that rock?
			if (!storage.contains(bullet)) {
				storage.add(bullet);
				program.add(bullet.getSprite());
			}

			// Set its location according to the offset
			if (storage.contains(bullet)) {
				bullet.getSprite().setLocationRespectSize(frontEndPos.getX(), frontEndPos.getY());
			}
		}
	}

	private <Item extends Entity> void drawSprites(ArrayList<Item> objects, ArrayList<Item> storage) {
		if(console.IS_DEBUGGING) {
			CURRENT_ASTEROIDS_LABEL.setLabel("Current ASTER: " + objects.size());
		}
		for (int i = 0; i < objects.size(); i++) {

			// Get the offset
			Item obj = objects.get(i);

			if(obj.getSpriteName().contains("gif")) {
				drawGif(obj, storage);
				continue;
			}

			Vector2 frontEndPos = Camera.backendToFrontend(obj.getPhysObj().getPosition());

			// Are we already drawing that rock?
			if (!storage.contains(obj)) {
				storage.add(obj);
				program.add(obj.getSprite());
			}

			// Set its location according to the offset
			if (storage.contains(obj)) {
				obj.getSprite().setLocationRespectSize(frontEndPos.getX(), frontEndPos.getY());
			}

		}

		ArrayList<Item> new_draw = new ArrayList<Item>();
		new_draw.addAll(storage);
		// Remove asteroids
		for (Item obj : new_draw) {
			if (!objects.contains(obj)) {
				storage.remove(obj);
				program.remove(obj.getSprite());
			}
		}
	}

	public void drawFX(ArrayList<FXParticle> objects, ArrayList<FXParticle> storage) {
		for (int i = 0; i < objects.size(); i++) {

			// Get the offset
			FXParticle obj = objects.get(i);
			Vector2 frontEndPos = Camera.backendToFrontend(obj.getPosition());

			// Are we already drawing that rock?
			if (!storage.contains(obj)) {
				storage.add(obj);
				program.add(obj.getSprite());
			}

			// Set its location according to the offset
			if (storage.contains(obj)) {
				obj.getSprite().setLocation(frontEndPos.getX() - (obj.getSprite().getWidth() / 2 ), frontEndPos.getY() - (obj.getSprite().getHeight()/ 2));
			}

		}

		ArrayList<FXParticle> new_draw = new ArrayList<FXParticle>();
		new_draw.addAll(storage);
		for (FXParticle obj : new_draw) {
			if (!objects.contains(obj)) {
				storage.remove(obj);
				program.remove(obj.getSprite());
			}
		}
	}
	
	public <Item extends Entity> void drawGif (Item obj, ArrayList<Item> storage) {
		Vector2 frontEndPos = Camera.backendToFrontend(obj.getPhysObj().getPosition());

		// Are we already drawing that rock?
		if (!storage.contains(obj)) {
			storage.add(obj);
			program.add(obj.getGif());
		}

		// Set its location according to the offset
		if (storage.contains(obj)) {
			obj.getGif().setLocation(frontEndPos.getX() - (obj.getGif().getWidth() / 2), frontEndPos.getY() - (obj.getGif().getHeight() / 2));
		}
	}


	public void alignReticle(Vector2 coord) {
		Vector2 visual_root = new Vector2((float)(player_img.getX() + (player_img.getWidth()/2)), (float)(player_img.getY() + (player_img.getHeight()/2)));

		// Align them properly
		double off_x = (coord.getX() - visual_root.getX());
		double off_y = (coord.getY() - visual_root.getY());
		double theta_rad = Math.atan2(off_y, off_x);
		aiming_edge.setDegrees(Math.toDegrees(theta_rad));
		aiming_edge.setLocation(visual_root.getX() - (aiming_edge.getWidth() / 2), visual_root.getY() - (aiming_edge.getHeight() / 2));
		aiming_head.setDegrees(Math.toDegrees(theta_rad));
		aiming_head.setLocation(coord.getX() - (aiming_head.getWidth() / 2), coord.getY() - (aiming_head.getHeight() / 2));
		ALIGNMENT_LOCK = false;
	}

	private boolean containsKey(int key) {
		if (!pressed_keys.contains(key)) {
			return false;
		}
		return true;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Vector2 mousePos = new Vector2(e.getX(), e.getY());
		if (e.getButton() == MouseEvent.BUTTON3 && console.getBossRoomTrigger().getPhase() <= 0)
		{
			Vector2 newPos = Camera.frontendToBackend(mousePos);

			if(console.physx().isPositionSafe(newPos, 100)) {
				player.getPhysObj().setPosition(newPos);
				Vector2 newFEPOS = Camera.backendToFrontend(player.getPhysObj().getPosition());
				player_img.setLocationRespectSize(newFEPOS.getX(), newFEPOS.getY());
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON1 && console.getBossRoomTrigger().getPhase() <= 0) {
			if (console.IS_DEBUGGING) {
				if(!DO_POINT_TEST) {
					isShooting = true;
				}
				else {
					pointTest(new Vector2(e.getX(), e.getY()));
				}
			} else {
				isShooting = true;
			}
		}

		GObject item = program.getElementAt(e.getX(), e.getY());
		if (item instanceof LevelUpButton && player.getCurrentHealth() > 0 && console.getSP() > 0) {
			program.getGameConsole().levelUpSkill(((LevelUpButton) item).getStatUpType());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		isShooting = false;
		shotCount= 0;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		last_mouse_loc.setXY(e.getX(), e.getY()); 
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		last_mouse_loc.setXY(e.getX(), e.getY()); 
	}

	// Key Presses work; the println statements were removed to prevent clutter in the console as I test
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_D || key == KeyEvent.VK_S || key == KeyEvent.VK_W) {
			if (!containsKey(key)) {
				pressed_keys.add(key);
			}
		}

		if(key == KeyEvent.VK_L && !console.IS_DEBUGGING) {
			console.startDebugView();
			setupDebug();
		}

		if(key == KeyEvent.VK_K && console.IS_DEBUGGING) {
			REQUEST_DEBUG_END = true;
		}

		if(console.IS_DEBUGGING) {
			if(key == KeyEvent.VK_P) {
				DO_POINT_TEST = !DO_POINT_TEST;
				System.out.println("POINT_TEST --- " + DO_POINT_TEST);

				if(!DO_POINT_TEST) {
					for(GOval point : POINT_TEST)
						program.remove(point);
				} else {
					POINT_TEST = new ArrayList<GOval>();
				}
			}
			if(key == KeyEvent.VK_M) {
				System.out.println("SNAPSHOT --- ");
				System.out.println("Asteroids to draw: " + console.getActiveAsteroids().size());
				System.out.println("Player QUID: (" + player.getPhysObj().getQUID().getX() +", " + player.getPhysObj().getQUID().getY() + ", " + player.getPhysObj().getQUID().Order() + ")");
			}
			if(key == KeyEvent.VK_V) {
				if(!TRACKING_SET) {
					System.out.println("TRACKING --- ON");
					TRACKING_SET = true;
					TRACKING_POSITION = player.getPhysObj().getPosition();
				} else {
					System.out.println("TRACKING --- OFF");
					TRACKING_SET = false;
				}
			}

			if(key == KeyEvent.VK_T) {
				if(!DEBUGGING_MOVE_LOCK) {
					System.out.println("MOVING --- OFF");
					DEBUGGING_MOVE_LOCK = true;
				} else {
					System.out.println("MOVING --- ON");
					DEBUGGING_MOVE_LOCK = false;
				}
			}

			if(key == KeyEvent.VK_N) {
				if(DEBUGGING_DRAW_BULLETS) {
					System.out.println("DRAW BULLETS --- OFF");
					DEBUGGING_DRAW_BULLETS = false;
				} else {
					System.out.println("DRAW BULLETS --- ON");
					DEBUGGING_DRAW_BULLETS = true;
				}
			}
		}

		if (key == KeyEvent.VK_ESCAPE) {
			program.switchToPause();
		}

		if (console.getSP() > 0 && player.getCurrentHealth() > 0) {
			switch(key) {
			case KeyEvent.VK_1:
				console.levelUpSkill(LevelUpEnum.speed);
				break;
			case KeyEvent.VK_2:
				console.levelUpSkill(LevelUpEnum.damage);
				break;
			case KeyEvent.VK_3:
				console.levelUpSkill(LevelUpEnum.health);
				break;
			case KeyEvent.VK_4:
				console.levelUpSkill(LevelUpEnum.shield);
				break;
			default:
				break;
			}
		}

		//GOD Mode = \ Pleb mode = P *ONLY FOR TESTING PURPOSES*
		if(key == KeyEvent.VK_BACK_SLASH) {
			player.setCurrentHealth(9999999);
			console.HAX_SetSkillPoints(16);
		}
		else if(key == KeyEvent.VK_P) {
			player.setCurrentHealth(1);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_D || key == KeyEvent.VK_S || key == KeyEvent.VK_W) {
			if (containsKey(key)) {
				for (int i = 0; i < pressed_keys.size(); i++) {
					if (pressed_keys.get(i) == key) {
						pressed_keys.remove(i);
					}
				}
			}
		}
	}

	// ========================================= //
	// ============ EVENT REQUESTS ============= //
	// ========================================= //

	@Override
	public void eventRequest_drawGOval(GOval oval) {
		program.add(oval);
	}

	@Override
	public void eventRequest_addStaticGObject(StaticGObject obj) {
		this.BlinkerEyes.add(obj);
	}

	@Override
	public void eventRequest_addDeathEvent(ShipDeathData data) {
		this.deathEvents.add(data);
	}

	@Override
	public void eventRequest_removeShip(EnemyShip obj) {
		// TODO Auto-generated method stub
		drawn_ships.remove(obj);
		program.remove(obj.getSprite());
	}

	@Override
	public void eventRequest_addObjects(ArrayList<GameImage> objects) {
		// TODO Auto-generated method stub
		this.addObjects(objects);
	}

	@Override
	public void eventRequest_removeObjects(ArrayList<GameImage> objects) {
		// TODO Auto-generated method stub
		this.removeObjects(objects);
	}

	@Override
	public void eventRequest_addLaserLine(LaserLine line) {
		//		lasers.add(line);
	}

	@Override
	public void eventRequest_removeObject(GOval oval) {
		program.remove(oval);
	}

	@Override
	public void addThreat(Vector2 pos) {
		// TODO Auto-generated method stub
		Direction dir = PhysXLibrary.directionOffPoint(pos, player.getPhysObj().getPosition(), 200);

		// Lj's Method
		HUD.updateThreats(dir);
	}
}