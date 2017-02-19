package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Carte;
import model.Joueur;

public class VoleurFenetre extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private int nombreCartesADefausser;
	private JSpinner[] spinners = new JSpinner[10];
	private Joueur j;
	private JLabel labTitre;

	
	public VoleurFenetre(Joueur j, int nombreCartesADefausser) {
		setBounds(100, 100, 1000, 700);
		setModal(true);
		setLocationRelativeTo(null);
		setUndecorated(true);
		this.nombreCartesADefausser = nombreCartesADefausser;
		this.j = j;
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		labTitre = new JLabel("Défaussez "+nombreCartesADefausser+" cartes", SwingConstants.CENTER);
		labTitre.setFont(new Font("arial", Font.BOLD, 30));
		labTitre.setBounds(0, 0, 1000, 100);
		contentPanel.add(labTitre);
		
		Carte[] cartes = PartiePanel.getCartes();
		for(int i=0;i<6;i++){
			if(i<5){
				JLabel labImage = new JLabel();
				labImage.setBounds(10+i*200, 100, PartiePanel.widthCarte, PartiePanel.heightCarte);
		    	labImage.setIcon(new ImageIcon(cartes[i].getImage().getImage().getScaledInstance(PartiePanel.widthCarte, PartiePanel.heightCarte, Image.SCALE_DEFAULT)));
		    	contentPanel.add(labImage);
		    	JLabel lab = new JLabel(j.getNombreDeCarteType(cartes[i].getType())+"");
		    	lab.setFont(new Font("arial", Font.BOLD, 30));
		    	lab.setBounds(PartiePanel.widthCarte/2+i*200, 350, 50, 50);
		    	contentPanel.add(lab);
			}
			else{
				JLabel labImage = new JLabel();
				contentPanel.add(labImage);
			}
		}
		
		for(int i=0;i<10;i+=2){
			SpinnerNumberModel model = new SpinnerNumberModel(0, 0, j.getNombreDeCarteType(cartes[i/2].getType()), 1);
			spinners[i] = new JSpinner(model);
			spinners[i].setBounds(75+i*100, 400, 40, 30);
			spinners[i].addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					int sum = 0;
					for(int i=0;i<10;i+=2)
						sum += (int) spinners[i].getValue();
					if(nombreCartesADefausser-sum<0){
						JSpinner s =((JSpinner) e.getSource());
						s.setValue((int)s.getValue()-1);
						sum--;
					}
					labTitre.setText("Défaussez "+(nombreCartesADefausser-sum)+" cartes");
				}
			});
			contentPanel.add(spinners[i]);
		}
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("Valider");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		okButton.addMouseListener(new MouseListener() {
		
			public void mouseReleased(MouseEvent e) {
				int sum = 0;
				for(int i=0;i<10;i+=2)
					sum += (int) spinners[i].getValue();
				if(nombreCartesADefausser-sum == 0){
					for(int i=0;i<10;i+=2)
						for(int k=0;k<(int)spinners[i].getValue();k++)
							j.retirerCarte(cartes[i/2].getRessource());
					dispose();
				}
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
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		getRootPane().setDefaultButton(okButton);
		setVisible(true);
	}

}
