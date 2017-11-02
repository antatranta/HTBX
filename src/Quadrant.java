import java.util.*;
public class Quadrant{
	private ArrayList<PhysXObject> statics;
	private ArrayList<PhysXObject> ships;
	private boolean isActive;
	private QuadrantID QUID;
	
	public Quadrant() {
		this.isActive = false;
		this.QUID = new QuadrantID(0,0,0);
	}
	
	public Quadrant(QuadrantID QUID) {
		this.isActive = false;
		this.QUID = QUID;
	}
	
	public QuadrantID getQUID() {
		return QUID;
	}
	
	public void checkForCollisions() {
		// TODO: implement
	}
	
	public void Activate() {
		this.isActive = true;
	}
	
	public void Deactivate() {
		this.isActive = false;
	}
}
