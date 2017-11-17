import rotations.GameImage;

public class Asteroid extends Ship {
	private CollisionData collisionData;

	
	public Asteroid(PhysXObject physObj) {
		super(physObj, 500, new ShipStats(0, 0, 500, 500), "Asteroids.png");
		this.collisionData = new CollisionData(0, CollisionType.asteroid);
	}

	public CollisionData getCollisionData() {
		return new CollisionData(collisionData);
	}
}