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
	private GLabel toggle1;
	private GLabel toggle2;
	private GLabel selection;
	private GObject obj;
	private int count;
	
	public SettingsPane(MainApplication app) {
		this.program = app;
		count = 0;
		selection = new GLabel(">");
		selection.setFont(font);
		selection.setColor(Color.white);
		
		GLabel musicSetting = new GLabel("MUSIC ON");
		GLabel sfxSetting = new GLabel("SFX     ON", 0, 50);
		GLabel back = new GLabel("BACK", 0, 200);
		toggle1 = new GLabel("ON", 50, 0);
		toggle2 = new GLabel("ON", 50, 0);
		
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
		program.add(toggle1);
		program.add(toggle2);
		program.add(selection);
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
					case "MUSIC":
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
		obj = program.getElementAt(e.getX(), e.getY());
		for(GLabel setting:settings) {
			if(obj == setting) {
				selection.setColor(Color.black);
				switch(setting.getLabel()) {
					case "PLAY":
						selection.setLocation(setting.getX() - 25, setting.getY());
						break;
						
					case "SETTINGS":
						selection.setLocation(setting.getX() - 25, setting.getY());
						break;
						
					case "BACK":
						selection.setLocation(setting.getX() - 25, setting.getY());
						break;
						
					default:
						break;
				}
			}
			else if(obj == null) {
				selection.setColor(Color.white);
			}
		}
	}
}
