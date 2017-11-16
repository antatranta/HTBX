import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnemyShip extends Ship implements ActionListener{
	public EnemyShip(PhysXObject physObj, int current_health, ShipStats stats) {
		super(physObj, current_health, stats, "PlayerShip-Small.png");
		// TODO Auto-generated constructor stub
		
	}

	//private Ship ship;
	protected static EnemyType type;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public EnemyType getEnemyType() {
		return type;
	}
	
	public void AIUpdate() {
		//avoid asteroid method?
	}
	/*
	public void DifferentEnemies(EnemyType type) {
		
	}*/
	
}