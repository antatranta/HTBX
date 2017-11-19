import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnemyShip extends Ship implements ActionListener{
	protected static EnemyType type;
	private int orgin_degree = -90;
	private Vector2 target = new Vector2(500,500);
	private float interactionDistance = 500f;
	
	public EnemyShip(PhysXObject physObj, int current_health, ShipStats stats) {
		super(physObj, current_health, stats, "PlayerShip-Small.png", CollisionType.enemyShip);
		// TODO Auto-generated constructor stub
	}
	
	public void setInteractionDistance(float interactionDistance) {
		this.interactionDistance = interactionDistance;
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
		
		if(PhysXLibrary.distance(this.physObj.getPosition(), playerPos) > interactionDistance)
			return;
		
		float MovetoX = playerPos.getX();
		float MovetoY = playerPos.getY();
		
//		System.out.println("MovetoX: "+ MovetoX+" MovetoY: "+MovetoY);
		float thisX = this.getPhysObj().getPosition().getX();
		float thisY = this.getPhysObj().getPosition().getY();
		float differentX = MovetoX - thisX;
		float differentY = MovetoY - thisY;
		
//		System.out.println("DifferentX: "+ differentX+" DifferentY: "+differentY);
		float angle = (float)Math.atan2(differentY,differentX);
//		System.out.println("Angle: "+angle);
		
		thisX+= this.getStats().getSpeed()*Math.cos(angle);
		thisY+= this.getStats().getSpeed()*Math.sin(angle);
		
		//Set enemy backend position
		this.getPhysObj().setPosition(new Vector2(thisX,thisY));
		
		//Set enemy image position
//		this.getSprite().setLocationRespectSize(this.getPhysObj().getPosition().getX(),this.getPhysObj().getPosition().getY());
		Rotate2Player(angle);
	}
	
	public void Rotate2Player(float angle) {
		//Angle: 3.14 & -3.14 = 360'.     -1.57 = 90'.    1.57=270'
		int target_degree = (int) (angle * 57.32);
		int different = orgin_degree - target_degree;
		if(Math.abs(different)>180) {
			different+=different>0?-360:360;
		}
		if(different<0) {
			this.getSprite().rotate(1);
			orgin_degree++;
		}
		else if(different>0) {
			this.getSprite().rotate(-1);
			orgin_degree--;
		}
//		System.out.println("target_degree: "+target_degree); 
//		System.out.println("orgin_degree: "+orgin_degree); 
	}
	
	@Override
	public void Move() {
		if(getCurrentHealth()>0) {
			//move to player
			AIUpdate(target);
			//avoid asteroid method?

		}
	}
	
}