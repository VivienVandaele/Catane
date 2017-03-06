package model;

import java.awt.Image;

import javax.swing.ImageIcon;

import vue.PartiePanel;

public class Port {
	private ImageIcon image;
	private String type;
	private Emplacement emplacement;
	
	public Port(String type){
		image=new ImageIcon(new ImageIcon("images/ports/port"+type+".png").getImage().getScaledInstance(PartiePanel.widthCarte/4, PartiePanel.widthCarte/4, Image.SCALE_DEFAULT));
		this.type = type;
	}
	
	public void setEmplacement(Emplacement e){
		this.emplacement = e;
	}
	
	public Emplacement getEmplacement(){
		return emplacement;
	}
	
	public String getType(){
		return type;
	}
	
	public ImageIcon getImage(){
		return image;
	}
}
