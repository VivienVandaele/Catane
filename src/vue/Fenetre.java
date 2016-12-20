package vue;
import java.awt.Color;

import javax.swing.JFrame;

import controller.Controller;
import model.Ville;
import observer.Observable;
import observer.Observer;
import state.NormalState;
import state.PieceState;
import state.State;


public class Fenetre extends JFrame implements Observer{
	private State state;
	private Controller c;
	
	public Fenetre() {
		setTitle("Les colons de Catane");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(this.MAXIMIZED_BOTH);
		setUndecorated(true);
		state=new NormalState(this);
		setBackground(Color.decode("#f4eaaf"));
		setVisible(true);
		afficherAccueil();
	}
	
	public void afficherAccueil(){
		setContentPane(new AccueilPanel(this));
		revalidate();
	}
	
	public void afficherPartie(){
		c=new Controller(this);
		setContentPane(new PartiePanel(this));
		revalidate();
		setState(new PieceState(this));
	}
	
	public void setState(State s){
		removeMouseListener(state);
		state=s;
		addMouseListener(s);
	}
	
	public Controller getController(){
		return c;
	}

	public void update(Observable o){
		System.out.println("update");
		repaint();
	}
}
