import java.util.ArrayList;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GImage;

public class SettingsPane extends GraphicsPane {
	private static final double CENTER_WIDTH = MainApplication.WINDOW_WIDTH / 2;
	private static final double CENTER_HEIGHT = MainApplication.WINDOW_HEIGHT / 2;
	
	private MainApplication program;
	private ArrayList<GLabel> settings = new ArrayList<GLabel>();
	private GLabel toggle1;
	private GLabel toggle2;
	private GLabel selection;
	private GImage title;
	private GObject obj;
	private int count1;
	private int count2;
	
	public SettingsPane(MainApplication app) {
		this.program = app;
		count1 = 0;
		count2 = 0;
		
		title = new GImage("HTBX_Title.png");
		title.setLocation(CENTER_WIDTH - (title.getWidth() / 2), 50);
		
		selection = new GLabel(">");
		selection.setFont(font());
		selection.setColor(Color.white);
		
		toggle1 = new GLabel("ON", 100, 0);
		toggle2 = new GLabel("ON", 0, 50);
		toggle1.setFont(font());
		toggle2.setFont(font());
		toggle1.setColor(Color.black);
		toggle2.setColor(Color.black);
		
		GLabel musicSetting = new GLabel("MUSIC");
		GLabel sfxSetting = new GLabel("SFX", 0, 50);
		GLabel back = new GLabel("BACK", 0, 205);
		
		settings.add(musicSetting);
		settings.add(sfxSetting);
		settings.add(back);
		
		for(GLabel setting:settings) {
			setting.setFont(font());
			setting.setColor(Color.black);
			setting.move(CENTER_WIDTH - (setting.getWidth() / 2), CENTER_HEIGHT - (setting.getHeight() / 2));
			switch(setting.getLabel()) {
				case "MUSIC":
					toggle1.move(CENTER_WIDTH - (setting.getWidth() / 2), CENTER_HEIGHT - (setting.getHeight() / 2));
					break;
				case "SFX":
					setting.move(-12.5, 0);
					toggle2.move(toggle1.getX(), CENTER_HEIGHT - (setting.getHeight() / 2));
					break;
			}
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
		program.add(title);
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
						count1++;
						switch(count1 % 2) {
							case 0:
								toggle1.setLabel("ON");
								program.musicToggle(true);
								break;
							case 1:
								toggle1.setLabel("OFF");
								program.musicToggle(false);
								break;
						}
						break;
						
					case "SFX":
						count2++;
						switch(count2 % 2) {
							case 0:
								toggle2.setLabel("ON");
								program.sfxToggle(true);
								break;
							case 1:
								toggle2.setLabel("OFF");
								program.sfxToggle(false);
								break;
						}
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
					case "MUSIC":
						selection.setLocation(setting.getX() - 25, setting.getY());
						break;
						
					case "SFX":
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
