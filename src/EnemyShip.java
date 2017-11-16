import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnemyShip extends Ship implements ActionListener{
	protected static EnemyType type;
	
	public EnemyShip(PhysXObject physObj, int current_health, ShipStats stats) {
		super(physObj, current_health, stats);
		// TODO Auto-generated constructor stub
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Move();
		
	}
	public EnemyType getEnemyType() {
		return type;
	}
	
	public void AIUpdate() {
	
	}

	@Override
	public void Move() {
		if(getCurrentHealth()>0) {
			//toward to player
		}//avoid asteroid method?
	}

	
	//different bullet patterns method
	
	
}