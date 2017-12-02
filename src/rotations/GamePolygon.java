package rotations;
import java.awt.Polygon;

import acm.graphics.GPoint;
import acm.graphics.GPolygon;

public class GamePolygon extends GPolygon {

	public GamePolygon() {
		super();
	}

	public GamePolygon(double arg0, double arg1) {
		super(arg0, arg1);
	}

	public GamePolygon(GPoint[] arg0) {
		super(arg0);
	}
	
	public Polygon getPolygon() {
		return super.getPolygon();
	}
	
	public void markAsComplete() {
		super.markAsComplete();
	}
}
