
public class BulletFireEventData {
	
	private int damage;
	private int speed;
	private BulletType type;
	private CollisionType collision;
	private float time;
	private PhysXObject obj;
	private String sprite;
	private Vector2 movementVector;
	
	public BulletFireEventData(int damage, int speed, BulletType type, CollisionType collision, float time, PhysXObject obj, String sprite, Vector2 movementVector) {
		this.damage = damage;
		this.speed = speed;
		this.type = type;
		this.collision = collision;
		this.time = time;
		this.obj = obj;
		this.sprite = sprite;
		this.movementVector = movementVector;
	}

	public int getDamage() {
		return this.damage;
	}

	public int getSpeed() {
		return this.speed;
	}
	
	public BulletType getBulletType() {
		return this.type;
	}

	public CollisionType getCollisionType() {
		return this.collision;
	}

	public float getTime() {
		return this.time;
	}

	public PhysXObject getPhysXObject() {
		return this.obj;
	}

	public String getSprite() {
		return this.sprite;
	}

	public Vector2 getMovementVector() {
		return this.movementVector;
	}

}
