package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

import controller.Controller;
import vue.EchangeFenetre;
import vue.PartiePanel;

public class IntelligenceArtificielle extends Joueur{

	public IntelligenceArtificielle(int id, String pseudo) {
		super(id, pseudo);
	}
	
	public void deplacerVoleur(Voleur v){
		for(Emplacement e : Voleur.emplacements){
			if(e.getX() != v.getPosition().getX()){
				v.setPosition(e);
				break;
			}
		}
	}
	
	public boolean accepterEchange(Controller controller, Joueur j1, EchangeFenetre e, ArrayList<Carte> exporter, ArrayList<Carte> importer){
		boolean flag = true;
		for(Carte c : importer){
			if(getNombreDeCarteType(c.getType())<getNombreDeCarteType(importer, c.getType())){
				flag = false;
			}
		}
		return flag;
	}
	
	public void jouerTour(Controller c, Plateau p, PartiePanel pan){
		lancerDes(pan);
		
		Village village = new Village();
		ArrayList<Piece> villages = village.getPositionDisponible(p, this);
		if(villages.size()>0){
			if(possedeRessourceSuffisanteVillage()){
				c.acheterPiece(villages.get(0));
				c.poserPiece(villages.get(0).getX(), villages.get(0).getY());
			}
		}
		else if(possedeRessourceSuffisanteRoute()){
			Route route = new Route();
			ArrayList<Piece> routes = route.getPositionDisponible(p, this);
			if(routes.size()>0){
				c.acheterPiece(routes.get(0));
				c.poserPiece(routes.get(0).getX(), routes.get(0).getY());
			}
		}
	}
	
	public void poserRoute(Plateau p){
		if(possedeRessourceSuffisanteVillage()){
			
		}
	}
	
	public void lancerDes(PartiePanel pan){
	    Thread t = new Thread() {
	    	public void run(){
				try {
					pan.cliquerBouton().join();
					sleep(1000);
					pan.finTour();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
	    };
	    t.start();
	}	
	
	public void poserPieceDebutPartie(Controller c, Piece p, Plateau pl){
		Thread t = new Thread(){
			public void run(){
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(p instanceof Village)
					poserVillageDebutPartie(c, pl);
				else
					poserRouteDebutPartie(c, pl);
			}
		};
		t.start();
	}
	
	public void poserVillageDebutPartie(Controller c, Plateau p){
		Village village = new Village();
		ArrayList<Piece> villages = village.getPositionDisponibleDebutPartie(p, c.getJoueur());
        Collections.sort(villages, new Comparator<Piece>() {
		public int compare(Piece v1, Piece v2) {
			return ((Village) v2).getSumCases()-((Village) v1).getSumCases();
		}});
		village = (Village) villages.get(0);

		c.poserPieceDebutPartie(village.getX(), village.getY());
	}
	
	public void poserRouteDebutPartie(Controller c, Plateau p){
		Route route = new Route();
		for(Piece piece : route.getPositionDisponibleDebutPartie(p, c.getJoueur())){
			c.poserPieceDebutPartie(piece.getX(), piece.getY());
			break;
		}
	}
	
}
