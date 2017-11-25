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
	
	private void createBeam() {
		
	}
	
	private void updatePositions() {
		for(LaserSegment segment: segments) {
//			segment.getSprite().setLocationRespectSize(x, y);
		}
	}
}
