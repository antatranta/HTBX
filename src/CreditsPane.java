import acm.graphics.GImage;

import java.awt.event.MouseEvent;

public class CreditsPane extends GraphicsPane {
	private MainApplication program;
	private GImage creditVideo;
	
	public CreditsPane(MainApplication app) {
		program = app;
		
		
	}
	
	@Override
	public void showContents() {
		program.add(creditVideo);
	}
	
	@Override
	public void hideContents() {
		program.remove(creditVideo);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		program.switchToMenu();
	}
}
