public class Ship {
	private PhysXObject physObj;
	private CollisionData collisionData;
	
	public Ship() {
		this.physObj = new PhysXObject();
	}
	
	public PhysXObject getPhysObj() {
		return this.physObj;
	}
	
	public CollisionData getCollisionData() {
		return new CollisionData(collisionData);
	}
	
	public void sendCollisionMessage(CollisionData data) {
		
	}
	
	public void Move() {
		
	}
}