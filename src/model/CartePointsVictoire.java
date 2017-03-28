package model;

import java.io.Serializable;

public class CartePointsVictoire implements Serializable{
	private Joueur j;
	private int max;
	
	public CartePointsVictoire(int n){
		max = n;
	}
	
	public void setJoueur(Joueur j, int n){
		if(this.j != null){
			this.j.ajouterPoint(-2);
		}
		j.ajouterPoint(2);
		this.j = j;
		this.max = n;
	}
	
	public int getMax(){
		return max;
	}
	
	public Joueur getJoueur(){
		return j;
	}
}
