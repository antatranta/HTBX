
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GOval;

public class BlinkerTest extends GraphicsApplication implements ActionListener {
	private Blinker blinker;
	private Blinker target;
	private Timer gameTimer;

	private GOval colorTest;
	private float colorCount;

	private Vector2 targetVector = new Vector2(500,500);
	int speed = 10;
	int shield_max = 10;
	int health_max = 10;
	int damage	   = 10;
	int aggression = 0;
	int fireRate   = 100;
	int count = 0;


	public void run() {
		LavaLamp.setup(200);
		gameTimer = new Timer(15, this);
		gameTimer.start();
	}

	public void init() {
		setSize(1000,1000);
		setBackground(Color.white);

		targetVector.setXY(1000/2, 1000/2);
		count = 0;
		colorCount = 0;
		QuadrantID id = new QuadrantID(0,0,0);

		colorTest = new GOval(250, 250, 50, 50);
		add(colorTest);
		colorTest.setFilled(true);

		// Create the Blinker object
		PhysXObject shipPhysXObj =  new PhysXObject(id, targetVector.minus(Vector2.One().mult(new Vector2(2,2))));
		blinker = new Blinker(shipPhysXObj, "Blinker.png",10, new EnemyShipStats(speed, shield_max, health_max, damage, aggression, fireRate), 1, 15);
		add(blinker.getCharger());
		add(blinker.getSprite());

		// Create the Target object
		PhysXObject targetPhysXObj = new PhysXObject(id, targetVector);
		target = new Blinker(targetPhysXObj, "Enemy_1_S.png",10, ShipStats.EnemyStats_01(), 1, 15);
		add(target.getSprite());

		drawEntity(target);	
	}

	private void drawEntity(Entity obj) {
		Vector2 frontEndPos = Camera.backendToFrontend(obj.getPhysObj().getPosition());
		obj.getSprite().setLocationRespectSize(frontEndPos.getX(), frontEndPos.getY());
	}

	private void drawBlinker(Blinker obj) {
		drawEntity(obj);
		Vector2 frontEndPos = Camera.backendToFrontend(obj.getPhysObj().getPosition());
		obj.getCharger().setLocation(frontEndPos.getX(), frontEndPos.getY());
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Detect target
		Move();
		TargetMovementSimulate();
		colorCount += .001f;
		colorTest.setFillColor(PaintToolbox.blend(Color.CYAN, Color.GREEN, colorCount));
	}

	public void Move() {
		if(blinker.getCurrentHealth()>0) {
			blinker.AIUpdate(target.getPhysObj().getPosition());
		}

		drawEntity(blinker);
		drawEntity(target);
	}

	public void TargetMovementSimulate() {
		count++;
		if(count%2==0) {
			target.getPhysObj().setPosition(target.getPhysObj().getPosition().add(Vector2.One().mult(new Vector2(2,2))));
		}
	}
}