//TODO Implement bullet patterns that the enemy will use
public class BulletPattern {
	private float angle;
	private float x_angle;
	private float y_angle;
	
	public void clockwiseSpiralPattern(BulletManager enemyBullets) {
		angle = 0;
		x_angle = 0;
		y_angle = 0;
		
		for(Bullet spiral:enemyBullets.getBullets()) {
			switch(spiral.getBulletType()){
				case ENEMY_BULLET:
					x_angle = (float)Math.cos(Math.toRadians(angle));
					y_angle = (float)Math.sin(Math.toRadians(angle));
					
					spiral.setBulletDXDY(x_angle, y_angle);
					angle += 20;
					break;
					
				default:
					break;
			}
		}
	}
	
	public void counterClockwiseSpiralPattern(BulletManager enemyBullets) {
		angle = 0;
		x_angle = 0;
		y_angle = 0;
		
		for(Bullet spiral:enemyBullets.getBullets()) {
			switch(spiral.getBulletType()) {
				case ENEMY_BULLET:
					x_angle = (float)Math.cos(Math.toRadians(angle));
					y_angle = (float)Math.sin(Math.toRadians(angle));
				
					spiral.setBulletDXDY(x_angle, y_angle);
					angle -= 20;
					break;
					
				default:
					break;
			}
		}
	}
	
	public void zigZagBottom(BulletManager enemyBullets) {
		angle = 0;
		x_angle = 0;
		y_angle = 0;
		float i = 20;
		
		for(Bullet zigZag:enemyBullets.getBullets()) {
			switch(zigZag.getBulletType()) {
				case ENEMY_BULLET:
					x_angle = (float)Math.cos(Math.toRadians(angle));
					y_angle = (float)Math.sin(Math.toRadians(angle));
					
					zigZag.setBulletDXDY(x_angle, y_angle);
					
					if(angle >= 180) {
						i = -20;
					}
					else if(angle <= 0) {
						i = 20;
					}
					angle += i;
					break;
				
				default:
					break;
			}
		}
	}
	
	public void zigZagTop(BulletManager enemyBullets) {
		
	}
	
	public void zShapeBottom(BulletManager enemyBullets) {
		
	}
}
