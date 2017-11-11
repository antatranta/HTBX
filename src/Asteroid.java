import rotations.GameImage;

public class Asteroid {
	private PhysXObject physObj;
	private CollisionData collisionData;
	private GameImage sprite;
	
	public Asteroid() {
		this.physObj = new PhysXObject();
		this.collisionData = new CollisionData(0, CollisionType.asteroid);
		this.sprite = new GameImage("robot head.jpg", 0, 0);
	}
	
	public Asteroid(PhysXObject physObj) {
		this.physObj = new PhysXObject(physObj);
		this.collisionData = new CollisionData(0, CollisionType.asteroid);
		this.sprite = new GameImage("robot head.jpg", 0, 0);
	}
	
	public PhysXObject getPhysObj() {
		return this.physObj;
	}
	
	public GameImage getSprite() {
		return sprite;
	}
	
	public CollisionData getCollisionData() {
		return new CollisionData(collisionData);
	}
}