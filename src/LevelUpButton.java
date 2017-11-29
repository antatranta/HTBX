import acm.graphics.GImage;

public class LevelUpButton extends GImage {

	LevelUpEnum stat;
	public LevelUpButton(String img, LevelUpEnum stat) {
		super(img);
		this.setLocation(0, 0);
		this.stat = stat;
	}

	public LevelUpButton(String img, double x, double y, LevelUpEnum stat) {
		super(img);
		this.setLocation(x, y);
		this.stat = stat;
	}

	public LevelUpEnum getStatUpType() {
		return stat;
	}

}
