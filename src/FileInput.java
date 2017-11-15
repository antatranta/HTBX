import java.io.*;
import java.util.ArrayList;

public class FileInput {
	private String last_file;
	
	public FileInput() {
		last_file = null;
	}

	// Give this the file path and file name, ie, "C:\\Place1\\Place2\File.txt"
	public ArrayList<String> readFileLines(String file) {

		ArrayList<String> lines = new ArrayList<String>();
		
        try {
        	Reader reader = new FileReader(file);
        	BufferedReader buffer = new BufferedReader(reader);
        	String line = null;
            while((line = buffer.readLine()) != null) {
                lines.add(line);
            }   
            last_file = file;
            // Always close files.
            reader.close();
        }
        catch(IOException ex) {
            System.out.println("[ERROR] Couldn't read file: " + file);                  
        }
		return lines;
		
	}
	
	public ArrayList<String> readFileLinesPreviousFile() {
		return readFileLines(last_file);
	}
	
	// DRIVER
	public static void main(String [] args) {

		String file = "C:\\Users\\Kevin\\Pictures\\HTBX Assets\\Text.txt";
		FileInput red = new FileInput();
		ArrayList<String> recv = red.readFileLines(file);
		
		for (String line : recv) {
			System.out.println(line);
		}
		
		recv = red.readFileLinesPreviousFile();
		
		for (String line : recv) {
			System.out.println(line);
		}
	}
};