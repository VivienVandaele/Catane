package model;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public abstract class Piece {
	protected int x;
	protected int y;
	protected ImageIcon image;
	protected static Point[][] emplacements;

	public Piece(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x=x;
	}
	
	public void setY(int y){
		this.y=y;
	}
	
	public ImageIcon getImage(){
		return image;
	}
	
	public abstract boolean piecePosable(Plateau p);
}
