import java.util.*;
public class Quadrant{
	private ArrayList<Asteroid> asteroids;
	private ArrayList<EnemyShip> ships;
	private boolean isActive;
	private QuadrantID QUID;

	public Quadrant() {
		this.isActive = false;
		this.QUID = new QuadrantID(0,0,0);
	}

	public Quadrant(QuadrantID QUID) {
		this.isActive = false;
		this.QUID = QUID;
	}

	public QuadrantID getQUID() {
		return QUID;
	}

	public void checkForCollisions() {
		if (!isActive) {
			return;
		}

		if(ships != null && ships.size() > 0 && asteroids != null) {
			for(Ship A : ships) {
				
				if(asteroids.size() > 0) {
					for(Asteroid B : asteroids) {
						if(PhysXLibrary.areObjectsInCollisionRange(A.getPhysObj(),B.getPhysObj())) {
							if(PhysXLibrary.isCollision(A.getPhysObj(), B.getPhysObj())) {
		//						A.sendCollisionMessage(B.getCollisionData());
								
								if (GameConsole.IS_DEBUGGING) {
		//							System.out.println("Asteroid Collision!");
								}
							}
						}
					}
				}

				for(Ship C : ships) {
					if(PhysXLibrary.areObjectsInCollisionRange(A.getPhysObj(), C.getPhysObj())) {
						if(PhysXLibrary.isCollision(A.getPhysObj(), C.getPhysObj())) {
	//						A.sendCollisionMessage(C.getCollisionData());
	//						C.sendCollisionMessage(A.getCollisionData());
							
							if (GameConsole.IS_DEBUGGING) {
	//							System.out.println("Ship Collision!");
							}
						}
	
					}
				}
			}
		}
	}

	public void Activate() {
		this.isActive = true;
	}

	public void Deactivate() {
		this.isActive = false;
	}

	public ArrayList<Asteroid> getStatics(){
		return asteroids;
	}

	public ArrayList<EnemyShip> getShips(){
		return ships;
	}

	public ArrayList<Asteroid> getAsteroids() {
		return asteroids;
	}

	public void setAsteroids(ArrayList<Asteroid> asteroids) {
		this.asteroids = asteroids;
	}

	public void setShips(ArrayList<EnemyShip> ships) {
		this.ships = ships;
	}

	public void setBlinkers(ArrayList<Blinker> blinkers) {
		this.ships.addAll(blinkers);
	}

	public void setFencers(ArrayList<Fencer> fencers) {
		this.ships.addAll(fencers);
	}

	public void setQUID(QuadrantID qUID) {
		QUID = qUID;
	}

	public void setBanshes(ArrayList<Banshe> banshes) {
		this.ships.addAll(banshes);
	}
	
	public void addEnemyShip(EnemyShip ship) {
		
		if(this.ships == null) {
			this.ships = new ArrayList<EnemyShip>();
		}
		this.ships.add(ship);
	}
}
