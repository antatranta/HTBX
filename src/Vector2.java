public class Vector2 {
	private float x,y;
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2(Vector2 toCopy) {
		this.x = toCopy.getX();
		this.y = toCopy.getY();
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "(" + this.x + " , " + this.y + ")";
	}
	
	public Vector2 add(Vector2 vectorB) {
		return new Vector2(this.x + vectorB.getX(), this.y + vectorB.getY());
	}
	
	public static Vector2 Zero() {
		return new Vector2(0,0);
	}
	
	public static Vector2 One() {
		return new Vector2(1,1);
	}	
	
	public static Vector2 normalize(Vector2 vectorB) {
		float bX = vectorB.getX();
		float bY = vectorB.getY();
		double length = Math.sqrt((bX * bX) + (bY * bY));
		
		if(length > 0) {
			bX /= length;
			bY /= length;
		}
		
		return new Vector2(bX, bY);
	}
	
	public Vector2 normalize() {
		double length = Math.sqrt((x * x) + (y * y));
		
		if(length > 0) {
			x /= length;
			y /= length;
		}
		
		return new Vector2(x, y);
	}
	
	public Vector2 minus(Vector2 vectorB) {		
		return new Vector2(this.x - vectorB.getX(), this.y - vectorB.getY());
	}
}