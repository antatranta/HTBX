import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;



public class MapCreator extends GraphicsApplication{
	//get width in physx
	//500 for each. 250.
	private float seed;
	private int max_enemies=1;
	private int max_asteroid=1;
	private PhysX Physics;
	private int max_quad=0;
	private static Random rand = new Random();
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
		Physics = new PhysX(600, 600, 1800, 1800);//QUADRANT_HEIGHT, QUADRANT_WIDTH, MAP_WIDTH, MAP_HEIGHT
		max_quad = Calculate_Quad_Amount(Physics.getQUADRANT_HEIGHT(),Physics.getMAP_HEIGHT());
		System.out.println("quad_amount: "+max_quad);
		setSize(600, 600);

	}
	
	public void run() {
		//Quad_list = new ArrayList<Quadrant>();
		Quad_list = createMap();
		spawn_playerNboss();
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
		System.out.println("Player spawn at: "+player_quad+". Boss spawn at: "+boss_quad);
		player_spawn_quad=player_quad;
		boss_spawn_quad=boss_quad;
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
		int counter = (int) Math.sqrt(max_quad);
		int order = 0;
		System.out.println("Counter: "+ counter);
		//ArrayList<Integer> xlist = new ArrayList<Integer>();
		//ArrayList<Integer> ylist = new ArrayList<Integer>();
		//System.out.println(Arrays.toString(xlist.toArray()));
		//System.out.println(Arrays.toString(ylist.toArray()));
		//ylist.add(a*600);
		//xlist.add(b*600);
		for(int a = 0; a < counter;a ++) {
			for(int b = 0; b<counter;b++) {
				Quadrants.add(createQuadrants(b*600,a*600,order));
				order++;
			}
		}
		return Quadrants;
		
	}
	public Quadrant createQuadrants(int x,int y, int order) {

		
		Quadrant quad = new Quadrant(new QuadrantID(x,y,order));
		System.out.println("x:"+x+" y: "+y+" order: "+order);
		fillQuadrants(quad,max_enemies,max_asteroid);

		return quad;
	}
	public void fillQuadrants(Quadrant quad,int max_enemies,int max_asteroid) {
		//EnemyShip enemy = new EnemyShip()
		//Boss boss = new boss()
		//playership = new player();
		if(!(quad.getQUID().Order()==player_spawn_quad)&&!(quad.getQUID().Order()==boss_spawn_quad)){
			for(int i=0;i<max_enemies;i++) {
				//placeEnemies(quad,enemy);
			}
		}
		//placeEnemies(quad,EnemyShip enemy);
		//placeBoss(quad, Boss boss);
		//placePlayer(quad, PlayerShip player);
	}
	public void placeEnemies(Quadrant quad,EnemyShip enemy) {
		ArrayList<Ship> ships = new ArrayList<Ship>();
		//random x y for enemy.
		ships.add(enemy);
		quad.setShips(ships);
	}
	public void placeBoss(Quadrant quad, Boss boss) {
		
	}
	public void placePlayer(Quadrant quad, PlayerShip player) {
		
	}

}