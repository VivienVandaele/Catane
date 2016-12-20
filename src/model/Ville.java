package model;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import vue.PartiePanel;

public class Ville extends Piece{
	
	
	public Ville(int x, int y){
		super(x, y);
		image=new ImageIcon("images/pieces/ville1.png");
	}
	
	public static void setEmplacements(){
		emplacements=new Point[12][6];
    	Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    	int margeGauche=3*PartiePanel.widthCase/2+5;
    	int margeTop=PartiePanel.widthCase/6;

	}
	
	public boolean piecePosable(Plateau p) {
		int marge=40;
		for(int i=0;i<3;i++){
			if(x>emplacements[0][i].getX()-marge && x<emplacements[0][i].getX()+marge && y>emplacements[0][i].getY()-marge && y<emplacements[0][i].getY()+marge){
				setX((int)emplacements[0][i].getX());
				setY((int)emplacements[0][i].getY());
				return true;
			}
		}
		return false;
	}
}
