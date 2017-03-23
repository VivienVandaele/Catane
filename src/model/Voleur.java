package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import observer.Observable;

public class Voleur extends Observable implements Serializable{
	public static ArrayList<Emplacement> emplacements;
	public static Emplacement position;
	
	public Voleur(){
		emplacements = new ArrayList<Emplacement>();
	}
	
	public boolean poserVoleur(int x, int y){
		int marge=20;
		for(Emplacement e : emplacements)
			if(e!=position && e.getX()-marge<x && e.getX()+marge>x && e.getY()-marge<y && e.getY()+marge>y){
				setPosition(e);
				return true;	
			}
		return false;
	}
	
	public void ajouterEmplacement(int x, int y, Case c){
		emplacements.add(new Emplacement(x, y, c));
	}
	
	public void setPosition(Emplacement e){
		position = e;
		notifyObserver(e);
	}
	
	public void setEmplacement(ArrayList<Emplacement> emplacements){
		this.emplacements = emplacements;
	}
	
	public int getX(){
		return position.getX();
	}

	public int getY(){
		return position.getY();
	}
	
	public Emplacement getPosition(){
		return position;
	}
	
	public Case getCase(){
		return position.getCase();
	}
}
