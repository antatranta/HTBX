public class PhysXLibrary{
	/*
	public static float	QUADRANT_HEIGHT;
	public static float	QUADRANT_WIDTH;
	public static int 	MAP_WIDTH;
	public static int 	MAP_HEIGHT;
	*/
	//For testing the map.--wenrui

	public static float	QUADRANT_HEIGHT = 500f * 2;
	public static float	QUADRANT_WIDTH = 1000f * 2;

	public static int 	MAP_WIDTH=5;
	public static int 	MAP_HEIGHT=5;
	
	public static final float COLLISION_CONSTANT = 1000f;
	
	public static final float FRICTION = (float) 1.15;
	
	public static float getMapHeight() {
		return QUADRANT_HEIGHT * MAP_HEIGHT;
	}
	
	public static float getMapWidth() {
		return QUADRANT_WIDTH * MAP_WIDTH;
	}
	
	public static PhysX createPhysXInstance() {
		return new PhysX(QUADRANT_HEIGHT, QUADRANT_WIDTH, MAP_WIDTH, MAP_HEIGHT);
	}
	
	protected static double distance(Vector2 a, Vector2 b) {
//		return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
		return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
	}
	
	public static Vector2 midpoint(Vector2 a, Vector2 b) {
		return new Vector2((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2);
	}
	
	public static boolean isCollision(PhysXObject a, PhysXObject b) {
		CircleCollider[] colliderSetA = a.getColliders();
		CircleCollider[] colliderSetB = b.getColliders();
		Vector2 positionA = a.getPosition();
		Vector2 positionB = b.getPosition();
		for(CircleCollider coll_A : colliderSetA) {
			for(CircleCollider coll_B : colliderSetB) {
				
				// Get the distance between two colliders
				double dist = distance(positionA.add(coll_A.getCenter()), positionB.add(coll_B.getCenter()));
				
				// Add the radii of the two colliders
				float hittingDistance = coll_A.getRadius() + coll_B.getRadius();
				
				// if the distance is less than the raii of the colliders, collision
				if (dist <= hittingDistance) {
					return true;
				}
			}
		}
		return false;
	}
		
	public static QuadrantPlacement getPositionInQuadrant(PhysXObject a) {
		
		// First we offset the position of the object
		Vector2 transformedPosition =  new Vector2(a.getPosition().getX() - (a.getQUID().getX() * QUADRANT_WIDTH),
				a.getPosition().getY() - (a.getQUID().getY() * QUADRANT_HEIGHT));
		
		// Determine left or right
		boolean isLeft = false;
		if (transformedPosition.getX() < (QUADRANT_WIDTH / 2)) {
			isLeft = true;
		}
		
		// Determine top or bottom
		boolean isTop = false;
		if (transformedPosition.getY() < (QUADRANT_HEIGHT / 2)) {
			isTop = true;
		}
		
		// Return
		if (isLeft) {
			if (isTop)
				return QuadrantPlacement.top_left;
			else
				return QuadrantPlacement.bottom_left;
		} else {
			if (isTop)
				return QuadrantPlacement.top_right;
			else
				return QuadrantPlacement.bottom_right;
		}
	}
	
	public static boolean areObjectsInXRange(PhysXObject a, PhysXObject b, float range) {
		return arePositionsInXRange(a.getPosition(), b.getPosition(), range);
	}
	
	public static boolean arePositionsInXRange(Vector2 a, Vector2 b, float range) {
		if(Math.abs(a.getX() - b.getX()) < range
				&& Math.abs(a.getY() - b.getY()) < range) {
			return true;
		}
		return false;
	}
	
	public static boolean areObjectsInCollisionRange(PhysXObject a, PhysXObject b) {
		/*
		QuadrantPlacement aPlacement = getPositionInQuadrant(a);
		QuadrantPlacement bPlacement = getPositionInQuadrant(b);
		
		if (a.getQUID().Order() == b.getQUID().Order()) {
			return true;
		} else if (Math.abs(a.getQUID().getX() - b.getQUID().getX()) < 2) {
			if (aPlacement.)
			return true;
		} else if (Math.abs(a.getQUID().getY() - b.getQUID().getY()) < 2) {
			return true;
		} else if (distance(a.getPosition(), b.getPosition()) < COLLISION_CONSTANT) {
			return true;
		} else {
			return false;
		}
		*/
		return areObjectsInXRange(a, b, COLLISION_CONSTANT);
	}
	
	public static Vector2 calculateCollisionForce(Vector2 pos, PhysXObject physObj, float KB_FORCE) {
		double theta_rad = Math.atan2(pos.getY() - physObj.getPosition().getY(), pos.getX() - physObj.getPosition().getX());
		float unit_x = (float)(Math.cos(theta_rad));
		float unit_y = (float)(Math.sin(theta_rad));
		return new Vector2(unit_x * -KB_FORCE, unit_y * -KB_FORCE);
	}
}