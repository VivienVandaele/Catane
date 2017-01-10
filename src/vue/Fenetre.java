package vue;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import model.Ville;
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
		afficherPartie();
		setBackground(Color.decode("#f4eaaf"));
		setVisible(true);
	}
	
	public void afficherAccueil(){
		setContentPane(new AccueilPanel(this));
		revalidate();
	}
	 
	public void afficherPartie(){
		c=new Controller(this);
		setContentPane(new PartiePanel(this));
		revalidate();
		//c.lancerPartie();
		/*
        JLabel lab=new JLabel("test");
		PrintStream printStream = new PrintStream(new CustomOutputStream(lab, this));
		System.setOut(printStream);
		*/
	}
	
	public void setState(State s){
		removeMouseListener(state);
		state=s;
		addMouseListener(s);
	}
	
	public Controller getController(){
		return c;
	}

	public void update(Object o){
		if(o instanceof Ville)
			System.out.println("\bville");
		repaint();
	}
	
	public void update(Observable o){
		System.out.println("update");
		repaint();
	}
}
