
public class Camera {
	// @LJ: These weren't being set, and since we don't generate a Camera, 
	//		calling Camera.backendToFrontend uses 0 as the forward/backward ratio parameters
	private static float FORWARD_RATIO = 1;
	private static float BACKWARD_RATIO = 1;
	
	public void init() {
		
		// Set ratio to 1:1 to start
		FORWARD_RATIO = BACKWARD_RATIO = 1;
	}
	
	public static float getForwardRatio() {
		return FORWARD_RATIO;
	}
	
	public static float getBackwardRatio() {
		return BACKWARD_RATIO;
	}
	
	public static Vector2 frontendToBackend(Vector2 pos) {
		return new Vector2(pos.getX() * BACKWARD_RATIO, pos.getY() * BACKWARD_RATIO);
	}
	
	public static Vector2 backendToFrontend(Vector2 pos) {
		return new Vector2(pos.getX() * FORWARD_RATIO, pos.getY() * FORWARD_RATIO);
	}
	
}
