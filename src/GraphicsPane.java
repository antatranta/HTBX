import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;

public abstract class GraphicsPane implements Interfaceable {
	public static final Font font = new Font("", Font.BOLD, 20);
	public static final double CENTER_WIDTH = MainApplication.WINDOW_WIDTH / 2;
	public static final double CENTER_HEIGHT = MainApplication.WINDOW_HEIGHT / 2;
	public static final GLabel selection = new GLabel(">");
	
	private static final GImage title = new GImage("HTBX_Title.png");
	
	public GImage title() {
		title.setLocation(CENTER_WIDTH - (title.getWidth() / 2), 50);
		return title;
	}
	
	public GLabel selection() {
		selection.setFont(font);
		selection.setColor(Color.black);
		selection.setVisible(false);
		return selection;
	}
	
	@Override
	public abstract void showContents();

	@Override
	public abstract void hideContents();

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

}
