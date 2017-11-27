import java.util.Random;

public class LavaLamp {
	private static Random rand;
	
	public static void setup(float SEED) {
		rand = new Random((long) SEED);
	}
	
	public static int randomNumber(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		int rtrn = rand.nextInt((max - min) + 1) + min;
		return rtrn;
	}
	
	public static int randomSignedInt(int min, int max) {
		int sign = 0;
		while(sign == 0) {
			sign = randomNumber(-1,1);
		}
		return randomNumber(min, max) * sign;
	}
	
	public static float nextFloat() {
		return rand.nextFloat();
	}
	
	public static int randomRange(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
}
