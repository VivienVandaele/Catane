package state;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import vue.Fenetre;

public abstract class State implements MouseListener{
	Fenetre f;
	public State(Fenetre f){
		this.f=f;
	}
	
	public void mouseClicked(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent arg0) {
		
	}

	public void mouseExited(MouseEvent arg0) {
		
	}

	public void mouseReleased(MouseEvent arg0) {
		
	}
	
	public void mousePressed(MouseEvent arg0) {
		
	}

}
