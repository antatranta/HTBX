import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class Rotator extends GraphicsProgram implements ActionListener {
	private static final int HEIGHT = 600;
	private static final int WIDTH = 800;
	public static final String FILE_NAME = "PlayerShip.jpg";
	private GImage img, origImg;
	private int rotation;
	private GRect rect;

	public void run() {
		rotation = 0;
		img = new GImage(FILE_NAME, 200, 200);
		origImg = new GImage(FILE_NAME, 200, 200);
		add(img);
		rect = new GRect(origImg.getX(), origImg.getY(), origImg.getWidth(),
				origImg.getHeight());
		rect.setColor(Color.RED);
		add(rect);
		Timer t = new Timer(100, this);
		t.start();
	}

	public void actionPerformed(ActionEvent e) {
		rotateImg(origImg, img, 10);
		rect.setBounds(img.getX(), img.getY(), img.getWidth(), img.getHeight());
	}

	private void rotateImg(GImage unrotatedImage, GImage imageOnScreen,int degrees) {
		rotation = (rotation + degrees + 360) % 360;

		double halfWidth = unrotatedImage.getWidth()/2;
		double halfHeight = unrotatedImage.getHeight()/2;
		BufferedImage bImage = toBufferedImage(unrotatedImage.getImage());

		// Rotation information
		double rotationRequired = Math.toRadians (rotation);

		AffineTransform objTrans = new AffineTransform();

		//other solution
		objTrans.rotate(rotationRequired, halfWidth, halfHeight);
		AffineTransform translationTransform = findTranslation(rotation,
				unrotatedImage, bImage);
		objTrans.preConcatenate(translationTransform);

		AffineTransformOp op = new AffineTransformOp(objTrans,
				AffineTransformOp.TYPE_BILINEAR);

		// Drawing the rotated image at the required drawing locations
		BufferedImage newBImage = op.filter(bImage, null);

		if(imageOnScreen == null) {
			imageOnScreen = unrotatedImage;
		}
		double diffX = newBImage.getWidth()-imageOnScreen.getWidth();
		double diffY = newBImage.getHeight()-imageOnScreen.getHeight();
		// System.out.println("rotation: " + rotation + " - w, h:" + (diffX) +", " + (diffY));
		imageOnScreen.setImage(newBImage);
		imageOnScreen.setLocation(imageOnScreen.getX() - diffX/2,
				imageOnScreen.getY() - diffY/2);
	}
	/*
	 * find proper translations to keep rotated image correctly displayed
	 */
	private AffineTransform findTranslation(int degrees, GImage origImage,BufferedImage bi) {
		int theta = findTranslationDegrees(degrees);
		double thetaR = Math.toRadians(theta);

		AffineTransform at = new AffineTransform();
		at.rotate(thetaR, origImage.getWidth()/2, origImage.getHeight()/2);

		Point2D p2din, p2dout;
		p2din = new Point2D.Double(0.0, 0.0);
		p2dout = at.transform(p2din, null);
		double ytrans = p2dout.getY();

		p2din = new Point2D.Double(0, bi.getHeight());
		p2dout = at.transform(p2din, null);
		double xtrans = p2dout.getX();

		AffineTransform tat = new AffineTransform();

		// System.out.print("reg: " + -xtrans + ", " + -ytrans + "  --  ");
		tat.translate(-xtrans, -ytrans);
		return tat;
	}

	/* Given an angle, returns something between 0 and 90 that most
closely approximates it's distance to 0 */
	/* examples 0 => 0, 90 => 90, 91 =>89, 180 => 0 */
	private int findTranslationDegrees(int degrees) {
		int spectrum = degrees % 180;
		return 90 - Math.abs(90 - spectrum);
	}

	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img)
	{
		if (img instanceof BufferedImage)
		{
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null),
				img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}
	public void init() {
		setSize(WIDTH, HEIGHT);


	}

}
