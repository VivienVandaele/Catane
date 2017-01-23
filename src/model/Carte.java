package model;

import javax.swing.ImageIcon;

public class Carte {
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
}
