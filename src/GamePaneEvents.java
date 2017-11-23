import acm.graphics.GOval;

public interface GamePaneEvents {
	public void eventRequest_drawGOval(GOval oval);
	public void eventRequest_addStaticGObject(StaticGObject obj);
	void eventRequest_addDeathEvent(ShipDeathData data);
}
