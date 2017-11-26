import acm.graphics.GImage;
import rotations.GameImage;

public class Entity implements Collision {
	protected PhysXObject physObj;
	protected GameImage sprite;
	protected GImage gif;
	protected String sprite_name;
	protected boolean isGif;
	
	public Entity(PhysXObject physObj, String sprite, CollisionData data) {
		this.physObj = physObj;
		this.physObj.setCollisionData(new CollisionData(data));
		sprite_name = sprite;
		this.sprite = new GameImage(sprite_name);
	}
	
	public Entity(PhysXObject physObj, String sprite, CollisionData data, boolean isGif) {
		this.physObj = physObj;
		this.physObj.setCollisionData(new CollisionData(data));
		this.sprite_name = sprite;
		if(isGif) {
			this.sprite = null;
			this.gif = new GImage(sprite);
		} else {
			this.sprite = new GameImage(sprite_name);
			this.gif = null;
		}
		
		this.isGif = isGif;
	}
	
	protected void createSprite(String file) {
		this.sprite = new GameImage(file);
		//System.out.println("Asteroid at: " + physObj.getPosition().getX() + ", " + physObj.getPosition().getY());
	}
	
	public String getSpriteName() {
		return this.sprite_name;
	}
	
	public void setSprite(String name) {
		this.sprite.setImage(name);
	}
	
	public boolean isSpriteGif() {
		return this.isGif;
	}
	
	public GameImage getSprite() {
		return this.sprite;
	}
	
	public GImage getGif() {
		return this.gif;
	}
	
	public PhysXObject getPhysObj() {
		return this.physObj;
	}
		
	protected void setCollisionData(CollisionData data) {
		this.physObj.setCollisionData(new CollisionData(data));
//		this.physObj.getCollisionData().setDamage(45);
	}
	
	public CollisionData getCollisionData() {
//		System.out.println(this.physObj.getCollisionData().toString());
		return physObj.getCollisionData();
	}

	public void onCollisionEvent(CollisionData data, Vector2 pos) {
		// TODO Auto-generated method stub
		
	}
}
