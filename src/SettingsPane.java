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
		
		GLabel musicSettingOn = new GLabel("MUSIC ON");
		GLabel musicSettingOff = new GLabel("MUSIC OFF");
		GLabel sfxSettingOn = new GLabel("SFX     ON", 0, 50);
		GLabel sfxSettingOff = new GLabel("SFX     OFF", 0, 50);
		GLabel back = new GLabel("BACK", 0, 200);
		
		musicSettingOff.setVisible(false);
		sfxSettingOff.setVisible(false);
		settings.add(musicSettingOff);
		settings.add(musicSettingOn);
		settings.add(sfxSettingOff);
		settings.add(sfxSettingOn);
		settings.add(back);		
		
		//double yPos = CENTER_HEIGHT - (musicSettingOn.getHeight() / 2);
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
		for(GLabel setting:settings) {
			program.remove(setting);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		System.out.println(obj);
		for(GLabel setting:settings) {
			if(obj == setting) {
				switch(setting.getLabel()) {
					case "MUSIC ON":
						setting.setVisible(false);
						setting.sendToBack();
						for(GLabel musicToggle:settings) {
							if(musicToggle.getLabel() == "MUSIC OFF") {
								musicToggle.setVisible(true);
								musicToggle.sendToFront();
							}
						}
						break;
					
					case "MUSIC OFF":
						setting.setVisible(false);
						setting.sendToBack();
						for(GLabel musicToggle:settings) {
							if(musicToggle.getLabel() == "MUSIC ON") {
								musicToggle.setVisible(true);
								musicToggle.sendToFront();
							}
						}
						
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
