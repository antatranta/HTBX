import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;

public abstract class GraphicsPane implements Interfaceable, ActionListener {
	public static final Font font = new Font("", Font.BOLD, 20);
	public static final double CENTER_WIDTH = MainApplication.WINDOW_WIDTH / 2;
	public static final double CENTER_HEIGHT = MainApplication.WINDOW_HEIGHT / 2;
	public static final GLabel selection = new GLabel(">");

	private static final GRect whiteBG = new GRect(0, 0, MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
	private static final GImage title = new GImage("HTBX_Title.png");
	private static final GLabel clickToContinue = new GLabel("CLICK TO CONTINUE...");
	
	private Timer fadingLabel;
	private int colorRed;
	private int colorBlue;
	private int colorGreen;
	private int i;
	private int j;
	private int k;

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

	public GRect whiteBG() {
		whiteBG.setFilled(true);
		whiteBG.setColor(Color.white);
		return whiteBG;
	}
	
	public GLabel clickToContinue() {
		clickToContinue.setFont(font);
		clickToContinue.setColor(Color.white);
		clickToContinue.setLocation(5, MainApplication.WINDOW_HEIGHT - 10);
		return clickToContinue;
	}
	
	public void startFadingLabel() {
		colorRed = 0;
		colorBlue = 0;
		colorGreen = 0;
		i = 1;
		j = 1;
		k = 1;
		
		fadingLabel = new Timer(5, this);
		fadingLabel.start();
	}
	
	public void stopFadingLabel() {
		fadingLabel.stop();
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		clickToContinue.setColor(new Color(colorRed, colorGreen, colorBlue));


		switch(colorRed % 256) {
		case 0:
			i = 1;
			break;

		case 255:
			i = -1;
			break;

		default:
			break;
		}
		switch(colorGreen % 256) {
		case 0:
			j = 1;
			break;

		case 255:
			j = -1;
			break;

		default:
			break;
		}
		switch(colorBlue % 256) {
		case 0:
			k = 1;
			break;

		case 255:
			k = -1;
			break;

		default:
			break;
		}

		colorRed += i;
		colorGreen += j;
		colorBlue += k;
	}
}
