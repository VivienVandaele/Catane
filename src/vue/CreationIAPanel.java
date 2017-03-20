package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class CreationIAPanel extends JPanel {
    public static final int WIDTH=(int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int HEIGHT=(int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private Fenetre f;
	private ImageIcon imageBackground;
	private JButton boutonHotseat;
	private JButton boutonRetour;
	private JTextField[] names;
	private JLabel[] namesLab;
	private JSpinner nombreIA;
	
	public CreationIAPanel(Fenetre f){
		this.f=f;
		setLayout(null);

		imageBackground = new ImageIcon("images/gameBackground.png");
		
		JLabel titre = new JLabel("<HTML><BODY><center><B>Contre IA</B></center></BODY></HTML>");
		titre.setFont(new Font("arial", Font.BOLD, 100));
		titre.setBounds(WIDTH/3, 0, WIDTH, 200);
		titre.setForeground(Color.gray);
		add(titre);
		
	    names = new JTextField[4];
	    namesLab = new JLabel[4];
		for(int i=0;i<4;i++){
			names[i] = new JTextField();
			names[i].setBounds(WIDTH/2, HEIGHT/4+i*100, 200, 75);
			names[i].setFont(new Font("arial", Font.BOLD, 30));
			names[i].setText("Joueur"+i);
			namesLab[i] = new JLabel("Nom du joueur "+i+" :");
			namesLab[i].setFont(new Font("Arial", Font.BOLD, 40));
			namesLab[i].setBounds(WIDTH/4, HEIGHT/4+i*100, 400, 75);
			add(namesLab[i]);
			add(names[i]);
		}
		
		JLabel lab = new JLabel("Nombre d'IA :");
		lab.setBounds(WIDTH/4, HEIGHT/4+400, 400, 55);
		lab.setFont(new Font("Arial", Font.BOLD, 40));
		add(lab);
		
		SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 3, 1);
		nombreIA = new JSpinner(model);
		nombreIA.setBounds(WIDTH/2, HEIGHT/4+400, 100, 55);
	    add(nombreIA);
		
		boutonHotseat=new JButton("<HTML><BODY><center><B>Lancer la partie</B></center></BODY></HTML>");
		boutonHotseat.setPreferredSize(new Dimension(300, 75));
		boutonHotseat.setBounds(WIDTH/2, HEIGHT-300, 400, 65);
		boutonHotseat.setFont(new Font("Georgia", Font.PLAIN, 40));
		boutonHotseat.setBackground(Color.decode("#ffd11a"));
		boutonHotseat.setForeground(Color.white);
		boutonHotseat.setUI(new ButtonUI());
		BoutonListener bl = new BoutonListener();
		boutonHotseat.addActionListener(bl);
		add(boutonHotseat);
		boutonHotseat.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	boutonHotseat.setBackground(Color.orange);
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	boutonHotseat.setBackground(Color.decode("#ffd11a"));

		    }
		    public void mousePressed(java.awt.event.MouseEvent evt){
		    	boutonHotseat.setBackground(Color.decode("#ffa31a"));
		    }
		});
		
		boutonRetour=new JButton("<HTML><BODY><center><B>Retour</B></center></BODY></HTML>");
		boutonRetour.setPreferredSize(new Dimension(300, 75));
		boutonRetour.setBounds(WIDTH/4, HEIGHT-300, 200, 65);
		boutonRetour.setFont(new Font("Georgia", Font.PLAIN, 40));
		boutonRetour.setBackground(Color.decode("#ffd11a"));
		boutonRetour.setForeground(Color.white);
		boutonRetour.setUI(new ButtonUI());
		bl = new BoutonListener();
		boutonRetour.addActionListener(bl);
		add(boutonRetour);
		boutonRetour.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	boutonRetour.setBackground(Color.orange);
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	boutonRetour.setBackground(Color.decode("#ffd11a"));

		    }
		    public void mousePressed(java.awt.event.MouseEvent evt){
		    	boutonRetour.setBackground(Color.decode("#ffa31a"));
		    }
		});
	}
	
	public void paintComponent(Graphics g){
    	Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(imageBackground.getImage(), 0, 0, this);
	}
	
	class BoutonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == boutonHotseat){
				String name[] = new String[4];
				for(int i=0;i<4;i++)
					name[i] = names[i].getText();
				f.afficherPartie(name, new Integer((int) nombreIA.getValue()));
			}
			else if(e.getSource() == boutonRetour){
				f.afficherAccueil();
			}
		}
	}
}
