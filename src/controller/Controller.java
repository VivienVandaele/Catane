package controller;


import model.IntelligenceArtificielle;
import model.Joueur;
import model.Piece;
import model.Plateau;
import model.Route;
import model.Village;
import state.NormalState;
import state.PieceState;
import vue.Fenetre;

public class Controller {
	private Plateau p;
	private Fenetre f;
	private Piece piece;
	private Joueur[] joueurs;
	private int idJoueur;
	
	public Controller(Fenetre f){
		this.f=f;
		piece=null;
		p=new Plateau();
		p.addObserver(f);
		joueurs = new Joueur[4];
		joueurs[0] = new Joueur(0, "Joueur");
		for(int i=1;i<4;i++)
			joueurs[i] = new IntelligenceArtificielle(i, "IA"+i);
		idJoueur=0;
	}
	
	public void debutPartie(){
		piece=new Village(0, 0);
		f.setState(new PieceState(f));
	}
	
	public boolean acheterVille(){
		return true;
	}
	
	public boolean poserPiece(int x, int y){
		assert(piece!=null);
		if(piece.piecePosable(p, joueurs[idJoueur], x, y)){
			if(p.getNombreVillage()<8 || (p.getNombreVillage()==8 && piece instanceof Village)){
				if(piece instanceof Route){
					prochainJoueurDebutPartie();
					piece = new Village();
				}
				else{
					piece = new Route();
				}
				if(joueurs[idJoueur] instanceof IntelligenceArtificielle)
					((IntelligenceArtificielle) joueurs[idJoueur]).poserPiece(this, piece, p);
				else
					f.setState(new PieceState(f));	
			}
			else{
				f.setState(new NormalState(f));
			}
			return true;
		}
		return false;
	}
	
	public void prochainJoueurDebutPartie(){
		if(p.getNombreVillage()<4)
			idJoueur++;
		else
			idJoueur--;
		if(p.getNombreVillage()==4)
			idJoueur=3;
	}
	
	public void prochainJoueur(){
		idJoueur++;
		idJoueur%=4;
	}
	
	public Plateau getPlateau(){
		return p;
	}
	
	public Joueur[] getJoueurs(){
		return joueurs;
	}
	
	public Joueur getJoueur(){
		return joueurs[idJoueur];
	}
	
	public Piece getPiece(){
		return piece;
	}
}
