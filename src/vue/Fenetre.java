package vue;
import javax.swing.JFrame;

import controller.Controller;
import observer.Observable;
import observer.Observer;
import state.NormalState;
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
	}
	
	public void afficherRegle(){
		
	}
	
	public void afficherCreationPartie(){
		
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
