package model;
import javax.swing.ImageIcon;

public enum Ressource {
	bois("bois"),
	mouton("mouton"),
	argile("argile"),
	pierre("pierre"),
	ble("ble"),
	mer("mer"),
	desert("desert");
	
    private ImageIcon image;
	private String type;
    
	private Ressource(String type){
		this.type=type;
		image=new ImageIcon("images/cases/"+type+".png");
	}
	
	public ImageIcon getImage(){
		return image;
	}
	
	public String getType(){
		return type;
	}
}
