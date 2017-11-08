import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Boss extends EnemyShip implements ActionListener{
	private int currentStage = 0;
	private static final int Stage2Health = 10;
	
	public Boss(PhysXObject physObj, int current_health, ShipStats stats) {
		super(physObj, current_health, stats, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	public void increaseStage() {
		if(this.getCurrent_health()<Stage2Health){
			currentStage++;
		}
	}
	
	public void deathAnimation() {
		
	}
	
	public boolean startFight() {
		
		return false;
	}
}