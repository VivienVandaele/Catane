package controller;


import model.Plateau;
import vue.Fenetre;

public class Controller {
	private Plateau p;
	
	public Controller(Fenetre f){
		p=new Plateau();
		p.addObserver(f);
	}
	
	public boolean acheterVille(){
		return true;
	}
	
	public boolean poserPiece(int x, int y){
		return true;
	}
	
	public Plateau getPlateau(){
		return p;
	}
}
