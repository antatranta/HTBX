import java.util.ArrayList;

public class TeleportWaypoint {
	
	private float activationDistance;		// Alert
	private float teleportDistance;			// Actual Trigger
	private PhysXObject physObj;
	GameConsoleEvents gameConsole;
	
	private boolean active = false;
	private boolean interactable = false;
	public TeleportWaypoint() {
		this.active = false;
		this.setInteractable(false);
		physObj = new PhysXObject();
	}
	
	public TeleportWaypoint(QuadrantID QUID, GameConsoleEvents gameConsole) {
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
		System.out.println("     Within activation distance");
	}
	
	// Called once
	protected void onLeaveActivation() {
		
	}
	
	protected void onTeleport() {
		System.out.println("     Within teleport distance");
		gameConsole.programRequest_makeBossRoom();
	}
	
	// Called every frame
	protected void onActivationFrame() {
		if(!active) {
			active = true;
			onActivation();
		}
	}
	
	public void Update(Vector2 refrencePosition) {
		double distance = PhysXLibrary.distance(getPhysObj().getPosition(), refrencePosition);

		if(distance < activationDistance) {
			
			// Called every frame
			onActivationFrame();
			
			if(distance < teleportDistance) {
				onTeleport();
			}
			
		} else if (active) {
			active = false;
			onLeaveActivation();
		}
	}



	
	
}
