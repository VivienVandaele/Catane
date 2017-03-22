package model;

public class CartePointsVictoire {
	private Joueur j;
	private int number;
	
	public CartePointsVictoire(int n){
		number = n;
	}
	
	public void setJoueur(Joueur j, int n){
		if(this.j != null){
			this.j.ajouterPoint(-2);
		}
		j.ajouterPoint(2);
		this.j = j;
		this.number = n;
	}
	
	public int getNumber(){
		return number;
	}
	
	public Joueur getJoueur(){
		return j;
	}
}
