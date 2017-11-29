public interface ShipTriggers {
	public void onShipFire(BulletFireEventData data, CollisionType bulletType);
	public int isAreaSafe(Vector2 pos, float range);
	public int identify();
}
