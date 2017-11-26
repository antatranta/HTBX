
public interface LaserManagerEvents {
	public void createBasicLaser(Vector2 a, Vector2 b, int t);
	void createAdvancedLaser(PhysXObject a, PhysXObject b, int t);
	public void createAdvancedLaserAtPlayer(PhysXObject physObj, int f);
}
