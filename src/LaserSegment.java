
public class LaserSegment extends Entity{
	protected float dir;
	public LaserSegment(PhysXObject physObj, String sprite, CollisionData data, Vector2 direction) {
		super(physObj, sprite, data);
		this.dir = 90;
		rotateToPoint(direction);
		// TODO Auto-generated constructor stub
	}

	private void adjustAngle(double degree) {
		dir += degree;
		if (dir > 360) {
			dir -= 360;
		}
		else if (dir < 0) {
			dir += 360;
		}

		this.sprite.setDegrees(dir + 90);
	}

	public void rotateToPoint(Vector2 target) {
		Vector2 origin = physObj.getPosition();
		double target_angle = Math.atan2(target.getY() - origin.getY(), target.getX() - origin.getX());
		int target_degree = (int) (target_angle * 57.32);
		this.sprite.setDegrees(target_degree + 90);

		/*
		int different = (int) (dir - target_degree);

		if(Math.abs(different)>180) {
			different+=different>0?-360:360;
		}
		adjustAngle(different);
		 */
		/*
		if(different<0) {
			adjustAngle(1);
		}
		else if(different>0) {
			adjustAngle(-1);
		}*/
	}

}
