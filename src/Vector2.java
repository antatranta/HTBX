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
	
	public Vector2 minus(Vector2 vectorB) {		
		return new Vector2(this.x - vectorB.getX(), this.y - vectorB.getY());
	}
	
	public Vector2 div(Vector2 vectorB) {
		return new Vector2(this.x / vectorB.getX(), this.y / vectorB.getY());
	}
	
	public Vector2 mult(Vector2 vectorB) {
		return new Vector2(this.x * vectorB.getX(), this.y * vectorB.getY());
	}
	
	public static Vector2 Zero() {
		return new Vector2(0,0);
	}
	
	public static Vector2 One() {
		return new Vector2(1,1);
	}
	
	/*public static Vector2 normalize(Vector2 vectorB) {
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
		float length = (float)Math.sqrt((x * x) + (y * y));
		
		if(length > 0) {
			x /= length;
			y /= length;
		}
		
		return new Vector2(x, y);
	}*/
	
	// Better normalize that actually WORKS - Anthony
	public Vector2 normalize(Vector2 vectorB) {
		float magnitude = (float)Math.sqrt(Math.pow(vectorB.getX() - this.x, 2) + Math.pow(vectorB.getY() - this.y, 2));
		float aX = vectorB.getX() - this.x;
		float aY = vectorB.getY() - this.y;
		
		if(magnitude > 0) {
			aX /= magnitude;
			aY /= magnitude;
		}
		
		return new Vector2(aX, aY);
	}
}