package vue;
import java.util.ArrayList;

import javax.swing.JFrame;

import controller.Controller;
import model.Carte;
import model.CarteDeveloppement;
import model.Joueur;
import model.Piece;
import model.Ressource;
import model.Route;
import observer.Observable;
import observer.Observer;
import state.NormalState;
import state.State;


public class Fenetre extends JFrame implements Observer{
	private State state;
	private Controller c;
	private PartiePanel pan;
	
	public Fenetre() {
		setTitle("Les colons de Catane");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(this.MAXIMIZED_BOTH);
		setUndecorated(true);
		setState(new NormalState(this));
		afficherAccueil();
		setVisible(true);
	}
	
	public void afficherAccueil(){
		setContentPane(new AccueilPanel(this));
		revalidate();
	}
	 
	public void afficherPartieHotSeat(String[] name){
		c=new Controller(this, true, name);
		pan = new PartiePanel(this);
		setContentPane(pan);
		revalidate();
		c.debutPartie();
	}
	
	public void afficherCreationHotSeat(){
		setContentPane(new CreationHotSeatPanel(this));
		revalidate();
		repaint();
	}
	
	public void afficherCreationIA(){
		setContentPane(new CreationIAPanel(this));
		revalidate();
		repaint();
	}
	
	public void afficherPartie(String[] name, int nbIa){
		c=new Controller(this, name, nbIa);
		pan = new PartiePanel(this);
		setContentPane(pan);
		revalidate();
		c.debutPartie();
	}
	
	public void setState(State s){
		removeMouseListener(state);
		state=s;
		addMouseListener(s);
		repaint();
	}
	
	public Controller getController(){
		return c;
	}
	
	public PartiePanel getPartiePanel(){
		return pan;
	}
	
	public void update(Observable o, Object arg){
		repaint();
		if(arg instanceof Carte && ((Joueur) o).getId()==c.idJoueurHumain)
			pan.carteAnimation(arg, true);
		if(arg instanceof Ressource && ((Joueur) o).getId()==c.idJoueurHumain)
			pan.carteAnimation(new Carte((Ressource) arg), false);
		if(arg instanceof Piece)
			pan.lancerPieceAnimation(((Piece) arg));
		if(arg instanceof CarteDeveloppement)
			pan.setCarteDev();
		if(arg instanceof Route){
			c.setRoutePlusLongue();
		}
		pan.updateStats();
	}
}
