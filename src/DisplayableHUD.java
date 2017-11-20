import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GRect;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import rotations.GameImage;

public class DisplayableHUD implements Displayable {

	private MainApplication program;
	private PlayerShip player;
	
	private GRect status_back;
	private GRect status_bar_hp;
	private GRect status_bar_hp_back;
	//private GLabel hp_label;
	private GRect status_bar_shield;
	private GRect status_bar_shield_back;
	//private GLabel shield_label;
	private GRect iframes;
	private GRect compass_back;
	private GRect inner_compass_back;
	private GameImage compass_sprite;
	
	private GImage stats_display;
	private GRect stats_back;
	private GRect speed_stat;
	private GRect damage_stat;
	private GRect health_stat;
	private GRect shield_stat;
	
	private double bar_max_x;
	private double bar_max_y;
	private double stats_x = 21;
	private double stats_y = 19;
	
	private int last_shield;
	private int last_hp;

	public DisplayableHUD(MainApplication program, PlayerShip player) {
		this.program = program;
		this.player = player;
		init();
		updateStats();
	}
	
	private void init() {
		status_back = new GRect(10, MainApplication.WINDOW_HEIGHT - 10 - 75, 300, 75);
		//Vector2 status_origin = new Vector2((float)status_back.getX(), (float)status_back.getY());
		bar_max_y = status_back.getHeight() - 20 - ((status_back.getHeight() - 10) / 2);
		bar_max_x = status_back.getWidth() - 20;
		
		status_back.setFillColor(Color.BLACK);
		status_back.setFilled(true);
		status_back.setColor(Color.WHITE);
		
		status_bar_hp_back = new GRect(status_back.getLocation().getX() + 10, status_back.getY() - 10 + bar_max_y + bar_max_y + 10, bar_max_x, bar_max_y);
		status_bar_hp_back.setFillColor(Color.WHITE);
		status_bar_hp_back.setFilled(true);
		status_bar_hp_back.setColor(Color.WHITE);
		
		status_bar_shield_back = new GRect(status_back.getLocation().getX() + 10, status_back.getY() - 10 + bar_max_y, bar_max_x, bar_max_y);
		status_bar_shield_back.setFillColor(Color.WHITE);
		status_bar_shield_back.setFilled(true);
		status_bar_shield_back.setColor(Color.WHITE);
		
		compass_back = new GRect(status_back.getX() + status_back.getWidth() + 5, status_back.getY(), status_back.getHeight(), status_back.getHeight());
		compass_back.setFillColor(Color.BLACK);
		compass_back.setFilled(true);
		compass_back.setColor(Color.WHITE);
		
		inner_compass_back = new GRect(status_back.getX() + status_back.getWidth() + 15, status_back.getY() + 10, status_back.getHeight() - 20, status_back.getHeight() - 20);
		inner_compass_back.setFillColor(Color.WHITE);
		inner_compass_back.setFilled(true);
		inner_compass_back.setColor(Color.WHITE);
		
		compass_sprite = new GameImage("Compass2.png", compass_back.getX() + 15, compass_back.getY() + 15);
		
		status_bar_hp = new GRect(status_bar_hp_back.getLocation().getX(), status_bar_hp_back.getY(), status_bar_hp_back.getWidth(), status_bar_hp_back.getHeight());
		status_bar_hp.setFillColor(Color.GREEN);
		status_bar_hp.setFilled(true);
		status_bar_hp.setColor(Color.YELLOW);
		
		status_bar_shield = new GRect(status_bar_shield_back.getLocation().getX(), status_bar_shield_back.getY(), status_bar_shield_back.getWidth(), status_bar_shield_back.getHeight());
		status_bar_shield.setFillColor(Color.BLUE);
		status_bar_shield.setFilled(true);
		status_bar_shield.setColor(Color.CYAN);
		
		stats_display = new GImage("Stats_Display.png", 5, 5);
		stats_back = new GRect(stats_display.getX(), stats_display.getY(), stats_display.getWidth(), stats_display.getHeight());
		stats_back.setFillColor(Color.BLACK);
		stats_back.setFilled(true);
		stats_back.setColor(Color.WHITE);
		
		iframes = new GRect(status_bar_shield.getX(), status_back.getY() + 1, bar_max_x, status_back.getHeight() - 2);
		iframes.setFillColor(new Color(1, 1, 1, 35));
		iframes.setFilled(true);
		iframes.setColor(new Color(1, 1, 1, 0));
		
		double startx = stats_back.getX() + 6;
		double starty = stats_back.getY() + 28;
		double unity = 21;
		
		speed_stat = new GRect(startx, starty, 0, 0);
		speed_stat.setFilled(true);
		speed_stat.setFillColor(Color.WHITE);
		speed_stat.setColor(Color.WHITE);
		
		damage_stat = new GRect(startx, starty + (unity), 0, 0);
		damage_stat.setFilled(true);
		damage_stat.setFillColor(Color.WHITE);
		damage_stat.setColor(Color.WHITE);
		
		health_stat = new GRect(startx, starty + (unity * 2), 0, 0);
		health_stat.setFilled(true);
		health_stat.setFillColor(Color.WHITE);
		health_stat.setColor(Color.WHITE);
		
		shield_stat = new GRect(startx, starty + (unity * 3), 0, 0);
		shield_stat.setFilled(true);
		shield_stat.setFillColor(Color.WHITE);
		shield_stat.setColor(Color.WHITE);
	}
	
	private void scaleStatusBar(GRect bar, double percent) {
		bar.setSize(bar_max_x * percent, bar.getHeight());
	}
	
	private void scaleStatsBar(GRect bar, int level) {
		bar.setSize(stats_x * level, stats_y);
	}
	
	private void aimCompass(GameImage compass, Vector2 spot) {
		double x = spot.getX() - player.getPhysObj().getPosition().getX();
		double y = spot.getY() - player.getPhysObj().getPosition().getY();
		compass_sprite.setDegrees(Math.toDegrees(Math.atan2(y, x)));
	}
	
	public void updateHUD() {
		scaleStatusBar(status_bar_hp, (double)player.getCurrentHealth() / (double)player.getStats().getHealthMax());
		scaleStatusBar(status_bar_shield, (double)player.getCurrentShield() / (double)player.getStats().getShieldMax());
		scaleStatusBar(iframes, (double)player.getIFrames() / (double)PlayerShip.INV_CAP);
		aimCompass(compass_sprite, new Vector2(0,0));
	}
	
	public void updateStats() {
		scaleStatsBar(speed_stat, (player.getStats().getSpeed() - 1) / 4);
		scaleStatsBar(damage_stat, (player.getStats().getDamage() - 1) / 4);
		scaleStatsBar(health_stat, (player.getStats().getHealthMax() - 1) / 4);
		scaleStatsBar(shield_stat, (player.getStats().getShieldMax() - 1) / 4);
	}
	
	public void layerSprites() {
		iframes.sendToBack();
		status_bar_hp.sendToBack();
		status_bar_hp_back.sendToBack();
		status_bar_shield.sendToBack();
		status_bar_shield_back.sendToBack();
		status_back.sendToBack();
		compass_sprite.sendToBack();
		inner_compass_back.sendToBack();
		compass_back.sendToBack();
		stats_display.sendToBack();
		speed_stat.sendToBack();
		damage_stat.sendToBack();
		health_stat.sendToBack();
		shield_stat.sendToBack();
		stats_back.sendToBack();
	}
	
	@Override
	public void showContents() {
		program.add(status_back);
		program.add(status_bar_hp_back);
		program.add(status_bar_hp);
		program.add(status_bar_shield_back);
		program.add(status_bar_shield);
		program.add(iframes);
		
		program.add(compass_back);
		program.add(inner_compass_back);
		program.add(compass_sprite);
		
		program.add(stats_back);
		program.add(stats_display);
		program.add(speed_stat);
		program.add(damage_stat);
		program.add(health_stat);
		program.add(shield_stat);
	}

	@Override
	public void hideContents() {
		program.remove(status_back);
		program.remove(status_bar_hp);
		program.remove(status_bar_hp_back);
		program.remove(status_bar_shield);
		program.remove(status_bar_shield_back);
		program.remove(iframes);
		
		program.remove(compass_back);
		program.remove(inner_compass_back);
		program.remove(compass_sprite);
		
		program.remove(stats_back);
		program.remove(stats_display);
		program.remove(speed_stat);
		program.remove(damage_stat);
		program.remove(health_stat);
		program.remove(shield_stat);
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
