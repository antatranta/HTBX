import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;
import rotations.GameImage;

// This one uses LJ's image

public class DisplayableHUD2 implements Displayable {
	private MainApplication program;
	private PlayerShip player;
	
	private GImage status_back;
	private GRect status_bar_hp;
	private GRect status_bar_hp_back;
	private GRect status_bar_shield;
	private GRect status_bar_shield_back;
	private GRect compass_back;
	private GameImage compass_sprite;
	
	private double bar_max_x = 338;
	private double bar_max_y = 58;
	
	
	public DisplayableHUD2(MainApplication program, PlayerShip player) {
		this.program = program;
		this.player = player;
		init();
	}
	
	private void init() {
		status_back = new GImage("Backing Debug.png", 0, 0);
		status_back.move(0, MainApplication.WINDOW_HEIGHT - status_back.getHeight());
		
		compass_sprite = new GameImage("Compass2.png", status_back.getX() + 362, status_back.getY() + 25);
		
		status_bar_hp = new GRect(status_back.getLocation().getX() + 22, status_back.getY() + 85, bar_max_x, bar_max_y);
		status_bar_hp.setFillColor(Color.GREEN);
		status_bar_hp.setFilled(true);
		status_bar_hp.setColor(Color.YELLOW);
		
		status_bar_shield = new GRect(status_back.getLocation().getX() + 22, status_back.getY() + 25, bar_max_x, bar_max_y);
		status_bar_shield.setFillColor(Color.BLUE);
		status_bar_shield.setFilled(true);
		status_bar_shield.setColor(Color.CYAN);
		

	}
	
	private void scaleStatusBar(GRect bar, double percent) {
		bar.setSize(bar_max_x * percent, bar.getHeight());
	}
	
	private void aimCompass(GameImage compass, Vector2 spot) {
		double x = spot.getX() - player.getPhysObj().getPosition().getX();
		double y = spot.getY() - player.getPhysObj().getPosition().getY();
		compass_sprite.setDegrees(Math.toDegrees(Math.atan2(y, x)));
	}
	
	public void updateHUD() {
		scaleStatusBar(status_bar_hp, (double)player.getCurrentHealth() / (double)player.getStats().getHealthMax());
		scaleStatusBar(status_bar_shield, (double)player.getCurrentShield() / (double)player.getStats().getShieldMax());
		aimCompass(compass_sprite, new Vector2(0,0));
	}
	
	public void layerSprites() {
		status_bar_hp.sendToBack();
		status_bar_shield.sendToBack();
		status_back.sendToBack();
		compass_sprite.sendToBack();
	}
	
	@Override
	public void showContents() {
		program.add(status_back);
		program.add(status_bar_hp);
		program.add(status_bar_shield);
		program.add(compass_sprite);
		
	}

	@Override
	public void hideContents() {
		program.remove(status_back);
		program.remove(status_bar_hp);
		program.remove(status_bar_hp_back);
		program.remove(status_bar_shield);
		program.remove(status_bar_shield_back);
		program.remove(compass_back);
		program.remove(compass_sprite);
		
	}

}
