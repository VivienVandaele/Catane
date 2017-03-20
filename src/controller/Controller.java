package controller;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

import model.Carte;
import model.CarteDeveloppement;
import model.Case;
import model.IntelligenceArtificielle;
import model.Joueur;
import model.Piece;
import model.Plateau;
import model.Ressource;
import model.Route;
import model.Village;
import state.NormalState;
import state.PieceState;
import state.VoleurState;
import vue.EchangeFenetre;
import vue.Fenetre;
import vue.ProposerEchangeFenetre;
import vue.VolerRessourceFenetre;
import vue.VoleurFenetre;

public class Controller {
	private Plateau p;
	private Fenetre f;
	private Piece piece;
	private Joueur[] joueurs;
	private int idJoueur;
	private boolean carteDevRoutes = false;
	public static int idJoueurHumain;
	public static int nombreJoueurHumain;
	
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
		idJoueurHumain=0;
		nombreJoueurHumain=1;
	}
	
	public Controller(Fenetre f, boolean b){
		this.f=f;
		piece=null;
		p=new Plateau();
		p.addObserver(f);
		joueurs = new Joueur[4];
		for(int i=0;i<4;i++){
			joueurs[i] = new Joueur(i, "Joueur"+i);
			joueurs[i].addObserver(f);
		}
		idJoueur=0;
		idJoueurHumain=0;
		nombreJoueurHumain=4;
	}
	
	public void jouerTour(){
		prochainJoueur();
		if(joueurs[idJoueur] instanceof IntelligenceArtificielle){
			((IntelligenceArtificielle)joueurs[idJoueur]).jouerTour(this, p, f.getPartiePanel());
		}
	}
	
	public void proposerEchange(EchangeFenetre e, ArrayList<Carte> exporter, ArrayList<Carte> importer){
		for(int i=0;i<4;i++){
			if(i!=idJoueur && joueurs[i] instanceof IntelligenceArtificielle)
				e.setImageEchange(joueurs[i].getId(), joueurs[i].accepterEchange(this, joueurs[idJoueur], e, exporter, importer));
			else if(i!=idJoueur)
				joueurs[i].accepterEchange(this, joueurs[idJoueur], e, exporter, importer);
		}
	}
	
	public void setRoutePlusLongue(){
		for(int i=0;i<4;i++){
			ArrayList<Piece> pieces = p.getPieces();
			int max=0, max2=0;
			for(Piece piece : pieces){
				if(piece instanceof Route && piece.getPoser() && piece.getJoueur().getId()==i){
					ArrayList<Route> routes = new ArrayList<Route>();
					routes.add((Route)piece);
					max2 = ((Route)piece).getPlusLongueRoute(p, routes, 0);
					if(max2 > max) max = max2;
				}
			}
			joueurs[i].setRouteLongue(max);
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
		for(int i=0;i<4;i++){
			joueurs[0].ajouterCarte(Ressource.pierre);
			joueurs[0].ajouterCarte(Ressource.ble);
			joueurs[0].ajouterCarte(Ressource.mouton);
			acheterCarteDev();
		}
		/*
		ArrayList<Carte> exporter = new ArrayList<Carte>();
		exporter.add(new Carte(Ressource.argile));
		ArrayList<Carte> importer = new ArrayList<Carte>();
		importer.add(new Carte(Ressource.pierre));
		new ProposerEchangeFenetre(joueurs[1], joueurs[0], exporter, importer, this);
		*/
		f.setState(new PieceState(f));
	}
	
	public void activerVoleur(){
		f.getPartiePanel().setBloquerFin(true);
		f.getPartiePanel().desactiverBoutons();
		f.setState(new VoleurState(f));
	}
	
	public boolean deplacerVoleur(int x, int y){
		if(p.getVoleur().poserVoleur(x, y)){
			f.setState(new NormalState(f));
			HashSet<Joueur> joueursVoisins = new HashSet<Joueur>();
			for(Piece piece : p.getPiecesPoser()){
				if(piece instanceof Village){
					for(Case c : ((Village) piece).getCases()){
						if(p.getVoleur().getCase() == c && piece.getJoueur()!=joueurs[idJoueur] && piece.getJoueur().getNombreCartes()>0)
							joueursVoisins.add(piece.getJoueur());
					}
				}
			}
			if(joueursVoisins.size()>0)
				new VolerRessourceFenetre(joueurs[idJoueur], joueursVoisins);
			f.getPartiePanel().activerBoutons();
			f.getPartiePanel().setBloquerFin(false);
			return true;
		}
		return false;
	}
	
	public boolean acheterPiece(Piece p){
		if(idJoueur == idJoueurHumain)
			f.getPartiePanel().desactiverBoutons();
		piece = p;
		joueurs[idJoueur].retirerRessourcesPiece(p);
		if(idJoueur == idJoueurHumain)
			f.setState(new PieceState(f));
		return true;
	}
	
	public boolean carteDevRoute(boolean b){
		if(idJoueur == idJoueurHumain)
			f.getPartiePanel().desactiverBoutons();
		piece = new Route();
		if(idJoueur == idJoueurHumain)
			f.setState(new PieceState(f));
		carteDevRoutes = b;
		return true;
	}
	
	public void acheterCarteDev(){
		joueurs[idJoueur].retirerCarte(Ressource.pierre);
		joueurs[idJoueur].retirerCarte(Ressource.mouton);
		joueurs[idJoueur].retirerCarte(Ressource.ble);
		ArrayList<String> list = new ArrayList<String>();
		list.add("chevalier");
		list.add("invention");
		list.add("monopole");
		list.add("point");
		list.add("route");
		joueurs[idJoueur].ajouterCartesDev(new CarteDeveloppement(list.remove(ThreadLocalRandom.current().nextInt(0, list.size()))));
	}
	
	public void distribuerRessources(int d){
		if(d==7){
			if(joueurs[0].getNombreCartes()>7)
				new VoleurFenetre(joueurs[0], joueurs[0].getNombreCartes()/2);
			if(joueurs[idJoueur] instanceof IntelligenceArtificielle){
				((IntelligenceArtificielle) joueurs[idJoueur]).deplacerVoleur(p.getVoleur());
			}
			else{
				activerVoleur();	
			}
		}
		else{
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
	}
	
	public boolean poserPiece(int x, int y){
		assert(piece!=null);
		if(piece.piecePosable(p, joueurs[idJoueur], x, y)){
			f.setState(new NormalState(f));
			f.getPartiePanel().activerBoutons();
			if(carteDevRoutes)
				carteDevRoute(false);
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
		if(idJoueur<nombreJoueurHumain)
			idJoueurHumain = idJoueur;
	}
	
	public void prochainJoueur(){
		idJoueur++;
		idJoueur%=4;
		if(idJoueur<nombreJoueurHumain)
			idJoueurHumain = idJoueur;
		if(idJoueur == idJoueurHumain)
			f.getPartiePanel().activerBoutons();
		else
			f.getPartiePanel().desactiverBoutons();
		f.getPartiePanel().setCarteDev();
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
