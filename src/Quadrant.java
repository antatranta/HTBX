import java.util.*;
public class Quadrant{
	private ArrayList<Asteroid> asteroids;
	private ArrayList<Ship> ships;
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
		if (!isActive) {
			return;
		}
		for(Ship A : ships) {
			for(Asteroid B : asteroids) {
				if(PhysXLibrary.areObjectsInCollisionRange(A.getPhysObj(),B.getPhysObj())) {
					if(PhysXLibrary.isCollision(A.getPhysObj(), B.getPhysObj())) {
						A.sendCollisionMessage(B.getCollisionData());
					}
				}
			}
			for(Ship C : ships) {
				if(PhysXLibrary.areObjectsInCollisionRange(A.getPhysObj(), C.getPhysObj())) {
					if(PhysXLibrary.isCollision(A.getPhysObj(), C.getPhysObj())) {
						A.sendCollisionMessage(C.getCollisionData());
						C.sendCollisionMessage(A.getCollisionData());
					}

				}
			}
		}
	}
	
	
	public void Activate() {
		this.isActive = true;
	}
	
	public void Deactivate() {
		this.isActive = false;
	}
}
