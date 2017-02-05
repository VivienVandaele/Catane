package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.Controller;
import model.Carte;
import model.Joueur;

public class EchangeFenetre extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel[] labImageEchange = new JLabel[4];
	private JButton[] accepterButton = new JButton[4];
	private JButton proposerButton;
	private Controller c;
	private Joueur j;

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
		
		JSpinner[] spinners = new JSpinner[10];
		for(int i=0;i<10;i+=2){
			SpinnerNumberModel model = new SpinnerNumberModel(0, 0.0, j.getNombreDeCarteType(cartes[i/2].getType()), 1.0);
			spinners[i] = new JSpinner(model);
			spinners[i].setBounds(75+i*100, 300, 40, 30);
			spinners[i].addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					reset();
				}
			});
			contentPanel.add(spinners[i]);
			
			model = new SpinnerNumberModel(0, 0.0, 100, 1.0);
			spinners[i+1] = new JSpinner(model);
			spinners[i+1].setBounds(75+i*100, 350, 40, 30);
			spinners[i+1].addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					reset();
				}
			});
			contentPanel.add(spinners[i+1]);
		}
		
		for(int i=1;i<4;i++){
			JLabel lab = new JLabel(c.getJoueurs()[(c.getIdJoueur()+i)%4].getPseudo());
			lab.setFont(new Font("Arial", Font.BOLD, 30));
			lab.setBounds(i*250-100, 410, 100, 40);
			contentPanel.add(lab);
			
			labImageEchange[(c.getIdJoueur()+i)%4] = new JLabel();
			labImageEchange[(c.getIdJoueur()+i)%4].setBounds(i*250-125, 470, 100, 100);
			labImageEchange[(c.getIdJoueur()+i)%4].setIcon(new ImageIcon(new ImageIcon("images/echange/defaut.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
	    	contentPanel.add(labImageEchange[(c.getIdJoueur()+i)%4]);
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
				c.proposerEchange(ef);
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
		proposerButton.setEnabled(true);;
		for(int i=0;i<4;i++){
			if(labImageEchange[i]!=null){
				labImageEchange[(c.getIdJoueur()+i)%4].setIcon(new ImageIcon(new ImageIcon("images/echange/defaut.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
			}
			if(accepterButton[i]!=null){
				contentPanel.remove(accepterButton[i]);
				accepterButton[i] = null;
			}
		}
		contentPanel.repaint();
	}
	
	public void setImageEchange(int id, boolean accepter){
		if(j.getId()!=0 && id>j.getId())
			id--;
		else if((j.getId()!=0 && id<j.getId()))
			id++;
		contentPanel.remove(labImageEchange[id]);
		labImageEchange[id] = new JLabel();
		labImageEchange[id].setBounds(id*250-125, 470, 100, 100);
		if(accepter){
			labImageEchange[id].setIcon(new ImageIcon(new ImageIcon("images/echange/valide.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
			accepterButton[id] = new JButton("Accepter");
			accepterButton[id].setBounds(id*250-125, 600, 100, 30);
			contentPanel.add(accepterButton[id]);
		}
		else
			labImageEchange[id].setIcon(new ImageIcon(new ImageIcon("images/echange/refuse.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		contentPanel.add(labImageEchange[id]);
		repaint();
	}
}
