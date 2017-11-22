
public class BulletEmitter extends Entity{
//	protected float KB_FORCE = 10;
//	protected Vector2 external_force;
	
	protected int health;
	protected float rate;

	public BulletEmitter(int health, float rate, PhysXObject physObj, String sprite, CollisionData data) {
		super(physObj, sprite, data);
		this.health = health;
		this.rate = rate;
	}
	
	protected void destroy() {
		this.physObj = null;
	}
	
	public void takeDamage(float damage) {
		health -= damage;
		if(health <= 0) {
			destroy();
		}
	}
	
	@Override
	public void onCollisionEvent(CollisionData data, Vector2 pos) {
		if (data.getType() == CollisionType.player_bullet) {
			takeDamage(data.getDamage());
		}
	}

}
