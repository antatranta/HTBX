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
	private static float min_distance_between_objects=400.0f;
	private final int MAX_ENEMIES_IN_QUAD = 2;
	private final int MAX_ASTEROIDS_IN_QUAD = 4;
	private final float BORDER_X = 100f;
	private final float BORDER_Y = 100f;
	private int max_quad=0;
	private Quadrant player_spawn_quad;
	private Quadrant boss_spawn_quad;
	private int player_spawn_quad_order;
	private int boss_spawn_quad_order;
	
	private FileInput file;
	
	public MapCreator() {
		LavaLamp.setup(System.currentTimeMillis());
		
		file = new FileInput();
		file.decodeFile("Test.txt");
//		LavaLamp.setup(LavaLamp.randomNumber(0, 500));
	}
	
	public void setPlayerAndBossQuadPositions() {
		int totalQuads = (PhysXLibrary.MAP_WIDTH * PhysXLibrary.MAP_HEIGHT);
		max_quad = totalQuads;
		int player_quad = LavaLamp.randomNumber(0, totalQuads-1);
		int boss_quad = LavaLamp.randomNumber(0, totalQuads-1);
		System.out.println("player quad:"+player_quad+" boss_quad: "+boss_quad);
		while(player_quad == boss_quad) {
			player_quad = LavaLamp.randomNumber(0, totalQuads -1);
			boss_quad   = LavaLamp.randomNumber(0, totalQuads -1);

		}
		this.player_spawn_quad_order = player_quad;
		this.boss_spawn_quad_order = boss_quad;
		System.out.print("player_spawn_quad_order: "+ player_spawn_quad_order);
		System.out.print("boss_spawn_quad_order: "+ boss_spawn_quad_order);
	}
/*	
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
					System.out.print("player_spawn_quad: "+ player_spawn_quad);
				} else if (order == boss_spawn_quad_order) {
					this.boss_spawn_quad = quad;
					System.out.print("boss_spawn_quad_order: "+ boss_spawn_quad_order);
				}
				order--;
			}
		}
		System.out.println("Player spawn at: "+ (player_spawn_quad.getQUID().getX() -1) + ", " + (player_spawn_quad.getQUID().getY()-1));
		System.out.println("Boss spawn at: "+ (boss_spawn_quad.getQUID().getX() -1) + ", " + (boss_spawn_quad.getQUID().getY()-1));
		return Quadrants;
		
	}
	public Quadrant createQuadrant(int x,int y, int order) {
//		System.out.println("x:"+x+" y: "+y+" order: "+order);
		return new Quadrant(new QuadrantID(x,y,order));
	}
	public void fillQuadrant(Quadrant quad) {
		int numberOfEnemies = LavaLamp.randomNumber(0, MAX_ENEMIES_IN_QUAD);
		int numberOfAsteroids = LavaLamp.randomNumber(0, MAX_ASTEROIDS_IN_QUAD);
		ArrayList<EnemyShip> EnemyShips =  placeEnemies(quad.getQUID(), numberOfEnemies);
		ArrayList<Asteroid> Asteroids =  placeAsteroids(quad.getQUID(), numberOfAsteroids);
		
		//objects verification
		boolean check=true;
		while(check) {//return the object that has collision, remove that object.
			Asteroid check_ast = checkAsteroid(Asteroids);//check only asteroids
			while(check_ast!=null) {
				Asteroids.remove(check_ast);
				Asteroids.add(placeAsteroid(quad.getQUID()));
				check_ast = checkAsteroid(Asteroids);
			}
			EnemyShip check_enemy = checkEnemy(EnemyShips);//check only enemies
			while(check_enemy!=null) {
				EnemyShips.remove(check_enemy);
				EnemyShips.add(placeEnemy(quad.getQUID()));
				check_enemy = checkEnemy(EnemyShips);
			}
			Asteroid check_both = checkBoth(Asteroids,EnemyShips);//comparing asteroids with enemies.
			while(check_both!=null) {
//				System.out.println("X: "+check_both.getPhysObj().getPosition().getX()+" Y: "+check_both.getPhysObj().getPosition().getY());
				Asteroids.remove(check_both);
				//these two codes take some times:-wenrui
				Asteroids.add(placeAsteroid(quad.getQUID()));
				check_ast = checkAsteroid(Asteroids);
				
				check_both = checkBoth(Asteroids,EnemyShips);
			}
			if(check_ast==null&&check_enemy==null&&check_both==null) {
				System.out.println("asteroid:" +Asteroids.toString());
				System.out.println("Enemies:" +EnemyShips.toString());
				check=false;
			}
		}
		quad.setAsteroids(Asteroids);
		quad.setShips(EnemyShips);
	}
	
	public static Asteroid checkAsteroid(ArrayList<Asteroid> Asteroids) {
		for (int j=0;j<Asteroids.size();j++) {
			
			if(Asteroids.get(j).getPhysObj() == null) {
				System.out.println("Phys null");
			}
			float Jx = Asteroids.get(j).getPhysObj().getPosition().getX();
			float Jy = Asteroids.get(j).getPhysObj().getPosition().getY();
			for (int k=j+1;k<Asteroids.size();k++) {
				float Kx = Asteroids.get(k).getPhysObj().getPosition().getX();
				float Ky = Asteroids.get(k).getPhysObj().getPosition().getY();
            	float differentX = Math.abs(Jx - Kx);
            	float differentY = Math.abs(Jy - Ky);
            	/*
				if (k!=j &&(differentX<=min_distance_between_objects)) {
//					System.out.println("Interrupt object: "+Asteroids.get(k)+" Position x: "+Asteroids.get(k).getPhysObj().getPosition().getX()); 
					return Asteroids.get(k);
				}else if (k!=j &&(differentY<=min_distance_between_objects)) {
//					System.out.println("Interrupt object: "+Asteroids.get(k)+" Position y: "+Asteroids.get(k).getPhysObj().getPosition().getY()); 
					return Asteroids.get(k);
				}*/
            	if (k!=j &&(differentX<=min_distance_between_objects)) {
            		if (differentY<=min_distance_between_objects) {
//            			System.out.println("differentX: "+ differentX + " differentY: "+differentY);
            			return Asteroids.get(k);
            		}
            	}
			}
		}
		return null;
	}
	
	public EnemyShip checkEnemy(ArrayList<EnemyShip> EnemyShips) {
		for (int j=0;j<EnemyShips.size();j++) {
			float Jx = EnemyShips.get(j).getPhysObj().getPosition().getX();
			float Jy = EnemyShips.get(j).getPhysObj().getPosition().getY();
			for (int k=j+1;k<EnemyShips.size();k++) {
				float Kx = EnemyShips.get(k).getPhysObj().getPosition().getX();
				float Ky = EnemyShips.get(k).getPhysObj().getPosition().getY();
            	float differentX = Math.abs(Jx - Kx);
            	float differentY = Math.abs(Jy - Ky);
            	/*
				if (k!=j && (EnemyShips.get(k).getPhysObj().getPosition().getX()== EnemyShips.get(j).getPhysObj().getPosition().getX())) {//comparing x coord
//					System.out.println("Interrupt object: "+EnemyShips.get(k)+" Position x: "+EnemyShips.get(k).getPhysObj().getPosition().getX()); 
					return EnemyShips.get(k);
				}else if(k!=j && (EnemyShips.get(k).getPhysObj().getPosition().getY()== EnemyShips.get(j).getPhysObj().getPosition().getY())) {//comparing y coord
//					System.out.println("Interrupt object: "+EnemyShips.get(k)+" Position y: "+EnemyShips.get(k).getPhysObj().getPosition().getY()); 
					return EnemyShips.get(k);
				}*/
            	if (k!=j &&(differentX<=min_distance_between_objects)) {
            		if (differentY<=min_distance_between_objects) {
            			return EnemyShips.get(k);
            		}
            	}
			}
		}
		return null;
	}
	
	public Asteroid checkBoth(ArrayList<Asteroid> Asteroids,ArrayList<EnemyShip> EnemyShips) {
		for(int i = 0; i< Asteroids.size();i++) {
			float Ax = Asteroids.get(i).getPhysObj().getPosition().getX();
			float Ay = Asteroids.get(i).getPhysObj().getPosition().getY();
            for (int k = 0; k <EnemyShips.size(); k++) {
            	float Ex = EnemyShips.get(k).getPhysObj().getPosition().getX();
            	float Ey = EnemyShips.get(k).getPhysObj().getPosition().getY();
            	float differentX = Math.abs(Ax - Ex);
            	float differentY = Math.abs(Ay - Ey);
            	if(differentX<=min_distance_between_objects) {//if they at the same position or in the collision range.
            		if(differentY<=min_distance_between_objects) {
//            			System.out.println("differentX: "+ differentX + " differentY: "+differentY);
            			return Asteroids.get(i);
            		}
            	}
            	
            }
		}
		//check y coord
		/*
		for(int i = 0; i< Asteroids.size();i++) {
			float Ay = Asteroids.get(i).getPhysObj().getPosition().getY();
            for (int k = 0; k <EnemyShips.size(); k++) {
            	float Ey = EnemyShips.get(k).getPhysObj().getPosition().getY();
            	float different = Math.abs(Ay - Ey);
//            	System.out.println("different: "+different);
            	if(different<=min_distance_between_objects) {//if they at the same position or in the collision range.
//            		System.out.println("Interrupt object: "+Asteroids.get(i)+" Position y: "+Ay);
            		return Asteroids.get(i);
            	}
            }
		}*/
		return null;
	}
	
	public PhysXObject createPhysXObjectInQuad (QuadrantID quad) {
		float startingX = ((quad.getX() + 1)* PhysXLibrary.QUADRANT_WIDTH) - (PhysXLibrary.QUADRANT_WIDTH / 2);
		float startingY = ((quad.getY() + 1)* PhysXLibrary.QUADRANT_HEIGHT) -  (PhysXLibrary.QUADRANT_HEIGHT / 2);
		
		float randomMultiplierX = LavaLamp.nextFloat() * LavaLamp.randomNumber(-1, 1); // -1.0 - 1.0
		float randomMultiplierY = LavaLamp.nextFloat() * LavaLamp.randomNumber(-1, 1); // -1.0 - 1.0
		//the possibility of a float number is 0.0f is too big-wenrui 
		while (randomMultiplierX==0.0f) {
			randomMultiplierX = LavaLamp.nextFloat() * LavaLamp.randomNumber(-1, 1); // -1.0 - 1.0
		}
		while (randomMultiplierY==0.0f) {
			randomMultiplierY = LavaLamp.nextFloat() * LavaLamp.randomNumber(-1, 1); // -1.0 - 1.0
		}
//		float x_pos = randomMultiplierX * ((PhysXLibrary.QUADRANT_WIDTH - BORDER_X) / 2);
//		float y_pos = randomMultiplierY * ((PhysXLibrary.QUADRANT_HEIGHT - BORDER_Y) / 2);
		float x_pos = randomMultiplierX * (PhysXLibrary.QUADRANT_WIDTH / 2 - BORDER_X);
		float y_pos = randomMultiplierY * (PhysXLibrary.QUADRANT_HEIGHT / 2 - BORDER_Y);
/*		float x = startingX + x_pos;
		float y = startingY + y_pos;
		System.out.println("x_pos: "+ x +" y_pos: "+y);*/
		return new PhysXObject(quad, new Vector2(startingX + x_pos, startingY + y_pos));
	}
	
	public ArrayList<EnemyShip> placeEnemies(QuadrantID quad, int numToCreate) {
		ArrayList<EnemyShip> EnemyShips = new ArrayList<EnemyShip>();
		for(int i =0; i < numToCreate; ++i) {
			CircleCollider collider = new CircleCollider(Vector2.Zero(), 10);
			PhysXObject shipPhysXObj = createPhysXObjectInQuad(quad);
			shipPhysXObj.addCollider(collider);
			int level = LavaLamp.randomNumber(1,2);
			EnemyShip new_ship = new EnemyShip(shipPhysXObj, "Enemy_"+level+"_S.png", 10, ShipStats.EnemyStats_01(), level);
			EnemyShips.add(new_ship);
		}
		return EnemyShips;
	}
	
	public EnemyShip placeEnemy (QuadrantID quad) {
		CircleCollider collider = new CircleCollider(Vector2.Zero(), 15);
		PhysXObject shipPhysXObj = createPhysXObjectInQuad(quad);
		shipPhysXObj.addCollider(collider);
		int level = LavaLamp.randomNumber(1,2);
		EnemyShip enemy = new EnemyShip(shipPhysXObj, "Enemy_"+level+"_S.png", 10, ShipStats.EnemyStats_01(), level);
		return enemy; // Temporary
	}
	
	public ArrayList<Asteroid> placeAsteroids(QuadrantID quad, int numToCreate) {
		ArrayList<Asteroid> Asteroids = new ArrayList<Asteroid>();
		for(int i =0; i < numToCreate; ++i) {
			Asteroids.add(buildAsteroid(createPhysXObjectInQuad(quad)));
		}
		return Asteroids;
	}
	
	public PlayerShip placePlayer (QuadrantID quad) {
		PhysXObject physObj = createPhysXObjectInQuad(quad);
		return new PlayerShip(physObj, 1, new ShipStats(1,1,1,1), "PlayerShip-Small.png");
	}
	
	public Asteroid buildAsteroid(PhysXObject physObj) {
		int preset = LavaLamp.randomNumber(0,file.numberOfAsteroidPresets()-1);
		PhysXObject presetPhysObj = new PhysXObject(file.getAsteroidObject(preset));
		presetPhysObj.setPosition(physObj.getPosition());
		presetPhysObj.setQUID(physObj.getQUID());
		
		System.out.println("[" + file.getAsteroidSprite(preset) + "]");
		return new Asteroid(presetPhysObj, file.getAsteroidSprite(preset));
	}
	
	public Asteroid placeAsteroid (QuadrantID quad) {
		return buildAsteroid(createPhysXObjectInQuad(quad));
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