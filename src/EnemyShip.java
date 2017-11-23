import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnemyShip extends Ship implements ActionListener {
	protected static EnemyType type;
	private int orgin_degree = -90;
	private Vector2 target = new Vector2(500,500);
	private float interactionDistance = 500f;

	private float stoppingDistance = 200f;
	protected EnemyShipStats stats;
	
	private boolean inFlyby = false;
	private Vector2 flybyOffset = Vector2.Zero();

	private int shoot_cd;
	private int weapon_cd;
	
	protected Vector2 currentTarget = new Vector2(-99, -99);
	
	public EnemyShip(PhysXObject physObj, String sprite, int current_health, ShipStats stats, int aggression) {
		super(physObj, current_health, stats, sprite, CollisionType.enemyShip);
		this.stats = new EnemyShipStats(stats, aggression);
		System.out.println("Stats: " + stats);
		System.out.println("Aggression: " +aggression);
		System.out.println("Int. Dist: " + this.stats.getInteractionDistance());
		System.out.println("Stp. Dist: " + this.stats.getStoppingDistance());
		// TODO Auto-generated constructor stub
		shoot_cd = 60;
		weapon_cd = 90;
	}
	
//	public void setInteractionDistance(float interactionDistance) {
//		this.interactionDistance = interactionDistance;
//	}

	
//	public void setStoppingDistance(float stoppingDistance) {
//		this.stoppingDistance = stoppingDistance;
//	}
	
	public EnemyType getEnemyType() {
		return type;
	}
	
	public void AIUpdate(Vector2 playerPos) {
		
		
		// Is the player within range?
		if(PhysXLibrary.distance(this.physObj.getPosition(), playerPos) > stats.getInteractionDistance()) {
			currentTarget = playerPos;
			return;
		}
		
		// Are we too close to the player?
//		if (PhysXLibrary.distance(this.physObj.getPosition(), playerPos) < stats.getStoppingDistance()){
//			return;
//		}
		
		
//		target = playerPos;
		// || PhysXLibrary.distance(this.getPhysObj().getPosition(), currentTarget) < stats.getStoppingDistance()
		if(PhysXLibrary.distance(this.getPhysObj().getPosition(), currentTarget) < stats.getInteractionDistance() / 4
				|| currentTarget == new Vector2(-99, 99)) {
			currentTarget = PhysXLibrary.midpoint(playerPos, this.getPhysObj().getPosition());
			
//			currentTarget = playerPos;
		}
		if(PhysXLibrary.distance(playerPos, currentTarget) < this.stats.getStoppingDistance()) {
			return;
		}
		/*
		if (PhysXLibrary.distance(this.physObj.getPosition(), playerPos) < stats.getStoppingDistance()){
			currentTarget = currentTarget.add(new Vector2(LavaLamp.randomNumber(-500, 500), LavaLamp.randomNumber(-500, 500)));
		}*/
		
		float MovetoX = currentTarget.getX();
		float MovetoY = currentTarget.getY();
		/*
		if(inFlyby) {
			flybyOffset.setXY(LavaLamp.randomNumber(0, 10), LavaLamp.randomNumber(0, 10));
		} else {
//			flybyOffset = Vector2.Zero();
		}
		*/
		
		float thisX = this.getPhysObj().getPosition().getX();
		float thisY = this.getPhysObj().getPosition().getY();
		float differentX = MovetoX - thisX;
		float differentY = MovetoY - thisY;
		
		float angle = (float)Math.atan2(differentY,differentX);
		

		thisX+= (stats.getSpeedValue()*Math.cos(angle)*(1/PhysXLibrary.distance(playerPos, currentTarget)));
		thisY+= (stats.getSpeedValue()*Math.sin(angle)*((1/PhysXLibrary.distance(playerPos, currentTarget))));
		
		//Set back-end position
		this.getPhysObj().setPosition(new Vector2(thisX,thisY));
		moveExternalForce();
		//Set enemy image position
//		this.getSprite().setLocationRespectSize(this.getPhysObj().getPosition().getX(),this.getPhysObj().getPosition().getY());
		Rotate2Player(angle);
		
		
//		if (weapon_cd > 0) {
//			weapon_cd -= 1;
//		}
//		if (weapon_cd == 0) {
//			weapon_cd = shoot_cd;
			PhysXObject obj = new PhysXObject(physObj.getQUID(), physObj.getPosition(), new CircleCollider(4));
			shoot(1, 4, CollisionType.enemy_bullet, 5, obj, "RedCircle.png",target);
//		}
		
		
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
//		System.out.println("Dam: "+data.getDamage());
		if (data.getType() == CollisionType.playerShip) {
			external_force = PhysXLibrary.calculateCollisionForce(pos, this.physObj, KB_FORCE);
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