import acm.graphics.*;

public class StaticGObject {
	private Vector2 size;
	private PhysXObject physObj;
	private GOval[] objects;
	
	public StaticGObject(PhysXObject physObj) {
		this.physObj = physObj;
		this.size = Vector2.One();
	}
	
	public StaticGObject(Vector2 position, Vector2 size) {
		physObj = new PhysXObject();
		physObj.setPosition(position);
		this.size = size;
	}
	
	public PhysXObject getPhysObj() {
		return this.physObj;
	}
	
	public void setSize(Vector2 size) {
		this.size = size;
	}
	
	public void setup() {
		this.objects = new GOval[physObj.getColliders().length];
		for(int i =0; i<this.objects.length; ++i) {
			Vector2 center = physObj.getColliders()[i].getCenter().add(physObj.getPosition());
			float size = physObj.getColliders()[i].getRadius();
			this.objects[i] = new GOval(center.getX() - size, center.getY() - size, size * 2, size * 2);
		}
	}
	
	public GOval[] getObjects() {
		return this.objects;
	}
	
	public void setObjects(GOval[] objects) {
		this.objects = objects;
	}
}
