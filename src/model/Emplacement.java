package model;

import java.io.Serializable;

public class Emplacement implements Serializable{
	private int x;
	private int y;
	private Case c;
	
	public Emplacement(int x, int y){
		this.x = x;
		this.y = y;
	}
	
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