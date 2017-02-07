package model;

import java.util.ArrayList;

import observer.Observable;

public class Joueur extends Observable {
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
	
	public boolean accepterEchange(ArrayList<Carte> exporter, ArrayList<Carte> importer){
		return false;
	}
	
	public void ajouterCarte(Ressource ressource){
		Carte c = new Carte(ressource);
		cartes.add(c);
		notifyObserver(c);
	}
	
	public void retirerCarte(Ressource ressource){
		for(Carte c : cartes){
			if(c.getRessource() == ressource){
				notifyObserver(ressource);
				cartes.remove(c);
				break;
			}
		}
	}
	
	public void retirerRessourcesPiece(Piece p){
		if(p instanceof Route){
			retirerCarte(Ressource.bois);
			retirerCarte(Ressource.argile);
		}
		else if(p instanceof Village){
			if(((Village) p).getVille()){
				for(int i=0;i<3;i++)
					retirerCarte(Ressource.pierre);
				retirerCarte(Ressource.ble);
				retirerCarte(Ressource.ble);
			}
			else{
				retirerCarte(Ressource.bois);
				retirerCarte(Ressource.ble);
				retirerCarte(Ressource.argile);
				retirerCarte(Ressource.mouton);
			}
		}
	}
	
	public int getNombreDeCarteType(String type){
		int i=0;
		for(Carte c : cartes){
			if(c.getType().equals(type))
				i++;
		}
		return i;
	}
	
	public int getNombreDeCarteType(ArrayList<Carte> cartes, String type){
		int i=0;
		for(Carte c : cartes){
			if(c.getType().equals(type))
				i++;
		}
		return i;
	}
	
	public boolean possedeRessourceSuffisanteVillage(){
		return getNombreDeCarteType("argile")>0 && getNombreDeCarteType("bois")>0 && getNombreDeCarteType("mouton")>0 && getNombreDeCarteType("ble")>0;
	}
	
	public boolean possedeRessourceSuffisanteRoute(){
		return getNombreDeCarteType("argile")>0 && getNombreDeCarteType("bois")>0;
	}
	
	public boolean possedeRessourceSuffisanteVille(){
		return getNombreDeCarteType("ble")>1 && getNombreDeCarteType("pierre")>2;
	}
	
	public int getPoints(){
		return points;
	}
	
	public int getNombreCartes(){
		return cartes.size();
	}
	
	public int getNombreCartesDev(){
		return 0;
	}
	
	public int getPlusGrandeRoute(){
		return 1;
	}
	
	public String getPseudo(){
		return pseudo;
	}
	
	public int getId(){
		return id;
	}
}
