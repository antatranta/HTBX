import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class GameTimer implements ActionListener{
	private Timer gameTimer;
	private boolean callEvents = false;
	
	public GameTimer() {
	}
	
	public void setupTimer(int interval, int initialDelay) {
		gameTimer = new Timer(interval, this);
		gameTimer.setInitialDelay(initialDelay);
		callEvents = false;
	}
	
	public void startTimer() {
		gameTimer.start();
	}
	
	public void stopCalls() {
		callEvents = false;
	}
	
	public void startCalls() {
		callEvents = true;
	}
	
	public void stopTimer() {
		gameTimer.stop();
	}
	
	public void addListener(Object object) {
		if((ActionListener) object != null) {
			this.gameTimer.addActionListener((ActionListener) object);
		}
	}
	
	//@Override
	public void actionPerformed(ActionEvent e) {
		if (callEvents) {
			
		}
	}
}