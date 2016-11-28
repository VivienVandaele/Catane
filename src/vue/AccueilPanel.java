package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class AccueilPanel extends JPanel{
	private Fenetre f;
	
	public AccueilPanel(Fenetre f){
		this.f=f;
		setLayout( new FlowLayout(FlowLayout.CENTER, 50, 200));

		JTextArea texte = new JTextArea("Menu temporaire\n");
		texte.setFont(new Font("Arial", Font.BOLD, 50));
		texte.setBackground(Color.orange);
		add(texte);
		
		JButton boutonJouer=new JButton("<HTML><BODY><center><B>JOUER !</B></center></BODY></HTML>");
		
		boutonJouer.setPreferredSize(new Dimension(300, 150));
		boutonJouer.setFont(new Font("Arial", Font.PLAIN, 40));
		BoutonListener bl = new BoutonListener();
		boutonJouer.addActionListener(bl);
		add(boutonJouer);
	}
	
	class BoutonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			f.afficherPartie();
		}
	}
}
