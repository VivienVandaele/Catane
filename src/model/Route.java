package model;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Route extends Piece{
	private String orientation;
	
	public Route(){
		super();
	}
	
	public Route(int x, int y, String orientation){
		super(x, y);
		this.orientation = orientation;
	}
	
	public Route(int x, int y) {
		super(x, y);
	}
	
	public boolean piecePosable(Plateau p, Joueur j, int x, int y) {
		int marge=20;
		for(Piece piece : getPositionDisponible(p, j)){
			if(piece instanceof Route && x>piece.getX()-marge && x<piece.getX()+marge && y>piece.getY()-marge && y<piece.getY()+marge){
				piece.setPoser(true, j);
				p.ajouterPiece(piece);
				return true;
			}
		}
		return false;
	}

	public boolean piecePosableDebutPartie(Plateau p, Joueur j, int x, int y) {
		int marge=20;
		for(Piece piece : getPositionDisponibleDebutPartie(p, j)){
			if(piece instanceof Route && x>piece.getX()-marge && x<piece.getX()+marge && y>piece.getY()-marge && y<piece.getY()+marge){
				piece.setPoser(true, j);
				p.ajouterPiece(piece);
				return true;
			}
		}
		return false;
	}

	public ArrayList<Piece> getPositionDisponible(Plateau pl, Joueur j) {
		ArrayList<Piece> p=new ArrayList<Piece>();
		for(Piece piece : pl.getPieces()){
			if(!piece.getPoser() && piece instanceof Route){
				for(Piece village : pl.getPieces()){
					if(village instanceof Village && ((Village) village).getAdj().containsKey(piece)){
						if(((Village) village).routeAdj(j)){
							p.add(piece);
							break;
						}
					}
				}
			}
		}
		return p;
	}
	
	public ArrayList<Piece> getPositionDisponibleDebutPartie(Plateau pl, Joueur j) {
		ArrayList<Piece> p=new ArrayList<Piece>();
		for(Piece piece : pl.getPieces()){
			if(!piece.getPoser() && piece instanceof Route){
				for(Piece village : pl.getPieces()){
					if(village instanceof Village && ((Village) village).getAdj().containsKey(piece)){
						if(village.getPoser() && village.getJoueur().getId()==j.getId() && !((Village) village).routeAdj(j)){
							p.add(piece);
							break;
						}
					}
				}
			}
		}
		return p;
	}
	
	public String getOrientation(){
		return orientation;
	}

	public void setImage() {
		image=new ImageIcon("images/pieces/route"+j.getId()+".png");
	}



}
