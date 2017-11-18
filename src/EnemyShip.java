import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnemyShip extends Ship implements ActionListener{
	protected static EnemyType type;
	private static Vector2 target;
	
	public EnemyShip(PhysXObject physObj, int current_health, ShipStats stats) {
		super(physObj, current_health, stats, "PlayerShip-Small.png");
		target = new Vector2(0,0);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Detect target
		//BulletManager.shoot(1, 15, BulletType.ENEMY_BULLET, 4, new PhysXObject(), target);
		
	}
	public EnemyType getEnemyType() {
		return type;
	}
	
	public void AIUpdate(Vector2 playerPos) {
		float MovetoX = playerPos.getX();
		float MovetoY = playerPos.getY();
		System.out.println("MovetoX: "+ MovetoX+" MovetoY: "+MovetoY);
		float thisX = this.getPhysObj().getPosition().getX();
		float thisY = this.getPhysObj().getPosition().getY();
		float differentX = MovetoX - thisX;
		float differentY = MovetoY - thisY;
		System.out.println("DifferentX: "+ differentX+" DifferentY: "+differentY);
		float angle = (float)Math.atan2(differentY,differentX);
		System.out.println("Angle: "+angle);
		thisX+= this.getStats().getSpeed()*Math.cos(angle);
		thisY+= this.getStats().getSpeed()*Math.sin(angle);
		//thisX+= Math.cos(angle);
		//thisY+= Math.sin(angle);
		this.getPhysObj().setPosition(new Vector2(thisX,thisY));
	}

	@Override
	public void Move() {
		if(getCurrentHealth()>0) {
			//toward to player
			AIUpdate(target);
		}//avoid asteroid method?
	}
	
	public Vector2 getTarget() {
		return this.target;
	}
}