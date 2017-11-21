public class QuadrantID {
	private int x,y,order;
	
	public QuadrantID() {
		this.x = -99;
		this.y = -99;
		this.order = -99;
	}
	public QuadrantID(int x, int y, int order) {
		this.x = x;
		this.y = y;
		this.order = order;
	}
	
	public QuadrantID(QuadrantID toCopy) {
		this.x = toCopy.getX();
		this.y = toCopy.getY();
		this.order = toCopy.Order();
	}
	
	public static QuadrantID unassigned() {
		return new QuadrantID (-404, -404, -404);
	}
	
	public int Order() {
		return order;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String toString() {
		return "(" + this.x + " , " + this.y + " , " + this.order + ")"; 
	}
}