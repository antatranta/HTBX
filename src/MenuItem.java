import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

// TODO: REWRITE TO FORMAT INTO MOUSE ONLY INPUT
// SINCE FOUND OUT IT IS PRETTY HARD TO CODE BOTH KEYBOARD AND MOSUE INPUT
public class MenuItem extends HBox {
	private Text text;
	
	private static final Font font = Font.font("", FontWeight.BOLD, 24);
	
	public MenuItem(String menuName) {
		super(15);
		setAlignment(Pos.CENTER);
		setMaxWidth(0);
		text = new Text(menuName);
		text.setFont(font);
		text.fillProperty().bind(
				Bindings.when(hoverProperty())
						.then(Color.WHITE)
						.otherwise(Color.GREY)
		);
		
		getChildren().add(text);
		setOnActivate(() -> System.out.println(menuName + " activated"));
	}
	
	public void setOnActivate(Runnable run) {
		setOnMouseClicked(e -> run.run());
	}
}
