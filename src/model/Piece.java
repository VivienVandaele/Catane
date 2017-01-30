package model;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public abstract class Piece {
	protected int x;
	protected int y;
	protected boolean poser;
	protected Joueur j;
	protected ImageIcon image;

	public Piece(){
		x=0;
		y=0;
	}
	
	public Piece(int x, int y){
		this.x=x;
		this.y=y;
		this.poser=false;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean getPoser(){
		return poser;
	}
	
	public void setX(int x){
		this.x=x;
	}
	
	public void setY(int y){
		this.y=y;
	}
	
	public void setPoser(boolean p, Joueur j){
		poser=p;
		this.j=j;
		setImage();
	}
	
	public Joueur getJoueur(){
		return j;
	}
	
	public ImageIcon getImage(){
		return image;
	}
	
	public abstract void setImage();

	
	public abstract boolean piecePosableDebutPartie(Plateau p, Joueur j, int x, int y);
	
	public abstract ArrayList<Piece> getPositionDisponible(Plateau pl, Joueur j);

	
	public abstract ArrayList<Piece> getPositionDisponibleDebutPartie(Plateau pl, Joueur j);
	
	public abstract boolean piecePosable(Plateau p, Joueur j, int x, int y);
	
	//public abstract ArrayList<Piece> getPositionDisponible(Plateau pl, Joueur j);
}
