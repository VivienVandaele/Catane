package state;

import java.awt.event.MouseEvent;
import vue.Fenetre;

public class PieceState extends State{
	public PieceState(Fenetre f){
		super(f);
	}

	public void mousePressed(MouseEvent e) {
		if(f.getController().poserPiece(e.getX(), e.getY()))
			f.setState(new NormalState(f));
	}
}
