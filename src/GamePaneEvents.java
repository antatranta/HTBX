import java.util.ArrayList;

import acm.graphics.GObject;
import acm.graphics.GOval;
import rotations.GameImage;

public interface GamePaneEvents {
	public void eventRequest_drawGOval(GOval oval);
	public void eventRequest_addStaticGObject(StaticGObject obj);
	void eventRequest_addDeathEvent(ShipDeathData data);
	public void eventRequest_removeShip(EnemyShip obj);
	public void eventRequest_addObjects(ArrayList<GameImage> obj);
	public void eventRequest_removeObjects(ArrayList<GameImage> obj);
	void eventRequest_addLaserLine(LaserLine line);
	public void eventRequest_removeObject(GOval oval);
	public void addThreat(Vector2 pos);
}
