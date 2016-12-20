package model;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import vue.PartiePanel;

public class Ville extends Piece{
	public Ville(int x, int y){
		super(x, y);
		image=new ImageIcon("images/pieces/ville1.png");
	}
	
	public boolean piecePosable(Plateau pl) {
		ArrayList<Position> positions=pl.getPositions();
		int marge=20;
		for(Position p : positions){
			if(x>p.getX()-marge && x<p.getX()+marge && y>p.getY()-marge && y<p.getY()+marge){
				setX((int)p.getX());
				setY((int)p.getY());
				p.setPiece(this);
				return true;
			}
		}
		return false;
	}

}
