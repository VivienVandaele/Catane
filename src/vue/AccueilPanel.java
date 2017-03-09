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
	private JButton boutonHotseat;
	
	public AccueilPanel(Fenetre f){
		this.f=f;
		setLayout(null);

		imageBackground = new ImageIcon("images/background.jpg");
		titre = new ImageIcon("images/titre.png");
	    
		boutonHotseat=new JButton("<HTML><BODY><center><B>Hot Seat</B></center></BODY></HTML>");
		boutonHotseat.setPreferredSize(new Dimension(300, 75));
		boutonHotseat.setBounds(WIDTH/2-200, HEIGHT/2-100, 400, 65);
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
		
		JButton boutonJouer=new JButton("<HTML><BODY><center><B>Contre IA</B></center></BODY></HTML>");
		boutonJouer.setPreferredSize(new Dimension(300, 75));
		boutonJouer.setBounds(WIDTH/2-200, HEIGHT/2, 400, 65);
		boutonJouer.setFont(new Font("Georgia", Font.PLAIN, 40));
		boutonJouer.setBackground(Color.decode("#ffd11a"));
		boutonJouer.setForeground(Color.white);
		boutonJouer.setUI(new ButtonUI());
		bl = new BoutonListener();
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
		
		JButton boutonQuitter=new JButton("<HTML><BODY><center><B>Quitter</B></center></BODY></HTML>");
		boutonQuitter.setPreferredSize(new Dimension(300, 75));
		boutonQuitter.setBounds(WIDTH/2-200, HEIGHT/2+200, 400, 65);
		boutonQuitter.setFont(new Font("Georgia", Font.PLAIN, 40));
		boutonQuitter.setBackground(Color.decode("#ffd11a"));
		boutonQuitter.setForeground(Color.white);
		boutonQuitter.setUI(new ButtonUI());
		bl = new BoutonListener();
		boutonQuitter.addActionListener(bl);
		add(boutonQuitter);
		boutonQuitter.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	boutonQuitter.setBackground(Color.orange);
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	boutonQuitter.setBackground(Color.decode("#ffd11a"));

		    }
		    public void mousePressed(java.awt.event.MouseEvent evt){
		    	boutonQuitter.setBackground(Color.decode("#ffa31a"));
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
			if(e.getSource() == boutonHotseat)
				f.afficherPartieHotSeat();
			else
				f.afficherPartie();
		}
	}
}
