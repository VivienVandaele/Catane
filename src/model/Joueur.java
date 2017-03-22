package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import controller.Controller;
import observer.Observable;
import vue.ChoisirRessourceFenetre;
import vue.EchangeFenetre;
import vue.ProposerEchangeFenetre;

public class Joueur extends Observable {
	private int id;
	private String pseudo;
	private int points;
	private int nbChevalier;
	private ArrayList<Carte> cartes;
	private Integer[] nombreCartesDev;
	private int routeLongue;
	
	public Joueur(int id, String pseudo){
		this.id=id;
		this.pseudo=pseudo;
		this.nbChevalier = 0;
		this.points=0;
		this.routeLongue = 0;
		this.cartes = new ArrayList<Carte>();
		this.nombreCartesDev = new Integer[5];
		for(int i=0;i<5;i++)
			nombreCartesDev[i]=0;
	}
	
	public boolean accepterEchange(Controller c, Joueur j1, EchangeFenetre e, ArrayList<Carte> exporter, ArrayList<Carte> importer){
		new ProposerEchangeFenetre(e, j1, this, exporter, importer, c);
		return false;
	}
	
	public void ajouterCartesDev(CarteDeveloppement c){
		if(c.getType().equals("chevalier")) nombreCartesDev[0]++;
		else if(c.getType().equals("invention")) nombreCartesDev[1]++;
		else if(c.getType().equals("monopole")) nombreCartesDev[2]++;
		else if(c.getType().equals("point")) nombreCartesDev[3]++;
		else if(c.getType().equals("route"))	nombreCartesDev[4]++;
		notifyObserver(c);
	}
	
	public void retirerCartesDev(Controller controller, int i){
		CarteDeveloppement c = null;
		if(i==0){
			controller.activerVoleur();
			nbChevalier++;
			CartePointsVictoire carteChevalier = controller.getCarteChevalier();
			if((carteChevalier.getJoueur()==null || carteChevalier.getJoueur().getId() != getId()) && nbChevalier>carteChevalier.getNumber()){
				carteChevalier.setJoueur(this, nbChevalier);
			}
			c = new CarteDeveloppement("chevalier");
		}
		else if(i==1){
			new ChoisirRessourceFenetre(this, controller, false);
			c = new CarteDeveloppement("invention");
		}
		else if(i==2){
			new ChoisirRessourceFenetre(this, controller, true);
			c = new CarteDeveloppement("monopole");
		}
		else if(i==3){
			ajouterPoint(1);
			c = new CarteDeveloppement("point");
		}
		else if(i==4){
			controller.carteDevRoute(true);
			c = new CarteDeveloppement("route");
		}
		nombreCartesDev[i]--;
		notifyObserver(c);
	}
	
	public void ajouterCarte(Ressource ressource){
		Carte c = new Carte(ressource);
		cartes.add(c);
		notifyObserver(c);
	}
	
	public void retirerCarte(Ressource ressource){
		for(Carte c : cartes){
			if(c.getRessource() == ressource){
				cartes.remove(c);
				notifyObserver(ressource);
				break;
			}
		}
	}
	
	public Carte retirerCarteAleatoire(){
		Carte carte = cartes.remove(ThreadLocalRandom.current().nextInt(0, cartes.size()));
		notifyObserver(carte.getRessource());
		return carte;
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
	
	public boolean possedeRessourceSuffisanteCarteDev(){
		return getNombreDeCarteType("ble")>=1 && getNombreDeCarteType("pierre")>=1 && getNombreDeCarteType("mouton")>=1;
	}
	
	public Color getColor(){
		if(id == 0)
			return Color.red;
		if(id == 1)
			return Color.blue;
		if(id == 2)
			return Color.green;
		return Color.yellow;
	}
	
	public int getNombreCartesDev(int i){
		return nombreCartesDev[i];
	}
	
	public int getPoints(){
		return points;
	}
	
	public void ajouterPoint(int p){
		points+=p;
		notifyObserver(p);
	}
	
	public int getNombreCartes(){
		return cartes.size();
	}
	
	public int getNombreChevalier(){
		return nbChevalier;
	}
	
	public int getNombreCartesDev(){
		int sum = 0;
		for(int i=0;i<5;i++)
			sum+=nombreCartesDev[i];
		return sum;
	}
	
	public void setRouteLongue(int n){
		this.routeLongue = n;
	}
	
	public int getRouteLongue(){
		return routeLongue;
	}
	
	public String getPseudo(){
		return pseudo;
	}
	
	public int getId(){
		return id;
	}
}
