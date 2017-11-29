
public class Camera {
	// @LJ: These weren't being set, and since we don't generate a Camera, 
	//		calling Camera.backendToFrontend uses 0 as the forward/backward ratio parameters
	private static float FORWARD_RATIO = 1;
	private static float BACKWARD_RATIO = 1;

	private static int WINDOW_WIDTH; 
	private static int WINDOW_HEIGHT;

	private static Vector2 OFFSET = Vector2.Zero();

	public Camera () {
		init();
	}

	public Camera (float FR, float BR, int width, int height) {
		WINDOW_WIDTH = width;
		WINDOW_HEIGHT = height;
		FORWARD_RATIO = FR;
		BACKWARD_RATIO = BR;
	}

	private void init() {
		// Set ratio to 1:1 to start
		WINDOW_WIDTH = MainApplication.WINDOW_WIDTH;
		WINDOW_HEIGHT = MainApplication.WINDOW_HEIGHT;
		FORWARD_RATIO = BACKWARD_RATIO = 1;
		OFFSET = Vector2.Zero();
	}

	public void setupCamera(float FR, float BR) {
		FORWARD_RATIO = FR;
		BACKWARD_RATIO = BR;
	}

	public void setFrame(int width, int height) {
		WINDOW_WIDTH = width;
		WINDOW_HEIGHT = height;
	}

	public static float getForwardRatio() {
		return FORWARD_RATIO;
	}

	public static float getBackwardRatio() {
		return BACKWARD_RATIO;
	}

	public static void setOffset(Vector2 OFFSET) {
		Camera.OFFSET = OFFSET;
	}

	public static Vector2 getOffset() {
		return OFFSET;
	}

	public static Vector2 frontendToBackend(Vector2 pos) {
		return new Vector2((pos.getX() - OFFSET.getX() - (WINDOW_WIDTH / 2)) * BACKWARD_RATIO, (pos.getY() - OFFSET.getY() - (WINDOW_HEIGHT / 2)) * BACKWARD_RATIO);
	}

	public static Vector2 backendToFrontend(Vector2 pos) {
		return new Vector2((pos.getX() + OFFSET.getX() + (WINDOW_WIDTH / 2)) * FORWARD_RATIO, (pos.getY() + OFFSET.getY() + (WINDOW_HEIGHT / 2)) * FORWARD_RATIO);

	}

	public static Vector2 frontendToBackend(Vector2 pos, Vector2 size) {
		return new Vector2((pos.getX() - OFFSET.getX() + (size.getX() /2) - (WINDOW_WIDTH / 2)) * BACKWARD_RATIO, (pos.getY() - OFFSET.getY() + (size.getY()/2) - (WINDOW_HEIGHT / 2)) * BACKWARD_RATIO);
	}

	public static Vector2 backendToFrontend(Vector2 pos, Vector2 size) {
		return new Vector2((pos.getX() + OFFSET.getX() - (size.getX() /2) + (WINDOW_WIDTH / 2)) * FORWARD_RATIO, (pos.getY() + OFFSET.getY() - (size.getY()/2) + (WINDOW_HEIGHT / 2)) * FORWARD_RATIO);

	}

}
