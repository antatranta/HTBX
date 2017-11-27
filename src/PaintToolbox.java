import java.awt.Color;

public class PaintToolbox {
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
}
