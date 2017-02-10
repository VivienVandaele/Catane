package model;
import java.awt.Image;

import javax.swing.ImageIcon;

import vue.PartiePanel;

public class Case {
	private Ressource ressource;
	private int numero;
	private ImageIcon jeton;
	
	public Case(Ressource ressource){
		this.ressource=ressource;
		this.numero=0;
		jeton=new ImageIcon("images/jetons/jeton"+numero+".png");
	}
	
	public ImageIcon getImage(){
		return ressource.getImageCase();
	}
	
	public ImageIcon getJeton(){
		return jeton;
	}
	
	public int getNumero(){
		return numero;
	}
	
	public void setNumero(int numero){
		this.numero=numero;
		jeton=new ImageIcon(new ImageIcon("images/jetons/jeton"+numero+".png").getImage().getScaledInstance(PartiePanel.widthCarte/3, PartiePanel.widthCarte/3, Image.SCALE_DEFAULT));
	}
	
	public Ressource getRessource(){
		return ressource;
	}
}
