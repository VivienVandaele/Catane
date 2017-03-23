package vue;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import model.Carte;
import model.Joueur;
import model.Ressource;

public class GagnantFenetre extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JButton menuBoutton;
	private Joueur j;

	public GagnantFenetre(Joueur j) {
		setBounds(100, 100, 1000, 700);
		setModal(true);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		this.j = j;
		
		JLabel lab = new JLabel("Le gagnant est : ");
		
		lab.setForeground(j.getColor());
		menuBoutton = new JButton("Menu");
			
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setVisible(true);
	}
}
