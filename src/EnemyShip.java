import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnemyShip extends Ship implements ActionListener {
	protected static EnemyType type;
	private int orgin_degree = -90;
	private Vector2 target = new Vector2(500,500);
	private float interactionDistance = 500f;

	private float stoppingDistance = 100f;
	private EnemyShipStats stats;
	
	private boolean inFlyby = false;
	private Vector2 flybyOffset = Vector2.Zero();

	private int shoot_cd;
	private int weapon_cd;
	
	public EnemyShip(PhysXObject physObj, String sprite, int current_health, ShipStats stats, int aggression) {
		super(physObj, current_health, stats, sprite, CollisionType.enemyShip);
		this.stats = new EnemyShipStats(stats, aggression);
		// TODO Auto-generated constructor stub
		shoot_cd = 60;
		weapon_cd = 90;
	}
	
	public void setInteractionDistance(float interactionDistance) {
		this.interactionDistance = interactionDistance;
	}

	
	public void setStoppingDistance(float stoppingDistance) {
		this.stoppingDistance = stoppingDistance;
	}
	
	public EnemyType getEnemyType() {
		return type;
	}
	
	public void AIUpdate(Vector2 playerPos) {
		if (current_health <= 0) {
			return;
		}
		
		if(PhysXLibrary.distance(this.physObj.getPosition(), playerPos) > stats.getInteractionDistance())
			return;
		
		if (PhysXLibrary.distance(this.physObj.getPosition(), playerPos) < stats.getStoppingDistance()){
			inFlyby = true;
		} else if (PhysXLibrary.distance(this.physObj.getPosition(), playerPos) > stats.getStoppingDistance() *1.5f ) {
			inFlyby = false;
		}
		
		float MovetoX = playerPos.getX();
		float MovetoY = playerPos.getY();
		target = playerPos;
		
		if(inFlyby) {
			flybyOffset.setXY(LavaLamp.randomNumber(0, 10), LavaLamp.randomNumber(0, 10));
		} else {
//			flybyOffset = Vector2.Zero();
		}
		
		float thisX = this.getPhysObj().getPosition().getX() + flybyOffset.getX();
		float thisY = this.getPhysObj().getPosition().getY() + flybyOffset.getY();
		float differentX = MovetoX - thisX;
		float differentY = MovetoY - thisY;
		
		float angle = (float)Math.atan2(differentY,differentX);
		

		thisX+= (stats.getSpeedValue()*Math.cos(angle));
		thisY+= (stats.getSpeedValue()*Math.sin(angle));
		
		//Set back-end position
		this.getPhysObj().setPosition(new Vector2(thisX,thisY));
		moveExternalForce();
		//Set enemy image position
//		this.getSprite().setLocationRespectSize(this.getPhysObj().getPosition().getX(),this.getPhysObj().getPosition().getY());
		Rotate2Player(angle);
		
		if (weapon_cd > 0) {
			weapon_cd -= 1;
		}
		if (weapon_cd == 0) {
			weapon_cd = shoot_cd;
			PhysXObject obj = new PhysXObject(physObj.getQUID(), physObj.getPosition(), new CircleCollider(4));
			shoot(1, 4, BulletType.ENEMY_BULLET,(60 * 5), obj, "Cursor.png",target);
		}
		
	}
	
	public void Rotate2Player(float angle) {
		//Angle: 3.14 & -3.14 = 360'.     -1.57 = 90'.    1.57=270'
		int target_degree = (int) (angle * 57.32);
		
//		if(isClose < 0) {
//			target_degree += stats.getTurningSpeed();
//		}
		int different = orgin_degree - target_degree;
		if(Math.abs(different)>180) {
			different+=different>0?-360:360;
		}
		if(different<0) {
			this.getSprite().rotate(-(int)stats.getTurningSpeed());
			adjustAngle(stats.getTurningSpeed());
			orgin_degree++;
		}
		else if(different>0) {
			adjustAngle(-stats.getTurningSpeed());
			this.getSprite().rotate(-(int)stats.getTurningSpeed());
			orgin_degree--;
		}
//		System.out.println("target_degree: "+target_degree); 
//		System.out.println("orgin_degree: "+orgin_degree); 
	}
	
	@Override
	public void onCollisionEvent(CollisionData data, Vector2 pos) {

		if (data.getType() == CollisionType.playerShip) {
			calculateCollisionForce(pos);
		}
		if (data.getType() == CollisionType.asteroid ||
				data.getType() == CollisionType.player_bullet) {
			takeDamage(data.getDamage());
		}
	}
	
	
	// WARNING: THIS IS NEVER ACCESSED IN THE GAME LOOP (GAMEPANE CALLS AIUPDATE)
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Detect target
		Move();
		//BulletManager.shoot(1, 15, BulletType.ENEMY_BULLET, 4, new PhysXObject(), target);
		
	}
	
	// WARNING: THIS IS NEVER ACCESSED IN THE GAME LOOP (GAMEPANE CALLS AIUPDATE)
	@Override
	public void Move() {
		if(getCurrentHealth() > 0) {
			//move to player
			AIUpdate(target);
			//avoid asteroid method?
		}
	}
	
}