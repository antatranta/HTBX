import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

import acm.graphics.GImage;
import acm.program.GraphicsProgram;

public class PlayerShip extends Ship{
	private int current_shield;
	
	private int dx;
	private int dy;
	private int x;
	private int y;
	private GImage playership;
	
	public PlayerShip(PhysXObject physObj, int current_health, ShipStats stats,int dx, int dy, int x, int y) {
		super(physObj, current_health, stats);
		this.current_shield = current_shield;
		this.dy = dy;
		this.dx = dx;
		this.x = x;
		this.y = y;
		initShip();
	}
	private void initShip() {
		//ImageIcon ii = new ImageIcon("craft.png");
		//image = ii.getImage();
		x = 100;
		y = 100;
		//GImage playership = new GImage("triangle.png",x,y);
		//add(playership);

	}
	
	public void ChargeShield() {
		
	}
	
	public void takeDamage(int amount) {
		current_shield= current_shield - amount;
		if(current_shield<=0){
			current_shield = 0;
		}
	}

	public int getCurrent_shield() {
		return current_shield;
	}

	public void setCurrent_shield(int current_shield) {
		this.current_shield = current_shield;
	}
	
	public void move() {
		while(getCurrent_health()>0) {
	        x += dx;
	        y += dy;
		}
	}
	public int getDx() {
		return dx;
	}
	public void setDx(int dx) {
		this.dx = dx;
	}
	public int getDy() {
		return dy;
	}
	public void setDy(int dy) {
		this.dy = dy;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}