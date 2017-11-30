import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class PlayerShip extends Ship implements ActionListener{

	public static int INV_CAP = 120;
	public static int REGEN_BETWEEN = 30;
	public static int REGEN_CAP = 300;
	private int current_shield;
	private int shield_regen = 0;
	private int shield_between = REGEN_BETWEEN;
	private int i_frames = INV_CAP;
	private ShipStats bonus_stats;
	private int damageTaken=0;
	private boolean invincible = false;

	public PlayerShip(PhysXObject physObj, int current_health, ShipStats stats, String sprite) {
		super(physObj, current_health, stats, sprite, CollisionType.playerShip, 0);
		this.current_shield = 0;
		this.bonus_stats = new ShipStats(0, 0, 0, 0);
	}

	public void chargeShield(int amount) {
		current_shield += amount;
		shield_between = REGEN_BETWEEN;
		if (current_shield > getStats().getShieldMax() + bonus_stats.getShieldMax()) {
			current_shield = getStats().getShieldMax() + bonus_stats.getShieldMax();
		}
	}

	public void restoreHealth(int amount) {
		current_health += amount;
		if (current_health > getStats().getHealthMax() + bonus_stats.getHealthMax()) {
			current_health =  getStats().getHealthMax() + bonus_stats.getHealthMax();
		}
	}

	private void regenerateShield() {
		if (shield_regen == 0 && shield_between == 0 && (current_shield < getStats().getShieldMax() + bonus_stats.getShieldMax()) && current_health > 0) {
			chargeShield(1);
		}
		else {
			if (shield_regen == 0) {
				if (shield_between > 0) {
					shield_between -= 1;
				}
			}
			else if (shield_regen > 0) {
				shield_regen -= 1;
			}
		}
	}

	public ShipStats getBonusStats() {
		return bonus_stats;
	}

	private void processInvincibility() {
		if (i_frames > 0) {
			i_frames -= 1;
		}
	}

	public int getCurrentShield() {
		return current_shield;
	}

	public void setCurrentShield(int current_shield) {
		this.current_shield = current_shield;
	}

	public int getIFrames() {
		return i_frames;
	}
	
	public void setInvincibility(boolean tf) {
		this.invincible = tf;
	}

	@Override
	public void takeDamage(int amount) {
		if (i_frames > 0 && invincible) {
			return;
		}
		i_frames = INV_CAP;
		shield_regen = REGEN_CAP;
		if (current_shield > 0) {
			current_shield -= amount;
			if (current_shield < 0) {
				current_shield = 0;
			}
		}
		else {
			current_health -= amount;
			if (current_health < 0) {
				i_frames = 0;
				current_health = 0;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		processInvincibility();
		regenerateShield();
		moveExternalForce();
	}

	@Override
	public void onCollisionEvent(CollisionData data, Vector2 pos) {
		if (data.getType() == CollisionType.asteroid
				|| data.getType() == CollisionType.enemyShip) {
			if (data.getType() == CollisionType.asteroid) {
				external_force = PhysXLibrary.calculateCollisionForce(pos, this.physObj, KB_FORCE);
			}
			if (data.getType() == CollisionType.enemyShip && i_frames == 0) {
				external_force = PhysXLibrary.calculateCollisionForce(pos, this.physObj, KB_FORCE);
			}
			if (i_frames == 0) {
				System.out.println("Took " + data.getDamage() + " collision damage");
				takeDamage(data.getDamage());
				damageTaken+=data.getDamage();
			}
		}

		if (data.getType() == CollisionType.enemy_bullet && i_frames == 0) {
			external_force = PhysXLibrary.calculateCollisionForce(pos, this.physObj, KB_FORCE);
			takeDamage(data.getDamage());
			damageTaken+=data.getDamage();
		}
	}

	public int getDamageTaken() {
		return damageTaken;
	}
}
