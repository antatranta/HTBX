import java.awt.Color;
import java.io.Console;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import javafx.scene.text.Font;
import rotations.GameImage;

public class DisplayableHUD implements Displayable {

	private MainApplication program;
	private GameConsole console;
	private PlayerShip player;

	private GImage status_front;
	private GRect status_back;
	private GRect status_bar_hp;
	private GRect status_bar_shield;
	private GRect iframes;
	private GameImage compass_sprite;
	private GImage skill_msg;

	private GLabel sp_label;
	private GImage stats_display;
	private GRect speed_stat;
	private GRect damage_stat;
	private GRect health_stat;
	private GRect shield_stat;
	private LevelUpButton speed_up;
	private LevelUpButton damage_up;
	private LevelUpButton health_up;
	private LevelUpButton shield_up;
	private GRect stats_back;

	private GRect threat_left;
	private GRect threat_right;
	private GRect threat_up;
	private GRect threat_down;
	
	private GRect overlay;

	private Color oldColor;

	private ArrayList<Direction> threats;
	private float[] threatLevels;

	private float threatWidth = 100;

	private double bar_max_x;
	private double bar_max_y;
	private double stats_x = 19;
	private double stats_y = 19;

	private int last_shield = -1;
	private double shield_diff = 0;
	private int last_hp = -1;
	private double hp_diff = 0;

	private boolean have_sp;
	private double msg_diff_box;
	private boolean delta_spd;
	private double msg_diff_spd;
	private boolean delta_dmg;
	private double msg_diff_dmg;
	private boolean delta_hp;
	private double msg_diff_hp;
	private boolean delta_shd;
	private double msg_diff_shd;
	
	private GImage boss_display;
	private GRect boss_back;
	private GRect boss_bar;
	private boolean boss;
	private boolean last_boss_setting;
	private double boss_diff;
	

	double startx = 0;
	double starty = 0;
	double unity = 0;
	
	Vector2 boss_tele_pos;

	public DisplayableHUD(MainApplication program, PlayerShip player) {
		this.program = program;
		this.player = player;
		this.console = program.getGameConsole();
		this.boss = false;
		init();
		updateStats();
	}

	private void init() {
		// Ship Status HUD

		status_front = new GameImage("Artboard 10.png", 5, MainApplication.WINDOW_HEIGHT - 5);
		status_front.move(0, -status_front.getHeight());

		status_back = new GRect(status_front.getX(), status_front.getY(), status_front.getWidth(), status_front.getHeight());
		status_back.setFillColor(Color.WHITE);
		status_back.setFilled(true);
		status_back.setColor(new Color(0,0,0,0));

		bar_max_x = 156;
		bar_max_y = 29;

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

		iframes = new GRect(status_back.getX(), status_back.getY() + 1, bar_max_x, status_back.getHeight() - 2);
		iframes.setFillColor(new Color(1, 1, 1, 35));
		iframes.setFilled(true);
		iframes.setColor(new Color(1, 1, 1, 0));

		// Skills display

		stats_display = new GImage("Skills.png", 5, 5);
		stats_back = new GRect(stats_display.getX(), stats_display.getY(), stats_display.getWidth(), stats_display.getHeight());
		stats_back.setFillColor(Color.BLACK);
		stats_back.setFilled(true);
		stats_back.setColor(Color.WHITE);

		sp_label = new GLabel("0", stats_display.getX() + 80, stats_display.getY() + 23);
		sp_label.setColor(Color.WHITE);

		startx = stats_back.getX() + 5;
		starty = stats_back.getY() + 32;
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

		threat_left = new GRect(20, (MainApplication.WINDOW_HEIGHT / 2) - (threatWidth / 2), 10, threatWidth);
		threat_left.setFilled(true);
		threat_right = new GRect(MainApplication.WINDOW_WIDTH - 40, (MainApplication.WINDOW_HEIGHT / 2) - (threatWidth / 2), 10, threatWidth);
		threat_right.setFilled(true);
		threat_up = new GRect((MainApplication.WINDOW_WIDTH / 2) - (threatWidth / 2), 10, threatWidth, 10);
		threat_up.setFilled(true);
		threat_down = new GRect((MainApplication.WINDOW_WIDTH / 2) - (threatWidth / 2), MainApplication.WINDOW_HEIGHT - 20, threatWidth, 10);

		boss_display = new GImage("Boss_HP_Bar.png",0,0);
		boss_display.setVisible(false);
		boss_back = new GRect(0,0,0,0);
		boss_back.setFillColor(PaintToolbox.WHITE);
		boss_back.setFilled(true);
		boss_back.setVisible(false);
		boss_bar = new GRect(0,0,0,0);
		boss_bar.setFilled(true);
		boss_bar.setFillColor(PaintToolbox.RED);
		boss_bar.setVisible(false);
		
		threats = new ArrayList<Direction>();
		threatLevels = new float[4];

		threat_down.setFilled(true);
		oldColor = new Color(0.0f, 0.0f, 0.0f, 0.0f);
		
		boss_tele_pos = console.getBossRoomTrigger().getPhysObj().getPosition();
		
		overlay = null;
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
		// Status HUD
		if (player.getCurrentShield() != last_shield) {
			shield_diff = recalculateDifference(player.getCurrentShield(), last_shield);
			last_shield = player.getCurrentShield();
		}
		if (player.getCurrentHealth() != last_hp) {
			hp_diff = recalculateDifference(player.getCurrentHealth(), last_hp);
			last_hp = player.getCurrentHealth();
		}

		scaleStatusBar(status_bar_shield, (double)(player.getCurrentShield() - shield_diff) / (double)(player.getStats().getShieldMax() + player.getBonusStats().getShieldMax()));
		scaleStatusBar(status_bar_hp, (double)(player.getCurrentHealth() - hp_diff) / (double)(player.getStats().getHealthMax() + player.getBonusStats().getHealthMax()));
		scaleStatusBar(iframes, (double)player.getIFrames() / (double)PlayerShip.INV_CAP);
		shield_diff /= 1.1;
		hp_diff /= 1.1;
		aimCompass(compass_sprite, boss_tele_pos);

		// Skills
		if (program.getGameConsole().getSP() > 0) {
			if (have_sp == false) {
				have_sp = true;
				msg_diff_box = skill_msg.getHeight() * 1.35;
			}
			skill_msg.setLocation(skill_msg.getX(), MainApplication.WINDOW_HEIGHT - (skill_msg.getHeight() * 1.5) + (msg_diff_box));
		}
		else {
			if (have_sp == true) {
				have_sp = false;
				msg_diff_box = skill_msg.getHeight() * 1.35;
			}
			skill_msg.setLocation(skill_msg.getX(), MainApplication.WINDOW_HEIGHT - (msg_diff_box));
		}

		if (have_sp && player.getBonusStats().getSpeedSetting() < 4) {
			if (delta_spd == false) {
				delta_spd = true;
				msg_diff_spd = skill_msg.getHeight();
			}
			speed_up.setLocation(stats_display.getX() + stats_display.getWidth() - msg_diff_spd, speed_stat.getY());
		}
		else {
			if (delta_spd == true) {
				delta_spd = false;
				msg_diff_spd = skill_msg.getHeight();
			}
			speed_up.setLocation(stats_display.getX() + stats_display.getWidth() + msg_diff_spd - skill_msg.getHeight(), speed_stat.getY());
		}

		if (have_sp && player.getBonusStats().getDamage() < 4) {
			if (delta_dmg == false) {
				delta_dmg = true;
				msg_diff_dmg = skill_msg.getHeight();
			}
			damage_up.setLocation(stats_display.getX() + stats_display.getWidth() - msg_diff_dmg, damage_stat.getY());
		}
		else {
			if (delta_dmg == true) {
				delta_dmg = false;
				msg_diff_dmg = skill_msg.getHeight();
			}
			damage_up.setLocation(stats_display.getX() + stats_display.getWidth() + msg_diff_dmg - skill_msg.getHeight(), damage_stat.getY());
		}

		if (have_sp && player.getBonusStats().getHealthMax() < 4) {
			if (delta_hp == false) {
				delta_hp = true;
				msg_diff_hp = skill_msg.getHeight();
			}
			health_up.setLocation(stats_display.getX() + stats_display.getWidth() - msg_diff_hp, health_stat.getY());
		}
		else {
			if (delta_hp == true) {
				delta_hp = false;
				msg_diff_hp = skill_msg.getHeight();
			}
			health_up.setLocation(stats_display.getX() + stats_display.getWidth() + msg_diff_hp - skill_msg.getHeight(), health_stat.getY());
		}

		if (have_sp && player.getBonusStats().getShieldMax() < 4) {
			if (delta_shd == false) {
				delta_shd = true;
				msg_diff_shd = skill_msg.getHeight();
			}
			shield_up.setLocation(stats_display.getX() + stats_display.getWidth() - msg_diff_shd, shield_stat.getY());

		}
		else {
			if (delta_shd == true) {
				delta_shd = false;
				msg_diff_shd = skill_msg.getHeight();
			}
			shield_up.setLocation(stats_display.getX() + stats_display.getWidth() + msg_diff_shd - skill_msg.getHeight(), shield_stat.getY());
		}

		msg_diff_box /= 1.1;
		msg_diff_spd /= 1.1;
		msg_diff_dmg /= 1.1;
		msg_diff_hp /= 1.1;
		msg_diff_shd /= 1.1;

		// Handle threats		
		updateThreatBar(threat_up, threatLevels[0]);
		updateThreatBar(threat_down, threatLevels[1]);
		updateThreatBar(threat_left, threatLevels[2]);
		updateThreatBar(threat_right, threatLevels[3]);

		// Reset the levels
		threats = new ArrayList<Direction>();
		threatLevels = new float[4];
		
		// Boss portal
		double dist = PhysXLibrary.distance(player.getPhysObj().getPosition(), boss_tele_pos);
		if(TeleportWaypoint.calculateOverlay) {
			if(overlay == null) {
				overlay = new GRect(-1, -1, MainApplication.WINDOW_WIDTH + 1, MainApplication.WINDOW_HEIGHT + 1);
				program.add(overlay);
				overlay.setFillColor(PaintToolbox.setAlpha(PaintToolbox.BLACK, 0));
				overlay.setFilled(true);
			}
			if (dist < TeleportWaypoint.AURORA_DISTANCE && dist > TeleportWaypoint.AURORA_INNER) {
				overlay.setFillColor(PaintToolbox.setAlpha(overlay.getFillColor(), (int) (255 - (255 * ((dist - TeleportWaypoint.AURORA_INNER) / (TeleportWaypoint.AURORA_DISTANCE - TeleportWaypoint.AURORA_INNER))))));
			}
			else if (dist < TeleportWaypoint.AURORA_INNER) {
				overlay.setFillColor(PaintToolbox.setAlpha(overlay.getFillColor(), 255));
			}
			else {
				overlay.setFillColor(PaintToolbox.setAlpha(overlay.getFillColor(), 0));
			}
		} else if (overlay != null) {
			program.remove(overlay);
			overlay = null;
		}
		
	}
	
	public void drawBossHPBar(int hp, int max) {
		if (boss) {
			if (!last_boss_setting) {
				last_boss_setting = true;
				boss_diff = 200;
			}
			else {
				boss_display.setVisible(true);
				boss_display.setLocation(MainApplication.WINDOW_WIDTH - (boss_display.getWidth() * 1.5) + boss_diff, MainApplication.WINDOW_HEIGHT * 0.15);
				boss_diff /= 1.1;

				double ratio = (double)hp / (double)max;
				boss_back.setSize(22, 364);
				boss_back.setLocation(boss_display.getX() + 19, boss_display.getY() + 9);
				boss_back.setVisible(true);
				boss_bar.setSize(22, 364 * ratio);
				boss_bar.setLocation(boss_back.getX(), boss_display.getY() + 9 + (364 * (1.0 - ratio)));
				boss_bar.setVisible(true);
			}
	
		}
		else {
			last_boss_setting = boss;
		}
	}

	private void updateThreatBar(GRect bar, float value) {
		bar.setFilled(true);
		Color newColor = new Color(1.0f, 0.0f, 0.0f, value);
		Color color = PaintToolbox.blendAlpha(oldColor, newColor, 0.15f);

		if (value < .001f) {
			color = new Color(1.0f, 0.0f, 0.0f, 0.0f);
		}
		this.oldColor = color;
		bar.setFillColor(color);
		bar.setColor(color);
	}

	public void updateThreats(Direction threatDirection) {
		threats.add(threatDirection);

		if(threatDirection == Direction.up
				|| threatDirection == Direction.upper_left
				|| threatDirection == Direction.upper_right) {
			if(threatLevels[0] + .25f < .999f) {
				threatLevels[0] += .25f;
			} else {
				threatLevels[0] = .999f;
			}
		}
		if(threatDirection == Direction.down
				|| threatDirection == Direction.lower_left
				|| threatDirection == Direction.lower_right) {
			if(threatLevels[1] + .25f < .999f) {
				threatLevels[1] += .25f;
			} else {
				threatLevels[1] = .999f;
			}
		}
		if(threatDirection == Direction.left
				|| threatDirection == Direction.upper_left
				|| threatDirection == Direction.lower_left) {
			if(threatLevels[2] + .25f < .999f) {
				threatLevels[2] += .25f;
			} else {
				threatLevels[2] = .999f;
			}
		}
		if(threatDirection == Direction.right
				|| threatDirection == Direction.upper_right
				|| threatDirection == Direction.lower_right) {
			if(threatLevels[3] + .25f < .999f) {
				threatLevels[3] += .25f;
			} else {
				threatLevels[3] = .999f;
			}
		}
	}

	public int recalculateDifference(int cur, int last) {
		return cur - last;
	}

	public void updateStats() {
		scaleStatsBar(speed_stat, player.getBonusStats().getSpeedSetting());
		scaleStatsBar(damage_stat, player.getBonusStats().getDamage());
		scaleStatsBar(health_stat, player.getBonusStats().getHealthMax());
		scaleStatsBar(shield_stat, player.getBonusStats().getShieldMax());
		sp_label.setLabel("" + program.getGameConsole().getSP());
	}
	
	/* Pseudocode for updating game HUD for Out-Of-Bounds Warning;
	 * This is to correlate the time pass with what seconds to display
	 * on the HUD of the game when the player is out of bounds. Only updated
	 * with each passing second and only when the player is out of bounds. 
	 */
	// If the player is out of bounds
		// Get how many seconds passed and minus from 10
		// Decrement the number
		// Show "Warning! Out of Bounds!" and the number on the screen
	// If the player got back in bounds
		// Reset counter back to 10
		// Remove warning and number from screen

	public Vector2 getBossTelePos() {
		return this.boss_tele_pos;
	}
	
	public GRect getBackgroundOverlay() {
		return this.overlay;
	}
	
	public void setBoss(boolean tf) {
		this.boss = tf;
	}
	
	public void layerSprites() {
		status_front.sendToBack();
		iframes.sendToBack();
		status_bar_hp.sendToBack();
		status_bar_shield.sendToBack();
		compass_sprite.sendToBack();
		status_back.sendToBack();

		sp_label.sendToBack();
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
		
		boss_display.sendToBack();
		boss_bar.sendToBack();
		boss_back.sendToBack();
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
		program.add(sp_label);
		program.add(skill_msg);
		program.add(threat_left);
		program.add(threat_right);
		program.add(threat_up);
		program.add(threat_down);
		program.add(boss_display);
		program.add(boss_bar);
		program.add(boss_back);
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
		program.remove(sp_label);
		program.remove(skill_msg);
		program.remove(boss_display);
		program.remove(boss_bar);
		program.remove(boss_back);
	}
}
