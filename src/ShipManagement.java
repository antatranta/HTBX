
public class ShipManagement implements ShipTriggers {
	
	private GameConsoleEvents gameConsole_ref;
//	private ShipTriggers bulletManager_ref;
	
	public ShipManagement(GameConsoleEvents ref) {
		gameConsole_ref = ref;
	}
	
	@Override
	public void onShipFire(BulletFireEventData data, CollisionType bulletType) {
		// TODO Auto-generated method stub
	}

//	@Override
//	public void onShipDeath(Vector2 position, QuadrantID currentQUID) {
//	}

	@Override
	public int isAreaSafe(Vector2 pos, float range) {
		boolean return_value=gameConsole_ref.physXRequest_isAreaSafe(pos, range);
		if(return_value) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int identify() {
		// TODO Auto-generated method stub
		return 10;
	}

}
