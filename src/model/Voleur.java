package model;

import java.awt.Point;
import java.util.ArrayList;

public class Voleur {
	private class Emplacement {
		private int x;
		private int y;
		private Case c;
		
		public Emplacement(int x, int y, Case c){
			this.x = x;
			this.y = y;
			this.c = c;
		}
		
		public int getX(){
			return x;
		}
		
		public int getY(){
			return y;
		}
		
		public Case getCase(){
			return c;
		}
	}
	
	public static ArrayList<Emplacement> emplacements;
	public Emplacement position;
	
	public Voleur(){
		emplacements = new ArrayList<Emplacement>();
	}
	
	public void ajouterEmplacement(int x, int y, Case c){
		emplacements.add(new Emplacement(x, y, c));
	}
	
	public void setPosition(Emplacement e){
		position = e;
	}
	
	public int getX(){
		return position.getX();
	}

	public int getY(){
		return position.getY();
	}
	
	public Case getCase(){
		return position.getCase();
	}
}
