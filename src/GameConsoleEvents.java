import acm.graphics.GOval;

public interface GameConsoleEvents {
	public void onShipDeath(Vector2 pos);
	public boolean physXRequest_isAreaSafe(Vector2 pos, float range);
	public void programRequest_drawGOval(PhysXObject obj, GOval oval);
	public void bulletRequest_burst(Vector2 pos, QuadrantID QUID);
//	void programRequest_drawGOval(PhysXObject obj, GOval oval);
}
