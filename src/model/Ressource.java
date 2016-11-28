package model;
import javax.swing.ImageIcon;

public enum Ressource {
	bois("bois.png"),
	mouton("mouton.png"),
	argile("argile.png"),
	pierre("pierre.png"),
	ble("ble.png"),
	mer("mer.png"),
	desert("desert.png");
	
    private ImageIcon image;
	
	private Ressource(String nomImage){
		image=new ImageIcon("images/cases/"+nomImage);
	}
	
	public ImageIcon getImage(){
		return image;
	}
}
