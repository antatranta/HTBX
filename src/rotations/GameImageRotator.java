package rotations;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import acm.graphics.*;
import acm.program.*;

public class GameImageRotator extends GraphicsProgram {
	private static final int HEIGHT = 600;
	private static final int WIDTH = 800;

	public static final int POINT_X = 219;
	public static final int POINT_Y = 199;
	public static final int LABEL_X = 200;
	public static final int LABEL_Y = 100;
	public static final int IMAGE_X = 200;
	public static final int IMAGE_Y = 200;
	public static final int IMAGE_DX = 2;
	public static final int IMAGE_DY = 1;
	public static final int DOT_RADIUS = 2;
	public static final int MS_DELAY = 100;
	public static final int DEGREES = 10;
	
	public static final String FILE_NAME = "PlayerShip.jpg";
	public static final String NO_LABEL = "no";
	public static final String YES_LABEL = "yes";
	
	private GLabel label;
	private GameImage image;
	private GamePolygon poly;
	private GOval oval;
	
	private double x;
	private double y;

	public void init() {
		setSize(WIDTH, HEIGHT);
	}
	
	public void run() {
		x = POINT_X;
		y = POINT_Y;

		label = new GLabel(NO_LABEL, LABEL_X, LABEL_Y);
		image = new GameImage(FILE_NAME, IMAGE_X, IMAGE_Y);
		
		oval = new GOval(x-DOT_RADIUS, x-DOT_RADIUS, DOT_RADIUS*2, DOT_RADIUS*2);
		oval.setColor(Color.blue);
		oval.setFilled(true);
		
		add(label);
		add(oval);
		add(image);
		
		addMouseListeners();
		Timer t = new Timer(MS_DELAY, this);
		t.start();
	}

	public void actionPerformed(ActionEvent e) {
		image.rotate(DEGREES);
		//image.move(IMAGE_DX, IMAGE_DY);
		if(getElementAt(oval.getX() + oval.getWidth()/2, oval.getY() + oval.getHeight()/2) == image) {
			label.setLabel(YES_LABEL);
		}else{
			label.setLabel(NO_LABEL);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		image.setLocation(x, y);
	}
}
