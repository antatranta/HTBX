
public interface ShipTriggers {
	public void onShipFire(BulletFireEventData data, CollisionType bulletType);
//	public void onShipDeath(Vector2 position);
	public int isAreaSafe(Vector2 pos, float range);
	public int identify();
	void onShipDeath(Vector2 position, QuadrantID currentQUID);
}
