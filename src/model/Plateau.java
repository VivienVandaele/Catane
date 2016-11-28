package model;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;

import observer.Observable;
import vue.Fenetre;

public class Plateau extends Observable{
	private Case[][] cases;
	private ArrayList<Piece> pieces;
	
	
	public Plateau(){
		cases = new Case[7][7];
		pieces = new ArrayList<Piece>();
		
		Case bois=new Case(Ressource.bois);
		Case mer=new Case(Ressource.mer);
		Case pierre=new Case(Ressource.pierre);
		Case argile=new Case(Ressource.argile);
		Case ble=new Case(Ressource.ble);
		Case mouton=new Case(Ressource.mouton);
		Case desert=new Case(Ressource.desert);

		ArrayList<Case> listCases = new ArrayList<Case>();
		listCases.add(desert);
		for(int i=0;i<4;i++){
			listCases.add(bois);
			listCases.add(ble);
			listCases.add(mouton);
			if(i<3){
				listCases.add(argile);
				listCases.add(pierre);
			}
			for(int k=0;k<4+i;k++)
				cases[i][k]=mer;
		}
		
    	
		for(int k=0;k<3;k++)
			for(int i=0;i<4+(2-k);i++)
				if(k==2 || i==0 || i==5-k) cases[k+4][i]=mer;
				else cases[k+4][i]=listCases.remove(ThreadLocalRandom.current().nextInt(0, listCases.size()));

		for(int i=0;i<3;i++)
			for(int j=1;j<4+i;j++)
				cases[i+1][j]=listCases.remove(ThreadLocalRandom.current().nextInt(0, listCases.size()));

	}
	
	public void ajouterPiece(Piece p){
		pieces.add(p);
		notifyObserver();
	}
	
	public ArrayList<Piece> getListVille(){
		return pieces;
	}
	
	public ImageIcon getImageOfCase(int i, int j){
		return cases[i][j].getImage();
	}
}
