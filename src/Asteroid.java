public class Asteroid extends Entity {
	private CollisionData collisionData;
	
	public Asteroid(PhysXObject physObj, String sprite) {
		super(physObj, sprite, new CollisionData(100, CollisionType.asteroid));
	}

	public CollisionData getCollisionData() {
		return new CollisionData(this.collisionData);
	}

	@Override
	public void onCollisionEvent(CollisionData data, Vector2 pos) {
	}
}