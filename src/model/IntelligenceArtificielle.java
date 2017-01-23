package model;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import controller.Controller;

public class IntelligenceArtificielle extends Joueur{

	public IntelligenceArtificielle(int id, String pseudo) {
		super(id, pseudo);
	}
	
	public void poserPiece(Controller c, Piece p, Plateau pl){
		if(p instanceof Village)
			poserVillage(c, pl);
		else
			poserRoute(c, pl);
	}
	
	public void poserVillage(Controller c, Plateau p){
		Village village = new Village();
		ArrayList<Piece> villages = village.getPositionDisponible(p, c.getJoueur());
		village = (Village) villages.get(ThreadLocalRandom.current().nextInt(0, villages.size()));
		c.poserPiece(village.getX(), village.getY());
	}
	
	public void poserRoute(Controller c, Plateau p){
		Route route = new Route();
		for(Piece piece : route.getPositionDisponible(p, c.getJoueur())){
			c.poserPiece(piece.getX(), piece.getY());
			break;
		}
	}
	
}
