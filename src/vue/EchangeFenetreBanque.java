package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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

public class EchangeFenetreBanque extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private int[] tauxEchange;
	private JButton proposerButton;
	private Controller c;
	private Joueur j;
	private JSpinner[] spinners = new JSpinner[10];
	private ArrayList<Port> ports;
	
	public EchangeFenetreBanque(Joueur j, Controller c) {
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
		
		ports = new ArrayList<Port>();
		for(Piece p :c.getPlateau().getPiecesPoser()){
			if(p instanceof Village && p.getJoueur().getId() == j.getId()){
				if(((Village) p).getPort() != null){
					ports.add(((Village) p).getPort());
				}
			}
		}
		
		tauxEchange = new int[5];
		int tauxDefaut = 4;
		for(Port p : ports){
			if(p.getType().equals("Trois")){
				tauxDefaut = 3;
			}
		}
		for(int i=0;i<5;i++){
			tauxEchange[i] = tauxDefaut;
			for(Port p : ports){
				if((i==0 && p.getType().equals("Argile")) || (i==1 && p.getType().equals("Bois")) || (i==2 && p.getType().equals("Ble")) || (i==3 && p.getType().equals("Mouton")) || (i==4 && p.getType().equals("Pierre"))){
					tauxEchange[i] = 2;
				}
			}
		}
		
		for(int i=0;i<10;i+=2){
			SpinnerNumberModel model = new SpinnerNumberModel(0, 0, j.getNombreDeCarteType(cartes[i/2].getType()), 1);
			spinners[i] = new JSpinner(model);
			spinners[i].setBounds(75+i*100, 300, 40, 30);
			spinners[i].addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					updateProposerButton();
				}
			});
			contentPanel.add(spinners[i]);
			
			model = new SpinnerNumberModel(0, 0, 100, 1);
			spinners[i+1] = new JSpinner(model);
			spinners[i+1].setBounds(75+i*100, 350, 40, 30);
			spinners[i+1].addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					updateProposerButton();
				}
			});
			contentPanel.add(spinners[i+1]);
		}
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
	
		proposerButton = new JButton("Valider");
		proposerButton.setBounds(850, 420, 100, 30);
		proposerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				dispose();
				for(int i=0;i<5;i++){
					for(int k=0;k<Integer.valueOf(spinners[i*2].getValue().toString());k++)
						j.retirerCarte(cartes[i].getRessource());
					for(int k=0;k<(int)spinners[i*2+1].getValue();k++)
						j.ajouterCarte(cartes[i].getRessource());
				}
			}
		});
		contentPanel.add(proposerButton);
		
		JButton cancelButton = new JButton("Annuler");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		buttonPane.add(cancelButton);
		
		updateProposerButton();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setVisible(true);
	}
	
	public void updateProposerButton(){
		int sumJoueur = 0;
		int sumBank = 0;
		
		for(int i=0;i<5;i++){
			if(Integer.valueOf(spinners[i*2].getValue().toString())%tauxEchange[i]==0){
				sumJoueur += Integer.valueOf(spinners[i*2].getValue().toString())/tauxEchange[i];
			}
			else{
				proposerButton.setEnabled(false);
				return ;
			}
			sumBank += Integer.valueOf(spinners[i*2+1].getValue().toString());
		}
		if(sumJoueur == sumBank)
			proposerButton.setEnabled(true);
		else
			proposerButton.setEnabled(false);
	}
}
