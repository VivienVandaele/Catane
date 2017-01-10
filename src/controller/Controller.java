package controller;


import model.Joueur;
import model.Plateau;
import model.Ville;
import state.NormalState;
import state.PieceState;
import vue.Fenetre;

public class Controller {
	private Plateau p;
	private Joueur[] joueurs;
	private int idJoueur;
	private Fenetre f;
	
	public Controller(Fenetre f){
		this.f=f;
		joueurs = new Joueur[4];
		for(int i=0;i<4;i++)
			joueurs[i]=new Joueur(i, "Joueur"+i);
		p=new Plateau();
		p.addObserver(f);
		f.revalidate();
	}
	
	public void lancerPartie(){
		f.setState(new PieceState(f));
	}
	
	public boolean acheterVille(){
		return true;
	}
	
	public boolean poserPiece(int x, int y){
		Ville v=new Ville(x, y);
		if(v.piecePosable(p)){
			if(p.nombreVillage()<4)
				f.setState(new PieceState(f));
			else
				f.setState(new NormalState(f));
			p.notifyObserver(v);
			return true;
		}
		return false;
	}
	
	public Plateau getPlateau(){
		return p;
	}
}
