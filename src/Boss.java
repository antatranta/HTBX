import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Boss extends EnemyShip implements ActionListener{
	public Boss(PhysXObject physObj, int current_health, ShipStats stats) {
		super(physObj, "Enemy_1.png", current_health, stats, 5);
		// TODO Auto-generated constructor stub
	}

	private int currentStage = 0;
	private static final int Stage2Health = 10;
	


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	public void increaseStage() {
		if(this.getCurrentHealth()<Stage2Health){
			currentStage++;
		}
	}
	
	public void deathAnimation() {
		
	}
	
	public boolean startFight() {
		
		return false;
	}
}