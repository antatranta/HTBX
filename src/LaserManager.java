import java.util.ArrayList;

import rotations.GameImage;

public class LaserManager implements LaserManagerEvents {
	private ArrayList<LaserLine> lasers;
	private ArrayList<LaserLine> advancedLasers;
	private GameConsoleEvents console;

	public LaserManager(GameConsoleEvents console) {
		lasers = new ArrayList<LaserLine>();
		advancedLasers = new ArrayList<LaserLine>();
		this.console = console;
	}

	private void createBasicLaserBeam(Vector2 a, Vector2 b, int t) {

		// init a new line
		LaserLine newLine = new LaserLine(t);

		// create the beam
		newLine.updateBeam(a, b);

		// add to storage
		lasers.add(newLine);
	}

	private void createAdvancedLaserBeam(PhysXObject a, Vector2 pos, int t) {

		// init a new line
		LaserLine newLine = new LaserLine(t);

		// create the beam
		newLine.updateBeam(a.getPosition(), pos);

		// add to storage
		advancedLasers.add(newLine);
	}

	public ArrayList<LaserSegment> getSegments() {
		ArrayList<LaserSegment> segments = new ArrayList<LaserSegment>();
		for(LaserLine laser : lasers) {
			segments.addAll(laser.getSegments());
		}

		for(LaserLine laser : advancedLasers) {
			segments.addAll(laser.getSegments());
		}
		return segments;
	}


	public void updateLasers() {
		ArrayList<LaserLine> lasersToRemove = new ArrayList<LaserLine>();
		for(LaserLine laser : lasers) {
			laser.incrementTime();
			if(laser.getTimeRemaining() <= 0) {
				lasersToRemove.add(laser);
			}
		}
		lasers.removeAll(lasersToRemove);

		ArrayList<LaserLine> advancedLasersToRemove = new ArrayList<LaserLine>();
		for(LaserLine laser : advancedLasers) {
			laser.incrementTime();
			if(laser.getTimeRemaining() <= 0) {
				advancedLasersToRemove.add(laser);
			}
		}
		advancedLasers.removeAll(advancedLasersToRemove);

		for(LaserLine laser : advancedLasers) {
			laser.incrementTime();
			PhysXObject obj = console.physXRequest_getPlayerPhysObj();
			if(obj != null) {
				laser.updateBeam(laser.getOrigin(),obj.getPosition());
			}
		}
	}

	@Override
	public void createBasicLaser(Vector2 a, Vector2 b, int t) {
		createBasicLaserBeam(a,b,t);
	}

	@Override
	public void createAdvancedLaser(PhysXObject a, PhysXObject b, int t) {

		createAdvancedLaserBeam(a,b.getPosition(), t);
	}

	@Override
	public void createAdvancedLaserAtPlayer(PhysXObject physObj, int f) {
		PhysXObject playerObj = console.physXRequest_getPlayerPhysObj();
		if(playerObj != null) {
			createAdvancedLaserBeam(physObj,playerObj.getPosition(),f);
		}
	}
}
