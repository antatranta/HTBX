
public class BulletFireEventData {
	
	private int damage;
	private int speed;
	private CollisionType type;
	private float time;
	private PhysXObject obj;
	private String sprite;
	private Vector2 movementVector;
	
	public BulletFireEventData(int damage, int speed, CollisionType type, float time, PhysXObject obj, String sprite, Vector2 movementVector) {
		this.damage = damage;
		this.speed = speed;
		this.type = type;
		this.time = time;
		this.obj = obj;
		this.sprite = sprite;
		this.movementVector = movementVector;
	}

	public int getDamage() {
		// TODO Auto-generated method stub
		return this.damage;
	}

	public int getSpeed() {
		// TODO Auto-generated method stub
		return this.speed;
	}

	public CollisionType getCollisionType() {
		// TODO Auto-generated method stub
		return this.type;
	}

	public float getTime() {
		// TODO Auto-generated method stub
		return this.time;
	}

	public PhysXObject getPhysXObject() {
		// TODO Auto-generated method stub
		return this.obj;
	}

	public String getSprite() {
		// TODO Auto-generated method stub
		return this.sprite;
	}

	public Vector2 getMovementVector() {
		// TODO Auto-generated method stub
		return this.movementVector;
	}

}
