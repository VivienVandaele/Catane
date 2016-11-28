package state;

import java.awt.event.MouseEvent;

import vue.Fenetre;

public class VoleurState extends State{
	public VoleurState(Fenetre f){
		super(f);
	}
	
	public void mousePressed(MouseEvent e) {
		//f.getController().poserPiece(e.getX(), e.getY());
		//f.setState(new NormalState(f));
	}
}
