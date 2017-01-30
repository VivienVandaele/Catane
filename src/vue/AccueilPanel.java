package vue;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AccueilPanel extends JPanel{
    public static final int WIDTH=(int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int HEIGHT=(int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private Fenetre f;
	private ImageIcon imageBackground;
	private ImageIcon titre;
	private Thread t=null;
	private float transparence=0.1f;
	
	public AccueilPanel(Fenetre f){
		this.f=f;
		setLayout(null);

		imageBackground = new ImageIcon("images/background.jpg");
		titre = new ImageIcon("images/titre.png");
	    
		JButton boutonJouer=new JButton("<HTML><BODY><center><B>Jouer</B></center></BODY></HTML>");
		boutonJouer.setPreferredSize(new Dimension(300, 75));
		boutonJouer.setBounds(WIDTH/2-150, HEIGHT/2-100, 300, 65);
		boutonJouer.setFont(new Font("Georgia", Font.PLAIN, 40));
		boutonJouer.setBackground(Color.decode("#ffd11a"));
		boutonJouer.setForeground(Color.white);
		boutonJouer.setUI(new ButtonUI());
		BoutonListener bl = new BoutonListener();
		boutonJouer.addActionListener(bl);
		add(boutonJouer);
		boutonJouer.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	boutonJouer.setBackground(Color.orange);
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	boutonJouer.setBackground(Color.decode("#ffd11a"));

		    }
		    public void mousePressed(java.awt.event.MouseEvent evt){
		    	boutonJouer.setBackground(Color.decode("#ffa31a"));
		    }
		});
        if(t==null){
	        t = new Thread() {
	            public void run() {
	            	while(transparence<0.98){
	            		transparence+=0.03f;
	            		repaint();
		            	try {
							Thread.sleep(60);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	            	}
	            }
	          };
	          t.start();
        }
	}
	
	public void paintComponent(Graphics g){
    	Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(imageBackground.getImage(), 0, 0, this);
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparence));
		if(WIDTH==1920)
			g2d.drawImage(titre.getImage(), WIDTH/2-500, 0, this);
		else
			g2d.drawImage(titre.getImage(), WIDTH/4, 0, WIDTH/2, HEIGHT/3, this);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	
	class BoutonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			f.afficherPartie();
		}
	}
}
