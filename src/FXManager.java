import java.awt.Color;
import java.util.ArrayList;

import acm.graphics.GOval;

public class FXManager {
	ArrayList<FXParticle> particles;
	GamePane pane;
	
	public FXManager() {
		this.particles = new ArrayList<FXParticle>();
		this.pane = null;
		System.out.println("[Warning]: FX Manager requires GamePane reference to be set after intialization");
	}
	
	public void moveParticles() {
		int size = particles.size();
		for (int i = 0; i < size; i++) {
			FXParticle fx = particles.get(i);
			fx.move();
			if (fx.getLife() == 0) {
				particles.remove(fx);
			}
		}
	}
	
	public void makeSparks(FXType type, FXParticle particle) {
		for (int i = 0; i < 5; i++) {
			FXParticle x = new FXParticle(particle);
			particles.add(x);
			
		}
	}
	
	public void setReferences(GamePane pane) {
		this.pane = pane;
	}
	
	public ArrayList<FXParticle> getParticles() {
		return this.particles;
	}
	
	public static FXParticle redParticle() {
		GOval sp = new GOval(5,5,5,5);
		sp.setFillColor(Color.RED);
		sp.setFilled(true);
		sp.setColor(Color.RED);
		FXParticle fx_p = new FXParticle(sp, FXPattern.SPARKS_DEFLECT, FXType.SHRINK, Vector2.Zero(), Vector2.Zero(), 10);
		return fx_p;
	}
}
