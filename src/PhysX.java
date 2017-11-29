// PhysX Class
import java.util.*;
public class PhysX {
	// good to be back

	private float	QUADRANT_HEIGHT;
	private float	QUADRANT_WIDTH;
	private int 		MAP_WIDTH;
	private int 		MAP_HEIGHT;

	private boolean COLLISION_LOCK;

	private ArrayList<Quadrant> Quadrants;
	private QuadrantID ActiveQuadrant;

	public PhysX() {
		Quadrants = new ArrayList<Quadrant>();
		ActiveQuadrant = new QuadrantID(0,0,0);
	}

	public void verifyOrder() {
		int order = 0;
		for(Quadrant quad : Quadrants) {
			order++;
		}
	}

	public PhysX(float QUADRANT_HEIGHT, float QUADRANT_WIDTH, int MAP_WIDTH, int MAP_HEIGHT) {
		this.QUADRANT_HEIGHT = QUADRANT_HEIGHT;
		this.QUADRANT_WIDTH = QUADRANT_WIDTH;
		this.MAP_WIDTH = MAP_WIDTH;
		this.MAP_HEIGHT = MAP_HEIGHT;

		Quadrants = new ArrayList<Quadrant>();
		ActiveQuadrant = new QuadrantID(0,0,0);
	}

	public QuadrantID assignQuadrant(Vector2 assign) {

		int order = 0;
		for (int a = 0; a < MAP_HEIGHT; ++a) {
			for( int b =0; b < MAP_WIDTH; ++b) {
				if (assign.getY() > a * PhysXLibrary.QUADRANT_HEIGHT
						&& assign.getY() < (a + 1) * PhysXLibrary.QUADRANT_HEIGHT
						&& assign.getX() > b * PhysXLibrary.QUADRANT_WIDTH
						&& assign.getX() < (b + 1) * PhysXLibrary.QUADRANT_WIDTH) {
					return new QuadrantID(b,a,order);
				}
				order ++;
			}
		}
		/*
		int y_pos = (int)(assign.getY() / PhysXLibrary.QUADRANT_HEIGHT);
		int x_pos = (int)(assign.getX() / PhysXLibrary.QUADRANT_WIDTH);
		for (Quadrant quad : Quadrants) {
			if (quad.getQUID().getX() == x_pos && quad.getQUID().getY() == y_pos) {
				return quad.getQUID();
			}
		}
		 */

		// -45 is an error code
		return new QuadrantID (0,0,-45);
	}

	public ArrayList<Quadrant> getActiveQuadrants(){
		return getNearbyQuadrants(ActiveQuadrant);
	}

	public ArrayList<Quadrant> getNearbyQuadrants(QuadrantID QUID){

		// Create our returning object
		ArrayList<Quadrant> quads = new ArrayList<Quadrant>();

		if(QUID.Order() != -45 && QUID.Order() < Quadrants.size() && QUID.Order() != -99) {
			quads.add(Quadrants.get(QUID.Order()));
		} else {
			if(GameConsole.IS_DEBUGGING) {
				//				System.out.println("- - OUT OF BOUNDS - -");
			}
			return new ArrayList<Quadrant>();
		}


		for(Quadrant quad : Quadrants) {
			QuadrantID testQUID = quad.getQUID();

			if(Math.abs(testQUID.getX() - QUID.getX()) < 2
					&& Math.abs(testQUID.getY() - QUID.getY()) < 2) {
				if(testQUID.Order() != -45 && testQUID.Order() < Quadrants.size()) {
					quads.add(Quadrants.get(testQUID.Order()));
				}
			}
		}

		return quads;
	}

	private void updateQuadrantStates() {

		// since PhysX controls when the next phys step
		// will happen we don't need to worry about 
		// any weird issues deriving from deactivating
		// a quadrant and then reactivating it.
		for (Quadrant quad : Quadrants) {
			quad.Deactivate();
		}
		for (Quadrant quad : getNearbyQuadrants(this.ActiveQuadrant)) {
			quad.Activate();
		}
	}

	public void setActiveQuadrant(QuadrantID newQUID) {

		// Only set if it's a valid quad.
		if(newQUID.Order() != -45 && newQUID.Order() < Quadrants.size() && newQUID.Order() != -99) {
			this.ActiveQuadrant = newQUID;
		}
		updateQuadrantStates();
	}

	private ArrayList<PhysXObject> getNearbyPhysXObjects(PhysXObject obj, float range){
		// Get Thingy's QUID
		// Look at the elements in the surrounding QUIDs
		ArrayList<Quadrant> quads = getNearbyQuadrants(obj.getQUID());
		ArrayList<PhysXObject> objects = new ArrayList<PhysXObject>();

		for(Quadrant quad : quads) {
			ArrayList<EnemyShip> ships = quad.getShips();
			if(ships != null && ships.size() > 0) {
				for(Ship ship : ships) {
					if(PhysXLibrary.areObjectsInXRange(obj, ship.getPhysObj(), range)) {
						objects.add(ship.getPhysObj());
					}
				}
			}
			ArrayList<Asteroid> asteroids = quad.getAsteroids();
			if(asteroids != null && asteroids.size() > 0) {
				for (Asteroid asteroid : asteroids) {
					if(PhysXLibrary.areObjectsInXRange(obj, asteroid.getPhysObj(), range)) {
						objects.add(asteroid.getPhysObj());
					}
				}
			}
		}

		// Do a distance calc to see if in camera range
		// Draw everything that returns
		// Update what is and what is not to be displayed
		// Create an offset so that the camera will be centered
		return objects;

	}

	private ArrayList<PhysXObject> getObjectsNearPosition(Vector2 pos, float range){

		QuadrantID QUID = assignQuadrant(pos);

		ArrayList<Quadrant> quads = getNearbyQuadrants(QUID);
		ArrayList<PhysXObject> objects = new ArrayList<PhysXObject>();

		for(Quadrant quad : quads) {
			
			ArrayList<EnemyShip> ships = quad.getShips();
			if(ships != null && ships.size() > 0) {
				for(Ship ship : ships) {
					if(PhysXLibrary.arePositionsInXRange(pos, ship.getPhysObj().getPosition(), range)) {
						objects.add(ship.getPhysObj());
					}
				}
			}
			ArrayList<Asteroid> asteroids = quad.getAsteroids();
			if(asteroids != null && asteroids.size() > 0) {
				for (Asteroid asteroid : asteroids) {
					if(PhysXLibrary.arePositionsInXRange(pos, asteroid.getPhysObj().getPosition(), range)) {
						objects.add(asteroid.getPhysObj());
					}
				}
			}
		}

		// Do a distance calc to see if in camera range
		// Draw everything that returns
		// Update what is and what is not to be displayed
		// Create an offset so that the camera will be centered
		return objects;
	}

	public boolean isPositionSafe(Vector2 pos, float range) {
		if(getObjectsNearPosition(pos, range).size() > 0) {
			return false;
		}
		return true;
	}

	public void addQuadrants(ArrayList<Quadrant> quads) {

		for(int i=quads.size()-1; i > 0; --i) {
			addQuadrant(quads.get(i));
		}
		verifyOrder();
	}
	// The quadrants will always be read in order
	// so there is no need to verify placement
	// or order.
	public void addQuadrant(Quadrant quad) {
		Quadrants.add(quad);
	}

	public void checkForCollisions(PhysXObject player) {

		if(!COLLISION_LOCK) {
			COLLISION_LOCK = true;

			checkForCollisionsOnObject(player);

			COLLISION_LOCK = false;
		} else {
			COLLISION_LOCK = false;
			if (GameConsole.IS_DEBUGGING) {
				System.out.println("Dropped PhysX frame");
			}
		}
	}

	public void checkForCollisionsInQuads() {
		for(Quadrant quad : this.Quadrants) {
			quad.checkForCollisions();
		}
	}

	public void checkForCollisions(PhysXObject obj, ArrayList<PhysXObject> objects) {
		for(PhysXObject coll : objects) {
			if(PhysXLibrary.areObjectsInCollisionRange(obj, coll)) {
				if (PhysXLibrary.isCollision(obj, coll)) {

					if (coll.getCollisionData() != null) {
						obj.sendCollisionData(coll.getCollisionData(), coll.getPosition());
					}
					if (obj.getCollisionData() != null) {
						coll.sendCollisionData(obj.getCollisionData(), obj.getPosition());

					}

					if (GameConsole.IS_DEBUGGING) {
						/*
						System.out.println(" - - ");
						System.out.println("Player Pos : " + obj.getPosition().toString());
						System.out.println("Coll Pos   : " + coll.getPosition().toString());
						System.out.println("Distance   : " + (int)PhysXLibrary.distance(coll.getPosition(), obj.getPosition()));
						 */

					}
				}
			}
		}
	}

	public void checkForCollisionsOnObject(PhysXObject obj) {
		ArrayList<PhysXObject> objects = getNearbyPhysXObjects(obj, 1000);
		checkForCollisions(obj, objects);
	}


	public ArrayList<Quadrant> getQuadrants() {
		return Quadrants;
	}

	public void setQuadrants(ArrayList<Quadrant> quadrants) {
		Quadrants = quadrants;
	}
}