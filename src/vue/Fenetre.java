package vue;
import javax.swing.JFrame;

import controller.Controller;
import model.Carte;
import model.Joueur;
import model.Piece;
import model.Ressource;
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
		afficherPartie();
		setVisible(true);
	}
	
	public void afficherAccueil(){
		setContentPane(new AccueilPanel(this));
		revalidate();
	}
	 
	public void afficherPartie(){
		c=new Controller(this);
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
		if(arg instanceof Carte && ((Joueur) o).getId()==0)
			pan.carteAnimation(arg, true);
		if(arg instanceof Ressource && ((Joueur) o).getId()==0)
			pan.carteAnimation(new Carte((Ressource) arg), false);
		if(arg instanceof Piece)
			pan.lancerPieceAnimation(((Piece) arg));
			
	}
}
