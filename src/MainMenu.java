import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

// The Main Menu of HTBX
// Credit goes to Almas Baimagambetov for the base code
public class MainMenu extends Application {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	
	private VBox menuOptions;
	private int currentOption = 0;
	
	private Parent createContent() {
		StackPane root = new StackPane();
		root.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		Rectangle backGround = new Rectangle(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		MenuItem quit = new MenuItem("QUIT");
		quit.setOnActivate(() -> System.exit(0));
		
		//Menu options
		menuOptions = new VBox(10, new MenuItem("PLAY"), new MenuItem("SETTINGS"), new MenuItem("SCORES"), new MenuItem("CONTROLS"), quit);
		menuOptions.setAlignment(Pos.CENTER);
		
		getMenuItem(0).setActive(true);
		
		root.getChildren().addAll(backGround, menuOptions);
		StackPane.setAlignment(menuOptions, Pos.CENTER);
		return root;
	}
	
	private MenuItem getMenuItem(int index) {
        return (MenuItem)menuOptions.getChildren().get(index);
    }
	
	// Main application that brings up the main menu
	// As of right now the only way to control the menu is through WASD and ENTER
	@Override
	public void start(Stage primaryStage) {
		Scene scene = new Scene(createContent());
		
		scene.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.W) {
                if(currentOption > 0) {
                    getMenuItem(currentOption).setActive(false);
                    getMenuItem(--currentOption).setActive(true);
                }
            }

            if(event.getCode() == KeyCode.S) {
                if(currentOption < menuOptions.getChildren().size() - 1) {
                    getMenuItem(currentOption).setActive(false);
                    getMenuItem(++currentOption).setActive(true);
                }
            }

            if(event.getCode() == KeyCode.ENTER) {
                getMenuItem(currentOption).activate();
            }
		});
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("HTBX");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}
