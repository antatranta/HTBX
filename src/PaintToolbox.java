import java.awt.Color;

public class PaintToolbox {
	public static final Color TRANSPARENT = new Color(0,0,0,0);
	public static final Color BLACK = new Color(0,0,0,255);
	public static final Color WHITE = new Color(255,255,255,255);
	public static final Color RED = new Color(255,20,20,255);
	public static final Color YELLOW = new Color(225, 225, 0, 255);
	public static final Color GREY = new Color(84, 84, 84, 255);
	public static final Color BLUE = new Color(71, 195, 242, 255);
	public static final Color LIGHTGREY = new Color(170,170,170);

	public static Color blend(Color b, Color a, float blending) {

		if(0 >= blending || .9999999999999f <= blending) {
			//			System.out.println("0-1");
			return Color.MAGENTA;
		}

		// uses linear interp.
		// has to run on each channel indep.
		float inverse_blending = 1 - blending;

		float red =   a.getRed()   * blending   +   b.getRed()   * inverse_blending;
		float green = a.getGreen() * blending   +   b.getGreen() * inverse_blending;
		float blue =  a.getBlue()  * blending   +   b.getBlue()  * inverse_blending;
		return new Color ((float)(red / 256), (float)(green / 256), (float)(blue / 256));
	}

	public static Color blendAlpha(Color b, Color a, float blending) {

		if(0 >= blending || .9999999999999f <= blending) {
			//			System.out.println("0-1");
			return Color.MAGENTA;
		}

		// uses linear interp.
		// has to run on each channel indep.
		float inverse_blending = 1 - blending;

		float red =   a.getRed()   * blending   +   b.getRed()   * inverse_blending;
		float green = a.getGreen() * blending   +   b.getGreen() * inverse_blending;
		float blue =  a.getBlue()  * blending   +   b.getBlue()  * inverse_blending;
		float alpha = a.getAlpha() * blending   +   b.getAlpha() * inverse_blending;
		return new Color ((float)(red / 256), (float)(green / 256), (float)(blue / 256), (float)(alpha / 256));
	}

	public static Color setAlpha(Color a, int transparency) {
		return new Color (a.getRed(), a.getGreen(), a.getBlue(), transparency);
	}
}
