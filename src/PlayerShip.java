import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class PlayerShip extends Ship {

	public static int INV_CAP = 90;
	public static int REGEN_CAP = 240;
	private int current_shield;
	private int shield_regen = REGEN_CAP;
	private int i_frames = INV_CAP;
	
	public PlayerShip(PhysXObject physObj, int current_health, ShipStats stats, String sprite) {
		super(physObj, current_health, stats, sprite, CollisionType.playerShip);
		this.current_shield = 0;//stats.getShield_max();
	}

	public void chargeShield(int amount) {
		current_shield += amount;
		if (current_shield > getStats().getShieldMax()) {
			current_shield = getStats().getShieldMax();
		}
	}
	
	private void regenerateShield() {
		if (shield_regen == 0 && current_shield < getStats().getShieldMax()) {
			chargeShield(1);
		}
		else {
			if (shield_regen > 0) {
				shield_regen -= 1;
			}
		}
		
		//System.out.println("Shield regen cd at: " + shield_regen + " | Current shield: " + current_shield + " / " + getStats().getShieldMax());
	}
	
	@Override
	public void onCollisionEvent(CollisionData data, Vector2 pos) {

		if (i_frames == 0) {
			System.out.println("Got hit!");
			if (data.getType() == CollisionType.asteroid || data.getType() == CollisionType.enemyShip) {
				calculateCollisionForce(pos);
				
			}
		}
		
		takeDamage(data.getDamage());
	}
	
	@Override
	protected void handleCollision(CollisionData data) {
		// TODO Auto-generated method stub
		takeDamage(data.getDamage());
	}
	
	/*
	@Override
	public void sendCollisionMessage(CollisionData data) {
		int damage = data.getDamage();
		if (data.getType() == CollisionType.asteroid) {
			damage = getCurrentHealth();
		}
		takeDamage(damage);
	}
	*/
	
	private void processInvincibility() {
		if (i_frames > 0) {
			i_frames -= 1;
		}
	}
	
	@Override
	public void shoot() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void takeDamage(int amount) {
		if (i_frames > 0) {
			return;
		}
		i_frames = INV_CAP;
		shield_regen = REGEN_CAP;
		if (current_shield > 0) {
			current_shield -= amount;
		}
		else {
			current_health -= amount;
			if (current_health < 0) {
				i_frames = 0;
				current_health = 0;
			}
		}

	}

	public int getCurrentShield() {
		return current_shield;
	}

	public void setCurrentShield(int current_shield) {
		this.current_shield = current_shield;
	}
	
	public int getIFrames() {
		return i_frames;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		processInvincibility();
		regenerateShield();
		moveExternalForce();

		// TODO: Charges the shield. Use a timer to check for hits before charge, and when charging starts
	}

}
