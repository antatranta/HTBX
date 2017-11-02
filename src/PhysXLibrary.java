public class PhysXLibrary{
	
	public static final float COLLISION_CONSTANT = 10f;
	
	protected static double distance(Vector2 a, Vector2 b) {
		return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
	}
	
	public static boolean isCollision(PhysXObject a, PhysXObject b) {
		CircleCollider[] colliderSetA = a.getColliders();
		CircleCollider[] colliderSetB = b.getColliders();
		Vector2 positionA = a.getPosition();
		Vector2 positionB = b.getPosition();
		for(CircleCollider coll_A : colliderSetA) {
			for(CircleCollider coll_B : colliderSetB) {
				double dist = distance(positionA.add(coll_A.getCenter()), positionB.add(coll_B.getCenter()));
				float hittingDistance = coll_A.getRadius() + coll_B.getRadius();
				if (dist <= hittingDistance) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean areObjectsInCollisionRange(PhysXObject a, PhysXObject b) {
		if (a.getQUID().Order() == b.getQUID().Order()) {
			return true;
		} else if (Math.abs(a.getQUID().getX() - b.getQUID().getX()) < 2) {
			return true;
		} else if (Math.abs(a.getQUID().getY() - b.getQUID().getY()) < 2) {
			return true;
		} else if (distance(a.getPosition(), b.getPosition()) < COLLISION_CONSTANT) {
			return true;
		} else {
			return false;
		}
	}
}