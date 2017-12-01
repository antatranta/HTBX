import java.util.Timer;
import java.util.TimerTask;

import java.awt.event.MouseEvent;

import acm.graphics.GImage;

public class CreditsPane extends GraphicsPane {
	private MainApplication program;
	private GImage creditVideo;
	private Timer timer;
	
	public CreditsPane(MainApplication app) {
		program = app;
		program.removeAll();
		
		creditVideo = new GImage("Credits.gif", 0, 0);
		creditVideo.setLocation(CENTER_WIDTH - creditVideo.getWidth() / 2, CENTER_HEIGHT - creditVideo.getHeight() / 2);
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				program.switchToMenu();
			}
		}, 93500);
	}
	
	@Override
	public void showContents() {
		program.add(creditVideo);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				program.add(clickToContinue());
			}
		}, 30000);
		startFadingLabel();
	}
	
	@Override
	public void hideContents() {
		timer.cancel();
		timer.purge();
		program.removeAll();
		stopFadingLabel();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		program.switchToMenu();
	}
}
