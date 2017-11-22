import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileInput {
	public static final String ROOT_PATH = new File("").getAbsolutePath(); // Leave this
	private String last_file;
	
//	private Map<String, Asteroid> AsteroidDictionary;
	private ArrayList<PhysXObject> asteroidObjects = new ArrayList<PhysXObject>();
	private ArrayList<String> asteroidSprites = new ArrayList<String>();
	
	private ArrayList<PhysXObject> shipObjects = new ArrayList<PhysXObject>();
	private ArrayList<String> shipSprites = new ArrayList<String>();
	private ArrayList<Integer> shipLevels = new ArrayList<Integer>();
	
	public FileInput() {
		last_file = null;
		asteroidObjects = new ArrayList<PhysXObject>();
		asteroidSprites = new ArrayList<String>();
		
		shipObjects = new ArrayList<PhysXObject>();
		shipSprites = new ArrayList<String>();
		shipLevels = new ArrayList<Integer>();
	}

	// Give this the file path and file name, ie, "C:\\Place1\\Place2\File.txt"
	public ArrayList<String> readFile(String file) {

		ArrayList<String> lines = new ArrayList<String>();
		
        try {
        	Reader reader = new FileReader(file);
        	BufferedReader buffer = new BufferedReader(reader);
        	String line = null;
        	
            while((line = buffer.readLine()) != null) {
                lines.add(line);
            }   
            
            last_file = file;
            reader.close();
        }
        catch(IOException ex) {
            System.out.println("[ERROR] Couldn't read file: " + file);                  
        }
		return lines;
		
	}
	
	public void decodeFile(String file) {
		
		ArrayList<String> lines = readFile(file);
		
		for(String line : lines) {
			
			System.out.println("Line: "+ line);
			
			// Remove spaces
			String Reader = line.replaceAll("\\s", "");
			
			System.out.println("Reader: "+ Reader);
			String[] sections = Reader.split("!");
			
			PhysXObject physObj = new PhysXObject(QuadrantID.unassigned());
			String sprite = "";
			Integer level = 0;
			
			
			
			for(String section: sections) {
				
				System.out.println("Section: "+ section);
				
				if(section.contains("$COLL")) {
					
					// Remove header
					String readLine = section.replace("$COLL", "");
					CircleCollider[] colliders = readColliders(readLine);
					
					physObj.removeColliders();
					for(CircleCollider coll : colliders) {
						physObj.addCollider(coll);
					}
				} else if(section.contains("$SPRITE")) {
					
					// Remove header
					String readLine = section.replace("$SPRITE", "");
					sprite = readLine;
				} else if(section.contains("$LEVEL")) {
					
					// Remove header
					String readLine = section.replace("$LEVEL", "");
					level = Integer.parseInt(readLine);
				} else {
//					System.out.println("String not formated correctly");
				}
			}
			
			if(Reader.contains("#ASTER#")) {
				asteroidObjects.add(physObj);
				asteroidSprites.add(sprite);
			} else if (Reader.contains("#SHIP#")) {
				shipObjects.add(physObj);
				shipSprites.add(sprite);
				shipLevels.add(level);
			} else {
				System.out.println("String not formated correctly");
			}
		}
	}
	
	public CircleCollider[] readColliders(String colliderLine) {

		// Split
		String[] values = colliderLine.split(",");
		int numColliders = Integer.parseInt(values[0]);
		
		CircleCollider[] readColliders = new CircleCollider[numColliders];
		
		int currentCollider = 0;
		for(int i =1; i < values.length; i+=2) {
			if(i + 2 < values.length && currentCollider <= numColliders) {
				
				float colliderX 		= Float.parseFloat(values[i]);
				float colliderY 		= Float.parseFloat(values[i+1]);
				float colliderRadius = Float.parseFloat(values[i+2]);
				
				// data verification
				Vector2 pos = new Vector2(colliderX, colliderY);
				if(colliderRadius > 0 && PhysXLibrary.distance(pos, Vector2.Zero()) <= colliderRadius) {
					readColliders[currentCollider] = new CircleCollider(pos, colliderRadius);
				} else {
					System.out.println("Collider values invalid");
					System.out.println(values[i]);
				}
				
				currentCollider++;
			} else {
				System.out.println("COLLIDER: String not formated correctly");
			}
		}
		
		return readColliders;
	}
	
	// Read the previous file. REQUIRES FileInput to be implemented as composition of objects
	public ArrayList<String> readPreviousFile() {
		if (last_file == null) {
			System.out.println("[ERROR] No previous file");
			return null;
		}
		return readFile(last_file);
	}
	
	// Static version of the file reading method. Simply call FileInput.readFileLines(String) and you can get the lines out of the file
	public static ArrayList<String> readFileStatic(String file) {
		ArrayList<String> lines = new ArrayList<String>();
		
        try {
        	Reader reader = new FileReader(file);
        	BufferedReader buffer = new BufferedReader(reader);
        	String line = null;
        	
            while((line = buffer.readLine()) != null) {
                lines.add(line);
            }   

            reader.close();
        }
        catch(IOException ex) {
            System.out.println("[ERROR] Couldn't read file: " + file);                  
        }
		return lines;
	}
	/*
	public static Asteroid GetAsteroidData (String key){
		return Asteroid();
	}
	*/
	// DRIVER TESTS
	public static void main() {
		

		String file = ROOT_PATH + "\\src";
		System.out.println(file);
		FileInput red = new FileInput();
		ArrayList<String> recv = red.readFile(file);
		
		// These 3 should fail
		
		printList(recv);
		
		recv = red.readPreviousFile();
		
		printList(recv);
		
		recv = FileInput.readFileStatic(file);
		
		printList(recv);
		
		// This works
		
		recv = red.readFile(file + "\\Test.txt");
		
		printList(recv);
		
	}
	
	private static void printList(ArrayList<String> list) {
		if (list == null) {
			return;
		}
		for (String line : list) {
			System.out.println(line);
		}
	}

	public int numberOfAsteroidPresets() {
		return asteroidObjects.size();
	}
	
	public PhysXObject getAsteroidObject(int index) {
		return asteroidObjects.get(index);
	}
	
	public String getAsteroidSprite(int index) {
		return asteroidSprites.get(index);
	}
};