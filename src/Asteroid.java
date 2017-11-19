import rotations.GameImage;

public class Asteroid extends Entity {
	private CollisionData collisionData;
	
	public Asteroid(PhysXObject physObj) {
		super(physObj, "Asteroids.png", new CollisionData(100, CollisionType.asteroid));
//		this.collisionData = new CollisionData(0, CollisionType.asteroid);
//		this.physObj = physObj;
//		super.createSprite("Asteroids.png");
//		super.setCollisionData(new CollisionData(100, CollisionType.asteroid));
	}

	public CollisionData getCollisionData() {
		return new CollisionData(this.collisionData);
	}

	@Override
	public void onCollisionEvent(CollisionData data, Vector2 pos) {
		// TODO Auto-generated method stub
	}
}