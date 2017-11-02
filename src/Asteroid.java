public class Asteroid {
	private PhysXObject physObj;
	private CollisionData collisionData;
	
	public Asteroid() {
		this.physObj = new PhysXObject();
		this.collisionData = new CollisionData(0, CollisionType.asteroid);
	}
	
	public Asteroid(PhysXObject physObj) {
		this.physObj = new PhysXObject(physObj);
		this.collisionData = new CollisionData(0, CollisionType.asteroid);
	}
	public PhysXObject getPhysObj() {
		return this.physObj;
	}
	public CollisionData getCollisionData() {
		return new CollisionData(collisionData);
	}
}