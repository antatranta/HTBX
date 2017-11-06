package rotations;
import java.awt.Polygon;

import acm.graphics.GPoint;
import acm.graphics.GPolygon;

public class GamePolygon extends GPolygon {

	public GamePolygon() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GamePolygon(double arg0, double arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public GamePolygon(GPoint[] arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	public Polygon getPolygon() {
		return super.getPolygon();
	}
	
	public void markAsComplete() {
		super.markAsComplete();
	}
}
