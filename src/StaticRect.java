import acm.graphics.*;
public class StaticRect {
	private Vector2 size;
	private PhysXObject physObj;
	private GRect rect;

	public StaticRect(Vector2 position, Vector2 size) {
		physObj = new PhysXObject();
		physObj.setPosition(position);
		this.size = size;
		rect = new GRect(this.size.getX(), this.size.getY());
	}

	public PhysXObject getPhysObj() {
		return this.physObj;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}

	public GRect getRect() {
		return this.rect;
	}
}
