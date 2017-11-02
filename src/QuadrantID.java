public class QuadrantID {
	private int x,y,order;
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
	
	public int Order() {
		return order;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}