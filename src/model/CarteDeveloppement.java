package model;

import javax.swing.ImageIcon;

public class CarteDeveloppement {
	private String type;
	private ImageIcon image;
	
	public CarteDeveloppement(String type){
		image = new ImageIcon("images/cartes/"+type+"Dev.png");
	}
	
	public ImageIcon getImage(){
		return image;
	}
}
