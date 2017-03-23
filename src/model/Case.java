package model;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

import vue.PartiePanel;

public class Case implements Serializable{
	private Ressource ressource;
	private int numero;
	private ImageIcon jeton;
	private int jetonX;
	private int jetonY;
	
	public Case(Ressource ressource){
		this.ressource=ressource;
		this.numero=0;
		jeton=new ImageIcon("images/jetons/jeton"+numero+".png");
		jetonX = 0;
		jetonY = 0;
	}
	
	public ImageIcon getImage(){
		return ressource.getImageCase();
	}
	
	public void setCoordonneesJeton(int x, int y){
		jetonX = x;
		jetonY = y;
	}
	
	public int getJetonX(){
		return jetonX;
	}

	public int getJetonY(){
		return jetonY;
	}
	
	public ImageIcon getJeton(){
		return jeton;
	}
	
	public int getNumero(){
		return numero;
	}
	
	public int getProbabiliteNumero(){
		if(numero == 2 || numero == 12) return 1;
		if(numero == 3 || numero == 11) return 2;
		if(numero == 4 || numero == 10) return 3;
		if(numero == 5 || numero == 9) return 4;
		return 5;
		
	}
	
	public void setNumero(int numero){
		this.numero=numero;
		jeton=new ImageIcon(new ImageIcon("images/jetons/jeton"+numero+".png").getImage().getScaledInstance(PartiePanel.widthCarte/3, PartiePanel.widthCarte/3, Image.SCALE_DEFAULT));
	}
	
	public Ressource getRessource(){
		return ressource;
	}
}
