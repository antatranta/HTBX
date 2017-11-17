import java.util.ArrayList;

public class PhysXObject {
	private CircleCollider[] colliders;
	private QuadrantID QUID;
	private Vector2 position;
//	private Object host;
	private ArrayList<Collision> subscribers;
	private CollisionData collisionData;
	
	public PhysXObject() {
		this.colliders = new CircleCollider[1];
		this.colliders[0] = new CircleCollider();
		this.QUID = new QuadrantID();
		this.position = Vector2.Zero();
	}
	
	/*
	public PhysXObject(Object host) {
		this.host = host;
		this.colliders = new CircleCollider[1];
		this.colliders[0] = new CircleCollider();
		this.QUID = new QuadrantID();
		this.position = Vector2.Zero();
	}
	*/

	public PhysXObject(QuadrantID QUID) {
		this.colliders = new CircleCollider[1];
		this.colliders[0] = new CircleCollider();
		this.QUID = new QuadrantID(QUID);
		this.position = Vector2.Zero();
	}
	
	public PhysXObject(QuadrantID QUID, Vector2 position) {
		this.colliders = new CircleCollider[1];
		this.colliders[0] = new CircleCollider();
		this.QUID = QUID;
		this.position = position;
	}
	
	public PhysXObject(QuadrantID QUID, Vector2 position, CircleCollider collider) {
		CircleCollider[] colliders = new CircleCollider[1];
		colliders[0] = collider;
		this.colliders = colliders;
		this.QUID = QUID;
		this.position = position;
	}
	
	public PhysXObject(QuadrantID QUID, Vector2 position, CircleCollider collider, Object host) {
//		this.host = host;
		CircleCollider[] colliders = new CircleCollider[1];
		colliders[0] = collider;
		this.colliders = colliders;
		this.QUID = QUID;
		this.position = position;
	}
	
	public PhysXObject(PhysXObject toCopy) {
		this.colliders = toCopy.getColliders();
		this.QUID = toCopy.getQUID();
		this.position = toCopy.getPosition();
	}
	
	public Vector2 getPosition() {
		return new Vector2(position);
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public QuadrantID getQUID()
	{
		return new QuadrantID(QUID);
	}
	
	public void setQUID(QuadrantID QUID) {
		this.QUID = QUID;
	}
	
	public CircleCollider[] getColliders() {
		return colliders;
	}
	
	public void addCollider(CircleCollider newCollider) {
		if(colliders.length < 1) {
			colliders = new CircleCollider[1];
			colliders[0] = newCollider;
		} else {
			CircleCollider[] newColliderArray = new CircleCollider[colliders.length + 1];
			newColliderArray[colliders.length] = newCollider;
			System.arraycopy(colliders, 0, newColliderArray, 0, colliders.length);
			colliders = newColliderArray;
		}
	}
	
//	public void setHost(Object host) {
//		this.host = host;
//	}
//	
//	public Object getHost() {
//		return host;
//	}
	
	public void addSubscriber(Collision newSubscriber) {
		subscribers.add(newSubscriber);
	}
	
	public void setCollisionData(CollisionData coll) {
		this.collisionData = coll;
	}
	
	public void sendCollisionData(CollisionData data) {
		for(Collision sub : subscribers) {
			
			if(sub != null) {
				sub.onCollisionEvent(data);
			}
		}
	}
	
	
	public CollisionData getCollisionData() {
		return collisionData;
	}
}