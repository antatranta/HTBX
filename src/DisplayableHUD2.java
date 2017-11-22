import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;
import rotations.GameImage;

// This one uses LJ's image

public class DisplayableHUD2 implements Displayable {

	private MainApplication program;
	private PlayerShip player;
	
	private GRect stats_back;
	private GImage status_back;
	private GRect status_bar_hp;
	private GRect status_bar_hp_back;
	//private GLabel hp_label;
	private GRect status_bar_shield;
	private GRect status_bar_shield_back;
	//private GLabel shield_label;
	private GRect compass_back;
	private GRect inner_compass_back;
	private GameImage compass_sprite;
	private Vector2 status_origin;
	
	private double bar_max_x = 338;//362;
	private double bar_max_y = 58;//25;
	
	
	public DisplayableHUD2(MainApplication program, PlayerShip player) {
		this.program = program;
		this.player = player;
		init();
	}
	
	private void init() {
		//status_back = new GRect(10, MainApplication.WINDOW_HEIGHT - 10 - 75, 300, 75);
		//Vector2 status_origin = new Vector2((float)status_back.getX(), (float)status_back.getY());
//		bar_max_y = status_back.getHeight() - 20 - ((status_back.getHeight() - 10) / 2);
//		bar_max_x = status_back.getWidth() - 20;
		
//		status_back.setFillColor(Color.BLACK);
//		status_back.setFilled(true);
//		status_back.setColor(Color.WHITE);
		status_back = new GImage("Backing Debug.png", 0, 0);
		status_back.move(0, MainApplication.WINDOW_HEIGHT - status_back.getHeight());
//		
//		status_bar_hp_back = new GRect(status_back.getLocation().getX() + 10, status_back.getY() - 10 + bar_max_y + bar_max_y + 10, bar_max_x, bar_max_y);
//		status_bar_hp_back.setFillColor(Color.WHITE);
//		status_bar_hp_back.setFilled(true);
//		status_bar_hp_back.setColor(Color.WHITE);
		
//		status_bar_shield_back = new GRect(status_back.getLocation().getX() + 10, status_back.getY() - 10 + bar_max_y, bar_max_x, bar_max_y);
//		status_bar_shield_back.setFillColor(Color.WHITE);
//		status_bar_shield_back.setFilled(true);
//		status_bar_shield_back.setColor(Color.WHITE);
		
//		compass_back = new GRect(status_back.getX() + status_back.getWidth() + 5, status_back.getY(), status_back.getHeight(), status_back.getHeight());
//		compass_back.setFillColor(Color.BLACK);
//		compass_back.setFilled(true);
//		compass_back.setColor(Color.WHITE);
//		
//		inner_compass_back = new GRect(status_back.getX() + status_back.getWidth() + 15, status_back.getY() + 10, status_back.getHeight() - 20, status_back.getHeight() - 20);
//		inner_compass_back.setFillColor(Color.WHITE);
//		inner_compass_back.setFilled(true);
//		inner_compass_back.setColor(Color.WHITE);
		
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
	
//	public void updateStats() {
//		scaleStatsBar(speed_stat, (player.getStats().getSpeedSetting() - 1) / 4);
//		scaleStatsBar(damage_stat, (player.getStats().getDamage() - 1) / 4);
//		scaleStatsBar(health_stat, (player.getStats().getHealthMax() - 1) / 4);
//		scaleStatsBar(shield_stat, (player.getStats().getShieldMax() - 1) / 4);
//	}
	
	public void layerSprites() {
		status_bar_hp.sendToBack();
//		status_bar_hp_back.sendToBack();
		status_bar_shield.sendToBack();
//		status_bar_shield_back.sendToBack();
		status_back.sendToBack();
		compass_sprite.sendToBack();
//		inner_compass_back.sendToBack();
//		compass_back.sendToBack();
	}
	
	@Override
	public void showContents() {
		program.add(status_back);
//		program.add(status_bar_hp_back);
		program.add(status_bar_hp);
//		program.add(status_bar_shield_back);
		program.add(status_bar_shield);
//		program.add(compass_back);
//		program.add(inner_compass_back);
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
