import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;



public class MapCreator {
	//500 for each. 250.
	private float SEED =200;
	private final int MAX_ENEMIES_IN_QUAD = 2;
	private final int MAX_ASTEROIDS_IN_QUAD = 4;
	private final float BORDER_X = 100f;
	private final float BORDER_Y = 100f;
	private int max_quad=0;
	private Random rand;
//	private static ArrayList<Quadrant> Quad_list;
	private Quadrant player_spawn_quad;
	private Quadrant boss_spawn_quad;
	private int player_spawn_quad_order;
	private int boss_spawn_quad_order;
	
	
	public MapCreator () {
		init();
	}
	
	public void init() {
		rand = new Random((long) SEED);
		System.out.println("Creating MAP using seed: "+SEED);
//		max_quad = Calculate_Quad_Amount(PhysXLibrary.QUADRANT_HEIGHT ,PhysXLibrary.MAP_HEIGHT);
//		System.out.println("quad_amount: "+max_quad);
	}
	
	public void run() {
//		Quad_list = new ArrayList<Quadrant>();
//		Quad_list = createMap();
		
	}
	public void setPlayerAndBossQuadPositions() {
		int totalQuads = (PhysXLibrary.MAP_WIDTH * PhysXLibrary.MAP_HEIGHT);
		max_quad = totalQuads;
		int player_quad = randomNumber(0, totalQuads);
		int boss_quad = randomNumber(0, totalQuads);
		while(player_quad == boss_quad) {
			player_quad = randomNumber(0, totalQuads);
			boss_quad   = randomNumber(0, totalQuads);
		}
		this.player_spawn_quad_order = player_quad;
		this.boss_spawn_quad_order = boss_quad;
	}
	/*
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
	*/
	
	public ArrayList<Quadrant> createMap(){
		
		// Create a new array to hold the quads
		ArrayList<Quadrant> Quadrants = new ArrayList<Quadrant>();
		setPlayerAndBossQuadPositions();
		
		// Store the current position in the Array
//		int order = 0;
	
		int order = (PhysXLibrary.MAP_HEIGHT * PhysXLibrary.MAP_WIDTH) - 1;
		for(int a = PhysXLibrary.MAP_WIDTH; a > 0; --a) {
			for(int b = PhysXLibrary.MAP_HEIGHT; b > 0; --b) {
				
//				int quad_width = (int) Math.round(b*PhysXLibrary.QUADRANT_WIDTH);
//				int quad_height = (int) Math.round(a*PhysXLibrary.QUADRANT_HEIGHT);
				Quadrant quad = createQuadrant(b-1,a-1,order);
				fillQuadrant(quad);
				Quadrants.add(quad);
				
				if (order == player_spawn_quad_order) {
					this.player_spawn_quad = quad;
				} else if (order == boss_spawn_quad_order) {
					this.boss_spawn_quad = quad;
				}
				order--;
			}
		}
		System.out.println("Player spawn at: "+ (player_spawn_quad.getQUID().getX() -1) + ", " + (player_spawn_quad.getQUID().getY()-1));
		System.out.println("Boss spawn at: "+ (boss_spawn_quad.getQUID().getX() -1) + ", " + (boss_spawn_quad.getQUID().getY()-1));
		return Quadrants;
		
	}
	public Quadrant createQuadrant(int x,int y, int order) {
//		Quadrant quad = new Quadrant(new QuadrantID(x,y,order));
//		System.out.println("x:"+x+" y: "+y+" order: "+order);
		return new Quadrant(new QuadrantID(x,y,order));
	}
	public void fillQuadrant(Quadrant quad) {
//		checkInterrupt(current_quad_x_startPoint, PhysXLibrary.QUADRANT_WIDTH, 'x');
//		checkInterrupt(current_quad_y_startPoint, PhysXLibrary.QUADRANT_HEIGHT,'y');
		int numberOfEnemies = randomNumber(0, MAX_ENEMIES_IN_QUAD);
		int numberOfAsteroids = randomNumber(0, MAX_ASTEROIDS_IN_QUAD);
		
		ArrayList<EnemyShip> EnemyShips =  placeEnemies(quad.getQUID(), numberOfEnemies);
		ArrayList<Asteroid> Asteroids =  placeAsteroids(quad.getQUID(), numberOfAsteroids);
		
		
		
		quad.setAsteroids(Asteroids);
		quad.setShips(EnemyShips);
	}
	
	/*
	public void checkInterrupt(int start, float edge, char type) {
		boolean interrupt = true;
		ArrayList<Integer> objects;
		int end = (int) Math.round(start+edge);
		while(interrupt) {
			objects = new ArrayList<Integer>();
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
				//System.out.println("Erand: "+enemy_rand);
				if(type == 'x') {
					Ex[i] = enemy_rand;
				}else if (type == 'y') {
					Ey[i] = enemy_rand;					
				}
			}
			for(int j=0;j<max_asteroid;j++) {
				int asteroid_rand = randomNumber(end, start);
				objects.add(asteroid_rand);
				//System.out.println("Arand: "+asteroid_rand);
				if(type == 'x') {
					Ax[j] = asteroid_rand;
				}else if (type == 'y') {
					Ay[j] = asteroid_rand;					
				}
			}
			interrupt=interrrupt_occurred(objects,PhysXLibrary.COLLISION_CONSTANT);
		}
	}
	*/
	
	private int randomNumber(int min, int max) {		
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		int rtrn = rand.nextInt((max - min) + 1) + min;
		return rtrn;
	}

	/*
	public boolean interrrupt_occurred(ArrayList<Integer> objects,float range) {
		// VERIFY SHIP IS "PhysXLibrary.COLLISION_CONSTANT * x" distance from any other 
		// objects!!!!
        for (int i = 0; i <objects.size(); i++) {
            float object = objects.get(i);
            for (int k = 0; k <objects.size(); k++) {
            	if(k!=i) {
	            	float compare = objects.get(k);
	            	float different = Math.abs(object - compare);
	            	if(different==0 || different<=range) {
	            		//System.out.println("Interrupt!!!!"); 
	            		return true;
	            	}
            	}
            }
        }
        return false;
	}
	*/
	
	public PhysXObject createPhysXObjectInQuad (QuadrantID quad) {
		float startingX = ((quad.getX() + 1)* PhysXLibrary.QUADRANT_WIDTH) - (PhysXLibrary.QUADRANT_WIDTH / 2);
		float startingY = ((quad.getY() + 1)* PhysXLibrary.QUADRANT_HEIGHT) -  (PhysXLibrary.QUADRANT_HEIGHT / 2);
		
		float randomMultiplierX = rand.nextFloat() * randomNumber(-1, 1); // -1.0 - 1.0
		float randomMultiplierY = rand.nextFloat() * randomNumber(-1, 1); // -1.0 - 1.0
		float x_pos = randomMultiplierX * ((PhysXLibrary.QUADRANT_WIDTH - BORDER_X) / 2);
		float y_pos = randomMultiplierY * ((PhysXLibrary.QUADRANT_HEIGHT - BORDER_Y) / 2);
		return new PhysXObject(quad, new Vector2(startingX + x_pos, startingY + y_pos));
	}
	
	public ArrayList<EnemyShip> placeEnemies(QuadrantID quad, int numToCreate) {
		ArrayList<EnemyShip> EnemyShips = new ArrayList<EnemyShip>();
		for(int i =0; i < numToCreate; ++i) {
			PhysXObject shipPhysXObj = createPhysXObjectInQuad(quad);
			EnemyShips.add(new EnemyShip(shipPhysXObj, 10, ShipStats.EnemyStats_01()));
		}
		return EnemyShips;
	}
	
	public EnemyShip placeEnemy (QuadrantID quad) {
		return null; // Temporary
	}
	
	public ArrayList<Asteroid> placeAsteroids(QuadrantID quad, int numToCreate) {
		ArrayList<Asteroid> Asteroids = new ArrayList<Asteroid>();
		for(int i =0; i < numToCreate; ++i) {
			PhysXObject newAsteroid = createPhysXObjectInQuad(quad);
			Asteroids.add(new Asteroid(newAsteroid));
		}
		return Asteroids;
	}
	
	public Asteroid placeAsteroid (QuadrantID quad) {
		return new Asteroid();
	}
	
	
	public void placeBoss(Quadrant quad, Boss boss) {
		System.out.println(boss);
	}
	
	public Quadrant getPlayerSpawn() {
		return this.player_spawn_quad;
	}
	public Quadrant getBossSpawn() {
		return this.boss_spawn_quad;
	}
	public void placePlayer(Quadrant quad, PlayerShip player) {
		System.out.println(player);
		//quad.setShips(player);
	}
}