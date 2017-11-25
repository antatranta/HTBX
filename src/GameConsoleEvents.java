import acm.graphics.GObject;
import acm.graphics.GOval;
import rotations.GameImage;

public interface GameConsoleEvents {
	public void onShipDeath(Vector2 pos);
	public boolean physXRequest_isAreaSafe(Vector2 pos, float range);
	public void programRequest_drawGOval(PhysXObject obj, GOval oval);
	public void bulletRequest_burst(Vector2 pos, QuadrantID QUID);
//	void programRequest_drawGOval(PhysXObject obj, GOval oval);
	public void programRequest_removeObject(EnemyShip obj);
	public void programRequest_removeDrawnObject(GObject obj);
	public void programRequest_drawObject(GObject obj);
}
