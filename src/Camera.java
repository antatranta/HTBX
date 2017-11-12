
public class Camera {
	// @LJ: These weren't being set, and since we don't generate a Camera, 
	//		calling Camera.backendToFrontend uses 0 as the forward/backward ratio parameters
	private static float FORWARD_RATIO = 1;
	private static float BACKWARD_RATIO = 1;
	
	private static Vector2 OFFSET = Vector2.Zero();
	
	public Camera () {
		init();
	}
	
	private void init() {
		
		// Set ratio to 1:1 to start
		FORWARD_RATIO = BACKWARD_RATIO = 1;
		OFFSET = Vector2.Zero();
	}
	
	public void setupCamera(float FR, float BR) {
		FORWARD_RATIO = FR;
		BACKWARD_RATIO = BR;
	}
	
	public static float getForwardRatio() {
		return FORWARD_RATIO;
	}
	
	public static float getBackwardRatio() {
		return BACKWARD_RATIO;
	}
	
	public static void setOffset(Vector2 OFFSET) {
		Camera.OFFSET = OFFSET;
//		.add(new Vector2(MainApplication.WINDOW_WIDTH / 2, MainApplication.WINDOW_HEIGHT / 2));
	}
	
	public static Vector2 getOffset() {
		return OFFSET;
	}
	
	public static Vector2 frontendToBackend(Vector2 pos) {
//		return new Vector2((pos.getX() - OFFSET.getX()) * BACKWARD_RATIO, (pos.getY() - OFFSET.getY()) * BACKWARD_RATIO).add(new Vector2(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT));
		return new Vector2((pos.getX() - OFFSET.getX() - (MainApplication.WINDOW_WIDTH / 2)) * BACKWARD_RATIO, (pos.getY() - OFFSET.getY() - (MainApplication.WINDOW_HEIGHT / 2)) * BACKWARD_RATIO);
//		return new Vector2((pos.getX()) * BACKWARD_RATIO, (pos.getY()) * BACKWARD_RATIO).minus(OFFSET);
	}
	
	public static Vector2 backendToFrontend(Vector2 pos) {
//		return new Vector2((pos.getX() + OFFSET.getX()) * FORWARD_RATIO, (pos.getY() + OFFSET.getY()) * FORWARD_RATIO).minus(new Vector2(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT));
		return new Vector2((pos.getX() + OFFSET.getX() + (MainApplication.WINDOW_WIDTH / 2)) * FORWARD_RATIO, (pos.getY() + OFFSET.getY() + (MainApplication.WINDOW_HEIGHT / 2)) * FORWARD_RATIO);

	}
	
}
