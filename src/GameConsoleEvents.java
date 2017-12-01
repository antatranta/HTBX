import java.util.ArrayList;

import acm.graphics.GObject;
import acm.graphics.GOval;
import rotations.GameImage;

public interface GameConsoleEvents {
	public void onShipDeath(Vector2 pos, int exp);
	public boolean physXRequest_isAreaSafe(Vector2 pos, float range);
	public void programRequest_drawGOval(PhysXObject obj, GOval oval);
	public void programRequest_removeGOval(PhysXObject obj, GOval oval);
	public void programRequest_removeObject(EnemyShip obj);
	public void programRequest_removeDrawnObjects(ArrayList<GameImage> objects);
	public void programRequest_drawObjects(ArrayList<GameImage> objects);
	public void programRequest_makeFX(FXPattern pattern, FXType type, FXParticle particle);

	public PlayerShip physXRequest_getPlayer();
	public PhysXObject physXRequest_getPlayerPhysObj();
	public void UIRequest_addThreat(Vector2 pos);
	void programRequest_makeBossRoom();
	public <Enemy extends EnemyShip> void programRequest_makeEnemy(Enemy enemy);
	public BulletManager programRequest_getBulletManager();
}
