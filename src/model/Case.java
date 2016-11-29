package model;
import javax.swing.ImageIcon;

public class Case {
	private Ressource ressource;
	private int numero;
	private ImageIcon jeton;
	
	public Case(Ressource ressource, int numero){
		this.ressource=ressource;
		this.numero=numero;
		jeton=new ImageIcon("images/jetons/jeton"+numero+".png");
	}
	
	public ImageIcon getImage(){
		return ressource.getImage();
	}
	
	public ImageIcon getJeton(){
		return jeton;
	}
	
	public int getNumero(){
		return numero;
	}
	
	public void setNumero(int numero){
		this.numero=numero;
		jeton=new ImageIcon("images/jetons/jeton"+numero+".png");
	}
	
	public Ressource getRessource(){
		return ressource;
	}
}
