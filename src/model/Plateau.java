package model;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;

import observer.Observable;
import vue.PartiePanel;

public class Plateau extends Observable{
	private Case[][] cases;
	public ArrayList<Piece> pieces;
	public ArrayList<Piece> piecesPoser;
	
	public Plateau(){
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
		for(int n=start;n>0;n--){
			if(!cases[1][n].getRessource().getType().equals("desert")){
				cases[1][n].setNumero(valeur[indice++]);
			}
		}
		for(int n=2;n<6;n++){
			if(!cases[n][1].getRessource().getType().equals("desert")){
				cases[n][1].setNumero(valeur[indice++]);
			}
		}
		if(!cases[5][2].getRessource().getType().equals("desert")){
			cases[5][2].setNumero(valeur[indice++]);
		}
		for(int n=5;n>2;n--){
			if(!cases[n][3+5-n].getRessource().getType().equals("desert")){
				cases[n][3+5-n].setNumero(valeur[indice++]);
			}
		}
		if(!cases[2][4].getRessource().getType().equals("desert")){
			cases[2][4].setNumero(valeur[indice++]);
		}
		if(start!=3 && !cases[1][3].getRessource().getType().equals("desert")){
			cases[1][3].setNumero(valeur[indice++]);
		}
		if(start==1 && !cases[1][2].getRessource().getType().equals("desert")){
			cases[1][2].setNumero(valeur[indice++]);
		}
		for(int n=2;n<4;n++){
			if(!cases[2][n].getRessource().getType().equals("desert")){
				cases[2][n].setNumero(valeur[indice++]);
			}
		}
		if(!cases[3][2].getRessource().getType().equals("desert")){
			cases[3][2].setNumero(valeur[indice++]);
		}
		for(int n=2;n<4;n++){
			if(!cases[4][n].getRessource().getType().equals("desert")){
				cases[4][n].setNumero(valeur[indice++]);
			}
		}
		if(!cases[3][4].getRessource().getType().equals("desert")){
			cases[3][4].setNumero(valeur[indice++]);
		}
		if(!cases[3][3].getRessource().getType().equals("desert")){
			cases[3][3].setNumero(valeur[indice++]);
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
	
	public Case[][] getCases(){
		return cases;
	}
	
	public ImageIcon getImageOfCase(int i, int j){
		return cases[i][j].getImage();
	}
}
