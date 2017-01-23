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
	
    private ImageIcon imageCarte;
    private ImageIcon imageCase;
	private String type;
    
	private Ressource(String type){
		this.type=type;
		imageCase=new ImageIcon("images/cases/"+type+".png");
		if(!type.equals("desert") && !type.equals("mer"))
			imageCarte=new ImageIcon("images/cartes/"+type+".png");
	}
	
	public ImageIcon getImageCase(){
		return imageCase;
	}
	
	public ImageIcon getImageCarte(){
		return imageCarte;
	}
	
	public String getType(){
		return type;
	}
}
