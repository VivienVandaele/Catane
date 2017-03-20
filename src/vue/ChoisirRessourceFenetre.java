package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.Controller;
import model.Carte;
import model.Joueur;
import model.Ressource;

public class ChoisirRessourceFenetre extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JButton[] choisirButton = new JButton[5];
	private Controller c;
	private Joueur j;

	public ChoisirRessourceFenetre(Joueur j, Controller c, boolean monopole) {
		setBounds(100, 100, 1000, 700);
		setModal(true);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		this.c = c;
		this.j = j;
		
		Carte[] cartes = PartiePanel.getCartes();
		for(int i=0;i<6;i++){
			if(i<5){
				JLabel labImage = new JLabel();
				labImage.setBounds(10+i*200, 10, PartiePanel.widthCarte, PartiePanel.heightCarte);
		    	labImage.setIcon(new ImageIcon(cartes[i].getImage().getImage().getScaledInstance(PartiePanel.widthCarte, PartiePanel.heightCarte, Image.SCALE_DEFAULT)));
		    	contentPanel.add(labImage);
			}
			else{
				JLabel labImage = new JLabel();
				contentPanel.add(labImage);
			}
		}
		
		for(int i=0;i<5;i++){
			choisirButton[i] = new JButton("Choisir");
			
			choisirButton[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					Ressource r = null;
					for(int i=0;i<5;i++){
						if(choisirButton[i] == e.getSource()){
							if(i==0) r = Ressource.argile;
							if(i==1) r = Ressource.bois;
							if(i==2) r = Ressource.ble;
							if(i==3) r = Ressource.mouton;
							if(i==4) r = Ressource.pierre;				
						}
					}
					if(monopole){
						Joueur[] joueurs = c.getJoueurs();
						for(int i=0;i<4;i++){
							if(c.getIdJoueur() != joueurs[i].getId()){
								int number = joueurs[i].getNombreDeCarteType(r.getType());
								for(int k=0;k<number;k++){
									joueurs[i].retirerCarte(r);
									j.ajouterCarte(r);
								}
							}
						}
					}
					else{
						j.ajouterCarte(r);
						j.ajouterCarte(r);
					}
					dispose();
				}
			});
			choisirButton[i].setBounds(50+i*200, 300, 100, 30);
			contentPanel.add(choisirButton[i]);
		}
		
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setVisible(true);
	}
}
