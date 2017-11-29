
public class BulletFireEventData {
	
	private int damage;
	private float speed;
	private BulletType type;
	private CollisionType collision;
	private float time;
	private PhysXObject obj;
	private String sprite;
	private Vector2 movementVector;
	private FXParticle fx;
	
	public BulletFireEventData(int damage, float speed, BulletType type, CollisionType collision, float time, PhysXObject obj, String sprite, Vector2 movementVector, FXParticle fx) {
		this.damage = damage;
		this.speed = speed;
		this.type = type;
		this.collision = collision;
		this.time = time;
		this.obj = obj;
		this.sprite = sprite;
		this.movementVector = movementVector;
		this.fx = fx;
	}

	public int getDamage() {
		return this.damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}

	public float getSpeed() {
		return this.speed;
	}
	
	public void setSpeed(float spd) {
		this.speed = spd;
	}
	
	public BulletType getBulletType() {
		return this.type;
	}
	
	public void setBulletType(BulletType type) {
		this.type = type;
	}

	public CollisionType getCollisionType() {
		return this.collision;
	}

	public float getTime() {
		return this.time;
	}
	
	public void setTime(float dur) {
		this.time = dur;
	}

	public PhysXObject getPhysXObject() {
		return this.obj;
	}

	public String getSprite() {
		return this.sprite;
	}
	
	public void setSprite(String spr) {
		this.sprite = spr;
	}

	public Vector2 getMovementVector() {
		return this.movementVector;
	}
	
	public void setMovementVector(Vector2 pos) {
		this.movementVector = pos;
	}
	
	public FXParticle getFXParticle() {
		return this.fx;
	}

}
