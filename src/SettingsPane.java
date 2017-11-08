import java.util.ArrayList;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class SettingsPane extends GraphicsPane {
	private static final Font font = new Font("", Font.BOLD, 20);
	private static final double CENTER_WIDTH = MainApplication.WINDOW_WIDTH / 2;
	private static final double CENTER_HEIGHT = MainApplication.WINDOW_HEIGHT / 2;
	
	private MainApplication program;
	private ArrayList<GLabel> settings = new ArrayList<GLabel>();
	private GObject obj;
	
	public SettingsPane(MainApplication app) {
		this.program = app;
		
		GLabel musicSetting = new GLabel("MUSIC ON");
		GLabel sfxSetting = new GLabel("SFX     ON", 0, 50);
		GLabel back = new GLabel("BACK", 0, 200);
		
		settings.add(musicSetting);
		settings.add(sfxSetting);
		settings.add(back);		
		
		for(GLabel setting:settings) {
			setting.setFont(font);
			setting.setColor(Color.black);
			setting.move(CENTER_WIDTH - (setting.getWidth() / 2), CENTER_HEIGHT - (setting.getHeight() / 2));
		}
	}
	
	@Override
	public void showContents() {
		for(GLabel setting:settings) {
			program.add(setting);
		}
	}

	@Override
	public void hideContents() {
		program.removeAll();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		for(GLabel setting:settings) {
			if(obj == setting) {
				switch(setting.getLabel()) {
					case "MUSIC ON":
						setting.setLabel("MUSIC OFF");
						break;
					
					case "MUSIC OFF":
						setting.setLabel("MUSIC ON");
						break;
						
					case "SFX     ON":
						setting.setLabel("SFX     OFF");
						break;
						
					case "SFX     OFF":
						setting.setLabel("SFX     ON");
						break;
						
					case "BACK":
						program.switchToMenu();
						break;
					
					default:
						break;
				}
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		return;
	}
}
