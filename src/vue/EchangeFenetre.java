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

public class EchangeFenetre extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JLabel[] labImageEchange = new JLabel[4];
	private JButton[] accepterButton = new JButton[4];
	private JButton proposerButton;
	private Controller c;
	private Joueur j;
	private JSpinner[] spinners = new JSpinner[10];

	public EchangeFenetre(Joueur j, Controller c) {
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
		
		for(int i=0;i<10;i+=2){
			SpinnerNumberModel model = new SpinnerNumberModel(0, 0, j.getNombreDeCarteType(cartes[i/2].getType()), 1);
			spinners[i] = new JSpinner(model);
			spinners[i].setBounds(75+i*100, 300, 40, 30);
			spinners[i].addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					reset();
				}
			});
			contentPanel.add(spinners[i]);
			
			model = new SpinnerNumberModel(0, 0, 100, 1);
			spinners[i+1] = new JSpinner(model);
			spinners[i+1].setBounds(75+i*100, 350, 40, 30);
			spinners[i+1].addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					reset();
				}
			});
			contentPanel.add(spinners[i+1]);
		}
		
		int k=0;
		for(int i=0;i<4;i++){
			if(j.getId()!=i){
				k++;
				JLabel lab = new JLabel(c.getJoueurs()[i].getPseudo());
				lab.setFont(new Font("Arial", Font.BOLD, 30));
				lab.setBounds(k*250-100, 410, 150, 40);
				contentPanel.add(lab);
				
				labImageEchange[i] = new JLabel();
				labImageEchange[i].setBounds(k*250-125, 470, 100, 100);
				labImageEchange[i].setIcon(new ImageIcon(new ImageIcon("images/echange/defaut.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		    	contentPanel.add(labImageEchange[i]);
			}
		}
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
	
		proposerButton = new JButton("Proposer");
		proposerButton.setBounds(850, 420, 100, 30);
		EchangeFenetre ef = this;
		proposerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				proposerButton.setEnabled(false);
				ArrayList<Carte> exporter = new ArrayList<Carte>();
				ArrayList<Carte> importer = new ArrayList<Carte>();
				for(int i=0;i<5;i++){
					for(int j=0;j<Integer.valueOf(spinners[i*2].getValue().toString());j++)
						exporter.add(new Carte(cartes[i].getRessource()));
					for(int j=0;j<(int)spinners[i*2+1].getValue();j++)
						importer.add(new Carte(cartes[i].getRessource()));
				}
				c.proposerEchange(ef, exporter, importer);
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
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setVisible(true);
	}
	
	public void reset(){
		proposerButton.setEnabled(true);
		for(int i=0;i<4;i++){
			if(labImageEchange[i]!=null){
				labImageEchange[i].setIcon(new ImageIcon(new ImageIcon("images/echange/defaut.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
			}
			if(accepterButton[i]!=null){
				contentPanel.remove(accepterButton[i]);
				accepterButton[i] = null;
			}
		}
		contentPanel.repaint();
	}
	
	public void setImageEchange(int idJoueur, boolean accepter){
		int id = idJoueur;
		
		contentPanel.remove(labImageEchange[id]);
		if(accepter){
			labImageEchange[id].setIcon(new ImageIcon(new ImageIcon("images/echange/valide.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
			accepterButton[id] = new JButton("Accepter");
			accepterButton[id].setBounds((int) labImageEchange[id].getBounds().getX(), 600, 100, 30);
			accepterButton[id].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ArrayList<Carte> exporter = new ArrayList<Carte>();
					ArrayList<Carte> importer = new ArrayList<Carte>();
					Carte[] cartes = PartiePanel.getCartes();
					for(int i=0;i<5;i++){
						for(int j=0;j<Integer.valueOf(spinners[i*2].getValue().toString());j++)
							exporter.add(new Carte(cartes[i].getRessource()));
						for(int j=0;j<(int)spinners[i*2+1].getValue();j++)
							importer.add(new Carte(cartes[i].getRessource()));
					}
					c.echanger(c.getJoueurs()[idJoueur], exporter, importer);
					dispose();
				}
			});
			contentPanel.add(accepterButton[id]);
		}
		else
			labImageEchange[id].setIcon(new ImageIcon(new ImageIcon("images/echange/refuse.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		contentPanel.add(labImageEchange[id]);
		repaint();
	}
}
