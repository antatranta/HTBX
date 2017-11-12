import rotations.GameImage;

public class Asteroid {
	private PhysXObject physObj;
	private CollisionData collisionData;
	private GameImage sprite;
	
	public Asteroid() {
		this.physObj = new PhysXObject();
		this.collisionData = new CollisionData(0, CollisionType.asteroid);
		createSprite();
	}
	
	public Asteroid(PhysXObject physObj) {
		this.physObj = new PhysXObject(physObj);
		this.collisionData = new CollisionData(0, CollisionType.asteroid);
		createSprite();
	}
	
	private void createSprite() {
		this.sprite = new GameImage("Asteroids.png", 0, 0);
		//System.out.println("Asteroid at: " + physObj.getPosition().getX() + ", " + physObj.getPosition().getY());
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