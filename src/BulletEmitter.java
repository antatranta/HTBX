import java.util.ArrayList;

import acm.graphics.GImage;

public class BulletEmitter extends Entity{
//	protected float KB_FORCE = 10;
//	protected Vector2 external_force;
	
	protected int health;
	protected int rate;
	protected int count = 0;
	protected float interactionDistance;
	private ArrayList<ShipTriggers> subscribers;

	public BulletEmitter(int health, int rate, PhysXObject physObj, String sprite, CollisionData data) {
		super(physObj, sprite, data);
		this.health = health;
		this.rate = rate;
		this.count = 0;
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
	
	public void Update(Vector2 playerPos) {
		if(PhysXLibrary.distance(this.physObj.getPosition(), playerPos) < interactionDistance
				&& count % rate == 0) {
			emitCircle();
			count ++;
		} else {
			count = 0;
		}
	}
	
	public void emitCircle() {
		double theta_rad = 0;
		double unit_x = Math.cos(theta_rad);
		double unit_y = -Math.sin(theta_rad);

		for (int i =0; i < 10; i++) {
			PhysXObject obj = new PhysXObject(physObj.getQUID(), physObj.getPosition(), new CircleCollider(1));
			//Vector2 pos = new Vector2(physObj.getPosition().getX(), physObj.getPosition().getY());
			Vector2 offset = new Vector2((float)unit_x, (float)unit_y);
			shoot(1, 4, BulletType.STRAIGHT, CollisionType.enemy_bullet, 4, obj, "RedCircle.png",  physObj.getPosition().add(offset));
			theta_rad += Math.toRadians(360 / 10);
			unit_x = Math.cos(theta_rad);
			unit_y = -Math.sin(theta_rad);
		}
	}
	
	protected void shoot(int damage, int speed, BulletType type, CollisionType enemyBullet, float time, PhysXObject obj, String sprite, Vector2 movementVector) {
		BulletFireEventData bfe = new BulletFireEventData(damage, speed, type, enemyBullet, time, obj, sprite, movementVector);
//		if(subscribers != null && subscribers.size() > 0) {
//			for(ShipTriggers sub: subscribers) {
//				sub.onShipFire(bfe, enemyBullet);
//			}
//		}
	}
	
	@Override
	public void onCollisionEvent(CollisionData data, Vector2 pos) {
		if (data.getType() == CollisionType.player_bullet) {
			takeDamage(data.getDamage());
		}
	}

	public void addSubscriber(ShipTriggers sub) {
		if(sub != null && this.subscribers != null && !this.subscribers.contains(sub)) {
			this.subscribers.add(sub);
		}
	}
}
