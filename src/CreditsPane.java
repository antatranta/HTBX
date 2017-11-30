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
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				program.switchToMenu();
			}
		}, 90000);
		timer.purge();
	}
	
	@Override
	public void showContents() {
		program.add(whiteBG());
		//program.add(creditVideo);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				program.add(clickToContinue());
				startFadingLabel();
			}
		}, 30000);
	}
	
	@Override
	public void hideContents() {
		program.remove(whiteBG());
		program.remove(clickToContinue());
		stopFadingLabel();
		//program.remove(creditVideo);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		program.switchToMenu();
	}
}
