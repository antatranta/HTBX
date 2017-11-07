import java.util.ArrayList;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class SettingsPane extends GraphicsPane {
	private static final Font font = new Font("", Font.BOLD, 20);
	private static double yPos = 225;
	private static double xPos = 345;
	
	private MainApplication program;
	private ArrayList<GLabel> settings = new ArrayList<GLabel>();
	private ArrayList<GLabel> onOff = new ArrayList<GLabel>();
	private GObject obj;
	
	public SettingsPane(MainApplication app) {
		this.program = app;
		
		GLabel musicSetting = new GLabel("MUSIC", xPos, yPos);
		GLabel sfxSetting = new GLabel("SFX", xPos, yPos + 50);
		GLabel back = new GLabel("BACK", xPos, yPos + 150);
		GLabel off = new GLabel("OFF", xPos + 100, yPos);
		GLabel on = new GLabel("ON", xPos + 100, yPos);
		GLabel off2 = new GLabel("OFF", xPos + 100, yPos + 50);
		GLabel on2 = new GLabel("ON", xPos + 100, yPos + 50);
		
		settings.add(musicSetting);
		settings.add(sfxSetting);
		settings.add(back);		
		onOff.add(off);
		onOff.add(on);
		onOff.add(off2);
		onOff.add(on2);
		for(GLabel setting:settings) {
			setting.setFont(font);
			setting.setColor(Color.black);
		}
	}
	
	@Override
	public void showContents() {
		for(GLabel setting:settings) {
			program.add(setting);
		}
		for(GLabel toggle:onOff) {
			program.add(toggle);
		}
	}

	@Override
	public void hideContents() {
		for(GLabel setting:settings) {
			program.remove(setting);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		for(GLabel setting:settings) {
			if(obj == setting) {
				switch(setting.getLabel()) {
					case "MUSIC":
						
						break;
						
					case "SFX":
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
