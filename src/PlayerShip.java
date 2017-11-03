public class PlayerShip extends Ship{
	private int current_shield;
	
	public PlayerShip(PhysXObject physObj, int current_health, ShipStats stats) {
		super(physObj, current_health, stats);
		this.current_shield = current_shield;
	}

	public void ChargeShield() {
		
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
	
}