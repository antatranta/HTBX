import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import acm.graphics.*;
import rotations.GameImage;

public class BulletManager implements ShipTriggers {
	private ArrayList<Bullet> bullets;
	private ArrayList<GameImage> deadBullets;
	private GameConsole console_ref;

	private BulletPattern pattern;

	public BulletManager() {
		this.bullets = new ArrayList<Bullet>();
		this.deadBullets = new ArrayList<GameImage>();
		pattern = new BulletPattern();
		this.console_ref = null;
	}

	public BulletManager(GameConsole console) {
		this.bullets = new ArrayList<Bullet>();
		this.deadBullets = new ArrayList<GameImage>();
		pattern = new BulletPattern();
		this.console_ref = console;
	}

	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}

	public ArrayList<Bullet> getBullets() {
		ArrayList<Bullet> r_bullets = new ArrayList<Bullet>();
		r_bullets.addAll(bullets);
		return r_bullets;
	}

	public ArrayList<PhysXObject> getPhysXObjects(){
		ArrayList<PhysXObject> objs = new ArrayList<PhysXObject>();
		for(Bullet bullet : bullets) {
			objs.add(bullet.getPhysObj());
		}
		return objs;
	}

	public GameImage onShootEvent(int dmg, float spd, BulletType type, CollisionType collision, float time, PhysXObject obj, String sprite, Vector2 movementVector, FXParticle fx) {
		Bullet shot = new Bullet(dmg, spd, type, collision, time, obj, sprite, movementVector);
		shot.giveFX(fx);
		this.bullets.add(shot);
		return shot.getSprite();
	}

	public ArrayList<GameImage> getDeadBullets(){
		return this.deadBullets;
	}

	public void moveBullets() {
		this.deadBullets = new ArrayList<GameImage>();
		for(int i=0; i < this.bullets.size(); i++) {
			Bullet current = this.bullets.get(i);
			current.move();

			// Take care of dead bullets: They die prematurely or reach the end of their travel time
			if(this.bullets.get(i).getSteps() > (int)this.bullets.get(i).getBulletDuration() * 100 || this.bullets.get(i).checkIfDead()) {
				this.deadBullets.add(bullets.get(i).getSprite());
				this.bullets.remove(bullets.get(i));

				// Check if the bullet has a particle effect or not
				if (this.console_ref != null && current.getFX() != null && current.checkIfDead()) {
					current.getFX().setPosition(current.getPhysObj().getPosition());
					current.getFX().setDir(new Vector2(current.getBulletDX(), current.getBulletDY()));
					this.console_ref.programRequest_makeFX(current.getFX().getPattern(), current.getFX().getType(), current.getFX());
				}
			}
		}
	}

	public void moveClockwiseSpiralPattern() {
		pattern.clockwiseSpiralPattern(this);
		moveBullets();
	}

	public void moveCounterSpiralPattern() {
		pattern.counterClockwiseSpiralPattern(this);
		moveBullets();
	}

	public void moveSunBurstBottom() {
		pattern.sunBurstBottom(this);
		moveBullets();
	}

	public void moveSunBurstTop() {
		pattern.sunBurstTop(this);
		moveBullets();
	}

	public void moveZigZagBottom() {
		pattern.zigZagBottom(this);
		moveBullets();
	}

	public void moveZigZagTop() {
		pattern.zigZagTop(this);
		moveBullets();
	}

	@Override
	public void onShipFire(BulletFireEventData data, CollisionType collision) {
		if(data != null)
			onShootEvent(data.getDamage(), data.getSpeed(), data.getBulletType(), data.getCollisionType(), data.getTime(), data.getPhysXObject(), data.getSprite(), data.getMovementVector(), data.getFXParticle());
	}

	@Override
	public int isAreaSafe(Vector2 pos, float range) {
		return -404;
	}

	@Override
	public int identify() {
		return 20;
	}
}