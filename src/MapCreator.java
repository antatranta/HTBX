import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MapCreator{
	//get width in physx
	//500 for each. 250.
	private float seed;
	private int max_enemies;
	private int max_asteroid;
	private PhysX Physics;
	private int max_quad;
	Random rand;
	ArrayList<Quadrant> Quad_list;
	private int player_spawn_quad;
	private int boss_spawn_quad;
	//random number of ships, asteroid for each quad.
	
	public MapCreator(float seed) {
		super();
		this.seed = seed;
		this.max_enemies=1;
		this.max_asteroid=1;
		this.max_quad=0;
	}
	
	public void init() {
		Physics = new PhysX(600, 600, 1800, 1800);//QUADRANT_HEIGHT, QUADRANT_WIDTH, MAP_WIDTH, MAP_HEIGHT
		max_quad = Calculate_Quad_Amount(Physics.getQUADRANT_HEIGHT(),Physics.getMAP_HEIGHT());
		System.out.println("quad_amount: "+max_quad);
		//Quad_list = new ArrayList<Quadrant>();
		Quad_list = createMap();
	}
	
	public void run() {

		
	}
	
	public int Calculate_Quad_Amount(float quadrant_height,int map_height) {
		int height_int = (int)quadrant_height;
		int amount = map_height/height_int;
		amount = amount*amount;
		return amount;
	}
	public void createNoiseMap() {
		//use perlin noise.
		//https://stackoverflow.com/questions/17440865/using-perlin-noise-to-generate-a-2d-tile-map
	}
	
	//random number of ships, asteroid for each quad.
	//x and y in quad.
	//max ship in quad,etc.
	//012
	//345
	//678
		//for each ship and each asteroid
			//get a random x and y for placement
			//run a check thruough physX to make sure they won't touch
	public ArrayList<Quadrant> createMap(){
		ArrayList<Quadrant> Quadrants = new ArrayList<Quadrant>();
		for(int i =0;i<max_quad;i++ ) {
			Quadrants.add(createQuadrants(i));
		}
		return Quadrants;
		
	}
	public Quadrant createQuadrants(int order) {
		int x = rand.nextInt(100);
		int y = rand.nextInt(100);
		//EnemyShip enemy = new EnemyShip()
		//EnemyShip enemy = new EnemyShip()
		Quadrant quad = new Quadrant(new QuadrantID(x,y,order));
		System.out.println("x:"+x+"y "+y+"order "+order);
		fillQuadrants(quad,max_enemies,max_asteroid);
		//placeEnemies(quad,EnemyShip enemy);
		//placeBoss(quad, Boss boss);
		//placePlayer(quad, PlayerShip player);
		return quad;
	}
	public void fillQuadrants(Quadrant quad,int max_enemies,int max_asteroid) {
		
		
	}
	public void placeEnemies(Quadrant quad,EnemyShip enemy) {
		
	}
	public void placeBoss(Quadrant quad, Boss boss) {
		
	}
	public void placePlayer(Quadrant quad, PlayerShip player) {
		
	}

}