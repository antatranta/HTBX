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
	
	public Vector2 add(Vector2 vectorB) {
		return new Vector2(this.x + vectorB.getX(), this.y + vectorB.getY());
	}
	
	public static Vector2 Zero() {
		return new Vector2(0,0);
	}
	
	public static Vector2 One() {
		return new Vector2(1,1);
	}
	
	public float distance(Vector2 from, Vector2 to) {
		return (float)Math.sqrt(Math.pow(to.getX() - from.getX(), 2) + Math.pow(to.getY() - from.getY(), 2));
	}
	
}