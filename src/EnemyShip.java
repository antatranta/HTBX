import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnemyShip extends Ship implements ActionListener{
	protected static EnemyType type;
	
	public EnemyShip(PhysXObject physObj, int current_health, ShipStats stats) {
		super(physObj, current_health, stats, "PlayerShip-Small.png");
		
		// TODO Auto-generated constructor stub
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Detect target
		Vector2 target = new Vector2(0, 0);
		//BulletManager.shoot(1, 15, BulletType.ENEMY_BULLET, 4, new PhysXObject(), target);
		
	}
	public EnemyType getEnemyType() {
		return type;
	}
	
	
	public void AI(Vector2 Player) {
		float MovetoX = Player.getX();
		float MovetoY = Player.getY();
		//dont pass player object, just pass the angle and stuffs of the player.
		float differentX = MovetoX;
	}

	@Override
	public void Move() {
		if(getCurrentHealth()>0) {
			//toward to player
		}//avoid asteroid method?
	}

	
	//different bullet patterns method
	
	
}