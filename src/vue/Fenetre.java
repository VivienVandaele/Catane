package vue;
import java.awt.Color;

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
		setState(new NormalState(this));
		afficherPartie();
		setBackground(Color.decode("#f4eaaf"));
		setVisible(true);
	}
	 
	public void afficherPartie(){
		c=new Controller(this);
		setContentPane(new PartiePanel(this));
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
	
	public void update(Observable o, Object arg){
		System.out.println("update");
		repaint();
	}
}
