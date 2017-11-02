import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MenuItem extends HBox {
	private Text text;
	private Runnable script;
	
	private static final Font font = Font.font("", FontWeight.BOLD, 24);
	
	public MenuItem(String menuName) {
		super(15);
		//hoverProperty().addListener(null);
		// TODO add mouseHover and mouseClick
		setAlignment(Pos.CENTER);
		
		text = new Text(menuName);
		text.setFont(font);
		
		getChildren().add(text);
		setActive(false);
		setOnActivate(() -> System.out.println(menuName + " activated"));
	}
	
	public void setActive(boolean bool) {
		text.setFill(bool ? Color.WHITE : Color.GREY);
	}
	
	public void setOnActivate(Runnable run) {
		script = run;
	}
	
	public void activate() {
		if (script != null) {
			script.run();
		}
	}
}
