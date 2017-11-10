import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;



public class MapCreator extends GameConsole{//I extends with gameconsole to get player and physXobject just for testing--Wenrui
	//get width in physx
	//500 for each. 250.
	private float SEED;
	private static int max_enemies=2;
	private static int max_asteroid=1;
	private int max_quad=0;
	private Random rand;
	private static ArrayList<Quadrant> Quad_list;
	private int player_spawn_quad;
	private int boss_spawn_quad;
	
	private int []Ex = new int[max_enemies];
	private int []Ey = new int[max_enemies];
	private int []Ax = new int[max_asteroid];
	private int []Ay = new int[max_asteroid];
	
	public void init() {
		//rand = new Random((long) SEED);
		rand = new Random();
		System.out.println("Seed: "+SEED);
		/*
		//set the random number range same as the quadrant edge.
		setSeed(PhysXLibrary.QUADRANT_HEIGHT);
		//setSeed(600);
		*/
		//Calculated max number of quadrant by (map_height/quad_height)^2.--wenrui
		max_quad = Calculate_Quad_Amount(PhysXLibrary.QUADRANT_HEIGHT ,PhysXLibrary.MAP_HEIGHT);
		System.out.println("quad_amount: "+max_quad);
		setSize((int) Math.round(PhysXLibrary.QUADRANT_WIDTH), (int) Math.round(PhysXLibrary.QUADRANT_HEIGHT));
	}
	
	public void run() {
		Quad_list = new ArrayList<Quadrant>();
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
		checkInterrupt(current_quad_x_startPoint, PhysXLibrary.QUADRANT_WIDTH, 'x');
		checkInterrupt(current_quad_y_startPoint, PhysXLibrary.QUADRANT_HEIGHT,'y');
		if(!(quad.getQUID().Order()==player_spawn_quad)&&!(quad.getQUID().Order()==boss_spawn_quad)){
			for(int i = 0; i < max_enemies;i++) {
				placeEnemies(quad,Ex[i],Ey[i]);
			}
			for(int i = 0; i < max_asteroid;i++) {
				placeAsteroid(quad,Ax[i],Ay[i]);
			}
		}else if(quad.getQUID().Order()==player_spawn_quad){
			
			placePlayer(quad,getPlayer());
		}else if(quad.getQUID().Order()==boss_spawn_quad){
			//placeBoss (quad,boss);
		}
	}
	
	public void checkInterrupt(int start, float edge, char type) {
		boolean interrupt = true;
		ArrayList<Integer> objects = new ArrayList<Integer>();
		int end = (int) Math.round(start+edge);
		while(interrupt) {
			if(type == 'x') {
				Ex = new int[max_enemies];
				Ax = new int[max_asteroid];
			}else if (type == 'y') {
				Ey = new int[max_enemies];
				Ay = new int[max_asteroid];
			}
			for(int i=0;i<max_enemies;i++) {
				int enemy_rand = randomNumber(end, start);
				objects.add(enemy_rand);
				if(type == 'x') {
					Ex[i] = enemy_rand;
				}else if (type == 'y') {
					Ey[i] = enemy_rand;					
				}
			}
			for(int j=0;j<max_asteroid;j++) {
				int asteroid_rand = randomNumber(end, start);
				objects.add(asteroid_rand);
				if(type == 'x') {
					Ax[j] = asteroid_rand;
				}else if (type == 'y') {
					Ay[j] = asteroid_rand;					
				}
			}
			interrupt=interrrupt_occurred(objects,PhysXLibrary.COLLISION_CONSTANT);
		}
	}
	
	private int randomNumber(int max, int min) {
		Random newrand = new Random(rand.nextInt(9));
		int randomNum = newrand.nextInt((max - min) + 1) + min;
		//float random = rand.nextFloat() * (max - min) + min;
		return randomNum;
	}

	public boolean interrrupt_occurred(ArrayList<Integer> objects,float range) {
		// VERIFY SHIP IS "PhysXLibrary.COLLISION_CONSTANT * x" distance from any other 
		// objects!!!!
        for (int i = 0; i <objects.size(); i++) {
            float object = objects.get(i);
            for (int k = 0; k <objects.size(); k++) {
            	if(k!=i) {
	            	float compare = objects.get(k);
	            	float different = Math.abs(object - compare);
	            	if(different!=0 && different<=range) {
	            		System.out.println("Interrupt!!!!"); 
	            		return true;
	            	}
            	}
            }
        }
        return false;
	}
	
	public void placeEnemies(Quadrant quad,int enemy_x, int enemy_y) {
		ArrayList<Ship> ships = new ArrayList<Ship>();
		System.out.println("Enemy x: "+ enemy_x + " y: "+enemy_y);
		PhysXObject EnemiesPhysXobj = new PhysXObject(quad.getQUID(), new Vector2(enemy_x,enemy_y));		
		//EnemyShip enemy = new EnemyShip(EnemiesPhysXobj, current_health, stats, weapon);

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
		System.out.println("Asteroid x: "+ asteroid_x + " y: "+asteroid_y);
		ArrayList<Asteroid> ast_list = new ArrayList<Asteroid>();
		PhysXObject AsteroidPhysXobj = new PhysXObject(quad.getQUID());	
		Asteroid ast = new Asteroid(AsteroidPhysXobj);
		ast_list.add(ast);
		quad.setAsteroids(ast_list);
	}
}