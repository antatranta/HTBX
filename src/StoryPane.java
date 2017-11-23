import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GImage;
import acm.graphics.GRect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class StoryPane extends GraphicsPane implements ActionListener {
	private MainApplication program;
	private GLabel clickToContinue;
	private GImage story;
	private GRect blackBG;
	private int switchStory;
	private int colorRed;
	private int colorBlue;
	private int colorGreen;
	private int i;
	private int j;
	private int k;
	
	public StoryPane(MainApplication app) {
		program = app;
		switchStory = 0;
		colorRed = 0;
		colorBlue = 0;
		colorGreen = 0;
		i = 1;
		j = 1;
		k = 1;
		
		clickToContinue = new GLabel("CLICK TO CONTINUE...", 0, 0);
		clickToContinue.setFont(font);
		clickToContinue.setColor(new Color(colorRed, colorBlue, colorGreen));
		clickToContinue.setLocation(5, MainApplication.WINDOW_HEIGHT - 10);
		
		story = new GImage("Story_1.png", 0, 0);
		
		blackBG = new GRect(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		blackBG.setFilled(true);
		blackBG.setColor(Color.black);
	}
	
	@Override
	public void showContents() {
		Timer fadingLabel = new Timer(5, this);
		fadingLabel.start();
		
		switch(switchStory % 3) {
			case 1:
				story.setImage("Story_2.png");
				break;
				
			case 2:
				story.setImage("Story_3.png");
				break;
			
			default:
				break;				
		}
		story.setLocation(CENTER_WIDTH - (story.getWidth() / 2), CENTER_HEIGHT - (story.getHeight() / 2));
		
		program.add(blackBG);
		program.add(story);
		program.add(clickToContinue);
	}
	
	@Override
	public void hideContents() {
		program.remove(blackBG);
		program.remove(story);
		program.remove(clickToContinue);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		program.switchToGame();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		clickToContinue.setColor(new Color(colorRed, colorGreen, colorBlue));
        
        if(colorRed % 256 == 255) {
            i = -1;
        }
        else if(colorRed % 256 == 0) {
            i = 1;
        }
        
        if(colorGreen % 256 == 255) {
            j = -1;
        }
        else if(colorGreen % 256 == 0) {
            j = 1;
        }
        
        if(colorBlue % 256 == 255) {
            k = -1;
        }
        else if(colorBlue % 256 == 0) {
            k = 1;
        }
        
        colorRed += i;
        colorGreen += j;
        colorBlue += k;
	}
}
