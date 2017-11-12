package rotations;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import acm.graphics.GPolygon;

public class GameImage extends GImage {
	private double rotation;
	private GImage origImage;
	private GamePolygon poly;
	
	private void initGameImage() {
		rotation = 0;
		poly = createBoundedPolygon(origImage);
	}
	
	private GamePolygon createBoundedPolygon(GImage rect) {
		GamePolygon p = new GamePolygon(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);
		p.addVertex(-rect.getWidth()/2, -rect.getHeight()/2);
		p.addEdge(rect.getWidth(), 0);
		p.addEdge(0, rect.getHeight());
		p.addEdge(-rect.getWidth(), 0);
		p.addEdge(0, -rect.getHeight());
		p.markAsComplete();
		return p;
	}
	
	public GameImage(Image arg0, double arg1, double arg2) {
		super(arg0, arg1, arg2);
		origImage = new GImage(arg0, arg1, arg2);
		initGameImage();
	}

	public GameImage(Image arg0) {
		super(arg0);
		origImage = new GImage(arg0);
		initGameImage();
	}

	public GameImage(int[][] arg0, double arg1, double arg2) {
		super(arg0, arg1, arg2);
		origImage = new GImage(arg0, arg1, arg2);
		initGameImage();
	}

	public GameImage(int[][] arg0) {
		super(arg0);
		origImage = new GImage(arg0);
		initGameImage();
	}

	public GameImage(String arg0, double arg1, double arg2) {
		super(arg0, arg1, arg2);
		origImage = new GImage(arg0, arg1, arg2);
		initGameImage();
	}

	public GameImage(String arg0) {
		super(arg0);
		origImage = new GImage(arg0);
		initGameImage();
	}
	
	/**
	 * sets the angle of the image to that set of degrees, and
	 * then calls rotate to update the image, where it rotates by (0);
	 * 
	 * @param degrees angle to setobject to
	 */
	public void setDegrees(double degrees) {
		rotation = degrees;
		rotate(0);
	}
	
	public double getDegrees() {
		return rotation;
	}
	
	public double getRadians() {
		return Math.toRadians(rotation);
	}
	
	/**
	 * Rotates the image around the center which may cause the width and
	 * height of the image to change as well as its location
	 * 
	 * @param degrees the number of degrees to rotate around the center
	 */
	public void rotate(int degrees) {
		rotation = (rotation + degrees + 360) % 360;

		double halfWidth = origImage.getWidth()/2;
		double halfHeight = origImage.getHeight()/2;
		BufferedImage bImage = toBufferedImage(origImage.getImage());

		// Rotation information
		double radians = Math.toRadians (rotation);
		AffineTransform objTrans = new AffineTransform();

		objTrans.rotate(radians, halfWidth, halfHeight);
		AffineTransform translationTransform = findTranslation(rotation, origImage, bImage);
		objTrans.preConcatenate(translationTransform);

		AffineTransformOp op = new AffineTransformOp(objTrans, AffineTransformOp.TYPE_BILINEAR);

		// Drawing the rotated image at the required drawing locations
		BufferedImage newBImage = op.filter(bImage, null);

		double diffX = newBImage.getWidth()-getWidth();
		double diffY = newBImage.getHeight()-getHeight();
		setImage(newBImage);
		setLocation(getX() - diffX/2, getY() - diffY/2, false);
		poly.rotate(-degrees);
		poly.recenter();
	}
	
	@Override
	public boolean contains(double x, double y) {
		Polygon p = poly.getPolygon();
//		System.out.println(p);
		if(p.contains(x, y)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void move(double dx, double dy) {
		super.move(dx, dy);
		poly.move(dx, dy);
	}
	
	@Override
	public void setLocation(double x, double y) {
		setLocation(x, y, true);
	}
	
	public void setLocation(double x, double y, boolean movePoly) {
		super.setLocation(x, y);
		if(poly != null && movePoly) {
			poly.setLocation(x+getWidth()/2, y+getHeight()/2);
		}
	}
	
	public void setLocationRespectSize(double x, double y) {
		setLocation(x - (origImage.getWidth()), y - (origImage.getHeight()), true);
	}
	
	/* setSize is not supported for the polygon */
	@Override
	public void setSize(double width, double height) {
		
	}
	
	@Override
	public void scale(double sx, double sy) {
		super.scale(sx, sy);
		poly.scale(sx, sy);
	}
	
//	@Override
//	public void setImage(Image image) {
//		super.setImage(image);
//		initGameImage();
//	}
	
	@Override
	public void setImage(String name) {
		super.setImage(name);
		initGameImage();
	}
	
	/**
	 * find proper translations to keep rotated image correctly displayed
	 * This was hobbled together from stackoverflow & google searches, but modified to work with GImages
	 * 
	 * @param degrees the angle that we wish to rotate to.
	 * @param origImage the original, unrotated image
	 * @param bi the rotated buffered image
	 * @return the affinetransform that contains the translation calculation 
	 * needed to say how much the image needed to be moved so that it won't clip
	 */
	private AffineTransform findTranslation(double degrees, GImage origImage, BufferedImage bi) {
		double theta = findTranslationDegrees(degrees);
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

	/**
	 *  Given an angle in degrees, returns something between 0 and 90 that most closely approximates it's distance to 0
	  examples 0 => 0, 90 => 90, 91 =>89, 180 => 0
	 *
	 * @param degrees an angle represented in degrees
	 * @return a number between 0 and 90 degrees
	 */
	private double findTranslationDegrees(double degrees) {
		double spectrum = degrees % 180;
		return 90 - Math.abs(90 - spectrum);
	}

	/**
	 * Converts a given Image into a BufferedImage
	 * 
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	private static BufferedImage toBufferedImage(Image img)
	{
		if (img instanceof BufferedImage)
		{
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}
	
	public GamePolygon getPolygonBounds() {
		return poly;
	}
}
