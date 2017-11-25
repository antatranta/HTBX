import acm.graphics.GObject;
import acm.graphics.GOval;
import rotations.GameImage;

public interface GamePaneEvents {
	public void eventRequest_drawGOval(GOval oval);
	public void eventRequest_addStaticGObject(StaticGObject obj);
	void eventRequest_addDeathEvent(ShipDeathData data);
	public void eventRequest_removeShip(EnemyShip obj);
	public void eventRequest_addObject(GameImage obj);
	public void eventRequest_removeObject(GameImage obj);
}
