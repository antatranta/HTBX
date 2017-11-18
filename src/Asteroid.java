import rotations.GameImage;

public class Asteroid extends Entity implements Collision {
	private CollisionData collisionData;
	
	public Asteroid(PhysXObject physObj) {
		this.collisionData = new CollisionData(0, CollisionType.asteroid);
	}

	public CollisionData getCollisionData() {
		return new CollisionData(collisionData);
	}

	@Override
	public void onCollisionEvent(CollisionData data) {
		// TODO Auto-generated method stub
	}
}