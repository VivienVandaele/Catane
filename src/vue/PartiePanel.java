package vue;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Carte;
import model.Piece;
import model.Plateau;
import model.Ressource;
import model.Route;
import model.Village;

public class PartiePanel extends JPanel implements MouseListener{
    public static final int WIDTH=(int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int HEIGHT=(int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static final int widthCase=(int)((double)180/1920*WIDTH);
    public static final int margeGauche=3*widthCase/2+5;
    public static final int margeTop=10;
    public static final int widthCarte = (int)((double)170/1920*WIDTH);
    public static final int heightCarte = (int)((double)250/1920*WIDTH);
	private int widthJeton=60;
    private Plateau p;
    private Carte[] cartes;
    private ImageIcon carteRessources;
	private JLabel labRessources = new JLabel();
    private Fenetre f;
    
    public PartiePanel(Fenetre f) {
    	this.f=f;
    	p=f.getController().getPlateau();
        setLayout(null);
        cartes = new Carte[5];
        cartes[0] = new Carte(Ressource.argile);
        cartes[1] = new Carte(Ressource.bois);
        cartes[2] = new Carte(Ressource.ble);
        cartes[3] = new Carte(Ressource.mouton);
        cartes[4] = new Carte(Ressource.pierre);
        
        carteRessources = new ImageIcon(new ImageIcon("images/cartes/ressources.png").getImage().getScaledInstance(250, 350, Image.SCALE_DEFAULT));
        labRessources.setIcon(carteRessources);
    	add(labRessources);
    	labRessources.setBounds(WIDTH-300, HEIGHT-50, 250, 350);
    	labRessources.addMouseListener(this);
    }
	
	public void paintComponent(Graphics g) {
    	Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
        for(int k=0;k<4;k++){
			for(int i=0;i<4+k;i++){
		        g2d.drawImage(p.getImageOfCase(k, i).getImage(), margeGauche+widthCase*i-k*widthCase/2, widthCase*3/4*k+margeTop, widthCase, widthCase, this);
		        g2d.drawImage(p.getCases()[k][i].getJeton().getImage(), margeGauche+widthCase*i-k*widthCase/2+widthCase/2-widthJeton/2, widthCase*3/4*k+margeTop+widthCase/2-widthJeton/2, widthJeton, widthJeton, this);
			}
    	}
		for(int k=0;k<3;k++){
			for(int i=0;i<6-k;i++){
		        g2d.drawImage(p.getImageOfCase(k+4, i).getImage(), margeGauche+widthCase*i+k*widthCase/2-widthCase, 3*widthCase+widthCase*3/4*k+margeTop-2, widthCase, widthCase, this);
		        g2d.drawImage(p.getCases()[k+4][i].getJeton().getImage(), margeGauche+widthCase*i+k*widthCase/2-widthCase+widthCase/2-widthJeton/2, 3*widthCase+widthCase*3/4*k+margeTop-2+widthCase/2-widthJeton/2, widthJeton, widthJeton, this);
			}
		}
		
    	ArrayList<Piece> pieces=p.getPiecesPoser();
    	for(Piece p : pieces){
    		if(p instanceof Village)
    			g2d.drawImage(p.getImage().getImage(), p.getX()-25, p.getY()-10, 50, 20, this);
    		else{
    			if(((Route) p).getOrientation().equals("est")){
	    	    	AffineTransform rotation = AffineTransform.getTranslateInstance(p.getX()-p.getImage().getIconWidth()/2, p.getY()-p.getImage().getIconHeight()/2);
	    	    	rotation.rotate(Math.toRadians(120), p.getImage().getIconWidth()/2, p.getImage().getIconHeight()/2);
	    			g2d.drawImage(p.getImage().getImage(), rotation, null);
    			}
    			else if(((Route) p).getOrientation().equals("ouest")){
	    	    	AffineTransform rotation = AffineTransform.getTranslateInstance(p.getX()-p.getImage().getIconWidth()/2, p.getY()-p.getImage().getIconHeight()/2);
	    	    	rotation.rotate(Math.toRadians(-120), p.getImage().getIconWidth()/2, p.getImage().getIconHeight()/2);
	    			g2d.drawImage(p.getImage().getImage(), rotation, null);
    			}
    			else{
	    			g2d.drawImage(p.getImage().getImage(), p.getX()-p.getImage().getIconWidth()/2, p.getY()-p.getImage().getIconHeight()/2, null);
    			}
    		}
    	}
    	g2d.setFont(new Font("Arial", Font.PLAIN, 40));
    	FontMetrics fontMetrics = g2d.getFontMetrics();
    	g2d.drawImage(cartes[0].getImage().getImage(), margeGauche+11*widthCase/2, 100, widthCarte, heightCarte, this);
    	g2d.drawString(f.getController().getJoueurs()[0].getNombreDeCarteType(cartes[0].getType())+"", margeGauche+5*widthCase+widthCarte-fontMetrics.getLeading(), 100+heightCarte+fontMetrics.getAscent());
    	g2d.drawImage(cartes[1].getImage().getImage(), margeGauche+11*widthCase/2+widthCarte+40, 100, widthCarte, heightCarte, this);
    	g2d.drawString(f.getController().getJoueurs()[0].getNombreDeCarteType(cartes[1].getType())+"", margeGauche+5*widthCase+2*widthCarte+40-fontMetrics.getLeading(), 100+heightCarte+fontMetrics.getAscent());
    	g2d.drawImage(cartes[2].getImage().getImage(), margeGauche+11*widthCase/2+2*(widthCarte+40), 100, widthCarte, heightCarte, this);
    	g2d.drawString(f.getController().getJoueurs()[0].getNombreDeCarteType(cartes[2].getType())+"", margeGauche+5*widthCase+3*widthCarte+80-fontMetrics.getLeading(), 100+heightCarte+fontMetrics.getAscent());
    	g2d.drawImage(cartes[3].getImage().getImage(), margeGauche+6*widthCase+20, 100+heightCarte+50, widthCarte, heightCarte, this);
    	g2d.drawString(f.getController().getJoueurs()[0].getNombreDeCarteType(cartes[3].getType())+"", margeGauche+6*widthCase+20+widthCarte/2-fontMetrics.getAscent()/4, 100+heightCarte*2+50+fontMetrics.getAscent());
    	g2d.drawImage(cartes[4].getImage().getImage(), margeGauche+6*widthCase+widthCarte+80, 100+heightCarte+50, widthCarte, heightCarte, this);
    	g2d.drawString(f.getController().getJoueurs()[0].getNombreDeCarteType(cartes[4].getType())+"", margeGauche+6*widthCase+80+3*widthCarte/2-fontMetrics.getAscent()/4, 100+heightCarte*2+50+fontMetrics.getAscent());
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == labRessources){
	    	labRessources.setBounds(WIDTH-300, HEIGHT-350, 250, 350);
			JLabel labRoute = new JLabel();
			labRoute.setBackground(new Color((float)0, (float)0.5, (float)0, (float)0.1));
	    	labRoute.setOpaque(true);
	    	labRoute.setBounds(20, 50, 210, 65);
	    	labRessources.add(labRoute);
			JLabel labVillage = new JLabel();
			labVillage.setBackground(new Color((float)0.5, (float)0, (float)0, (float)0.1));
			labVillage.setOpaque(true);
			labVillage.setBounds(20, 115, 210, 65);
	    	labRessources.add(labVillage);
			JLabel labVille = new JLabel();
			labVille.setBackground(new Color((float)0, (float)0.5, (float)0, (float)0.1));
			labVille.setOpaque(true);
			labVille.setBounds(20, 180, 210, 65);
	    	labRessources.add(labVille);
	    	f.repaint();
		}
	}

	public void mouseExited(MouseEvent e) {
		if(e.getSource() == labRessources){
	    	labRessources.setBounds(WIDTH-300, HEIGHT-50, 250, 350);
	    	f.repaint();
		}
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}
}
