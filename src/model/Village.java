package model;

public class Village extends Piece{

	public Village(int x, int y) {
		super(x, y);
	}

	public boolean piecePosable(Plateau p) {
		return true;
	}

}
