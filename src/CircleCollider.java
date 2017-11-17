// CircleCollider.Java

public class CircleCollider {

	private Vector2 center;
	private float radius;
	
	public CircleCollider() {
		this.center = Vector2.Zero();
		this.radius = 50;
	}
	
	public CircleCollider(Vector2 center, float radius) {
		this.center = center;
		this.radius = radius;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public Vector2 getCenter() {
		return center;
	}

	/*
	public void changeRadius(float newRadius) {
		this.radius = newRadius;
	}
	public void moveCenter(Vector2 newCenter) {
		this.center = newCenter;
	}
	*/
}