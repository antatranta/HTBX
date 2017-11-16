import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import acm.graphics.*;

public class BulletManager {
	private ArrayList<Bullet> bullets;
	
	private ArrayList<GOval> deadBullets;
	private ArrayList<GOval> gOvals;
	
	public BulletManager() {
		this.bullets = new ArrayList<Bullet>();
		this.deadBullets = new ArrayList<GOval>();
		this.gOvals = new ArrayList<GOval>();
	}
	
	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}
	
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	public GOval onShootEvent(int dmg, int spd, BulletType bullet, float time, PhysXObject obj, Vector2 movementVector) {
		this.bullets.add(new Bullet(dmg, spd, bullet, time, obj, movementVector));
		this.gOvals.add(new GOval(obj.getPosition().getX(), obj.getPosition().getY(), 10, 10));
		
		System.out.println(gOvals.size() + " <- Bullets");
		
		return this.gOvals.get(gOvals.size() -1);
	}
	
	public ArrayList<GOval> getDeadBullets(){
		return this.deadBullets;
	}
	
	public void moveBullets() {
		this.deadBullets = new ArrayList<GOval>();
		for(int i=0; i < this.bullets.size(); i++) {
			this.bullets.get(i).move();
			Vector2 pos = Camera.backendToFrontend(this.bullets.get(i).getPhysObj().getPosition());
			this.gOvals.get(i).setLocation(pos.getX() - (gOvals.get(i).getWidth() / 2), pos.getY() - (gOvals.get(i).getHeight() / 2));
			
			if(this.bullets.get(i).getSteps() > (int)this.bullets.get(i).getBulletDuration() * 100) {
				this.deadBullets.add(gOvals.get(i));
				this.bullets.remove(bullets.get(i));
				this.gOvals.remove(gOvals.get(i));
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
}