package model;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class CarteDeveloppement implements Serializable {
	private String type;
	private ImageIcon image;
	
	public CarteDeveloppement(String type){
		image = new ImageIcon("images/cartes/"+type+"Dev.png");
		this.type = type;
	}

	public String getType(){
		return type;
	}
	
	public ImageIcon getImage(){
		return image;
	}
}
