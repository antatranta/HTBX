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
		}
		
		// CHECK FOR DEAD ONES AFTER
		for (int i = 0; i < size; i++) {
			FXParticle fx = particles.get(i);
			if (fx.getLife() == 0) {
				size -= 1;
				particles.remove(fx);
			}
		}
	}
	
	public void makeDeflectSparks(FXType type, FXParticle particle) {
		double theta = Math.toDegrees(Math.atan2(0 - particle.getPosition().getY(), 0 - particle.getPosition().getX())) - 90;
		
		for (int i = 0; i < 10; i++) {
			FXParticle x = new FXParticle(particle.getSprite(), particle.getPattern(), particle.getType(), particle.getPosition(), particle.getDir(), particle.getLife());
			double angle_off = theta + LavaLamp.randomRange(-45, 45);
			float spd = LavaLamp.randomRange(3, 7);
			x.setDir(new Vector2((float)Math.cos(Math.toRadians(angle_off)) * spd, (float)Math.sin(Math.toRadians(angle_off)) * spd));
			particles.add(x);
		}
		System.out.println(particles.size());
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
