import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import acm.graphics.GOval;
import rotations.GameImage;

public class LaserTest extends GraphicsApplication implements ActionListener {
	//	private EnemyShip enemy;
	private Fencer enemy;
	private Timer gameTimer;
	private EnemyShip target1;
	LaserLine line;

	LaserLine laser_0;
	LaserLine laser_1;

	private Vector2 enemyVector = new Vector2(100,100);
	private Vector2 targetVector = new Vector2(500,500);

	Vector2[] trajectories;
	Vector2[] positions;

	GOval[] markers;

	Vector2 trajectory_0 = new Vector2(0,0);
	Vector2 trajectory_1 = new Vector2(0,0);
	Vector2 trajectory_2 = new Vector2(0,0);

	Vector2 position_0 = new Vector2(0,0);
	Vector2 position_1 = new Vector2(0,0);
	Vector2 position_2 = new Vector2(0,0);

	GOval pos_0;
	GOval pos_1;

	int shots = 1;
	int shotCount = 0;
	int laserDuration = 500;
	int laserDelay = 100;
	boolean hasTrajectories = false;
	int count;

	public void init() {

		setSize(1000,1000);
		setBackground(Color.white);
		count = 0;
		QuadrantID id = new QuadrantID(0,0,0);

		PhysXObject shipPhysXObj =  new PhysXObject(id, enemyVector);
		enemy = new Fencer(shipPhysXObj, "Enemy_1_S.png",10, ShipStats.EnemyStats_01(), 1, 20);

		PhysXObject TargetPhysXObj =  new PhysXObject(id, targetVector);
		target1 = new EnemyShip(TargetPhysXObj, "Enemy_2_S.png",10,ShipStats.EnemyStats_01(), 1, EnemyType.LEVEL_1, 20);

		enemy.getSprite().setLocationRespectSize(enemyVector.getX(),enemyVector.getY());
		target1.getSprite().setLocationRespectSize(targetVector.getX(),targetVector.getY());

		line = new LaserLine();
		//		line.createBeam(enemy.getPhysObj().getPosition(), target1.getPhysObj().getPosition());
		for(GameImage sprite : line.updateBeam(enemy.getPhysObj().getPosition(), target1.getPhysObj().getPosition())){
			add(sprite);
		}



		this.count = 0;
		this.shots = 1;
		this.shotCount = 0;
		this.laserDuration = 500;
		this.laserDelay = 100;
		this.hasTrajectories = false;

		this.trajectory_0 = new Vector2(0,0);
		this.trajectory_1 = new Vector2(0,0);
		this.trajectory_2 = new Vector2(0,0);

		this.position_0 = new Vector2(0,0);
		this.position_1 = new Vector2(0,0);
		this.position_2 = new Vector2(0,0);

		this.laser_0 = new LaserLine();

		add(enemy.getSprite());
		add(target1.getSprite());

		markers = new GOval[3];

		for(int i =0; i < 3; ++i) {
			markers[i] = new GOval(0,0,25,25);
			add(markers[i]);
		}
	}

	public void run() {
		gameTimer = new Timer(15, this);
		gameTimer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		enemy.AIUpdate(target1.getPhysObj().getPosition());
	}

	public void Move() {
		if(enemy.getCurrentHealth()>0) {
			//enemy.AIUpdate(enemy.getTarget());
			enemy.AIUpdate(target1.getPhysObj().getPosition());
			enemy.getSprite().setLocationRespectSize(enemy.getPhysObj().getPosition().getX(),enemy.getPhysObj().getPosition().getY());
		}
	}

	public void TargetMovementSimulate() {
		count++;
		if(count%2==0) {
			target1.getPhysObj().setPosition(new Vector2(target1.getPhysObj().getPosition().getX()+1,target1.getPhysObj().getPosition().getY()-1));
			target1.getSprite().setLocationRespectSize(target1.getPhysObj().getPosition().getX(),target1.getPhysObj().getPosition().getY());
		}
	}

	private void removeLasers() {
		// Remove old laser
		for(GameImage sprite : laser_0.getSprites()) {
			remove(sprite);
		}
	}

	private void calculateTrajectories(Vector2 pos) {

		positions = new Vector2[3];
		for(int i =0; i < 3; ++i) {
			positions[i] = new Vector2(enemy.getPhysObj().getPosition());
		}

		double theta_deg = Math.toDegrees(Math.atan2(pos.getY() - enemy.getPhysObj().getPosition().getY(), pos.getX() - enemy.getPhysObj().getPosition().getX()));
		int deg_spread = 100;
		theta_deg -= deg_spread;
		double unit_x = Math.cos(Math.toRadians(theta_deg));
		double unit_y = Math.sin(Math.toRadians(theta_deg));

		trajectories = new Vector2[3];
		for (int i = 0; i < 3; i++) {
			trajectories[i] = new Vector2((float)(enemy.getPhysObj().getPosition().getX() + unit_x), (float)(enemy.getPhysObj().getPosition().getY() + unit_y));
			theta_deg += 60;
			unit_x = Math.cos(Math.toRadians(theta_deg));
			unit_y = Math.sin(Math.toRadians(theta_deg));
		}
	}

	private void moveProjectiles() {

		// Find the new positions
		//		position_0 = position_0.minus(trajectory_0.mult(new Vector2(10,10)));
		//		position_1 = position_1.minus(trajectory_1.mult(new Vector2(10,10)));

		for(int i =0; i < positions.length; ++i) {
			positions[i] = positions[i].add(trajectories[i].mult(new Vector2(1f,1f)));
		}
	}

	public void AIUpdate(Vector2 playerPos) {
		// If the laser is supposed to be active
		if(count > laserDelay) { 

			if(shotCount > shots) {

				// Reset the counter
				shotCount = 0;

				// Reset the counter
				count = 0;

				return;
			}

			// If the laser is active, Draw
			if(count < laserDelay + laserDuration) {
				if(!hasTrajectories) {
					calculateTrajectories(playerPos);

					System.out.println("Calculated trajectories");
					System.out.println("		0: "+ this.trajectories[0].toString());
					System.out.println("		1: "+ this.trajectories[2].toString());

					System.out.println("		pos 0: "+this.positions[0].toString());
					System.out.println("		pos 1: "+this.positions[2].toString());
					hasTrajectories = true;
				}

				if(count %5 == 0) {
					moveProjectiles();

					System.out.println("Moved targets");
					System.out.println("		pos 0: "+this.positions[0].toString());
					System.out.println("		pos 1: "+this.positions[2].toString());
					System.out.println("     Dist: "+PhysXLibrary.distance(this.positions[0], this.positions[2]));
				}
			} else {


				hasTrajectories = false;

				// Reset the counter
				count = 0;

				// Increase the shot count
				shotCount++;
			}
		}

		count++;
	}
}