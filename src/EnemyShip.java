import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnemyShip extends Ship implements ActionListener{
	//private Ship ship;
	protected static EnemyType type;
	
	public EnemyShip(PhysXObject physObj, int current_health, ShipStats stats, EnemyType ty) {
		super(physObj, current_health, stats,0);
		EnemyShip.type = ty;

		// TODO Auto-generated constructor stub
	}

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