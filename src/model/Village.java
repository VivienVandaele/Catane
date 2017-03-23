package model;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import vue.PartiePanel;

public class Village extends Piece implements Serializable{
	private HashMap<Route, Village> adj;
	private ArrayList<Case> cases;
	private boolean ville;
	private Port p;
	
	public Village(){
		super();
	}
	
	public Village(boolean ville){
		super();
		this.ville=ville;
	}
	
	public Village(int x, int y) {
		super(x, y);
		ville = false;
		adj=new HashMap<Route, Village>();
		cases = new ArrayList<Case>();
	}
	
	public Village(int x, int y, boolean ville) {
		super(x, y);
		this.ville = ville;
		adj=new HashMap<Route, Village>();
		cases = new ArrayList<Case>();
	}
	
	public boolean piecePosable(Plateau p, Joueur j, int x, int y) {
		int marge=20;
		for(Piece piece : getPositionDisponible(p, j)){
			if(ville){
				if(piece.getPoser() && piece instanceof Village && x>piece.getX()-marge && x<piece.getX()+marge && y>piece.getY()-marge && y<piece.getY()+marge){
					((Village) piece).setVille();
					piece.setPoser(true, j);
					p.ajouterPiece(piece);
					j.ajouterPoint(1);
					return true;
				}
			}
			else{
				if(!piece.getPoser() && piece instanceof Village && x>piece.getX()-marge && x<piece.getX()+marge && y>piece.getY()-marge && y<piece.getY()+marge){
					piece.setPoser(true, j);
					p.ajouterPiece(piece);
					j.ajouterPoint(1);
					return true;
				}
			}
		}
		return false;
	}

	public boolean piecePosableDebutPartie(Plateau p, Joueur j, int x, int y) {
		int marge=20;
		for(Piece piece : getPositionDisponibleDebutPartie(p, j)){
			if(!piece.getPoser() && piece instanceof Village && x>piece.getX()-marge && x<piece.getX()+marge && y>piece.getY()-marge && y<piece.getY()+marge){
				piece.setPoser(true, j);
				p.ajouterPiece(piece);
				if(p.getNombreVillage()>4){
					for(Case c : ((Village)piece).getCases()){
						j.ajouterCarte(c.getRessource());
					}
				}
				j.ajouterPoint(1);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Piece> getPositionDisponible(Plateau pl, Joueur j) {
		ArrayList<Piece> p=new ArrayList<Piece>();
		boolean voisin;
		for(Piece piece : pl.getPieces()){
			voisin = false;
				if(piece instanceof Village){
					if(ville && !((Village) piece).getVille() && piece.getPoser() && piece.getJoueur()==j)
						p.add(piece);
					else if(!ville && !piece.getPoser()){
						for(Village v : (((Village) piece).getVillagesAdj())){
							if(v.getPoser())
								voisin = true;
						}
						if(!voisin && ((Village) piece).routeAdj(j))
							p.add(piece);
					}
				}
		}
		return p;
	}
	
	public ArrayList<Piece> getPositionDisponibleDebutPartie(Plateau pl, Joueur j) {
		ArrayList<Piece> p=new ArrayList<Piece>();
		boolean voisin;
		for(Piece piece : pl.getPieces()){
			voisin = false;
				if(piece instanceof Village){
					if(ville && !((Village) piece).getVille())
						p.add(piece);
					else if(!ville && !piece.getPoser()){
						for(Village v : (((Village) piece).getVillagesAdj())){
							if(v.getPoser())
								voisin = true;
						}
						if(!voisin)
							p.add(piece);
					}
				}
		}
		return p;
	}
	
	public boolean routeAdj(Joueur j){
		for(Route route : adj.keySet()){
			if(route.getPoser() && route.getJoueur()==j)
				return true;
		}
		return false;
	}
	
	public ArrayList<Village> getVillagesAdj(){
		ArrayList<Village> villages = new ArrayList<Village>();
		for(Route key : adj.keySet()){
			villages.add(adj.get(key));
		}
		return villages;
	}
	
	public void ajouterCase(Case c){
		if(!c.getRessource().getType().equals("desert"))
			cases.add(c);
	}
	
	public void ajouterVillageAdj(Route a, Village n){
		adj.put(a, n);
		n.getAdj().put(a, this);
	}
	
	public HashMap<Route, Village> getAdj(){
		return adj;
	}
	
	public ArrayList<Case> getCases(){
		return cases;
	}
	
	public boolean getVille(){
		return ville;
	}
	
	public void setVille(){
		ville = true;
	}
	
	public void setPort(Port p){
		this.p = p;
	}
	
	public Port getPort(){
		return this.p;
	}
	
	public int getSumCases(){
		int sum=0;
		for(Case c : cases)
			sum += c.getProbabiliteNumero();
		return sum;
	}

	public void setImage() {
		if(!ville)
			image=new ImageIcon(new ImageIcon("images/pieces/village"+j.getId()+".png").getImage().getScaledInstance(PartiePanel.widthCarte/4, PartiePanel.widthCarte/4, Image.SCALE_DEFAULT));
		else
			image=new ImageIcon(new ImageIcon("images/pieces/ville"+j.getId()+".png").getImage().getScaledInstance(PartiePanel.widthCarte/3, PartiePanel.widthCarte/3, Image.SCALE_DEFAULT));
	}
}
