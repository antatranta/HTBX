import java.awt.Color;

import acm.graphics.GOval;

public class FXParticle {
	protected GOval sprite;
	FXPattern pattern;
	FXType type; 
	Vector2 pos, dir;
	protected int life;
	protected int max;

	private Object[] args;

	public FXParticle(GOval particle, FXPattern pattern, FXType type, Vector2 position, Vector2 dir, int max) {
		this.sprite = particle;
		this.pattern = pattern;
		this.type = type;
		this.pos = position;
		this.dir = dir;
		this.life = max;
		this.max = max;
	}

	public FXParticle(FXParticle copy) {
		GOval spr = new GOval(0,0,0,0);
		spr.setSize(copy.getSprite().getSize());
		spr.setFilled(true);
		spr.setFillColor(copy.getSprite().getFillColor());
		spr.setColor(copy.getSprite().getColor());
		this.sprite = spr;
		this.pattern = copy.getPattern();
		this.type = copy.getType();
		this.pos = copy.getPosition();
		this.dir = copy.getDir();
		this.life = copy.getLife();
		this.max = life;

	}

	// ============================================================================

	public void move() {
		pos = pos.add(dir);
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


	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	// ============================================================================

	public void setPosition(Vector2 pos) {
		this.pos = pos;
	}

	public void setDir(Vector2 dir) {
		this.dir = dir;
	}

	public void shrink() {
		double ratio = 0.85;
		this.sprite.setSize(this.sprite.getWidth() * ratio, this.sprite.getHeight() * ratio);
	}

	public void colorChange() {
		Color startValue,endValue;
		try {
			startValue = (Color)args[2];
			endValue = (Color)args[3];
		} catch (NullPointerException e) {
			System.out.println("[WARN] You need to assign values.");
			return;
		}
		float blending = (float)this.life/(float)this.max;
		this.sprite.setFillColor(PaintToolbox.blendAlpha(startValue, endValue, blending));
	}
}
