package model;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Carte implements Serializable{
	private Ressource ressource;
	
	public Carte(Ressource ressource){
		this.ressource = ressource;
	}
	
	public String getType(){
		return ressource.getType();
	}
	
	public ImageIcon getImage(){
		return ressource.getImageCarte();
	}
	
	public Ressource getRessource(){
		return ressource;
	}
}
