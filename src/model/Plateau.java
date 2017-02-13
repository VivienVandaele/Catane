package model;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;

import observer.Observable;
import vue.PartiePanel;

public class Plateau extends Observable{
	private Case[][] cases;
	private ArrayList<Piece> pieces;
	private ArrayList<Piece> piecesPoser;
	private Voleur voleur;
	
	public Plateau(){
		voleur = new Voleur();
		voleur.setOberservers(getOberservers());
		cases = new Case[7][7];
		genererCasesAleatoire();
		setNumeros();
		piecesPoser = new ArrayList<Piece>();
		pieces= new ArrayList<Piece>();
		setPositionPieces();
	}
	
	public void genererCasesAleatoire(){
		ArrayList<Case> listCases = new ArrayList<Case>();
		listCases.add(new Case(Ressource.desert));
		for(int i=0;i<4;i++){
			listCases.add(new Case(Ressource.bois));
			listCases.add(new Case(Ressource.ble));
			listCases.add(new Case(Ressource.mouton));
			if(i<3){
				listCases.add(new Case(Ressource.argile));
				listCases.add(new Case(Ressource.pierre));
			}
			for(int k=0;k<4+i;k++)
				cases[i][k]=new Case(Ressource.mer);
		}
    	
		for(int k=0;k<3;k++)
			for(int i=0;i<4+(2-k);i++)
				if(k==2 || i==0 || i==5-k)
					cases[k+4][i]=new Case(Ressource.mer);
				else
					cases[k+4][i]=listCases.remove(ThreadLocalRandom.current().nextInt(0, listCases.size()));

		for(int i=0;i<3;i++)
			for(int j=1;j<4+i;j++)
				cases[i+1][j]=listCases.remove(ThreadLocalRandom.current().nextInt(0, listCases.size()));
	}
	
	public void setNumeros(){
		int start=ThreadLocalRandom.current().nextInt(1, 4);
		int valeur[]={5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4, 5, 6, 3, 11};
		int indice=0;
		int margeGauche = PartiePanel.margeGauche;
		int widthCase = PartiePanel.widthCase;
		int margeTop = PartiePanel.margeTop;
		
		for(int n=start;n>0;n--){
			if(!cases[1][n].getRessource().getType().equals("desert")){
				cases[1][n].setNumero(valeur[indice++]);
			}
			cases[1][n].setCoordonneesJeton(margeGauche+widthCase*n-widthCase/2+widthCase/2-cases[1][n].getJeton().getIconWidth()/2, widthCase*3/4+margeTop+widthCase/2-cases[1][n].getJeton().getIconHeight()/2);
			voleur.ajouterEmplacement(cases[1][n].getJetonX()-cases[1][n].getJeton().getIconWidth()/2, cases[1][n].getJetonY()+cases[1][n].getJeton().getIconWidth()/2, cases[1][n]);
		}
		for(int n=2;n<6;n++){
			if(!cases[n][1].getRessource().getType().equals("desert")){
				cases[n][1].setNumero(valeur[indice++]);
			}
			if(n>2){
				cases[n][1].setCoordonneesJeton(margeGauche+(n/3+n%3)*widthCase-n*widthCase/2+widthCase/2-cases[n][1].getJeton().getIconWidth()/2, widthCase*3/4*n+margeTop+widthCase/2-cases[n][1].getJeton().getIconHeight()/2);
				voleur.ajouterEmplacement(cases[n][1].getJetonX()-cases[n][1].getJeton().getIconWidth()/2, cases[n][1].getJetonY()+cases[n][1].getJeton().getIconWidth()/2, cases[n][1]);
			}
			else{
				cases[n][1].setCoordonneesJeton(margeGauche+widthCase-n*widthCase/2+widthCase/2-cases[n][1].getJeton().getIconWidth()/2, widthCase*3/4*n+margeTop+widthCase/2-cases[n][1].getJeton().getIconHeight()/2);
				voleur.ajouterEmplacement(cases[n][1].getJetonX()-cases[n][1].getJeton().getIconWidth()/2, cases[n][1].getJetonY()+cases[n][1].getJeton().getIconWidth()/2, cases[n][1]);
			}

		}
		if(!cases[5][2].getRessource().getType().equals("desert")){
			cases[5][2].setNumero(valeur[indice++]);
		}
		cases[5][2].setCoordonneesJeton(margeGauche+5*widthCase/2-widthCase+widthCase/2-cases[5][2].getJeton().getIconWidth()/2, 3*widthCase+widthCase*3/4+margeTop-2+widthCase/2-cases[5][2].getJeton().getIconHeight()/2);
		voleur.ajouterEmplacement(cases[5][2].getJetonX()-cases[5][2].getJeton().getIconWidth()/2, cases[5][2].getJetonY()+cases[5][2].getJeton().getIconWidth()/2, cases[5][2]);

		for(int n=5;n>2;n--){
			if(!cases[n][3+5-n].getRessource().getType().equals("desert")){
				cases[n][3+5-n].setNumero(valeur[indice++]);
			}
			cases[n][3+5-n].setCoordonneesJeton( margeGauche+widthCase*(3+5-n)+(n-4)*widthCase/2-widthCase+widthCase/2-cases[n][3+5-n].getJeton().getIconWidth()/2, 3*widthCase+widthCase*3/4*(n-4)+margeTop-2+widthCase/2-cases[n][3+5-n].getJeton().getIconHeight()/2);
			voleur.ajouterEmplacement(cases[n][3+5-n].getJetonX()-cases[n][3+5-n].getJeton().getIconWidth()/2, cases[n][3+5-n].getJetonY()+cases[n][3+5-n].getJeton().getIconWidth()/2, cases[n][3+5-n]);
		}
		if(!cases[2][4].getRessource().getType().equals("desert")){
			cases[2][4].setNumero(valeur[indice++]);
		}
		cases[2][4].setCoordonneesJeton(margeGauche+widthCase*4-widthCase+widthCase/2-cases[2][4].getJeton().getIconWidth()/2, widthCase*3/4*2+margeTop+widthCase/2-cases[2][4].getJeton().getIconHeight()/2);
		voleur.ajouterEmplacement(cases[2][4].getJetonX()-cases[2][4].getJeton().getIconWidth()/2, cases[2][4].getJetonY()+cases[2][4].getJeton().getIconWidth()/2, cases[2][4]);
		if(start!=3 && !cases[1][3].getRessource().getType().equals("desert")){
			cases[1][3].setNumero(valeur[indice++]);
		}
		cases[1][3].setCoordonneesJeton(margeGauche+widthCase*3-widthCase/2+widthCase/2-cases[1][3].getJeton().getIconWidth()/2, widthCase*3/4+margeTop+widthCase/2-cases[1][3].getJeton().getIconHeight()/2);
		voleur.ajouterEmplacement(cases[1][3].getJetonX()-cases[1][3].getJeton().getIconWidth()/2, cases[1][3].getJetonY()+cases[1][3].getJeton().getIconWidth()/2, cases[1][3]);
		if(start==1 && !cases[1][2].getRessource().getType().equals("desert")){
			cases[1][2].setNumero(valeur[indice++]);
		}
		cases[1][2].setCoordonneesJeton(margeGauche+widthCase*2-cases[1][2].getJeton().getIconWidth()/2, widthCase*3/4+margeTop+widthCase/2-cases[1][2].getJeton().getIconHeight()/2);
		voleur.ajouterEmplacement(cases[1][2].getJetonX()-cases[1][2].getJeton().getIconWidth()/2, cases[1][2].getJetonY()+cases[1][2].getJeton().getIconWidth()/2, cases[1][2]);
		for(int n=2;n<4;n++){
			if(!cases[2][n].getRessource().getType().equals("desert")){
				cases[2][n].setNumero(valeur[indice++]);
			}
			cases[2][n].setCoordonneesJeton(margeGauche+widthCase*n-2*widthCase/2+widthCase/2-cases[2][n].getJeton().getIconWidth()/2, widthCase*3/4*2+margeTop+widthCase/2-cases[2][n].getJeton().getIconHeight()/2);
			voleur.ajouterEmplacement(cases[2][n].getJetonX()-cases[2][n].getJeton().getIconWidth()/2, cases[2][n].getJetonY()+cases[2][n].getJeton().getIconWidth()/2, cases[2][n]);
		}
		if(!cases[3][2].getRessource().getType().equals("desert")){
			cases[3][2].setNumero(valeur[indice++]);
		}
		cases[3][2].setCoordonneesJeton(margeGauche+widthCase*2-3*widthCase/2+widthCase/2-cases[3][2].getJeton().getIconWidth()/2, widthCase*3/4*3+margeTop+widthCase/2-cases[3][2].getJeton().getIconHeight()/2);
		voleur.ajouterEmplacement(cases[3][2].getJetonX()-cases[3][2].getJeton().getIconWidth()/2, cases[3][2].getJetonY()+cases[3][2].getJeton().getIconWidth()/2, cases[3][2]);
		for(int n=2;n<4;n++){
			if(!cases[4][n].getRessource().getType().equals("desert")){
				cases[4][n].setNumero(valeur[indice++]);
			}
			cases[4][n].setCoordonneesJeton(margeGauche+widthCase*n-widthCase+widthCase/2-cases[4][n].getJeton().getIconWidth()/2, 3*widthCase+margeTop-2+widthCase/2-cases[4][n].getJeton().getIconHeight()/2);
			voleur.ajouterEmplacement(cases[4][n].getJetonX()-cases[4][n].getJeton().getIconWidth()/2, cases[4][n].getJetonY()+cases[4][n].getJeton().getIconWidth()/2, cases[4][n]);
		}
		if(!cases[3][4].getRessource().getType().equals("desert")){
			cases[3][4].setNumero(valeur[indice++]);
		}
		cases[3][4].setCoordonneesJeton(margeGauche+widthCase*4-widthCase-cases[3][4].getJeton().getIconWidth()/2, widthCase*3/4*3+margeTop+widthCase/2-cases[3][4].getJeton().getIconHeight()/2);
		voleur.ajouterEmplacement(cases[3][4].getJetonX()-cases[3][4].getJeton().getIconWidth()/2, cases[3][4].getJetonY()+cases[3][4].getJeton().getIconWidth()/2, cases[3][4]);

		if(!cases[3][3].getRessource().getType().equals("desert")){
			cases[3][3].setNumero(valeur[indice++]);
		}
		cases[3][3].setCoordonneesJeton(margeGauche+widthCase*4-2*widthCase-cases[3][3].getJeton().getIconWidth()/2, widthCase*3/4*3+margeTop+widthCase/2-cases[3][3].getJeton().getIconHeight()/2);
		voleur.ajouterEmplacement(cases[3][3].getJetonX()-cases[3][3].getJeton().getIconWidth()/2, cases[3][3].getJetonY()+cases[3][3].getJeton().getIconWidth()/2, cases[3][3]);
		
		//Initialisation de la position du voleur
		for(Emplacement p : Voleur.emplacements){
			if(p.getCase().getRessource().getType().equals("desert"))
				voleur.setPosition(p);
		}
	
	}
	
	public void setPositionPieces(){
		int width=PartiePanel.widthCase;
		int margeGauche=PartiePanel.margeGauche;
		int margeTop=PartiePanel.margeTop;
		Village[] n=new Village[6];
		Village[] n2=new Village[6];
		Village[] nFin=new Village[6];
		Village[] n2Fin=new Village[6];
		Route a;
		for(int i=0;i<3;i++){
    		n[i]=new Village(margeGauche+(1+i)*width, margeTop+3*width/4);
    		n[i].ajouterCase(cases[1][1+i]);
    		pieces.add(n[i]);
    		nFin[i]=new Village(margeGauche+(1+i)*width, margeTop+19*width/4);
    		nFin[i].ajouterCase(cases[5][1+i]);
    		pieces.add(nFin[i]);
    	}
		for(int j=0;j<5;j++){
			for(int i=0;i<4+j/2;i++){
				if(j%2==0){
		    		n2[i]=new Village(margeGauche+width/4*(2+4*i-j), margeTop+width/4*(4+3*j/2));
		    		if(i<3+j/2)
		    			n2[i].ajouterCase(cases[1+j/2][1+i]);
		    		if(i>0)
		        		n2[i].ajouterCase(cases[1+j/2][i]);
		    		if(j>1 && i>0 && i<3+j/2)
		    			n2[i].ajouterCase(cases[j/2][i]);
		    		n2Fin[i]=new Village(margeGauche+width/4*(2+4*i-j), (int)(margeTop+width/8.0*(36-3*j)));
		    		if(i<3+j/2)
		    			n2Fin[i].ajouterCase(cases[5-j/2][1+i]);
		    		if(i>0)
		    			n2Fin[i].ajouterCase(cases[5-j/2][i]);
		    		if(j>1 && i>0 && i<3+j/2)
		    			n2Fin[i].ajouterCase(cases[6-j/2][i]);
		    		pieces.add(n2[i]);
		    		pieces.add(n2Fin[i]);
		    		if(i<4+j/2-1){
		        		a=new Route(margeGauche+width/4*(3+4*i-j), (int)(margeTop+width/8.0*(7+3*j)), "ouest");
		        		pieces.add(a);
		    			n[i].ajouterVillageAdj(a, n2[i]);
		        		a=new Route(margeGauche+width/4*(3+4*i-j), (int)(margeTop+width/8.0*(37-3*j)), "est");
		        		pieces.add(a);
		    			nFin[i].ajouterVillageAdj(a, n2Fin[i]);
		    		}
		    		if(i>0){
		        		a=new Route(margeGauche+width/4*(1+4*i-j), (int)(margeTop+width/8.0*(7+3*j)), "est");
		        		pieces.add(a);
		    			n[i-1].ajouterVillageAdj(a, n2[i]);
		        		a=new Route(margeGauche+width/4*(1+4*i-j), (int)(margeTop+width/8.0*(37-3*j)), "ouest");
		        		pieces.add(a);
		    			nFin[i-1].ajouterVillageAdj(a, n2Fin[i]);
		    		}
				}
				else{
		    		n[i]=new Village(margeGauche+width/2*(1+2*i-j/2), margeTop+width/4*(6+3*(j/2)));
		    		if(i<3+j/2)
		    			n[i].ajouterCase(cases[1+j/2][1+i]);
		    		n[i].ajouterCase(cases[2+j/2][1+i]);
		    		if(i>0)
		        		n[i].ajouterCase(cases[1+j/2][i]);
		    		nFin[i]=new Village(margeGauche+width/2*(1+2*i-j/2), margeTop+width/4*(16-3*(j/2)));
		    		if(i<3+j/2)
		    			nFin[i].ajouterCase(cases[5-j/2][1+i]);
		    		nFin[i].ajouterCase(cases[4-j/2][1+i]);
		    		if(i>0)
		    			nFin[i].ajouterCase(cases[5-j/2][i]);
		    		pieces.add(n[i]);
		    		pieces.add(nFin[i]);
		    		a=new Route(margeGauche+width/2*(1+2*i-j/2), margeTop+width/4*(5+3*(j/2)), "nord");
		    		pieces.add(a);
		    		n2[i].ajouterVillageAdj(a, n[i]);
		    		a=new Route(margeGauche+width/2*(1+2*i-j/2), margeTop+width/4*(17-3*(j/2)), "nord");
		    		pieces.add(a);
		    		n2Fin[i].ajouterVillageAdj(a, nFin[i]);
				}
			}
		}
		for(int i=0;i<6;i++){
    		a=new Route(margeGauche+width/2*(-1+2*i), margeTop+11*width/4, "nord");
			pieces.add(a);
    		n2[i].ajouterVillageAdj(a, n2Fin[i]);
		}
	}

	public void ajouterPiece(Piece p){
		piecesPoser.add(p);
		notifyObserver(p);
	}
	
	public ArrayList<Piece> getPieces(){
		return pieces;
	}
	
	public ArrayList<Piece> getPiecesPoser(){
		return piecesPoser;
	}
	
	public int getNombreVillage(){
		int i=0;
		for(Piece p : pieces)
			if(p instanceof Village && p.getPoser())
				i++;
		return i;
	}
	
	public Voleur getVoleur(){
		return voleur;
	}
	
	public Case[][] getCases(){
		return cases;
	}
	
	public ImageIcon getImageOfCase(int i, int j){
		return cases[i][j].getImage();
	}
}
