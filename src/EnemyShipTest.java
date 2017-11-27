import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class EnemyShipTest extends GraphicsApplication implements ActionListener {
	private EnemyShip enemy;
	private Timer gameTimer;
	private EnemyShip target1;
	private Vector2 enemyVector = new Vector2(100,100);
	private Vector2 targetVector = new Vector2(500,500);
	int count;
	
	public void init() {
		setSize(1000,1000);
		setBackground(Color.white);
		count = 0;
		QuadrantID id = new QuadrantID(0,0,0);

		PhysXObject shipPhysXObj =  new PhysXObject(id, enemyVector);
		enemy = new EnemyShip(shipPhysXObj, "Enemy_1.png",10, ShipStats.EnemyStats_01(), 1, EnemyType.LEVEL_1, 10);
		PhysXObject TargetPhysXObj =  new PhysXObject(id, targetVector);
		target1 = new EnemyShip(TargetPhysXObj, "Enemy_2.png",10,ShipStats.EnemyStats_01(), 1, EnemyType.LEVEL_2, 10);
		enemy.getSprite().setLocationRespectSize(enemyVector.getX(),enemyVector.getY());
		target1.getSprite().setLocationRespectSize(targetVector.getX(),targetVector.getY());
		add(enemy.getSprite());
		add(target1.getSprite());
	}
	
	public void run() {
		gameTimer = new Timer(15, this);
		gameTimer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Detect target
		Move();
		TargetMovementSimulate();
		//BulletManager.shoot(1, 15, BulletType.ENEMY_BULLET, 4, new PhysXObject(), target);
		
	}
	
	public void Move() {
		if(enemy.getCurrentHealth()>0) {
			//enemy.AIUpdate(enemy.getTarget());
			enemy.AIUpdate(target1.getPhysObj().getPosition());
			//enemy.getSprite().setLocationRespectSize(enemy.getPhysObj().getPosition().getX(),enemy.getPhysObj().getPosition().getY());
		}
	}
	
	public void TargetMovementSimulate() {
		count++;
		if(count%2==0) {
			target1.getPhysObj().setPosition(new Vector2(target1.getPhysObj().getPosition().getX()+1,target1.getPhysObj().getPosition().getY()-1));
			target1.getSprite().setLocationRespectSize(target1.getPhysObj().getPosition().getX(),target1.getPhysObj().getPosition().getY());
		}
	}
}