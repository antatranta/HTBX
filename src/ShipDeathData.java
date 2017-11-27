
public class ShipDeathData {
	protected Vector2 pos;
	protected QuadrantID QUID;
	public ShipDeathData(Vector2 pos, QuadrantID QUID) {
		this.pos = pos;
		this.QUID  = QUID;
	}
	
	public Vector2 getPos () {
		return this.pos;
	}
	
	public QuadrantID getQUID() {
		return this.QUID;
	}
	
}
