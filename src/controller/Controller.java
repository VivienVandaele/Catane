package controller;


import java.util.ArrayList;

import javax.swing.plaf.SliderUI;

import model.Carte;
import model.Case;
import model.IntelligenceArtificielle;
import model.Joueur;
import model.Piece;
import model.Plateau;
import model.Route;
import model.Village;
import state.NormalState;
import state.PieceState;
import vue.EchangeFenetre;
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
		joueurs[0].addObserver(f);
		for(int i=1;i<4;i++){
			joueurs[i] = new IntelligenceArtificielle(i, "IA"+i);
			joueurs[i].addObserver(f);
		}
		idJoueur=0;
	}
	
	public void jouerTour(){
		prochainJoueur();
		if(joueurs[idJoueur] instanceof IntelligenceArtificielle){
			((IntelligenceArtificielle)joueurs[idJoueur]).lancerDes(f.getPartiePanel());
		}
	}
	
	public void proposerEchange(EchangeFenetre e, ArrayList<Carte> exporter, ArrayList<Carte> importer){
		for(int i=0;i<4;i++){
			if(i!=idJoueur)
				e.setImageEchange(joueurs[i].getId(), joueurs[i].accepterEchange(exporter, importer));
		}
	}
	
	public void echanger(Joueur j, ArrayList<Carte> exporter, ArrayList<Carte> importer){
		Thread t = new Thread(){
	    	public void run(){
	    		for(Carte c : exporter){
	    			joueurs[idJoueur].retirerCarte(c.getRessource());
	    			j.ajouterCarte(c.getRessource());
	    			try {
						sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	    		}
	    		for(Carte c : importer){
	    			joueurs[idJoueur].ajouterCarte(c.getRessource());
	    			j.retirerCarte(c.getRessource());
	    			try {
						sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	    		}
	    	}
          };
          t.start();
	}
	
	public void debutPartie(){
		piece=new Village(0, 0);
		f.setState(new PieceState(f));
	}
	
	public boolean acheterPiece(Piece p){
		f.getPartiePanel().desactiverBoutons();
		piece = p;
		joueurs[idJoueur].retirerRessourcesPiece(p);
		f.setState(new PieceState(f));
		return true;
	}
	
	public void distribuerRessources(int d){
		for(Piece piece : p.getPiecesPoser()){
			if(piece instanceof Village){
				for(Case c : ((Village) piece).getCases()){
					if(c.getNumero()==d){
						piece.getJoueur().ajouterCarte(c.getRessource());
					}
				}
			}
		}
	}
	
	public boolean poserPiece(int x, int y){
		assert(piece!=null);
		if(piece.piecePosable(p, joueurs[idJoueur], x, y)){
			f.setState(new NormalState(f));
			f.getPartiePanel().activerBoutons();
			return true;
		}
		return false;
	}
	
	public boolean poserPieceDebutPartie(int x, int y){
		assert(piece!=null);
		if(piece.piecePosableDebutPartie(p, joueurs[idJoueur], x, y)){
			if(p.getNombreVillage()<8 || (p.getNombreVillage()==8 && piece instanceof Village)){
				if(piece instanceof Route){
					prochainJoueurDebutPartie();
					piece = new Village();
				}
				else{
					piece = new Route();
				}
				if(joueurs[idJoueur] instanceof IntelligenceArtificielle)
					((IntelligenceArtificielle) joueurs[idJoueur]).poserPieceDebutPartie(this, piece, p);
				else
					f.setState(new PieceState(f));	
			}
			else{
				f.getPartiePanel().activerBoutons();
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
		if(idJoueur == 0)
			f.getPartiePanel().activerBoutons();
		else
			f.getPartiePanel().desactiverBoutons();
	}
	
	public Plateau getPlateau(){
		return p;
	}
	
	public int getIdJoueur(){
		return idJoueur;
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
	
	public boolean getDebutPartie(){
		return p.getPiecesPoser().size()<16;
	}
}
