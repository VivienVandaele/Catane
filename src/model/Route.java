package model;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import vue.PartiePanel;

public class Route extends Piece implements Serializable{
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
	
	public int getPlusLongueRoute(Plateau pl, ArrayList<Route> routes, int t){
		t++;
		int max=t, max2=0;
		for(Piece piece : pl.getPieces()){
			if(piece instanceof Route && piece.getPoser() && !routes.contains(piece) && piece.getJoueur()==getJoueur()){
				for(Piece village : pl.getPieces()){
					if(village instanceof Village && ((Village) village).getAdj().containsKey(piece)){
						for(Route r : ((Village) village).getAdj().keySet()){
							if(r == this){
								routes.add((Route)piece);
								max2 = ((Route)piece).getPlusLongueRoute(pl, routes, t);
								routes.remove((Route)piece);
								if(max<max2)
									max = max2;
							}
						}
					}
				}
			}
		}
		return max;
	}
	
	public String getOrientation(){
		return orientation;
	}

	public void setImage() {
		image=new ImageIcon(new ImageIcon("images/pieces/route"+j.getId()+".png").getImage().getScaledInstance(PartiePanel.widthCarte/7, PartiePanel.widthCarte/3, Image.SCALE_DEFAULT));
	}
}
