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
import acm.graphics.GPoint;
import acm.graphics.GRect;
import rotations.GameImage;

public class GamePane extends GraphicsPane implements ActionListener, KeyListener {
	// LAYER DATA: 0 Is the front, above 0 puts things behind
	private static final int DEBUG_LAYER 		= 0;
	private static final int CURSOR_LAYER 		= 1;
	private static final int PLAYER_LAYER 		= 3;
	private static final int PLAYER_RECTILE 		= 4;
	private static final int PLAYER_RECTILE_2 	= 5;
	// TODO: 
	//private static final int BOSS_LAYER = 4;
	// TODO: 
	//private static final int ENEMY_LAYER = 5;
	private static final int ROCK_LAYER = 6;
//	private static final int BG_LAYER1 = 7;
//	private static final int BG_LAYER2 = 8;
//	private static final int BG_LAYER3 = 9;
	
	// =============================================================================
	
	private static final int CURSOR_DIST = 75;
	private static final int CURSOR_SIZE = 10;
	private static final int TURN_POWER = 6;
	
	private MainApplication program; // You will use program to get access to all of the GraphicsProgram calls
	private GameConsole console; // Not a new one; just uses the one from MainApplication

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
	private float MOVEMENT_CONSTANT = .0000001f;
	
	private boolean CAN_ALIGN = true;
	private boolean ALIGNMENT_LOCK = false;
	private boolean DRAWING_LOCK = false;
	
	private boolean isShooting = false;
	private int shotCount = 0;
	
	// THINGS TO BE DRAWN
	private GameImage player_img;
	private GameImage aiming_head;
	private GameImage aiming_edge;
	private ArrayList <GameImage> cursor_dots;
	private ArrayList <Integer> pressed_keys;
	private ArrayList <Asteroid> drawn_rocks;
	private ArrayList <EnemyShip> drawn_ships;
	
	private ArrayList <Asteroid> DEBUGGING_COLLIDERS_OBJECTS;
	private ArrayList <StaticGObject> DEBUGGING_COLLIDERS_OBJECTS_ref;
	private ArrayList <StaticGObject> DEBUGGING_COLLIDERS;
	
	private ArrayList <GLabel> DEBUGGING_QUID_LABELS;
	private float xAxis = 0;
	private float yAxis = 0;
	private int track_amount = 0;
	
	
	private boolean TRACKING_SET = false;
	private Vector2 TRACKING_POSITION;
	private Vector2 tracking_offset;

//	private static final String CURSOR_LINE_SPRITE = "Aiming_Line.png";
//	private static final String PLAYER_SPRITE = "PlayerShip-Small.png";
	
	// Player STATUS HUD Stuffs
	private GRect stats_back;
	private GRect status_back;
	private GRect status_bar_hp;
	private GRect status_bar_hp_back;
	//private GLabel hp_label;
	private GRect status_bar_shield;
	private GRect status_bar_shield_back;
	//private GLabel shield_label;
	private GRect compass_back;
	private GRect inner_compass_back;
	private GameImage compass_sprite;
	private Vector2 status_origin;
	private double bar_max_x;
	private double bar_max_y;
	
	public GamePane(MainApplication app) {
		this.program = app;
		init();
		TRACKING_POSITION = player.getPhysObj().getPosition();
		setOffset();
	}
	
	public double debuggingColliderSize(PhysXObject object) {
		float size = object.getColliders()[0].getRadius() * 2;
		
		Vector2 testPoint0 = Camera.backendToFrontend(object.getPosition());
	    Vector2 testPoint1 = Camera.backendToFrontend(object.getPosition().add(new Vector2(0f, size)));
	    return PhysXLibrary.distance(testPoint0, testPoint1);
	}
	
	public void setupDebug() {
		
		double dist = debuggingColliderSize(player.getPhysObj());
		playerCollider = new GOval(0,0,dist,dist);
		
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
		DEBUGGING_COLLIDERS_OBJECTS = new ArrayList<Asteroid>();
		DEBUGGING_COLLIDERS_OBJECTS_ref = new ArrayList<StaticGObject>();
		
		tracking_offset = new Vector2(0, 0);
		
		for(int i =0; i < PhysXLibrary.MAP_WIDTH; ++i) {
			DEBUGGING_ROWS.add(new StaticRect(new Vector2((PhysXLibrary.QUADRANT_WIDTH * i), 0), new Vector2(5, PhysXLibrary.getMapHeight())));
			program.add(DEBUGGING_ROWS.get(i).getRect());
			DEBUGGING_ROWS.get(i).getRect().setFillColor(Color.LIGHT_GRAY);
			DEBUGGING_ROWS.get(i).getRect().setFilled(true);
		}
		
		for(int i =0; i < PhysXLibrary.MAP_HEIGHT; ++i) {
			DEBUGGING_COLS.add(new StaticRect(new Vector2(0, (PhysXLibrary.QUADRANT_HEIGHT * i)), new Vector2(PhysXLibrary.getMapWidth(), 5)));
			program.add(DEBUGGING_COLS.get(i).getRect());
			DEBUGGING_COLS.get(i).getRect().setFillColor(Color.LIGHT_GRAY);
			DEBUGGING_COLS.get(i).getRect().setFilled(true);
		}
		
		
		setSpriteLayer(DEBUGGING_BOX, DEBUG_LAYER);
		setSpriteLayer(CURRENT_QUID_LABEL, DEBUG_LAYER);
		setSpriteLayer(CURRENT_ASTEROIDS_LABEL, DEBUG_LAYER);
		
		setSpriteLayer(CURRENT_PLAYER_POS_LABEL, DEBUG_LAYER);
		setSpriteLayer(CURRENT_MOUSE_POS_LABEL, DEBUG_LAYER);
		
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
				setSpriteLayer(DEBUGGING_QUID_LABELS.get(DEBUGGING_QUID_LABELS.size() - 1), DEBUG_LAYER);
				statics.get(i).setup(debuggingColliderSize(statics.get(i).getPhysObj()));
				
			
				for(GOval col : statics.get(i).getObjects()) {
					program.add(col);
					col.setColor(Color.MAGENTA);
					setSpriteLayer(col, 1);
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
					setSpriteLayer(DEBUGGING_QUID_LABELS.get(i), DEBUG_LAYER);
//					statics.get(i).getObjects()[f].setLocation(frontEndPos.getX(), frontEndPos.getY());
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
		cursor_dots = new ArrayList <GameImage>();
		pressed_keys = new ArrayList <Integer>();
		drawn_rocks = new ArrayList <Asteroid>();
		drawn_ships = new ArrayList <EnemyShip>();
		
		DEBUGGING_COLLIDERS = new ArrayList<StaticGObject>();
		DEBUGGING_COLLIDERS_OBJECTS = new ArrayList<Asteroid>();
		DEBUGGING_COLLIDERS_OBJECTS_ref = new ArrayList<StaticGObject>();
		
		CURRENT_QUID_LABEL = new GLabel("Current QUID", 15, 25);
		CURRENT_ASTEROIDS_LABEL = new GLabel("Current ASTEROIDS", 15, 50);
		CURRENT_PLAYER_POS_LABEL = new GLabel("Current P Position", 15, 75);
		CURRENT_MOUSE_POS_LABEL = new GLabel("Current M Position", 15, 100);
		DEBUGGING_BOX = new GRect(10, 10, 300, 100);
		playerCollider = new GOval(0,0,0,0);

		drawHUD();
		
		console = program.getGameConsole();
		player = console.getPlayer();

		aiming_edge = new GameImage("rectile.png", 0, 0);
		setSpriteLayer(aiming_edge, PLAYER_RECTILE);
		aiming_head = new GameImage("Cursor.png", 0, 0);
		setSpriteLayer(aiming_head, PLAYER_RECTILE_2);
		player_img = player.getSprite();
		setSpriteLayer(player_img, 1);
		if (console.getPlayer() != null && player != null) {
			System.out.println("GamePane successfully accessed GameConsole's Player ship");
		}
		centerPlayer();
		System.out.println("Player spawning at: " + player.getPhysObj().getPosition().getX() + ", " + player.getPhysObj().getPosition().getY());
		player.getPhysObj().setQUID(console.physx().assignQuadrant(player.getPhysObj().getPosition()));
		console.physx().setActiveQuadrant(console.physx().assignQuadrant(player.getPhysObj().getPosition()));
		
		
	
		CAN_MOVE = true;
	}
	
	private void drawHUD() {
		status_back = new GRect(10, MainApplication.WINDOW_HEIGHT - 10 - 75, 300, 75);
		//Vector2 status_origin = new Vector2((float)status_back.getX(), (float)status_back.getY());
		bar_max_y = status_back.getHeight() - 20 - ((status_back.getHeight() - 10) / 2);
		bar_max_x = status_back.getWidth() - 20;
		
		status_back.setFillColor(Color.BLACK);
		status_back.setFilled(true);
		status_back.setColor(Color.WHITE);
		setSpriteLayer(status_back, CURSOR_LAYER);
		
		status_bar_hp_back = new GRect(status_back.getLocation().getX() + 10, status_back.getY() - 10 + bar_max_y + bar_max_y + 10, bar_max_x, bar_max_y);
		status_bar_hp_back.setFillColor(Color.WHITE);
		status_bar_hp_back.setFilled(true);
		status_bar_hp_back.setColor(Color.WHITE);
		setSpriteLayer(status_bar_hp_back, CURSOR_LAYER);
		
		status_bar_shield_back = new GRect(status_back.getLocation().getX() + 10, status_back.getY() - 10 + bar_max_y, bar_max_x, bar_max_y);
		status_bar_shield_back.setFillColor(Color.WHITE);
		status_bar_shield_back.setFilled(true);
		status_bar_shield_back.setColor(Color.WHITE);
		setSpriteLayer(status_bar_shield_back, CURSOR_LAYER);
		
		compass_back = new GRect(status_back.getX() + status_back.getWidth() + 5, status_back.getY(), status_back.getHeight(), status_back.getHeight());
		compass_back.setFillColor(Color.BLACK);
		compass_back.setFilled(true);
		compass_back.setColor(Color.WHITE);
		setSpriteLayer(compass_back, CURSOR_LAYER);
		
		inner_compass_back = new GRect(status_back.getX() + status_back.getWidth() + 15, status_back.getY() + 10, status_back.getHeight() - 20, status_back.getHeight() - 20);
		inner_compass_back.setFillColor(Color.WHITE);
		inner_compass_back.setFilled(true);
		inner_compass_back.setColor(Color.WHITE);
		setSpriteLayer(inner_compass_back, 0);
		
		compass_sprite = new GameImage("Compass2.png", compass_back.getX() + 15, compass_back.getY() + 15);
		setSpriteLayer(compass_sprite, CURSOR_LAYER);
		
		status_bar_hp = new GRect(status_bar_hp_back.getLocation().getX(), status_bar_hp_back.getY(), status_bar_hp_back.getWidth(), status_bar_hp_back.getHeight());
		status_bar_hp.setFillColor(Color.GREEN);
		status_bar_hp.setFilled(true);
		status_bar_hp.setColor(Color.YELLOW);
		setSpriteLayer(status_bar_hp, CURSOR_LAYER);
		
		status_bar_shield = new GRect(status_bar_shield_back.getLocation().getX(), status_bar_shield_back.getY(), status_bar_shield_back.getWidth(), status_bar_shield_back.getHeight());
		status_bar_shield.setFillColor(Color.BLUE);
		status_bar_shield.setFilled(true);
		status_bar_shield.setColor(Color.CYAN);
		setSpriteLayer(status_bar_shield, CURSOR_LAYER);
		
//		stats_back = new GRect();
	}
	
	private void updateHUD() {
		scaleStatusBar(status_bar_hp, (double)player.getCurrentHealth() / (double)player.getStats().getHealthMax());
		scaleStatusBar(status_bar_shield, (double)player.getCurrentShield() / (double)player.getStats().getShieldMax());
		aimCompass(compass_sprite, new Vector2(0,0));
	}
	
	private void scaleStatusBar(GRect bar, double percent) {
		bar.setSize(bar_max_x * percent, bar.getHeight());
	}
	
	private void aimCompass(GameImage compass, Vector2 spot) {
		double x = spot.getX() - player.getPhysObj().getPosition().getX();
		double y = spot.getY() - player.getPhysObj().getPosition().getY();
		compass_sprite.setDegrees(Math.toDegrees(Math.atan2(y, x)));
	}
	
	public void centerPlayer() {
		Vector2 frontPos = Camera.backendToFrontend(player.getPhysObj().getPosition());
		player_img.setLocationRespectSize(frontPos.getX(), frontPos.getY());
//		player_img.setLocation((MainApplication.WINDOW_WIDTH / 2) - (player_img.getWidth() / 2), (MainApplication.WINDOW_HEIGHT / 2) - (player_img.getHeight() /2));
	}
	
	public void setOffset() {
		Camera.setOffset(Vector2.Zero().minus(TRACKING_POSITION));
	}
	
	@Override
	public void showContents() {
		program.add(player_img);
		program.add(aiming_edge);
		program.add(aiming_head);
		showHUD();
	}
	
	public void showHUD() {
		program.add(status_back);
		program.add(status_bar_hp_back);
		program.add(status_bar_hp);
		program.add(status_bar_shield_back);
		program.add(status_bar_shield);
		program.add(compass_back);
		program.add(inner_compass_back);
		program.add(compass_sprite);
	}

	@Override
	public void hideContents() {
		program.remove(player_img);
		program.remove(aiming_edge);
		program.remove(aiming_head);
		hideHUD();
	}
	
	public void hideHUD() {
		program.remove(status_back);
		//program.remove(status_bar_hp);
		program.remove(status_bar_hp_back);
		//program.remove(status_bar_shield);
		program.remove(status_bar_shield_back);
		program.remove(compass_back);
		program.remove(compass_sprite);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		if (console.IS_DEBUGGING) {
			if(!DO_POINT_TEST) {
				isShooting = true;
			} else {
				pointTest(new Vector2(e.getX(), e.getY()));
			}
		} else {
			isShooting = true;
		}
				
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == player_img) {
			program.switchToMenu();
		}
		else {
//			System.out.println("Clicked empty space");
		}
	}
	
	private void shoot() {
		//float radius = (player.getPhysObj().getColliders()[0].getRadius() / 2);
		Vector2 pos = new Vector2((float)( player.getPhysObj().getPosition().getX() ), (float)( player.getPhysObj().getPosition().getY() ));
		GOval bullet = console.Shoot(1, 25, BulletType.PLAYER_BULLET, 4, new PhysXObject(player.getPhysObj().getQUID(), pos), Camera.frontendToBackend(last_mouse_loc) );
		bullet.setFilled(true);
		bullet.setFillColor(Color.orange);
		bullet.setColor(Color.orange);
		program.add(bullet);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		isShooting = false;
		shotCount= 0;
//		auto_fire.stop();
		System.out.println("Stopped shooting");
	}

	// Every tick of the global game clock calls all visual drawing necessary
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!TRACKING_SET) {
			TRACKING_POSITION = player.getPhysObj().getPosition();
		}
		
		setOffset();
		if (CAN_ALIGN && !ALIGNMENT_LOCK) {
			ALIGNMENT_LOCK = true;
			alignReticle(last_mouse_loc);
		}
		
		if(CAN_MOVE) {
			movementLoop();
		}
		
		moveEnemyShips();
		
		drawSprites();
		
		if(isShooting) {
			if(shotCount % 5 == 0) {
				shoot();
			}
			shotCount++;
		}
		
		console.testCollisions(player);
		console.moveBullets();
		for(GOval bullet : console.cullBullets()) {
			program.remove(bullet);
		}
		
		debugUpdate();		

		
		updateHUD();
		layerSprites();
		/*
		ArrayList <Quadrant> quads = console.physx().getQuadrants();
		for (int i = 0; i < quads.size(); i++) {
			if (quads.get(i).getAsteroids().size() > 0) {
//				System.out.println(quads.get(i));
//				for (Asteroid rock : quads.get(i).getAsteroids()) {
//					System.out.println(rock);
//					if (rock.getSprite() == null) {
//						System.out.println("No sprite!");
//					}
//				}
				drawAsteroids(quads.get(i).getAsteroids());
			}
		}
		*/
		
		
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
				//Vector2 GOvalSize = new Vector2((float)playerCollider.getWidth(), (float)playerCollider.getHeight());
				Vector2 newFEPOS = Camera.backendToFrontend(player.getPhysObj().getPosition(), size);
				playerCollider.setLocation(newFEPOS.getX(), newFEPOS.getY());
				
				drawStaticRect(DEBUGGING_ROWS);
				drawStaticRect(DEBUGGING_COLS);
				
				if (!DRAWING_LOCK) {
					try {
					DRAWING_LOCK = true;
					int MAXDRAW = 50;
					int draw = 0;
					for(Asteroid aster : drawn_rocks) {
						if(draw >= MAXDRAW) {
							break;
						}
						if(!DEBUGGING_COLLIDERS_OBJECTS.contains(aster)) {
							DEBUGGING_COLLIDERS_OBJECTS.add(aster);
							Vector2 imageSize = new Vector2((float)aster.getSprite().getWidth(), (float)aster.getSprite().getHeight());
							DEBUGGING_COLLIDERS_OBJECTS_ref.add(new StaticGObject(aster.getPhysObj(), imageSize));
							draw ++;
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
		drawSprites(console.getActiveAsteroids(), drawn_rocks, ROCK_LAYER);
		drawSprites(console.getActiveShips(), drawn_ships, ROCK_LAYER);
	}
	
	public void moveEnemyShips() {
		ArrayList<EnemyShip> ships = console.getActiveShips();
		for(EnemyShip ship: ships) {
			ship.AIUpdate(player.physObj.getPosition());
		}
	}
	
	// Logic is backwards because .acm is weird. Sprites must be sent to front before sending back to layer appropriately
	private void setSpriteLayer(GObject sprite, int layer) {
		sprite.sendToFront();
		for(int i = 0; i < layer; i++) {
			sprite.sendBackward();
		}
	}
	
	// TODO
	private void layerSprites() {
		// First, put aiming reticles in front
		aiming_edge.sendToBack();
		aiming_head.sendToBack();
		
		// Then dynamic parts of the HUD
		status_bar_hp.sendToBack();
		status_bar_hp_back.sendToBack();
		status_bar_shield.sendToBack();
		status_bar_shield_back.sendToBack();
		status_back.sendToBack();
		compass_sprite.sendToBack();
		inner_compass_back.sendToBack();
		compass_back.sendToBack();
		player_img.sendToBack();
		
		// FX Layer
		
		// Bullets; needs to be redone
		
		// Enemy ships have priority next
		for (int i = 0; i < drawn_ships.size(); i++) {
			drawn_ships.get(i).getSprite().sendToBack();
		}
		
		// Big rocks have priority next
		for (int i = 0; i < drawn_rocks.size(); i++) {
			drawn_rocks.get(i).getSprite().sendToBack();
		}
		
	}
	
	private void movementLoop() {
		
		if(MOVEMENT_LOCK)
			return;
		
		MOVEMENT_LOCK = true;
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
		float speed = (float) player.getStats().getSpeed() * 5 * final_forward;
		
		
		float cos = (float) Math.cos(angle) * speed;
		float sin = (float) Math.sin(angle) * speed;

		player_img.rotate(TURN_POWER * final_turn);
		player.adjustAngle(TURN_POWER * -final_turn);
		player_img.setDegrees(-player.getAngle() + 90);
		
		
		
//		float dia = player.getPhysObj().getColliders()[0].getRadius() * 2;
//		Vector2 size = new Vector2(dia, dia);
		Vector2 newFEPOS = Camera.backendToFrontend(player.getPhysObj().getPosition());

//		player_img.setLocationRespectSize(newFEPOS.getX() + (player.getPhysObj().getColliders()[0].getRadius() / 2), newFEPOS.getY() + (player.getPhysObj().getColliders()[0].getRadius() / 2));
		if(!DEBUGGING_MOVE_LOCK) {
			player_img.setLocationRespectSize(newFEPOS.getX(), newFEPOS.getY());
		}
		player.moveVector2(new Vector2(cos, sin));

//		if (xAxis > 0 + MOVEMENT_CONSTANT) {
//			player.adjustAngle(-TURN_POWER);
//			player_img.rotate(TURN_POWER);
//		} else if (xAxis < 0 - MOVEMENT_CONSTANT) {
//			player.adjustAngle(TURN_POWER);
//			player_img.rotate(-TURN_POWER);
//		}
		
//		player.setDx((float) player.getStats().getSpeed() * 5 * xAxis);
//		player.setDy((float) player.getStats().getSpeed() * 5 * yAxis);

		player.getPhysObj().setQUID(console.physx().assignQuadrant(player.getPhysObj().getPosition()));
		console.physx().setActiveQuadrant(console.physx().assignQuadrant(player.getPhysObj().getPosition()));

		
		MOVEMENT_LOCK = false;
		
		//System.out.println("Player Pos: " + (int)player.getPhysObj().getPosition().getX() + ", " + (int)player.getPhysObj().getPosition().getY() + " | Angle: " + player.getAngle() + "*");
		// Someone changed the code, so I commented it out if we want to retain any information from it.
		/*
		if (yAxis > 0 + MOVEMENT_CONSTANT) {
			double angle = -Math.toRadians(player.getAngle());
			float speed = (float) player.getStats().getSpeed() * 5;
			float cos = (float) Math.cos(angle) * speed;
			float sin = (float) Math.sin(angle) * speed;
			
			player_img.move(cos, sin);
			player.getPhysObj().getPosition().add(new Vector2(cos, sin));
		}
		else if (yAxis < 0 - MOVEMENT_CONSTANT) {
    		double angle2 = -Math.toRadians(player.getAngle());
			float speed2 = (float) player.getStats().getSpeed() * -5;
			float cos2 = (float) Math.cos(angle2) * speed2;
			float sin2 = (float) Math.sin(angle2) * speed2;
		
			player_img.move(cos2, sin2);
			player.getPhysObj().getPosition().add(new Vector2(cos2, sin2));
		}
		*/
		
//        String pressed = "Pressed keys: ";
//        for (int i = 0; i < pressed_keys.size(); i++) {
//        	pressed += String.format("%X", pressed_keys.get(i)) + " ";
//        }
//		System.out.println(pressed);

//		System.out.println("\tImage Pos: " + (int)player_img.getX() + ", " + (int)player_img.getY());
		// this has to be the last call!

	}
	
//	private Vector2 fromGPoint(GPoint point) {
//		return Camera.frontendToBackend(new Vector2((float)point.getX(), (float)point.getY()));
//	}
	
	private void drawStaticRect(ArrayList<StaticRect> lines) {
		for (int i = 0; i < lines.size(); i++) {
			// Get the offset
			StaticRect rect = lines.get(i);
//			float offset_x = rect.getPhysObj().getPosition().getX() - player.getPhysObj().getPosition().getX();
//			float offset_y = rect.getPhysObj().getPosition().getY() - player.getPhysObj().getPosition().getY();
			
			// Make a proper vector2 location according to the camera zoom scale
			Vector2 final_off = Camera.backendToFrontend(rect.getPhysObj().getPosition());

			// Are we already drawing that rect?
			if (!DEBUGGING_LINES.contains(rect)) {
				DEBUGGING_LINES.add(rect);
				//setSpriteLayer(rect.getRect(), ROCK_LAYER);
			}
			
			// Set its location according to the offset
			if (DEBUGGING_LINES.contains(rect)) {
				rect.getRect().setLocation(final_off.getX(), final_off.getY());
			}
			
		}
	}
	
	private <Item extends Entity> void drawSprites(ArrayList<Item> objects, ArrayList<Item> storage, int layer) {
		if(console.IS_DEBUGGING) {
			CURRENT_ASTEROIDS_LABEL.setLabel("Current ASTER: " + objects.size());
		}
		for (int i = 0; i < objects.size(); i++) {
			// Get the offset
			Item obj = objects.get(i);
//			Vector2 offset = asteroid.getPhysObj().getPosition().minus(player.getPhysObj().getPosition());
//			float offset_x = asteroid.getPhysObj().getPosition().getX() - player.getPhysObj().getPosition().getX();
//			float offset_y = asteroid.getPhysObj().getPosition().getY() - player.getPhysObj().getPosition().getY();
			
			// Make a proper vector2 location according to the camera zoom scale
//			Vector2 size = new Vector2(asteroid.getPhysObj().getColliders()[0].getRadius() * 2, asteroid.getPhysObj().getColliders()[0].getRadius() * 2);
//			Vector2 frontEndPos = Camera.backendToFrontend(asteroid.getPhysObj().getPosition(), size);
			Vector2 frontEndPos = Camera.backendToFrontend(obj.getPhysObj().getPosition());

			// Are we already drawing that rock?
			if (!storage.contains(obj)) {
				storage.add(obj);
				program.add(obj.getSprite());
				setSpriteLayer(obj.getSprite(), layer);
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
	
	// Might be a very taxing method. We can change to having a simple cursor at the mouse pointer. Luckily, won't draw more than 5 dots
	public void alignReticle(Vector2 coord) {
		//Vector2 root = player.getPhysObj().getPosition();
		Vector2 visual_root = new Vector2((float)(player_img.getX() + (player_img.getWidth()/2)), (float)(player_img.getY() + (player_img.getHeight()/2)));
		
		/*
		int distance = (int)Math.floor(PhysXLibrary.distance(visual_root, new Vector2(coord.getX(), coord.getY())));
		int dots = (distance / CURSOR_DIST);
		if (cursor_dots.size() < dots) {
			for (int i = 0; i < dots - cursor_dots.size(); i++) {
				GameImage dot = new GameImage(CURSOR_LINE_SPRITE, 0, 0);
				//dot.setColor(Color.black);
				cursor_dots.add(dot);
				program.add(dot);
				setSpriteLayer(dot, CURSOR_LAYER);
			}
		}
		if (cursor_dots.size() > dots) {
			for (int i = 0; i < cursor_dots.size() - dots; i++) {
				program.remove(cursor_dots.get(cursor_dots.size() - i - 1));
				cursor_dots.remove(cursor_dots.size() - i - 1);
			}
		}
		*/
		
		// Align them properly
		double off_x = (coord.getX() - visual_root.getX());
		double off_y = (coord.getY() - visual_root.getY());
		double theta_rad = Math.atan2(off_y, off_x);
		double unit_x = (Math.cos(theta_rad) * CURSOR_DIST);
		double unit_y = (Math.sin(theta_rad) * CURSOR_DIST);
		aiming_edge.setDegrees(Math.toDegrees(theta_rad));
		aiming_edge.setLocation(visual_root.getX() - (aiming_edge.getWidth() / 2), visual_root.getY() - (aiming_edge.getHeight() / 2));
		aiming_head.setDegrees(Math.toDegrees(theta_rad));
		aiming_head.setLocation(coord.getX() - (aiming_head.getWidth() / 2), coord.getY() - (aiming_head.getHeight() / 2));
		for (int i = 0; i < cursor_dots.size(); i++) {
			GameImage dot = cursor_dots.get(i);
			double pos_x = visual_root.getX() - (dot.getWidth() / 2) + (unit_x * (i + 1.5));
			double pos_y = visual_root.getY() - (dot.getHeight() / 2) + (unit_y * (i + 1.5));
			dot.setDegrees(Math.toDegrees(theta_rad));
			dot.setLocation(pos_x, pos_y);
		}
		//System.out.println("Distance: " + distance + ", Drawn: " + dots);
		ALIGNMENT_LOCK = false;
	}
	
	private boolean containsKey(int key) {
		if (!pressed_keys.contains(key)) {
			return false;
		}
		return true;
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
        }
        
        if (key == KeyEvent.VK_ESCAPE) {
        		program.switchToPause();
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
	
	
}