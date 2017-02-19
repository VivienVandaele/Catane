package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.Joueur;

public class VolerRessourceFenetre extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton[] boutonsVoler = new JButton[3];

	public VolerRessourceFenetre(Joueur joueur, HashSet<Joueur> joueursVoisins) {
		setBounds(100, 100, 1000, 500);
		setModal(true);
		setLocationRelativeTo(null);
		setUndecorated(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lab = new JLabel("Quel joueur voulez-vous voler ?", SwingConstants.CENTER);
		lab.setFont(new Font("arial", Font.BOLD, 30));
		lab.setBounds(0, 0, 1000, 100);
		contentPanel.add(lab);
		
		int i=0;
		for(Joueur j : joueursVoisins){
			lab = new JLabel(j.getPseudo(), SwingConstants.CENTER);
	    	lab.setFont(new Font("arial", Font.BOLD, 30));
	    	lab.setBounds(i*340, 150, 340, 50);
    		if(j.getId()==0)
    			lab.setForeground(Color.red);
    		else if(j.getId()==1)
    			lab.setForeground(Color.blue);
    		else if(j.getId()==2)
    			lab.setForeground(Color.green);
    		else if(j.getId()==3)
    			lab.setForeground(Color.yellow);
	    	contentPanel.add(lab);
	    	
	    	boutonsVoler[i] = new JButton("Voler");
	    	boutonsVoler[i].setBounds(120+i*340, 230, 100, 40);
	    	boutonsVoler[i].addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
					joueur.ajouterCarte(j.retirerCarteAleatoire().getRessource());
					dispose();
				}
				
				public void mousePressed(MouseEvent e) {
					
				}
				
				public void mouseExited(MouseEvent e) {
					
				}
				
				public void mouseEntered(MouseEvent e) {
					
				}
				
				public void mouseClicked(MouseEvent e) {
					
				}
			});
	    	contentPanel.add(boutonsVoler[i]);
		    i++;
		}

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
