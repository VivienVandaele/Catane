package model;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import controller.Controller;
import vue.PartiePanel;

public class IntelligenceArtificielle extends Joueur{

	public IntelligenceArtificielle(int id, String pseudo) {
		super(id, pseudo);
	}
	
	public boolean accepterEchange(ArrayList<Carte> exporter, ArrayList<Carte> importer){
		boolean flag = true;
		for(Carte c : importer){
			if(getNombreDeCarteType(c.getType())<getNombreDeCarteType(importer, c.getType())){
				flag = false;
			}
		}
		return flag;
	}
	
	public void lancerDes(PartiePanel pan){
	    Thread t = new Thread() {
	    	public void run(){
				try {
					pan.cliquerBouton().join();
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				pan.finTour();
	    	}
	    };
	    t.start();
	}
	
	public void poserPieceDebutPartie(Controller c, Piece p, Plateau pl){
		if(p instanceof Village)
			poserVillageDebutPartie(c, pl);
		else
			poserRouteDebutPartie(c, pl);
	}
	
	public void poserVillageDebutPartie(Controller c, Plateau p){
		Village village = new Village();
		ArrayList<Piece> villages = village.getPositionDisponibleDebutPartie(p, c.getJoueur());
		village = (Village) villages.get(ThreadLocalRandom.current().nextInt(0, villages.size()));
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
