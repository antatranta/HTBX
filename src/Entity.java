import rotations.GameImage;

public class Entity implements Collision{
	protected PhysXObject physObj;
	protected GameImage sprite;
	
	public Entity(PhysXObject physObj, String sprite, CollisionData data) {
		this.physObj = physObj;
		this.physObj.setCollisionData(new CollisionData(data));
		this.sprite = new GameImage(sprite);
	}
	
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
		
	protected void setCollisionData(CollisionData data) {
		this.physObj.setCollisionData(new CollisionData(data));
	}
	
	public CollisionData getCollisionData() {
		System.out.println(this.physObj.getCollisionData().toString());
		return new CollisionData(this.physObj.getCollisionData());
	}

	@Override
	public void onCollisionEvent(CollisionData data) {
		// TODO Auto-generated method stub
		
	}
}
