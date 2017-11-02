// PhysX Class
import java.util.*;
public class PhysX {
	public static float	QUADRANT_HEIGHT;
	public static float	QUADRANT_WIDTH;
	public static int 	MAP_WIDTH;
	public static int 	MAP_HEIGHT;
	
	private ArrayList<Quadrant> Quadrants;
	private QuadrantID ActiveQuadrant;
	
	public static QuadrantID assignQuadrant(Vector2 a) {
		return new QuadrantID(0,0,0);
	}
	
	public ArrayList<Quadrant> getNearbyQuadrants(QuadrantID QUID){
		
		// Create our returning object
		ArrayList<Quadrant> quads = new ArrayList<Quadrant>();
		
		for(Quadrant quad : Quadrants) {
			QuadrantID testQUID = quad.getQUID();
			
			// Test if the quad is Above or Below
			if(Math.abs(testQUID.getY() - QUID.getY()) < 2) {
				quads.add(Quadrants.get(testQUID.Order()));
				
				// Doesn't work for corner cases!
				quads.add(Quadrants.get(testQUID.Order() - 1));
				quads.add(Quadrants.get(testQUID.Order() + 1));
			}
			
			// Test if the quad is Left or Right
			if(Math.abs(testQUID.getX() - QUID.getX()) < 2) {
				quads.add(Quadrants.get(testQUID.Order()));
				
				// Doesn't work for corner cases!
				quads.add(Quadrants.get(testQUID.Order() - 1));
				quads.add(Quadrants.get(testQUID.Order() + 1));
			}
		}
		
		return quads;
	}

	private void updateQuadrantStates() {
		
		// since PhysX controls when the next phys step
		// will happen we don't need to worry about 
		// any weird issues deriving from deactivating
		// a quadrant and then reactivating it.
		for (Quadrant quad : Quadrants) {
			quad.Deactivate();
		}
		for (Quadrant quad : getNearbyQuadrants(ActiveQuadrant)) {
			quad.Activate();
		}
	}
	
	private void setActiveQuadrant(QuadrantID newQUID) {
		//TODO: Add validation
		this.ActiveQuadrant = newQUID;
	}
	
	private ArrayList<PhysXObject> getNearbyPhysXObjects(){
		return new ArrayList<PhysXObject>();
	}
	
	// The quadrants will always be read in order
	// so there is no need to verify placement
	// or order.
	public void addQuadrant(Quadrant quad) {
		Quadrants.add(quad);
	}
}