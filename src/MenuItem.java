import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Glow;

import javafx.scene.effect.Effect;

// TODO: REWRITE TO FORMAT INTO MOUSE ONLY INPUT
// SINCE FOUND OUT IT IS PRETTY HARD TO CODE BOTH KEYBOARD AND MOSUE INPUT
public class MenuItem extends HBox {
	private Text text;
	private Runnable script;
	
	private static final Font font = Font.font("", FontWeight.BOLD, 24);
	private Effect glow = new Glow(1);
    private Effect noGlow = new Glow(0);
	
	public MenuItem(String menuName) {
		super(15);
		setAlignment(Pos.CENTER);
		setMaxWidth(0);
		text = new Text(menuName);
		text.setFont(font);
		
		/*text.fillProperty().bind(
				Bindings.when(hoverProperty())
						.then(Color.WHITE)
						.otherwise(Color.GREY)
		);*/
		text.effectProperty().bind(
                Bindings.when(hoverProperty())
                        .then(glow)
                        .otherwise(noGlow)
        );
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
