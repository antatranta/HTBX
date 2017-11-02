public class PlayerShip{
	
	private int current_shield;
	
	public PlayerShip(int current_shield) {
		super();
		this.current_shield = current_shield;
	}

	public void ChargeShield() {
		
	}
	
	public void takeDamage(int amount) {
		current_shield= current_shield - amount;
		//if(current_shield<=0){ current_shield = 0;}
	}

	public int getCurrent_shield() {
		return current_shield;
	}

	public void setCurrent_shield(int current_shield) {
		this.current_shield = current_shield;
	}
}