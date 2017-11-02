import java.util.*;
public class Quadrant{
	private ArrayList<PhysXObject> statics;
//	private ArrayList<Ship> ships;
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
	
	/*
	public void checkForCollisions() {
		for(Ship A : ships) {
			for(PhysXObject B : statics) {
				if(PhysXLibrary.areObjectsInCollisionRange(A,B)) {
					if(PhysXLibrary.isCollision(A, B)) {
//						A.sendMessage()
					}
				}
			}
			for(PhysXObject C : ships) {
				if(PhysXLibrary.areObjectsInCollisionRange(A, C)) {
					if(PhysXLibrary.isCollision(A, C)) {
//						A.sendMessage()
//						B.sendMessage()
					}

				}
			}
		}
	}
	*/
	
	public void Activate() {
		this.isActive = true;
	}
	
	public void Deactivate() {
		this.isActive = false;
	}
}
