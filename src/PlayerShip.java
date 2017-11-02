public class PlayerShip extends Ship{
	private int current_shield;
	
	public PlayerShip(PhysXObject physObj, int current_health, ShipStats stats, int x, int y) {
		super(physObj, current_health, stats, x, y);
		this.current_shield = current_shield;
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
	
}