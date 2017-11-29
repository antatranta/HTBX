import java.util.ArrayList;
import java.util.Collections;
import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class ScoresPane extends GraphicsPane {
	private MainApplication program;
	private ArrayList<String> scores;
	private ArrayList<GLabel> displayScores;
	private GLabel back;
	private GObject obj;
	
	public ScoresPane(MainApplication app) {
		program = app;
		
		scores = new ArrayList<String>();
		String fileName = "Scores.txt";
		String line = null;
		
		try {
			FileReader fileReader = new FileReader(fileName);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) {
				scores.add(line);
			}
			
			bufferedReader.close();
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file \"" + fileName + "\"");
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		
		Collections.sort(scores, Collections.reverseOrder());
		displayScores = new ArrayList<GLabel>();
		
		//Only display the top 5 scores
		int j = 0;
		for(int i = 0; i < 5; i++) {
			GLabel temp = new GLabel(scores.get(i));
			temp.setFont(font);
			temp.setColor(Color.black);
			temp.setLocation(CENTER_WIDTH - (temp.getWidth() / 2), CENTER_HEIGHT - (temp.getHeight() / 2) + j);
			displayScores.add(temp);
			j += 25;
		}
		
		back = new GLabel("BACK");
		back.setFont(font);
		back.setColor(Color.black);
		back.setLocation(CENTER_WIDTH - (back.getWidth() / 2), CENTER_HEIGHT - (back.getHeight() / 2) + 205);
	}
	
	@Override
	public void showContents() {
		program.add(whiteBG());
		program.add(title());
		for(GLabel scoresToDisplay:displayScores) {
			program.add(scoresToDisplay);
		}
		program.add(back);
		program.add(selection());
		selection.setLocation(back.getX() - 25, back.getY());
	}

	@Override
	public void hideContents() {
		program.remove(whiteBG());
		program.remove(title());
		for(GLabel scoresToDisplay:displayScores) {
			program.remove(scoresToDisplay);
		}
		program.remove(back);
		program.remove(selection());
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		if(obj == back) {
			back.setColor(Color.gray);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		back.setColor(Color.black);
		obj = program.getElementAt(e.getX(), e.getY());
		if(obj == back) {
			if(!program.isPaused()) {
				program.switchToMenu();
			}
			else {
				program.switchToPause();
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		if(obj == back) {
			selection.setVisible(true);
		}
		else {
			selection.setVisible(false);
		}
	}
}
