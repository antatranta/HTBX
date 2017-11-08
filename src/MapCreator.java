import java.util.ArrayList;

public class MapCreator{
	
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