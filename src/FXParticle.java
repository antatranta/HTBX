import acm.graphics.GOval;

public class FXParticle {
	GOval sprite;
	FXPattern pattern;
	FXType type; 
	Vector2 pos, dir;
	int life;
	
	public FXParticle(GOval particle, FXPattern pattern, FXType type, Vector2 position, Vector2 dir, int max) {
		this.sprite = particle;
		this.pattern = pattern;
		this.type = type;
		this.pos = position;
		this.dir = dir;
		this.life = max;
	}
	
	public FXParticle(FXParticle copy) {
		this(copy.getSprite(), copy.getPattern(), copy.getType(), copy.getPosition(), copy.getDir(), copy.getLife());
	}
	
	// ============================================================================
	
	public void move() {
		pos.add(dir);
		this.life -= 1;
	}
	
	// ============================================================================
	
	public GOval getSprite() {
		return this.sprite;
	}
	
	public FXPattern getPattern() {
		return this.pattern;
	}
	
	public FXType getType() {
		return this.type;
	}
	
	public Vector2 getPosition() {
		return this.pos;
	}
	
	public Vector2 getDir() {
		return this.dir;
	}
	
	public int getLife() {
		return this.life;
	}
	
	// ============================================================================
	
	public void setPosition(Vector2 pos) {
		this.pos = pos;
	}
	
	public void setDir(Vector2 dir) {
		this.dir = dir;
	}
	
}
