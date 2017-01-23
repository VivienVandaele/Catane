package model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class Village extends Piece{
	private HashMap<Route, Village> adj;
	private ArrayList<Case> cases;
	private boolean ville;
	
	public Village(){
		super();
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
				
			}
			else{
				if(!piece.getPoser() && piece instanceof Village && x>piece.getX()-marge && x<piece.getX()+marge && y>piece.getY()-marge && y<piece.getY()+marge){
					piece.setPoser(true, j);
					p.ajouterPiece(piece);
					if(p.getNombreVillage()>4){
						for(Case c : ((Village)piece).getCases()){
							j.ajouterCarte(c.getRessource());
						}
					}
					return true;
				}
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
	
	public boolean routeAdj(){
		for(Route route : adj.keySet()){
			if(route.getPoser())
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

	public void setImage() {
		image=new ImageIcon("images/pieces/village"+j.getId()+".png");
	}

}
