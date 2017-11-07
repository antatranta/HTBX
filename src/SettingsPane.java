import java.util.ArrayList;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class SettingsPane extends GraphicsPane {
	private static final Font font = new Font("", Font.BOLD, 20);
	
	private MainApplication program;
	private GLabel musicSetting;
	private GLabel sfxSetting;
	private GLabel back;
	private GLabel on;
	private GLabel off;
	private ArrayList<GLabel> settings;
	
	public SettingsPane(MainApplication app) {
		program = app;
		
		musicSetting = new GLabel("MUSIC");
		sfxSetting = new GLabel("SFX");
		back = new GLabel("BACK");
		on = new GLabel("ON");
		off = new GLabel("OFF");
	}
	
	@Override
	public void showContents() {
		for(GLabel setting:settings) {
			program.add(setting);
		}
	}

	@Override
	public void hideContents() {
		for(GLabel setting:settings) {
			program.remove(setting);
		}
	}
}
