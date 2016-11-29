package state;

import java.awt.event.MouseEvent;

import model.Piece;
import vue.Fenetre;

public class PieceState extends State{
	private Piece p;
	
	public PieceState(Fenetre f, Piece p){
		super(f);
		this.p=p;
	}

	public void mousePressed(MouseEvent e) {
		if(f.getController().poserPiece(e.getX(), e.getY()))
			f.setState(new NormalState(f));
	}
}
