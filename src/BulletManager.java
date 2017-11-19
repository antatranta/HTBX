import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import acm.graphics.*;

public class BulletManager {
	private ArrayList<Bullet> bullets;
	private ArrayList<GOval> deadBullets;
	
	private BulletPattern pattern;
	
	public BulletManager() {
		this.bullets = new ArrayList<Bullet>();
		pattern = new BulletPattern();
	}
	
	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}
	
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	public GOval onShootEvent(int dmg, int spd, BulletType bullet, float time, PhysXObject obj, Vector2 movementVector) {
		Bullet shot = new Bullet(dmg, spd, bullet, time, obj, movementVector);
		this.bullets.add(shot);
		//this.gOvals.add(shot.getSprite());
		
		System.out.println(bullets.size() + " <- Bullets");
		
		return shot.getSprite();
	}
	
	public ArrayList<GOval> getDeadBullets(){
		return this.deadBullets;
	}
	
	public void moveBullets() {
		this.deadBullets = new ArrayList<GOval>();
		for(int i=0; i < this.bullets.size(); i++) {
			this.bullets.get(i).move();
			Vector2 pos = Camera.backendToFrontend(this.bullets.get(i).getPhysObj().getPosition());

			this.bullets.get(i).getSprite().setLocation(pos.getX(), pos.getY());

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
}