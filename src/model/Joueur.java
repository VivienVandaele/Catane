package model;

import java.util.ArrayList;

public class Joueur {
	private int id;
	private String pseudo;
	private int points;
	private ArrayList<Carte> cartes;
	
	public Joueur(int id, String pseudo){
		this.id=id;
		this.pseudo=pseudo;
		this.points=0;
		this.cartes = new ArrayList<Carte>();
	}
	
	public void jouerTour(){

	}
	
	public void ajouterCarte(Ressource ressource){
		cartes.add(new Carte(ressource));
	}
	
	public int getNombreDeCarteType(String type){
		int i=0;
		for(Carte c : cartes){
			if(c.getType().equals(type))
				i++;
		}
		return i;
	}
	
	public int getPoints(){
		return points;
	}
	
	public String getPseudo(){
		return pseudo;
	}
	
	public int getId(){
		return id;
	}
}
