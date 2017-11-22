import java.awt.Color;
import acm.graphics.GImage;
import acm.graphics.GRect;
import rotations.GameImage;

public class DisplayableHUD implements Displayable {

	private MainApplication program;
	private PlayerShip player;
	
	private GImage status_front;
	private GRect status_back;
	private GRect status_bar_hp;
	private GRect status_bar_shield;
	private GRect iframes;
	private GameImage compass_sprite;
	private GImage skill_msg;
	
	private GImage stats_display;
	private GRect stats_back;
	private GRect speed_stat;
	private GRect damage_stat;
	private GRect health_stat;
	private GRect shield_stat;
	private LevelUpButton speed_up;
	private LevelUpButton damage_up;
	private LevelUpButton health_up;
	private LevelUpButton shield_up;
	
	private double bar_max_x;
	private double bar_max_y;
	private double stats_x = 21;
	private double stats_y = 19;
	
	private int last_shield = -1;
	private double shield_diff = 0;
	private int last_hp = -1;
	private double hp_diff = 0;
	private boolean have_sp;
	private double msg_diff;
	
	double startx = 0;
	double starty = 0;
	double unity = 0;
	
	public DisplayableHUD(MainApplication program, PlayerShip player) {
		this.program = program;
		this.player = player;
		init();
		updateStats();
	}
	
	private void init() {
		status_front = new GameImage("Artboard 10.png", 5, MainApplication.WINDOW_HEIGHT - 5);
		status_front.move(0, -status_front.getHeight());
		
		status_back = new GRect(status_front.getX(), status_front.getY(), status_front.getWidth(), status_front.getHeight());
		status_back.setFillColor(Color.WHITE);
		status_back.setFilled(true);
		status_back.setColor(new Color(0,0,0,0));
		
		bar_max_x = 156;
		bar_max_y = 29;

//		status_bar_hp_back = new GRect(status_back.getLocation().getX() + 10, status_back.getY() - 10 + bar_max_y + bar_max_y + 10, bar_max_x, bar_max_y);
//		status_bar_hp_back.setFillColor(Color.WHITE);
//		status_bar_hp_back.setFilled(true);
//		status_bar_hp_back.setColor(Color.WHITE);
//		
//		status_bar_shield_back = new GRect(status_back.getLocation().getX() + 10, status_back.getY() - 10 + bar_max_y, bar_max_x, bar_max_y);
//		status_bar_shield_back.setFillColor(Color.WHITE);
//		status_bar_shield_back.setFilled(true);
//		status_bar_shield_back.setColor(Color.WHITE);
		
//		compass_back = new GRect(status_back.getX() + status_back.getWidth() + 5, status_back.getY(), status_back.getHeight(), status_back.getHeight());
//		compass_back.setFillColor(Color.BLACK);
//		compass_back.setFilled(true);
//		compass_back.setColor(Color.WHITE);
		
//		inner_compass_back = new GRect(status_back.getX() + status_back.getWidth() + 15, status_back.getY() + 10, status_back.getHeight() - 20, status_back.getHeight() - 20);
//		inner_compass_back.setFillColor(Color.WHITE);
//		inner_compass_back.setFilled(true);
//		inner_compass_back.setColor(Color.WHITE);
		
		compass_sprite = new GameImage("Compass.png", status_front.getX() + 166, status_front.getY() + 4);
		
		Color shield = new Color(131, 255, 254);
		status_bar_shield = new GRect(status_front.getX() + 5, status_front.getY() + 4, bar_max_x, bar_max_y);
		status_bar_shield.setFillColor(shield);
		status_bar_shield.setFilled(true);
		status_bar_shield.setColor(shield);
		
		Color hp = new Color(184, 255, 199);
		status_bar_hp = new GRect(status_front.getX() + 5, status_front.getY() + 4 + 29 + 4, bar_max_x, bar_max_y);
		status_bar_hp.setFillColor(hp);
		status_bar_hp.setFilled(true);
		status_bar_hp.setColor(hp);

		stats_display = new GImage("Stats_Display.png", 5, 5);
		stats_back = new GRect(stats_display.getX(), stats_display.getY(), stats_display.getWidth(), stats_display.getHeight());
		stats_back.setFillColor(Color.BLACK);
		stats_back.setFilled(true);
		stats_back.setColor(Color.WHITE);
		
		iframes = new GRect(status_back.getX(), status_back.getY() + 1, bar_max_x, status_back.getHeight() - 2);
		iframes.setFillColor(new Color(1, 1, 1, 35));
		iframes.setFilled(true);
		iframes.setColor(new Color(1, 1, 1, 0));
		
		startx = stats_back.getX() + 6;
		starty = stats_back.getY() + 28;
		unity = 22;
		
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
		
		speed_up = new LevelUpButton("Plus_Button.png", -100, speed_stat.getY(), LevelUpEnum.speed);
		damage_up = new LevelUpButton("Plus_Button.png", -100, damage_stat.getY(), LevelUpEnum.damage);
		health_up = new LevelUpButton("Plus_Button.png", -100, health_stat.getY(), LevelUpEnum.health);
		shield_up = new LevelUpButton("Plus_Button.png", -100, shield_stat.getY(), LevelUpEnum.shield);
		
		skill_msg = new GImage("SkillMsg.png", 0, 0);
		skill_msg.setLocation((MainApplication.WINDOW_WIDTH / 2) -(skill_msg.getWidth() / 2), MainApplication.WINDOW_HEIGHT);
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
		if (player.getCurrentShield() != last_shield) {
			shield_diff = recalculateDifference(player.getCurrentShield(), last_shield);
//			System.out.println("shield_diff = " + shield_diff + ", last_shield = " + last_shield + " | max_shield: " + player.getStats().getShieldMax());
			last_shield = player.getCurrentShield();
		}
		if (player.getCurrentHealth() != last_hp) {
			hp_diff = recalculateDifference(player.getCurrentHealth(), last_hp);
//			System.out.println("hp_diff = " + hp_diff + ", last_hp = " + last_hp + " | max_hp: " + player.getStats().getHealthMax());
			last_hp = player.getCurrentHealth();
		}
		
		scaleStatusBar(status_bar_shield, (double)(player.getCurrentShield() - shield_diff) / (double)player.getStats().getShieldMax());
		scaleStatusBar(status_bar_hp, (double)(player.getCurrentHealth() - hp_diff) / (double)player.getStats().getHealthMax());
		scaleStatusBar(iframes, (double)player.getIFrames() / (double)PlayerShip.INV_CAP);
		shield_diff /= 1.1;
		hp_diff /= 1.1;
		aimCompass(compass_sprite, new Vector2(0,0));
		
		// Skills
		
		if (program.getGameConsole().getSP() > 0) {
			if (have_sp == false) {
				have_sp = true;
				msg_diff = skill_msg.getHeight();
			}
			speed_up.setLocation(stats_display.getX() + stats_display.getWidth() - msg_diff, speed_stat.getY());
			damage_up.setLocation(stats_display.getX() + stats_display.getWidth() - msg_diff, damage_stat.getY());
			health_up.setLocation(stats_display.getX() + stats_display.getWidth() - msg_diff, health_stat.getY());
			shield_up.setLocation(stats_display.getX() + stats_display.getWidth() - msg_diff, shield_stat.getY());
			skill_msg.setLocation(skill_msg.getX(), MainApplication.WINDOW_HEIGHT - (skill_msg.getHeight() * 1.35) + (msg_diff * 5));
		}
		else {
			if (have_sp == true) {
				have_sp = false;
				msg_diff = skill_msg.getHeight();
			}
			speed_up.setLocation(stats_display.getX() + stats_display.getWidth() + msg_diff - skill_msg.getHeight(), speed_stat.getY());
			damage_up.setLocation(stats_display.getX() + stats_display.getWidth() + msg_diff - skill_msg.getHeight(), damage_stat.getY());
			health_up.setLocation(stats_display.getX() + stats_display.getWidth() + msg_diff - skill_msg.getHeight(), health_stat.getY());
			shield_up.setLocation(stats_display.getX() + stats_display.getWidth() + msg_diff - skill_msg.getHeight(), shield_stat.getY());
			skill_msg.setLocation(skill_msg.getX(), MainApplication.WINDOW_HEIGHT + (skill_msg.getHeight() * 1.35) + (msg_diff * 5));
		}
		msg_diff /= 1.1;
	}
	
	public int recalculateDifference(int cur, int last) {
		return cur - last;
	}
	
	public void updateStats() {
		scaleStatsBar(speed_stat, (player.getStats().getSpeedSetting() - 1) );
		scaleStatsBar(damage_stat, (player.getStats().getDamage() - 1) );
		scaleStatsBar(health_stat, (player.getStats().getHealthMax() - 1));
		scaleStatsBar(shield_stat, (player.getStats().getShieldMax() - 1));
	}
	
	public void layerSprites() {
		status_front.sendToBack();
		iframes.sendToBack();
		status_bar_hp.sendToBack();
		status_bar_shield.sendToBack();
		compass_sprite.sendToBack();
		status_back.sendToBack();
		
		stats_display.sendToBack();
		speed_stat.sendToBack();
		damage_stat.sendToBack();
		health_stat.sendToBack();
		shield_stat.sendToBack();
		speed_up.sendToBack();
		damage_up.sendToBack();
		health_up.sendToBack();
		shield_up.sendToBack();
		stats_back.sendToBack();
	}
	
	@Override
	public void showContents() {
		program.add(status_back);
		program.add(compass_sprite);
		program.add(status_bar_hp);
		program.add(status_bar_shield);
		program.add(iframes);
		program.add(status_front);
		
		program.add(stats_back);
		program.add(speed_stat);
		program.add(damage_stat);
		program.add(health_stat);
		program.add(shield_stat);
		program.add(speed_up);
		program.add(damage_up);
		program.add(health_up);
		program.add(shield_up);
		program.add(stats_display);
		
		program.add(skill_msg);
	}

	@Override
	public void hideContents() {
		program.remove(status_back);
		program.remove(status_bar_hp);
		program.remove(status_bar_shield);
		program.remove(iframes);
		program.remove(status_front);
		program.remove(compass_sprite);
		
		program.remove(stats_back);
		program.remove(stats_display);
		program.remove(speed_stat);
		program.remove(damage_stat);
		program.remove(health_stat);
		program.remove(shield_stat);
		program.remove(speed_up);
		program.remove(damage_up);
		program.remove(health_up);
		program.remove(shield_up);
		
		program.remove(skill_msg);
	}
}
