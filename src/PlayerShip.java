import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerShip extends Ship implements ActionListener{
	private Ship ship;
	private int current_shield;
	
	public PlayerShip(PhysXObject physObj, int current_health, ShipStats stats) {
		super(physObj, current_health, stats);
		this.current_shield = current_shield;
	}

	public void ChargeShield() {
		current_shield += 1;
	}
	
	@Override
	public void sendCollisionMessage(CollisionData data) {
		int damage = data.getDamage();
		if (data.getType() == CollisionType.asteroid)
			damage = getCurrent_health();
		takeDamage(damage);
		
	}
	
	@Override
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO: Charges the shield. Use a timer to check for hits before charge, and when charging starts
		
	}
	
}