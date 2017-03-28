package vue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFrame;

import controller.Controller;
import model.Carte;
import model.CarteDeveloppement;
import model.CartePointsVictoire;
import model.Joueur;
import model.Piece;
import model.Ressource;
import model.Route;
import observer.Observable;
import observer.Observer;
import state.NormalState;
import state.State;
import vue.sauvegardePartie.DialogTest;


public class Fenetre extends JFrame implements Observer, KeyListener, Serializable {
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
		addKeyListener(this);
		setVisible(true);
	}
	
	public void afficherAccueil(){
		c = null;
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
			CartePointsVictoire routeLongue = c.getCarteRoute();
			for(int i=0;i<4;i++){
				if(c.getJoueurs()[i].getRouteLongue() > routeLongue.getMax()){
					routeLongue.setJoueur(c.getJoueurs()[i], c.getJoueurs()[i].getRouteLongue());
				}
			}
		}
		pan.updateStats();
		c.chercherGagnant();
	}

	@Override
	public void keyPressed(KeyEvent evt) {
        switch(evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE :
                new DialogTest(this);
                break;
        }
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void setController(Controller c){
		this.c = c;
	}
	
	public void setStateSauv(State s){
		this.state = s;
	}
	
	public void setPan(PartiePanel p){
		this.pan = p;
		setContentPane(p);
	}
}
