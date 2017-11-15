import java.util.ArrayList;

//TODO Implement bullet patterns that the enemy will use
public class BulletPattern {

	public void CounterClockwiseSpiralPattern(BulletManager enemyBullets) {
		ArrayList<Bullet> enemyBullet = enemyBullets.getBullets();
		float angle = 0;
		float x_angle;
		float y_angle;
		
		for(Bullet spiral:enemyBullet) {
			x_angle = (float)Math.cos(Math.toRadians(angle));
			y_angle = (float)Math.sin(Math.toRadians(angle));
			
			spiral.setBulletDXDY(x_angle, y_angle);
			angle += 1;
		}
		
		enemyBullets.setBullets(enemyBullet);
	}
	
	public void ClockwiseSpiralPattern(BulletManager enemyBullets) {
		ArrayList<Bullet> enemyBullet = enemyBullets.getBullets();
		float angle = 0;
		float x_angle;
		float y_angle;
		
		for(Bullet spiral:enemyBullet) {
			x_angle = (float)Math.cos(Math.toRadians(angle));
			y_angle = (float)Math.sin(Math.toRadians(angle));
			
			spiral.setBulletDXDY(x_angle, y_angle);
			angle -= 1;
		}
		
		enemyBullets.setBullets(enemyBullet);
	}
}
