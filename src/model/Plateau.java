package model;
import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;

import observer.Observable;
import vue.PartiePanel;

public class Plateau extends Observable{
	private Case[][] cases;
	public static ArrayList<Position> positions;
	
	public Plateau(){
		cases = new Case[7][7];
		positions=new ArrayList<Position>();
		setPosition();

		ArrayList<Case> listCases = new ArrayList<Case>();
		listCases.add(new Case(Ressource.desert, 0));
		for(int i=0;i<4;i++){
			listCases.add(new Case(Ressource.bois, 2));
			listCases.add(new Case(Ressource.ble, 2));
			listCases.add(new Case(Ressource.mouton, 2));
			if(i<3){
				listCases.add(new Case(Ressource.argile, 2));
				listCases.add(new Case(Ressource.pierre, 2));
			}
			for(int k=0;k<4+i;k++)
				cases[i][k]=new Case(Ressource.mer, 0);
		}
		
    	
		for(int k=0;k<3;k++)
			for(int i=0;i<4+(2-k);i++)
				if(k==2 || i==0 || i==5-k) cases[k+4][i]=new Case(Ressource.mer, 0);
				else cases[k+4][i]=listCases.remove(ThreadLocalRandom.current().nextInt(0, listCases.size()));

		for(int i=0;i<3;i++)
			for(int j=1;j<4+i;j++)
				cases[i+1][j]=listCases.remove(ThreadLocalRandom.current().nextInt(0, listCases.size()));

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
	
	public ArrayList<Position> getPositions(){
		return positions;
	}
	
	public void setPosition(){
		int width=PartiePanel.widthCase;
		int margeGauche=PartiePanel.margeGauche;
		int margeTop=PartiePanel.margeTop;
		Noeud[] n=new Noeud[6];
		Noeud[] n2=new Noeud[6];
		Noeud[] nFin=new Noeud[6];
		Noeud[] n2Fin=new Noeud[6];
		Arete a;
		for(int i=0;i<3;i++){
    		n[i]=new Noeud(margeGauche+(1+i)*width, margeTop+3*width/4);
    		positions.add(n[i]);
    		nFin[i]=new Noeud(margeGauche+(1+i)*width, margeTop+19*width/4);
    		positions.add(nFin[i]);
    	}
		for(int j=0;j<5;j++){
			for(int i=0;i<4+j/2;i++){
				if(j%2==0){
		    		n2[i]=new Noeud(margeGauche+width/4*(2+4*i-j), margeTop+width/4*(4+3*j/2));
		    		n2Fin[i]=new Noeud(margeGauche+width/4*(2+4*i-j), (int)(margeTop+width/8.0*(36-3*j)));
		    		positions.add(n2[i]);
		    		positions.add(n2Fin[i]);
		    		if(i<4+j/2-1){
		        		a=new Arete(margeGauche+width/4*(3+4*i-j), (int)(margeTop+width/8.0*(7+3*j)));
		        		positions.add(a);
		    			n[i].ajouterNoeud(a, n2[i]);
		        		a=new Arete(margeGauche+width/4*(3+4*i-j), (int)(margeTop+width/8.0*(37-3*j)));
		        		positions.add(a);
		    			nFin[i].ajouterNoeud(a, n2Fin[i]);
		    		}
		    		if(i>0){
		        		a=new Arete(margeGauche+width/4*(1+4*i-j), (int)(margeTop+width/8.0*(7+3*j)));
		        		positions.add(a);
		    			n[i-1].ajouterNoeud(a, n2[i]);
		        		a=new Arete(margeGauche+width/4*(1+4*i-j), (int)(margeTop+width/8.0*(37-3*j)));
		        		positions.add(a);
		    			nFin[i-1].ajouterNoeud(a, n2Fin[i]);
		    		}
				}
				else{
		    		n[i]=new Noeud(margeGauche+width/2*(1+2*i-j/2), margeTop+width/4*(6+3*(j/2)));
		    		nFin[i]=new Noeud(margeGauche+width/2*(1+2*i-j/2), margeTop+width/4*(16-3*(j/2)));
		    		positions.add(n[i]);
		    		positions.add(nFin[i]);
		    		a=new Arete(margeGauche+width/2*(1+2*i-j/2), margeTop+width/4*(5+3*(j/2)));
		    		positions.add(a);
		    		n2[i].ajouterNoeud(a, n[i]);
		    		a=new Arete(margeGauche+width/2*(1+2*i-j/2), margeTop+width/4*(17-3*(j/2)));
		    		positions.add(a);
		    		n2Fin[i].ajouterNoeud(a, nFin[i]);
				}
			}
		}
		for(int i=0;i<6;i++){
    		a=new Arete(margeGauche+width/2*(-1+2*i), margeTop+11*width/4);
			positions.add(a);
    		n2[i].ajouterNoeud(a, n2Fin[i]);
		}
	}
	
	public int nombreVillage(){
		int i=0;
		for(Position p : positions){
			if(p.getPiece()!=null && p.getPiece() instanceof Ville)
				i++;
		}
		return i;
	}
	
	public Case[][] getCases(){
		return cases;
	}
	
	public ImageIcon getImageOfCase(int i, int j){
		return cases[i][j].getImage();
	}
}
