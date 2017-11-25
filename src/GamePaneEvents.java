import acm.graphics.GObject;
import acm.graphics.GOval;

public interface GamePaneEvents {
	public void eventRequest_drawGOval(GOval oval);
	public void eventRequest_addStaticGObject(StaticGObject obj);
	void eventRequest_addDeathEvent(ShipDeathData data);
	public void eventRequest_removeShip(EnemyShip obj);
	public void eventRequest_addObject(GObject obj);
	public void eventRequest_removeObject(GObject obj);
}
