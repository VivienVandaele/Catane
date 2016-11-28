package model;

public class Route extends Piece{

	public Route(int x, int y) {
		super(x, y);
	}

	public boolean piecePosable(Plateau p) {
		return true;
	}

}
