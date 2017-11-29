import java.lang.reflect.Array;

public class BulletEmitterData {
	public static final int TOTAL_TYPES = BulletType.values().length; // Also max priority
	
	public static final int STRAIGHT_INDEX = 0;
	public static final int OSCILLATE_INDEX = 1;
	public static final int WAVE_INDEX = 2;
	public static final int CLOCKWISE_INDEX = 3;
	public static final int COUNTER_INDEX = 4;

	private int[] bullets;
	private int[] priority;
	
	public BulletEmitterData() {
		bullets = new int[TOTAL_TYPES];
		priority = new int[TOTAL_TYPES];
		for (int i = 0; i < TOTAL_TYPES; i++) {
			bullets[i] = 0;
			priority[i] = i;
		}
	}
	
	public BulletEmitterData(int str, int osc, int wave, int scw, int sccw) {
		bullets = new int[TOTAL_TYPES];
		this.bullets[STRAIGHT_INDEX] = str;
		this.bullets[OSCILLATE_INDEX] = osc;
		this.bullets[WAVE_INDEX] = wave;
		this.bullets[CLOCKWISE_INDEX] = scw;
		this.bullets[COUNTER_INDEX] = sccw;
		priority = new int[TOTAL_TYPES];
		for (int i = 0; i < TOTAL_TYPES; i++) {
			priority[i] = i;
		}
	}
	
	public BulletEmitterData(int str, int osc, int wave, int scw, int sccw, int[] priority) {
		bullets = new int[TOTAL_TYPES];
		this.bullets[STRAIGHT_INDEX] = str;
		this.bullets[OSCILLATE_INDEX] = osc;
		this.bullets[WAVE_INDEX] = wave;
		this.bullets[CLOCKWISE_INDEX] = scw;
		this.bullets[COUNTER_INDEX] = sccw;
		this.priority = priority;
	}
	
	public boolean checkBank() {
		for (int i = 0; i < TOTAL_TYPES; i++) {
			if (bullets[i] > 0) {
				return true;
			}
		}
		return false;
	}
	
	// SET: Set the values to a value
	
	public void setIndex(int ind, int amnt) {
		if (ind > TOTAL_TYPES) {
			System.out.println("[WARNING] Attempt to index more than TOTAL_TYPES in BulletEmitterData");
			return;
		}
		this.bullets[ind] = amnt;
	}
	
	public void setStraight(int amnt) {
		this.bullets[STRAIGHT_INDEX] = amnt;
	}
	
	public void setOscillate(int amnt) {
		this.bullets[OSCILLATE_INDEX] = amnt;
	}
	
	public void setWave(int amnt) {
		this.bullets[WAVE_INDEX] = amnt;
	}
	
	public void setClockwise(int amnt) {
		this.bullets[CLOCKWISE_INDEX] = amnt;
	}
	
	public void setCounterClockwise(int amnt) {
		this.bullets[COUNTER_INDEX] = amnt;
	}
	
	// REFILL: Add a value to the current value
	
	public void addIndex(int ind, int amnt) {
		if (ind > TOTAL_TYPES) {
			System.out.println("[WARNING] Attempt to index more than TOTAL_TYPES in BulletEmitterData");
			return;
		}
		this.bullets[ind] += amnt;
	}

	public void addStraight(int amnt) {
		this.bullets[STRAIGHT_INDEX] += amnt;
	}
	
	public void addOscillate(int amnt) {
		this.bullets[OSCILLATE_INDEX] += amnt;
	}
	
	public void addWave(int amnt) {
		this.bullets[WAVE_INDEX] += amnt;
	}
	
	public void addClockwise(int amnt) {
		this.bullets[CLOCKWISE_INDEX] += amnt;
	}
	
	public void addCounterClockwise(int amnt) {
		this.bullets[COUNTER_INDEX] += amnt;
	}
	
	// GET: Return the values of that bullet type
	
	public int getIndex(int ind) {
		if (ind > TOTAL_TYPES) {
			System.out.println("[WARNING] Attempt to index more than TOTAL_TYPES in BulletEmitterData");
			return 0;
		}
		return this.bullets[ind];
	}
	
	public int getStraight() {
		return this.bullets[STRAIGHT_INDEX];
	}
	
	public int getOscillate() {
		return this.bullets[OSCILLATE_INDEX];
	}
	
	public int getWave() {
		return this.bullets[WAVE_INDEX];
	}
	
	public int getClockwise() {
		return this.bullets[CLOCKWISE_INDEX];
	}
	
	public int getCounterClockwise() {
		return this.bullets[COUNTER_INDEX];
	}
	
	public int[] getPriorities() {
		return this.priority;
	}
}
