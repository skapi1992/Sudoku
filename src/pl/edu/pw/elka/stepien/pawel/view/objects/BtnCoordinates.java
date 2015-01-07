package pl.edu.pw.elka.stepien.pawel.view.objects;

/**
 * Coordinates of the button on the board
 * @author Pawel Stepien
 *
 */
public class BtnCoordinates {
	
	private int x;
	private int y;
	
	public BtnCoordinates(int x, int y) {
		setX(x);
		setY(y);
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
