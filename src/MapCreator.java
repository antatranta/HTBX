import java.util.ArrayList;

public class MapCreator{
	//get width in physx
	//500 for each. 250.
	private float seed;
	//random number of ships, asteroid for each quad.
	
	public MapCreator(float seed) {
		super();
		this.seed = seed;
	}

	public void createNoiseMap() {
		//use perlin noise.
		//https://stackoverflow.com/questions/17440865/using-perlin-noise-to-generate-a-2d-tile-map
		//random number of ships, asteroid for each quad.
		//x and y in quad.
		//max ship in quad,etc.
		//012
		//345
		//678
			//for each ship and each asteroid
				//get a random x and y for placement
				//run a check thruough physX to make sure they won't touch
	}
	public void createQuadrants() {
		
	}
	public void fillQuadrants(Quadrant quad) {
		
	}
	public void placeEnemies(Quadrant quad,EnemyShip enemy) {
		
	}
	public void placeBoss(Quadrant quad, Boss boss) {
		
	}
	public void placePlayer(Quadrant quad, PlayerShip player) {
		
	}
	public ArrayList<Quadrant> createMap(){
		return null;
		
	}
}