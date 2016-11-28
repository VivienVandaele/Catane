package model;
import javax.swing.ImageIcon;

public class Case {
	private Ressource ressource;

	public Case(Ressource ressource){
		this.ressource=ressource;
	}
	
	public ImageIcon getImage(){
		return ressource.getImage();
	}
}
