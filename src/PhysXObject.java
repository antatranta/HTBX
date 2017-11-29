import java.util.ArrayList;

public class PhysXObject {
	private CircleCollider[] colliders;
	private QuadrantID QUID;
	private Vector2 position;
	private ArrayList<Collision> subscribers;
	private CollisionData collisionData;
	
	public PhysXObject() {
		this.subscribers = new ArrayList<Collision>();
		this.colliders = new CircleCollider[1];
		this.colliders[0] = new CircleCollider();
		this.QUID = new QuadrantID();
		this.position = Vector2.Zero();
	}

	public PhysXObject(QuadrantID QUID) {
		this.subscribers = new ArrayList<Collision>();
		this.colliders = new CircleCollider[1];
		this.colliders[0] = new CircleCollider();
		this.QUID = new QuadrantID(QUID);
		this.position = Vector2.Zero();
	}
	
	public PhysXObject(QuadrantID QUID, CircleCollider collider) {
		this.subscribers = new ArrayList<Collision>();
		this.colliders = new CircleCollider[1];
		this.colliders[0] = new CircleCollider(collider);
		this.QUID = new QuadrantID(QUID);
		this.position = Vector2.Zero();
	}
	
	public PhysXObject(QuadrantID QUID, Vector2 position) {
		this.subscribers = new ArrayList<Collision>();
		this.colliders = new CircleCollider[1];
		this.colliders[0] = new CircleCollider();
		this.QUID = QUID;
		this.position = position;
	}
	
	public PhysXObject(QuadrantID QUID, Vector2 position, CircleCollider collider) {
		this.subscribers = new ArrayList<Collision>();
		CircleCollider[] colliders = new CircleCollider[1];
		colliders[0] = collider;
		this.colliders = colliders;
		this.QUID = QUID;
		this.position = position;
	}
	
	public PhysXObject(QuadrantID QUID, Vector2 position, CircleCollider collider, Object host) {
		this.subscribers = new ArrayList<Collision>();
		CircleCollider[] colliders = new CircleCollider[1];
		colliders[0] = collider;
		this.colliders = colliders;
		this.QUID = QUID;
		this.position = position;
	}
	
	public PhysXObject(PhysXObject toCopy) {
		this.subscribers = new ArrayList<Collision>();
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
	
	public void removeColliders() {
		colliders = new CircleCollider[0];
	}
	
	public void addCollider(CircleCollider newCollider) {
		if(colliders == null) {
			colliders = new CircleCollider[0];
		}
		
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
		if (newSubscriber != null) {
			subscribers.add(newSubscriber);
		} else {
			System.out.println("Error: Null object!");
		}
	}
	
	public void setCollisionData(CollisionData coll) {
		this.collisionData = coll;
	}
	
	public void sendCollisionData(CollisionData data, Vector2 pos) {
		
		if (subscribers.size() < 1) {
			return;
		}
		for(Collision sub : subscribers) {
			if(sub != null) {
				sub.onCollisionEvent(data, pos);
			}
		}
	}
	
	public CollisionData getCollisionData() {
		return this.collisionData;
	}
}