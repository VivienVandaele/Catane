package model;

import java.util.HashMap;

public class Noeud extends Position {
	private HashMap<Arete, Noeud> adj;
	
	public Noeud(int x, int y) {
		super(x, y);
		adj=new HashMap<Arete, Noeud>();
	}
	
	public void ajouterNoeud(Arete a, Noeud n){
		adj.put(a, n);
		n.getAdj().put(a, this);
	}
	
	public HashMap<Arete, Noeud> getAdj(){
		return adj;
	}
}
