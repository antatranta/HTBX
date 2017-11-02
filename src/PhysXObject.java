public class PhysXObject {
	private CircleCollider[] colliders;
	private QuadrantID QUID;
	private Vector2 position;
	
	public Vector2 getPosition() {
		return new Vector2(position);
	}
	
	public QuadrantID getQUID()
	{
		return new QuadrantID(QUID);
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
}