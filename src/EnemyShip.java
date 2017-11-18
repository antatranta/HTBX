import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnemyShip extends Ship implements ActionListener{
	protected static EnemyType type;
	private Vector2 target = new Vector2(500,500);
	
	public EnemyShip(PhysXObject physObj, int current_health, ShipStats stats) {
		super(physObj, current_health, stats, "PlayerShip-Small.png");
		// TODO Auto-generated constructor stub
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Detect target
		Move();
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
	
	public void Rotate2Player(Vector2 playerPos) {
		
	}
	@Override
	public void Move() {
		if(getCurrentHealth()>0) {
			//move to player
			AIUpdate(target);
			this.getSprite().setLocationRespectSize(this.getPhysObj().getPosition().getX(),this.getPhysObj().getPosition().getY());
		}//avoid asteroid method?
	}
	
}