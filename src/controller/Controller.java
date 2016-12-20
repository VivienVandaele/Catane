package controller;


import model.Joueur;
import model.Plateau;
import model.Ville;
import state.PieceState;
import vue.Fenetre;

public class Controller {
	private Plateau p;
	private Joueur[] joueurs;
	private int idJoueur;
	
	public Controller(Fenetre f){
		joueurs = new Joueur[4];
		p=new Plateau();
		p.addObserver(f);
	}
	
	public boolean acheterVille(){
		return true;
	}
	
	public boolean poserPiece(int x, int y){
		Ville v=new Ville(x, y);
		if(v.piecePosable(p)){
			p.notifyObserver();
			return true;
		}
		return false;
	}
	
	public Plateau getPlateau(){
		return p;
	}
}
