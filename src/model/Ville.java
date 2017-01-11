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
		return true;
	}

}
