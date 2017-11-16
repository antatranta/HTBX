//TODO Implement bullet patterns that the enemy will use
public class BulletPattern {

	public void clockwiseSpiralPattern(BulletManager enemyBullets) {
		float angle = 0;
		float x_angle;
		float y_angle;
		
		for(Bullet spiral:enemyBullets.getBullets()) {
			x_angle = (float)Math.cos(Math.toRadians(angle));
			y_angle = (float)Math.sin(Math.toRadians(angle));
			
			spiral.setBulletDXDY(x_angle, y_angle);
			angle += 10;
		}
	}
	
	public void counterClockwiseSpiralPattern(BulletManager enemyBullets) {
		float angle = 0;
		float x_angle;
		float y_angle;
		
		for(Bullet spiral:enemyBullets.getBullets()) {
			x_angle = (float)Math.cos(Math.toRadians(angle));
			y_angle = (float)Math.sin(Math.toRadians(angle));
			
			spiral.setBulletDXDY(x_angle, y_angle);
			angle -= 10;
		}
	}
	
	public void zigZagBottom(BulletManager enemyBullets) {
		
	}
}
