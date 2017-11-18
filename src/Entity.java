import rotations.GameImage;

public class Entity {
	protected PhysXObject physObj;
	protected CollisionData collisionData;
	protected GameImage sprite;
	
	protected void createSprite(String file) {
		this.sprite = new GameImage(file, 0, 0);
		//System.out.println("Asteroid at: " + physObj.getPosition().getX() + ", " + physObj.getPosition().getY());
	}
	public GameImage getSprite() {
		return this.sprite;
	}
	
	public PhysXObject getPhysObj() {
		return this.physObj;
	}
	
	public CollisionData getCollisionData() {
		return new CollisionData(this.collisionData);
	}
}
