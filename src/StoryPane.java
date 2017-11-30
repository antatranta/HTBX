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
	private GImage story;
	private GRect blackBG;
	private int switchStory;

	public StoryPane(MainApplication app) {
		program = app;
		switchStory = 0;

		story = new GImage("Story_1.png", 0, 0);

		blackBG = new GRect(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		blackBG.setFilled(true);
		blackBG.setColor(Color.black);
	}
	
	public void setSwitchStory(int num) {
		switchStory = num;
	}

	@Override
	public void showContents() {

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
		switchStory++;

		program.add(blackBG);
		program.add(story);
		program.add(clickToContinue());
		startFadingLabel();
	}

	@Override
	public void hideContents() {
		program.remove(blackBG);
		program.remove(story);
		program.remove(clickToContinue());
		stopFadingLabel();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(program.lookedAtControls()) {
			program.switchToGame();
		}
		else {
			program.switchToControls();
		}
	}
}
