import java.util.ArrayList;

import org.omg.CORBA.SystemException;

import acm.graphics.GImage;
import rotations.GameImage;

public class LaserLine {
	ArrayList<LaserSegment> segments;
	private double segmentHeight, segmentWidth;
	private Vector2 endPoint;
	private Vector2 origin;
	private boolean isDead = false;
	private int time;

	private boolean Tracking = false;
	private PhysXObject p_origin;

	public LaserLine() {
		init();
	}

	public LaserLine(int time) {
		init();
		this.time = time;
	}

	public LaserLine(PhysXObject a, int t) {
		p_origin = a;
		this.time = t;
	}

	public void incrementTime() {
		time--; 
	}

	public int getTimeRemaining() {
		return time;
	}

	public boolean isTracking() {
		return Tracking;
	}

	public Vector2 getOrigin() {
		return origin;
	}

	public PhysXObject getPhysXOrigin() {
		return this.p_origin;
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

	public ArrayList<GameImage> updateBeam(Vector2 origin, Vector2 endPoint) {
		segments = new ArrayList<LaserSegment>();

		int numberOfSegments = calculateNumberOfRequiredSegments(origin, endPoint);

		Vector2 currentPosition = new Vector2(origin);
		Vector2 movementVector = calcutlateTrajectory(origin, endPoint);

		for(int i =0; i < numberOfSegments; ++i) {

			CircleCollider newCollider = new CircleCollider((float)segmentHeight);
			PhysXObject newPhysObj = new PhysXObject(new QuadrantID(), newCollider);
			newPhysObj.setPosition(currentPosition);

			try {
				LaserSegment newSegment = new LaserSegment(newPhysObj, "Laser segment.png", new CollisionData(), endPoint);
				segments.add(newSegment);
			} catch (SystemException e) {
				System.out.println("Lag");
			}

			currentPosition = currentPosition.add(movementVector.mult(new Vector2((float)segmentHeight, (float)segmentHeight)));

		}
		this.origin = origin;
		this.endPoint = endPoint;

		//		// Create an array list of the objects to be removed
		ArrayList<GameImage> gameImages = new ArrayList<GameImage>();
		for(int i=0; i < segments.size(); ++i) {
			gameImages.add(segments.get(i).getSprite());
		}

		return gameImages;
	}

	private Vector2 calcutlateTrajectory(Vector2 origin, Vector2 endPoint) {
		return origin.normalize(endPoint);
	}

	public void updatePositions() {
		for(LaserSegment segment: segments) {
			Vector2 pos = Camera.backendToFrontend(segment.getPhysObj().getPosition());
			segment.getSprite().setLocationRespectSize(pos.getX(), pos.getY());
			segment.rotateToPoint(this.endPoint);
		}
	}

	public ArrayList<GameImage> updateEndPoint(Vector2 newOrigin, Vector2 newEndPoint) {
		return updateBeam(newOrigin, newEndPoint);
	}

	public ArrayList<GameImage> getSprites (){

		ArrayList<GameImage> sprites = new ArrayList<GameImage>();
		for(LaserSegment segment: segments) {
			sprites.add(segment.getSprite());
		}
		return sprites;
	}

	public ArrayList<LaserSegment> getSegments(){
		return segments;
	}
}
