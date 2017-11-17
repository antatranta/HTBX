import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileInput {
	public static final String ROOT_PATH = new File("").getAbsolutePath(); // Leave this
	private String last_file;
	
	private Map<String, Asteroid> AsteroidDictionary;
	
	public FileInput() {
		last_file = null;
		AsteroidDictionary = new HashMap<String, Asteroid>();
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
	
	public void decodeFile(ArrayList<String> lines) {
		for(String line : lines) {
			
			// Remove spaces
			String Reader = line.replace(" ", "");
			
			if(Reader.contains("#ASTER#")) {
				readAsteroid(line);

			}
		}
	}
	
	public void readAsteroid(String asteroidLine) {
		// Split
		String[] values = asteroidLine.split("/");
		
		int numColliders = Integer.parseInt(values[0]);
		
		for(int i =0; i < numColliders; i+=3) {
			float colliderX 		= Integer.parseInt(values[i]);
			float colliderY 		= Integer.parseInt(values[i]);
			float colliderRadius = Integer.parseInt(values[i]);
		}
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
};