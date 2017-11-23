import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import acm.graphics.*;
import rotations.GameImage;

public class BulletManager implements ShipTriggers {
	private ArrayList<Bullet> bullets;
	private ArrayList<GameImage> deadBullets;
	
	private BulletPattern pattern;
	
	public BulletManager() {
		this.bullets = new ArrayList<Bullet>();
		this.deadBullets = new ArrayList<GameImage>();
		pattern = new BulletPattern();
	}
	
	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}
	
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	public ArrayList<PhysXObject> getPhysXObjects(){
		ArrayList<PhysXObject> objs = new ArrayList<PhysXObject>();
		for(Bullet bullet : bullets) {
			objs.add(bullet.getPhysObj());
		}
		return objs;
	}
	
	public GameImage onShootEvent(int dmg, int spd, CollisionType collision, float time, PhysXObject obj, String sprite, Vector2 movementVector) {
		// Variable Verification
//		if (dmg >= 0 && spd >= 0 && time > 0 && obj != null && movementVector != null) {
			Bullet shot = new Bullet(dmg, spd, collision, time, obj, sprite, movementVector);
			this.bullets.add(shot);
			return shot.getSprite();
//		}		
//		return null;
	}
	
	public ArrayList<GameImage> getDeadBullets(){
		return this.deadBullets;
	}
	
	public void moveBullets() {
		this.deadBullets = new ArrayList<GameImage>();
		for(int i=0; i < this.bullets.size(); i++) {
			Bullet current = this.bullets.get(i);
			current.move();
			Vector2 pos = Camera.backendToFrontend(current.getPhysObj().getPosition());

			current.getSprite().setLocation(pos.getX() - (current.getSprite().getWidth() / 2), pos.getY() - (current.getSprite().getHeight() / 2));

			if(this.bullets.get(i).getSteps() > (int)this.bullets.get(i).getBulletDuration() * 100 || this.bullets.get(i).checkIfDead()) {
				this.deadBullets.add(bullets.get(i).getSprite());
				this.bullets.remove(bullets.get(i));
			}
		}
		
		/*
		for(int i=0; i< bullets.size(); ++i) {
			Timer delete = new Timer((int)bullets.get(i).getBulletDuration() * 1000, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					deadBullets.add(bullets.get(i));
					
					bullets.remove(bullets.get(i));
				}
			});
			delete.setRepeats(false);
			delete.start();
		}
		*/
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
			onShootEvent(data.getDamage(), data.getSpeed(), data.getCollisionType(), data.getTime(), data.getPhysXObject(), data.getSprite(), data.getMovementVector());
	}
	
	@Override
	public void onShipDeath(Vector2 position) {
		// Sun burst emitter
	}

	@Override
	public int isAreaSafe(Vector2 pos, float range) {
		// TODO Auto-generated method stub
		return -404;
	}
}