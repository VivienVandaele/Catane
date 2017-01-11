package model;
import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;

import observer.Observable;
import vue.PartiePanel;

public class Plateau extends Observable{
	private Case[][] cases;
	
	public Plateau(){
		cases = new Case[7][7];

		//Création aléatoire des cases
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

		//Création des numéros
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
	
	public Case[][] getCases(){
		return cases;
	}
	
	public ImageIcon getImageOfCase(int i, int j){
		return cases[i][j].getImage();
	}
}
