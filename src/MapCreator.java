import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;



public class MapCreator extends GameConsole{//I extends with gameconsole to get player and physXobject just for testing--Wenrui
	//get width in physx
	//500 for each. 250.
	private float SEED;
	private int max_enemies=1;
	private int max_asteroid=1;
//	private PhysX Physics; // MapCreator should not create, or store a physX instance
	private int max_quad=0;
	private static Random rand;
	ArrayList<Quadrant> Quad_list;
	private int player_spawn_quad;
	private int boss_spawn_quad;
	//random number of ships, asteroid for each quad.
	/*
	public MapCreator(float seed) {
		super();
		this.seed = seed;
		this.max_enemies=1;
		this.max_asteroid=1;
		this.max_quad=0;
	}*/
	
	public void init() {
		rand = new Random((long) SEED);
		System.out.println("Seed: "+SEED);
		/*
		//set the random number range same as the quadrant edge.
		setSeed(PhysXLibrary.QUADRANT_HEIGHT);
		//setSeed(600);
		*/
		//Calculated max number of quadrant by (map_height/quad_height)^2.--wenrui
		max_quad = Calculate_Quad_Amount(PhysXLibrary.QUADRANT_HEIGHT ,PhysXLibrary.MAP_HEIGHT);
		//max_quad = Calculate_Quad_Amount(600,1800);
		System.out.println("quad_amount: "+max_quad);
		setSize((int) Math.round(PhysXLibrary.QUADRANT_WIDTH), (int) Math.round(PhysXLibrary.QUADRANT_HEIGHT));
		//setSize(600,600);//Assume quad height and width are 600, for testing.
	}
	
	public void run() {
		//Quad_list = new ArrayList<Quadrant>();
		Quad_list = createMap();
		
	}
	public void spawn_playerNboss() {
		int player_quad=0;
		int boss_quad=0;
		player_quad= rand.nextInt(max_quad);
		boss_quad = rand.nextInt(max_quad);
		while(player_quad==boss_quad) {
			player_quad= rand.nextInt(max_quad);
			boss_quad = rand.nextInt(max_quad);
		}
		player_spawn_quad=player_quad;
		boss_spawn_quad=boss_quad;
		System.out.println("Player spawn at: "+player_spawn_quad+". Boss spawn at: "+boss_spawn_quad);
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
	
	public ArrayList<Quadrant> createMap(){
		ArrayList<Quadrant> Quadrants = new ArrayList<Quadrant>();
		spawn_playerNboss();
		int counter = (int) Math.sqrt(max_quad);
		int order = 0;
		System.out.println("Counter: "+ counter);
		for(int a = 0; a < counter;a ++) {
			for(int b = 0; b<counter;b++) {
				int quad_width = (int) Math.round(b*PhysXLibrary.QUADRANT_WIDTH);
				int quad_height = (int) Math.round(a*PhysXLibrary.QUADRANT_HEIGHT);
				Quadrant quad = createQuadrants(quad_width,quad_height,order);
				fillQuadrants(quad,quad_width,quad_height);
				Quadrants.add(quad);
				order++;
			}
		}
		return Quadrants;
		
	}
	public Quadrant createQuadrants(int x,int y, int order) {
		Quadrant quad = new Quadrant(new QuadrantID(x,y,order));
		System.out.println("x:"+x+" y: "+y+" order: "+order);
		return quad;
	}
	public void fillQuadrants(Quadrant quad,int current_quad_x_startPoint,int current_quad_y_startPoint) {
		//System.out.println("current_quad_width: "+ current_quad_x_startPoint);
		//System.out.println("current_quad_height: "+ current_quad_y_startPoint);
		int x_endpoint= (int) Math.round(current_quad_x_startPoint+PhysXLibrary.QUADRANT_WIDTH);
		int y_endpoint= (int) Math.round(current_quad_y_startPoint+PhysXLibrary.QUADRANT_HEIGHT);
		System.out.print("x_endpoint: "+ x_endpoint);
		System.out.println("   y_endpoint: "+ y_endpoint);
		//System.out.println("player quad: "+ player_spawn_quad);
		PlayerShip player = GameConsole.getPlayer();
		if(!(quad.getQUID().Order()==player_spawn_quad)&&!(quad.getQUID().Order()==boss_spawn_quad)){
			//while(checkSpace())
			//quad.getShips()
			//quad.getStatics()
			for(int i=0;i<max_enemies;i++) {
				//int randomNum = rand.nextInt((max - min) + 1) + min;
				int rand_x = rand.nextInt((x_endpoint-current_quad_x_startPoint)+1)+current_quad_x_startPoint;
				int rand_y = rand.nextInt((y_endpoint-current_quad_y_startPoint)+1)+current_quad_y_startPoint;
				placeEnemies(quad,rand_x,rand_y);
			}
			for(int i=0;i<max_asteroid;i++) {
				int rand_x = rand.nextInt((x_endpoint-current_quad_x_startPoint)+1)+current_quad_x_startPoint;
				int rand_y = rand.nextInt((y_endpoint-current_quad_y_startPoint)+1)+current_quad_y_startPoint;
				placeAsteroid(quad,rand_x,rand_y);
			}
		}else if(quad.getQUID().Order()==player_spawn_quad){
			placePlayer(quad,player);
		}else if(quad.getQUID().Order()==boss_spawn_quad){
			//placeBoss (quad,boss);
		}
	}
	
	public boolean CheckSpace() {
		// VERIFY SHIP IS "PhysXLibrary.COLLISION_CONSTANT * x" distance from any other 
		// objects!!!!
		
		return false;
	}
	
	public void placeEnemies(Quadrant quad,int enemy_x, int enemy_y) {
		//System.out.println("At quad: "+ quad.getQUID().Order());
		ArrayList<Ship> ships = new ArrayList<Ship>();
		System.out.println("Enemy x: "+ enemy_x + " y: "+enemy_y);
		//System.out.println("quad order number: "+quad.getQUID().Order());
		PhysXObject EnemiesPhysXobj = new PhysXObject(quad.getQUID(), new Vector2(enemy_x,enemy_y));		
		//EnemyShip enemy = new EnemyShip(EnemiesPhysXobj, current_health, stats, weapon);
		//random x y (WITHIN THE CURRENT QUAD) for enemy.

		//ships.add(enemy);
		quad.setShips(ships);
	}
	public void placeBoss(Quadrant quad, Boss boss) {
		System.out.println(boss);
	}
	public void placePlayer(Quadrant quad, PlayerShip player) {
		System.out.println(player);
		//quad.setShips(player);
	}
	public void placeAsteroid(Quadrant quad,int asteroid_x, int asteroid_y) {
		ArrayList<Asteroid> ast_list = new ArrayList<Asteroid>();
		PhysXObject AsteroidPhysXobj = new PhysXObject(quad.getQUID());	
		Asteroid ast = new Asteroid(AsteroidPhysXobj);
		ast_list.add(ast);
		quad.setAsteroids(ast_list);
	}
}