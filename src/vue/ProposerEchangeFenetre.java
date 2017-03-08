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
import model.Piece;
import model.Port;
import model.Village;

public class ProposerEchangeFenetre extends JDialog{
	private final JPanel contentPanel = new JPanel();
	private Controller c;
	private Joueur j2;
	private JButton accepterButton;
	private JLabel[] labels = new JLabel[10];
	private int[] values = new int[10];
	
	public ProposerEchangeFenetre(Joueur j1, Joueur j2, ArrayList<Carte> exporter, ArrayList<Carte> importer, Controller c) {
		setBounds(100, 100, 1000, 700);
		setModal(true);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		this.c = c;
		this.j2 = j2;
		
		for(Carte carte : exporter){
			if(carte.getType().equals("argile")){
				values[0]++;
			}
			else if(carte.getType().equals("bois")){
				values[2]++;
			}
			else if(carte.getType().equals("ble")){
				values[4]++;
			}
			else if(carte.getType().equals("mouton")){
				values[6]++;
			}
			else if(carte.getType().equals("pierre")){
				values[8]++;
			}
		}
		
		for(Carte carte : importer){
			if(carte.getType().equals("argile")){
				values[1]++;
			}
			else if(carte.getType().equals("bois")){
				values[3]++;
			}
			else if(carte.getType().equals("ble")){
				values[5]++;
			}
			else if(carte.getType().equals("mouton")){
				values[7]++;
			}
			else if(carte.getType().equals("pierre")){
				values[9]++;
			}
		}
		
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
		
		for(int i=0;i<10;i+=2){
			labels[i] = new JLabel(""+values[i]);
			labels[i].setBounds(75+i*102, 300, 40, 30);
			labels[i].setFont(new Font("arial", Font.PLAIN, 40));
			contentPanel.add(labels[i]);
			
			labels[i+1] = new JLabel(""+values[i+1]);
			labels[i+1].setBounds(75+i*102, 350, 40, 30);
			labels[i+1].setFont(new Font("arial", Font.PLAIN, 40));
			contentPanel.add(labels[i+1]);
		}
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
	
		accepterButton = new JButton("Accepter");
		accepterButton.setBounds(650, 420, 100, 30);
		accepterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				c.echanger(j2, exporter, importer);
				dispose();
			}
		});
		contentPanel.add(accepterButton);
		
		JButton refuserButton = new JButton("Refuser");
		refuserButton.setBounds(850, 420, 100, 30);
		refuserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		contentPanel.add(refuserButton);
		
		updateProposerButton();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setVisible(true);
	}
	
	public void updateProposerButton(){
		if(j2.getNombreDeCarteType("argile") < values[1]){
			accepterButton.setEnabled(false);
		}
		if(j2.getNombreDeCarteType("bois") < values[3]){
			accepterButton.setEnabled(false);
		}
		if(j2.getNombreDeCarteType("ble") < values[5]){
			accepterButton.setEnabled(false);
		}
		if(j2.getNombreDeCarteType("mouton") < values[7]){
			accepterButton.setEnabled(false);
		}
		if(j2.getNombreDeCarteType("pierre") < values[9]){
			accepterButton.setEnabled(false);
		}
	}
}
