// CircleCollider.Java

interface CollisionObject {
    void onCollision();
}

public class CircleCollider {

//	private Vector2 center;
	private float radius;

	public float getRadius() {
		return radius;
	}
//	public Vector2 getCenter();

	public void changeRadius(float newRadius) {
		radius = newRadius;
	}
//	public void moveCenter(Vector2 newCenter)
	
	public boolean isCollision() {
		return false;
	}
}