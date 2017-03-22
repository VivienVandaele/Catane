package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import controller.Controller;
import vue.EchangeFenetre;
import vue.PartiePanel;

public class IntelligenceArtificielle extends Joueur{
	private boolean villeFlag;
	
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
	    Thread t = new Thread() {
	    	public void run(){
				try {
					pan.cliquerBouton().join();
					sleep(1000);
					villeFlag = true;
					poserPiece(c, p);
					sleep(500);
					pan.finTour();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
	    };
	    t.start();
	}
	
	public void poserPiece(Controller c, Plateau p){
		Village village = new Village();
		ArrayList<Piece> villages = village.getPositionDisponible(p, this);
		Village ville = new Village(true);
		ArrayList<Piece> villes = ville.getPositionDisponible(p, this);
		if(villeFlag && (villages.size()>0 || villes.size()>0)){
			if(villages.size()>0 && possedeRessourceSuffisanteVillage()){
				Collections.sort(villages, new Comparator<Piece>() {
					public int compare(Piece v1, Piece v2) {
						return ((Village) v2).getSumCases()-((Village) v1).getSumCases();
					}});
				c.acheterPiece(villages.get(0));
				c.poserPiece(villages.get(0).getX(), villages.get(0).getY());
				return;
			}
			if(villes.size()>0 && possedeRessourceSuffisanteVille()){
				Collections.sort(villes, new Comparator<Piece>() {
					public int compare(Piece v1, Piece v2) {
						return ((Village) v2).getSumCases()-((Village) v1).getSumCases();
					}});
				c.acheterPiece(new Village(true));
				c.poserPiece(villes.get(0).getX(), villes.get(0).getY());
				return;
			}
			if(villages.size()>0 && ressourceNecessaireVillage(p).size()<ressourceNecessaireVille(p).size()){
				echangeIA(c, p, exportRessourceVillage(), ressourceNecessaireVillage(p));
				return;
			}
			else if(villes.size()>0){
				echangeIA(c, p, exportRessourceVille(), ressourceNecessaireVille(p));
				return;
			}
		}
		if(villages.size()==0 && possedeRessourceSuffisanteRoute()){
			Route route = getRouteEmplacement(p);
			if(route != null){
				c.acheterPiece(route);
				c.poserPiece(route.getX(), route.getY());
			}
		}
		else if(villages.size()==0){
			echangeIA(c, p, exportRessourceRoute(), ressourceNecessaireRoute(p));
		}
	}
	
	public ArrayList<Carte> ressourceNecessaireVillage(Plateau p){
		ArrayList<Carte> ressources = new ArrayList<Carte>();
		Village village = new Village();
		ArrayList<Piece> villages = village.getPositionDisponible(p, this);
		if(villages.size()>0){
			if(getNombreDeCarteType("argile")==0){
				ressources.add(new Carte(Ressource.argile));
			}
			if(getNombreDeCarteType("bois")==0){
				ressources.add(new Carte(Ressource.bois));
			}
			if(getNombreDeCarteType("mouton")==0){
				ressources.add(new Carte(Ressource.mouton));
			}
			if(getNombreDeCarteType("ble")==0){
				ressources.add(new Carte(Ressource.ble));
			}
		}
		return ressources;
	}
	
	public ArrayList<Carte> ressourceNecessaireVille(Plateau p){
		ArrayList<Carte> ressources = new ArrayList<Carte>();
		Village village = new Village(true);
		ArrayList<Piece> villages = village.getPositionDisponible(p, this);
		if(villages.size()>0){
			for(int i=0;i<2-getNombreDeCarteType("ble");i++)
				ressources.add(new Carte(Ressource.ble));
			for(int i=0;i<3-getNombreDeCarteType("pierre");i++)
				ressources.add(new Carte(Ressource.pierre));
		}
		return ressources;
	}
	
	public ArrayList<Carte> ressourceNecessaireRoute(Plateau p){
		ArrayList<Carte> ressources = new ArrayList<Carte>();
		Route route = new Route();
		ArrayList<Piece> routes = route.getPositionDisponible(p, this);
		if(routes.size()>0){
			for(int i=0;i<1-getNombreDeCarteType("bois");i++)
				ressources.add(new Carte(Ressource.bois));
			for(int i=0;i<1-getNombreDeCarteType("argile");i++)
				ressources.add(new Carte(Ressource.argile));
		}
		return ressources;
	}
	
	public void echangeIA(Controller c, Plateau p, ArrayList<Carte> exporter, ArrayList<Carte> importer){
		for(int i = 0;i<4;i++){
			if(i!=getId() && c.getJoueurs()[i] instanceof IntelligenceArtificielle){
				for(Carte ex : exporter){
					for(Carte im : importer){
						ArrayList<Carte> exporterTemp = new ArrayList<Carte>();
						ArrayList<Carte> importerTemp = new ArrayList<Carte>();
						exporterTemp.add(ex);
						importerTemp.add(im);
						if(c.getJoueurs()[i].accepterEchange(c, this, null, exporterTemp, importerTemp)){
							c.getJoueurs()[i].retirerCarte(im.getRessource());
							c.getJoueurs()[i].ajouterCarte(ex.getRessource());
							retirerCarte(ex.getRessource());
							ajouterCarte(im.getRessource());
							poserPiece(c, p);
							return;
						}
					}
				}
			}
		}
		if(villeFlag){
			villeFlag = false;
			poserPiece(c, p);
		}
	}

	public ArrayList<Carte> exportRessourceVillage(){
		ArrayList<Carte> ressources = new ArrayList<Carte>();
		for(int i=0;i<getNombreDeCarteType("ble")-1;i++)
			ressources.add(new Carte(Ressource.ble));
		for(int i=0;i<getNombreDeCarteType("argile")-1;i++)
			ressources.add(new Carte(Ressource.argile));
		for(int i=0;i<getNombreDeCarteType("bois")-1;i++)
			ressources.add(new Carte(Ressource.bois));
		for(int i=0;i<getNombreDeCarteType("mouton")-1;i++)
			ressources.add(new Carte(Ressource.mouton));
		for(int i=0;i<getNombreDeCarteType("pierre");i++)
			ressources.add(new Carte(Ressource.pierre));
		return ressources;
	}
	
	public ArrayList<Carte> exportRessourceVille(){
		ArrayList<Carte> ressources = new ArrayList<Carte>();
		for(int i=0;i<getNombreDeCarteType("ble")-2;i++)
			ressources.add(new Carte(Ressource.ble));
		for(int i=0;i<getNombreDeCarteType("argile");i++)
			ressources.add(new Carte(Ressource.argile));
		for(int i=0;i<getNombreDeCarteType("bois");i++)
			ressources.add(new Carte(Ressource.bois));
		for(int i=0;i<getNombreDeCarteType("mouton");i++)
			ressources.add(new Carte(Ressource.mouton));
		for(int i=0;i<getNombreDeCarteType("pierre")-3;i++)
			ressources.add(new Carte(Ressource.pierre));
		return ressources;
	}
	
	public ArrayList<Carte> exportRessourceRoute(){
		ArrayList<Carte> ressources = new ArrayList<Carte>();
		for(int i=0;i<getNombreDeCarteType("ble");i++)
			ressources.add(new Carte(Ressource.ble));
		for(int i=0;i<getNombreDeCarteType("argile")-1;i++)
			ressources.add(new Carte(Ressource.argile));
		for(int i=0;i<getNombreDeCarteType("bois")-1;i++)
			ressources.add(new Carte(Ressource.bois));
		for(int i=0;i<getNombreDeCarteType("mouton");i++)
			ressources.add(new Carte(Ressource.mouton));
		for(int i=0;i<getNombreDeCarteType("pierre");i++)
			ressources.add(new Carte(Ressource.pierre));
		return ressources;
	}
	
	public void proposerEchange(Controller c){
		ArrayList<Carte> exporter = new ArrayList<Carte>();
		ArrayList<Carte> importer = new ArrayList<Carte>();
		
		exporter.add(new Carte(Ressource.argile));
		importer.add(new Carte(Ressource.ble));
		c.proposerEchange(null, exporter, importer);
	}
	
	public Route getRouteEmplacement(Plateau p){
		Route route = new Route();
		Village village = new Village();
		ArrayList<Piece> routesSimuler = new ArrayList<Piece>();
		
		ArrayList<Piece> routes = route.getPositionDisponible(p, this);
		int min = 4;
		LinkedHashMap<Village, Route> positionVillages = new LinkedHashMap<Village, Route>();
		if(routes.size()>0){
			for(Piece piece : routes){

				for(Piece pi : routesSimuler){
					pi.setPoserSimulation(false, null);
				}
				
				int i = 1;
				piece.setPoserSimulation(true, this);
				routesSimuler.add(piece);
				while(i <= min){
					ArrayList<Piece> villages = village.getPositionDisponible(p, this);
					if(villages.size() > 0){
						if(i < min){
							min = i;
							positionVillages = new LinkedHashMap<Village, Route>();
						}
						for(Piece v : villages){
							positionVillages.put((Village)v, (Route)piece);
						}
						break;
					}
					
					ArrayList<Piece> routes2 = route.getPositionDisponible(p, this);
					routes2.removeAll(routes);
					for(Piece r : routes2){
						r.setPoserSimulation(true, this);
						routesSimuler.add(r);
					}
					i++;
				}
			}
		}
		for(Piece piece : routesSimuler){
			piece.setPoserSimulation(false, null);
		}
		
		if(!positionVillages.isEmpty()){
			int maxSum = 0;
			Village bestVillage = null;
			for(Village v : positionVillages.keySet()){
				if(v.getSumCases()>maxSum){
					maxSum = v.getSumCases();
					bestVillage = v;
				}
			}
			return positionVillages.get(bestVillage);
		}
		return null;
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
	
	public void supprimerCartesVoleur(int n){
		for(int i=0;i<n;i++)
			retirerCarteAleatoire();
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
