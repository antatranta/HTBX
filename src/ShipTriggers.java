
public interface ShipTriggers {
	public void onShipFire(BulletFireEventData data, CollisionType bulletType);
	public void onShipDeath(Vector2 position);
}
