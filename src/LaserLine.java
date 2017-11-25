import java.util.ArrayList;

import acm.graphics.GImage;

public class LaserLine {
	ArrayList<LaserSegment> segments;
	private double segmentHeight, segmentWidth;
	
	public LaserLine() {
		init();
	}
	
	public void init() {
		segments = new ArrayList<LaserSegment>();
		// get segment height
		GImage w_h_test = new GImage("Laser segment.png");
		segmentHeight = w_h_test.getHeight();
		segmentWidth = w_h_test.getWidth();
	}
	
	private int calculateNumberOfRequiredSegments(Vector2 origin, Vector2 endPoint) {
		Vector2 point0 = Camera.backendToFrontend(origin);
	    Vector2 point1 = Camera.backendToFrontend(endPoint);
	    double distance = PhysXLibrary.distance(point0, point1);
	    return (int)(distance / segmentHeight);
	}
	
	private void createBeam(Vector2 origin, Vector2 endPoint) {
		int numberOfSegments = calculateNumberOfRequiredSegments(origin, endPoint);
		Vector2 currentPosition = new Vector2(origin);
		Vector2 movementVector = calcutlateTrajectory(origin, endPoint);
		for(int i =0; i < numberOfSegments; ++i) {
			
//			PhysXObject physObj, String sprite, CollisionData data
			CircleCollider newCollider = new CircleCollider((float)segmentHeight);
			PhysXObject newPhysObj = new PhysXObject(new QuadrantID(), newCollider);
			newPhysObj.setPosition(currentPosition);
			
			LaserSegment newSegment = new LaserSegment(newPhysObj, "Laser segment.png", new CollisionData());
			segments.add(newSegment);
			
			currentPosition = currentPosition.add(movementVector.mult(new Vector2((float)segmentHeight, (float)segmentHeight)));
//			segments.add(e);
//			Vector2 movement = new Vector2(physObj.getPosition().getX() + getBulletDX(), physObj.getPosition().getY() + getBulletDY());
//			this.physObj.setPosition(movement);
		}
	}
	
	private Vector2 calcutlateTrajectory(Vector2 origin, Vector2 endPoint) {
		return origin.normalize(endPoint);
	}
	
	private void updatePositions() {
		for(LaserSegment segment: segments) {
//			segment.getSprite().setLocationRespectSize(x, y);
		}
	}
}
