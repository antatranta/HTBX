import java.awt.Color;
import java.util.ArrayList;

import acm.graphics.GOval;

public class TeleportWaypoint {
	
	public static final int AURORA_DISTANCE = 900;
	public static final int AURORA_INNER = 50;
	private static final double MAX_FLASH_SIZE = MainApplication.WINDOW_WIDTH * 3;
	private float activationDistance;		// Alert
	private float teleportDistance;			// Actual Trigger
	private PhysXObject physObj;
	GameConsoleEvents gameConsole;
	
	private boolean active = false;
	private boolean interactable = false;
	private int tele_sequence = 0;
	private int flash_sequence = 0;
	private int step = 0;
	
	public static boolean calculateOverlay;
	
	public TeleportWaypoint() {
		this.active = false;
		this.setInteractable(false);
		physObj = new PhysXObject();
		calculateOverlay = false;
	}
	
	public TeleportWaypoint(QuadrantID QUID, GameConsoleEvents gameConsole) {
		System.out.println("Boss Quad: "+ QUID.toString());
		this.active = false;
		this.setInteractable(false);
		this.gameConsole = gameConsole;
		// move to the center of the quadrant
		Vector2 pos = new Vector2((QUID.getX() * PhysXLibrary.QUADRANT_WIDTH) + (PhysXLibrary.QUADRANT_WIDTH / 2), (QUID.getY() * PhysXLibrary.QUADRANT_HEIGHT) + (PhysXLibrary.QUADRANT_HEIGHT / 2));
		physObj = new PhysXObject(QUID, pos);
	}
	
	public boolean isInteractable() {
		return interactable;
	}
	public void setInteractable(boolean interactable) {
		this.interactable = interactable;
	}
	
	public PhysXObject getPhysObj() {
		return physObj;
	}

	public void setPhysObj(PhysXObject physObj) {
		this.physObj = physObj;
	}
	
	
	public float getActivationDistance() {
		return activationDistance;
	}
	public void setActivationDistance(float activationDistance) {
		if (activationDistance > 0) {
			this.activationDistance = activationDistance;
		}
	}
	
	public float getTeleportDistance() {
		return teleportDistance;
	}
	public void setTeleportDistance(float teleportDistance) {
		if (teleportDistance > 0) {
			this.teleportDistance = teleportDistance;
		}
	}
	
	// Called once
	protected void onActivation() {
		System.out.println("     Within teleport distance");
	}
	
	// Called once
	protected void onLeaveActivation() {
		active = false;
		tele_sequence = 0;
	}
	
	protected void onTeleport() {
		System.out.println("     Teleport!");
		tele_sequence = 1;
		gameConsole.physXRequest_getPlayer().setInvincibility(true);
	}
	
	protected void leaveTeleport() {
		calculateOverlay = false;
		System.out.println("     Teleport Finished!");
		tele_sequence = -1;
		gameConsole.physXRequest_getPlayer().setInvincibility(false);
	}
	
	private void sendToBoss() {
		gameConsole.programRequest_makeBossRoom();
	}
	
	// Called every frame
	protected void onActivationFrame() {
		if(!active) {
			active = true;
			onActivation();
		}
	}
	
	public int getPhase() {
		return this.tele_sequence;
	}
	
	public void setPhase(int x) {
		this.tele_sequence = x;
	}
	
	public void Update(Vector2 refrencePosition, GOval flash) {
		
		double distance = PhysXLibrary.distance(getPhysObj().getPosition(), refrencePosition);
		Vector2 fr = Camera.backendToFrontend(physObj.getPosition());
		
		if(distance < activationDistance && tele_sequence == 0) {
//			calculateOverlay = true;
			if(distance < teleportDistance) {

				interactable = false;
				onTeleport();
				leaveTeleport();
				sendToBoss();
			}
			
		}
		
		// THE TELEPORTATION ANIMATIONS. KIND OF HARDCODE? NOT REALLY. BUT IT'S MAGICAL
		else if (tele_sequence == 1) {

			Vector2 off = gameConsole.physXRequest_getPlayerPhysObj().getPosition().add(physObj.getPosition().mult(new Vector2(-1, -1)));
			gameConsole.physXRequest_getPlayerPhysObj().getPosition().add(off.mult(new Vector2((float)0.95, (float)0.95)));
			
			// 0) Initialize the flash
			if (flash_sequence == 0) {
				flash.setFilled(false);
				flash.setColor(PaintToolbox.WHITE);
				flash.setSize(MAX_FLASH_SIZE, MAX_FLASH_SIZE);
				flash_sequence = 1;
			}
			// 1) White empty circle shrinks down into the center of the vortex
			else if (flash_sequence == 1) {
				double SHRINK = 0.8;
				flash.setSize(flash.getHeight() * SHRINK, flash.getHeight() * SHRINK);
				if (flash.getWidth() < 1 && flash.getHeight() < 1) {
					flash.setFillColor(PaintToolbox.setAlpha(flash.getColor(), 0));
					step += 1;
					if (step == 15) {
						step = 0;
						flash_sequence = 2;
					}
				}
			}
			// 2) After a brief wait, the screen fills up white!
			else if (flash_sequence == 2) {
				flash.setFilled(true);
				flash.setColor(PaintToolbox.WHITE);
				flash.setFillColor(PaintToolbox.WHITE);
				flash.setFillColor(PaintToolbox.setAlpha(flash.getColor(), 255));
				flash_sequence = 3;
			}
			else if (flash_sequence == 3) {
				double GROW = 1.3;
				flash.setSize(flash.getHeight() * GROW, flash.getHeight() * GROW);
				if (flash.getWidth() > MAX_FLASH_SIZE && flash.getHeight() > MAX_FLASH_SIZE) {
					tele_sequence = 3;
					flash_sequence = 4;
				}
			}
			
			flash.setLocation(fr.getX() - (flash.getWidth() / 2), fr.getY() - (flash.getHeight() / 2));

		}
		// 3) Perform all the necessary things: Create the boss room, send the player there, etc.
		else if (tele_sequence == 3) {
			tele_sequence = 4;
			sendToBoss();
		}
		// 4) Fade the white screen to black
		else if (tele_sequence == 4) {
			if (flash_sequence == 4) {
				step += 1;
				flash.setFillColor(PaintToolbox.setAlpha(new Color(255 - step, 255 - step, 255 - step), 255));
				flash.setLocation(fr.getX() - (flash.getWidth() / 2), fr.getY() - (flash.getHeight() / 2));
				if (step >= 255) {
					flash.setColor(flash.getFillColor());
					step = 0;
					tele_sequence = 5;
				}
			}
		}
		// 5) Brief delay
		else if (tele_sequence == 5) {
			step += 1;
			if (step == 60) {
				step = 0;
				tele_sequence = 6;
			}
		}
		// 6) Fade out the black overlay
		else if (tele_sequence == 6) {
			step += 5;
			flash.setFillColor(PaintToolbox.setAlpha(flash.getFillColor(), 255 - step));
			if (step >= 255) {
				step = 0;
				tele_sequence = 7;
			}
		}
		// 7) Exit the sequence
		else if (tele_sequence == 7) {
			leaveTeleport();
		} else {
			calculateOverlay = false;
		}
		
	}
	
}
