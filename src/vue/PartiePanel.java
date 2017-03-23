package vue;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
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
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.Controller;
import model.Carte;
import model.CarteDeveloppement;
import model.Joueur;
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
    public static final int widthCarteDev = (int)((double)150/1920*WIDTH);
    public static final int heightCarteDev = (int)((double)230/1920*WIDTH);
    public static final int CARTE_TOP_Y = 140;
    public static final int CARTE_BAS_Y = 140+heightCarte+50;
    public static final int CARTE_ARGILE_X = margeGauche+11*widthCase/2;
    public static final int CARTE_BOIS_X = margeGauche+11*widthCase/2+widthCarte+40;
    public static final int CARTE_BLE_X = margeGauche+11*widthCase/2+2*(widthCarte+40);
    public static final int CARTE_MOUTON_X = margeGauche+6*widthCase+20;
    public static final int CARTE_PIERRE_X = margeGauche+6*widthCase+widthCarte+80;
    public static final int CARTE_DEV_X = margeGauche+9*widthCase/2+20;
    public static final int CARTE_DEV_Y = 120+5*heightCarte/2;
    public static final int WIDTH_SCORE = (int)((WIDTH-(10+margeGauche+4*widthCase))/4.0);
    public static final int HEIGHT_SCORE = 4*widthCarte/5;
    private static Carte[] cartes;
    private Plateau p;
    private ImageIcon carteRessources;
    private ImageIcon voleurImage;
	private JLabel labRessources = new JLabel();
	private JLabel labBoutonDes = new JLabel();
	private JLabel labBoutonEchange = new JLabel();
	private JLabel labBoutonEchangeBanque = new JLabel();
	private JLabel labRoute = new JLabel();
	private JLabel labVillage = new JLabel();
	private JLabel labVille = new JLabel();
	private JLabel labCarteDev = new JLabel();
	private JLabel[] scores;
	private JLabel[] nomJoueurs;
	private JLabel[] pointJoueurs;
	private JLabel[] nbCarteJoueurs;
	private JLabel[] nbCarteDevJoueurs;
	private JLabel[] nbRouteJoueurs;
	private JLabel[] nbChevalierJoueurs;
	private String[] etatBouton = new String[3];
	private AffineTransform rotation;
	private float transparence=1.0f;
    private int circleSize=44;
	private ImageIcon pieceAnimation;
	private Piece pieceAnimation2;
	private boolean echangeEnable;
	private boolean achatEnable;
	private boolean desEnable;
	private boolean bloquerFin = false;
	private CarteDeveloppement[] carteDev;
	private JLabel[] allLabCarteDev;
    private Fenetre f;
    
    public PartiePanel(Fenetre f) {
    	f.setFocusable(true);
    	f.requestFocusInWindow();
    	
    	this.f=f;
    	p=f.getController().getPlateau();
        setLayout(null);
        voleurImage = new ImageIcon(new ImageIcon("images/pieces/voleur.png").getImage().getScaledInstance(30, 70, Image.SCALE_DEFAULT));
        cartes = new Carte[5];
        cartes[0] = new Carte(Ressource.argile);
        cartes[1] = new Carte(Ressource.bois);
        cartes[2] = new Carte(Ressource.ble);
        cartes[3] = new Carte(Ressource.mouton);
        cartes[4] = new Carte(Ressource.pierre);
        carteDev = new CarteDeveloppement[5];
        carteDev[0] = new CarteDeveloppement("chevalier");
        carteDev[1] = new CarteDeveloppement("invention");
        carteDev[2] = new CarteDeveloppement("monopole");
        carteDev[3] = new CarteDeveloppement("point");
        carteDev[4] = new CarteDeveloppement("route");
        allLabCarteDev = new JLabel[5];
        for(int i=0;i<5;i++)
        	allLabCarteDev[i] = new JLabel();
        
        scores = new JLabel[4];
        nomJoueurs = new JLabel[4];
        pointJoueurs = new JLabel[4];
        nbCarteJoueurs = new JLabel[4];
        nbCarteDevJoueurs = new JLabel[4];
        nbRouteJoueurs = new JLabel[4];
        nbChevalierJoueurs = new JLabel[4];
    	Joueur[] j = f.getController().getJoueurs();
        for(int i=0;i<4;i++){
        	scores[i] = new JLabel();
	    	nomJoueurs[i] = new JLabel(j[i].getPseudo(), SwingConstants.CENTER);
	    	nomJoueurs[i].setBounds(5, 0, WIDTH_SCORE, 45);
	    	nomJoueurs[i].setFont(new Font("arial", Font.BOLD, 35));
	    	nomJoueurs[i].setForeground(Color.white);
	    	scores[i].add(nomJoueurs[i]);
	    	pointJoueurs[i] = new JLabel(""+j[i].getPoints());
	    	int margin = 0;
	    	if(WIDTH >= 1920) margin = 4;
	    	pointJoueurs[i].setBounds((int)(WIDTH_SCORE/17.0-2), (int)(HEIGHT_SCORE*63.0/100.0+margin), 50, 50);
	    	pointJoueurs[i].setFont(new Font("arial", Font.BOLD, 35));
	    	pointJoueurs[i].setForeground(Color.white);
	    	scores[i].add(pointJoueurs[i]);
	    	
	    	nbCarteJoueurs[i] = new JLabel(""+j[i].getNombreCartes());
	    	nbCarteJoueurs[i].setBounds((int)(3*WIDTH_SCORE/7.0), (int)(HEIGHT_SCORE*30.0/100.0+margin), 50, 50);
	    	nbCarteJoueurs[i].setFont(new Font("arial", Font.BOLD, 35));
	    	nbCarteJoueurs[i].setForeground(Color.white);
	    	scores[i].add(nbCarteJoueurs[i]);
	    	
	    	nbRouteJoueurs[i] = new JLabel(""+j[i].getNombreCartes());
	    	nbRouteJoueurs[i].setBounds((int)(4*WIDTH_SCORE/5.0), (int)(HEIGHT_SCORE*30.0/100.0+margin), 50, 50);
	    	nbRouteJoueurs[i].setFont(new Font("arial", Font.BOLD, 35));
	    	nbRouteJoueurs[i].setForeground(Color.white);
	    	scores[i].add(nbRouteJoueurs[i]);
	    	
	    	nbCarteDevJoueurs[i] = new JLabel(""+j[i].getNombreCartes());
	    	nbCarteDevJoueurs[i].setBounds((int)(3*WIDTH_SCORE/7.0), (int)(HEIGHT_SCORE*63.0/100.0+margin), 50, 50);
	    	nbCarteDevJoueurs[i].setFont(new Font("arial", Font.BOLD, 35));
	    	nbCarteDevJoueurs[i].setForeground(Color.white);
	    	scores[i].add(nbCarteDevJoueurs[i]);
	    	
	    	nbChevalierJoueurs[i] = new JLabel(""+j[i].getNombreChevalier());
	    	nbChevalierJoueurs[i].setBounds((int)(4*WIDTH_SCORE/5.0), (int)(HEIGHT_SCORE*63.0/100.0+margin), 50, 50);
	    	nbChevalierJoueurs[i].setFont(new Font("arial", Font.BOLD, 35));
	    	nbChevalierJoueurs[i].setForeground(Color.white);
	    	scores[i].add(nbChevalierJoueurs[i]);
	    }
        ImageIcon score = new ImageIcon(new ImageIcon("images/scores/Joueur.png").getImage().getScaledInstance(WIDTH_SCORE, HEIGHT_SCORE, Image.SCALE_DEFAULT));
        scores[0].setIcon(score);
        scores[0].setBounds(10+margeGauche+4*widthCase, 0, WIDTH_SCORE, HEIGHT_SCORE);
        add(scores[0]);
        

        score = new ImageIcon(new ImageIcon("images/scores/IA1.png").getImage().getScaledInstance(WIDTH_SCORE, HEIGHT_SCORE, Image.SCALE_DEFAULT));
        scores[1].setIcon(score);
        scores[1].setBounds(10+margeGauche+4*widthCase+WIDTH_SCORE, 0, WIDTH_SCORE, HEIGHT_SCORE);
        add(scores[1]);
        
        score = new ImageIcon(new ImageIcon("images/scores/IA2.png").getImage().getScaledInstance(WIDTH_SCORE, HEIGHT_SCORE, Image.SCALE_DEFAULT));
        scores[2].setIcon(score);
        scores[2].setBounds(10+margeGauche+4*widthCase+2*WIDTH_SCORE, 0, WIDTH_SCORE, HEIGHT_SCORE);
        add(scores[2]);
        
        score = new ImageIcon(new ImageIcon("images/scores/IA3.png").getImage().getScaledInstance(WIDTH_SCORE, HEIGHT_SCORE, Image.SCALE_DEFAULT));
        scores[3].setIcon(score);
        scores[3].setBounds(10+margeGauche+4*widthCase+3*WIDTH_SCORE, 0, WIDTH_SCORE, HEIGHT_SCORE);
        add(scores[3]);
        
        
        etatBouton[0]="images/bouton/des.png";
        etatBouton[1]="images/bouton/desEntered.png";
        etatBouton[2]="images/bouton/desPressed.png";
		setIconBoutonDes(etatBouton[0]);
    	add(labBoutonDes);
    	labBoutonDes.addMouseListener(this);
    	labBoutonDes.setBounds(WIDTH-80, HEIGHT-80, 80, 80);
    	
    	setIconBoutonEchange("echange");
    	add(labBoutonEchange);
    	labBoutonEchange.setBounds(0, HEIGHT-80, 80, 80);
    	labBoutonEchange.addMouseListener(this);
    	
    	setIconBoutonEchangeBanque("echangeBanque");
    	add(labBoutonEchangeBanque);
    	labBoutonEchangeBanque.setBounds(100, HEIGHT-80, 80, 80);
    	labBoutonEchangeBanque.addMouseListener(this);
        
        carteRessources = new ImageIcon(new ImageIcon("images/cartes/ressources.png").getImage().getScaledInstance(250, 350, Image.SCALE_DEFAULT));
        labRessources.setIcon(carteRessources);
    	add(labRessources);
    	labRessources.setBounds(WIDTH-500, HEIGHT-50, 250, 350);
    	labRessources.addMouseListener(this);
		labRoute.addMouseListener(this);
		labVillage.addMouseListener(this);
		labVille.addMouseListener(this);
		labCarteDev.addMouseListener(this);
    }
	
	public void paintComponent(Graphics g) {
		afficherPiecesAchetable();
    	Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.drawImage(new ImageIcon("images/gameBackground.png").getImage(), 0, 0, null);
        for(int k=0;k<4;k++){
			for(int i=0;i<4+k;i++){
		        g2d.drawImage(p.getImageOfCase(k, i).getImage(), margeGauche+widthCase*i-k*widthCase/2, widthCase*3/4*k+margeTop, widthCase, widthCase, this);
		        g2d.drawImage(p.getCases()[k][i].getJeton().getImage(), p.getCases()[k][i].getJetonX(), p.getCases()[k][i].getJetonY(), this);
			}
    	}
		for(int k=0;k<3;k++){
			for(int i=0;i<6-k;i++){
		        g2d.drawImage(p.getImageOfCase(k+4, i).getImage(), margeGauche+widthCase*i+k*widthCase/2-widthCase, 3*widthCase+widthCase*3/4*k+margeTop-2, widthCase, widthCase, this);
		        g2d.drawImage(p.getCases()[k+4][i].getJeton().getImage(), p.getCases()[k+4][i].getJetonX(), p.getCases()[k+4][i].getJetonY(), this);
			}
		}
		
		ArrayList<Piece> pieces = p.getPieces();
		for(Piece p : pieces){
			if(p instanceof Village && ((Village) p).getPort()!=null)
    			g2d.drawImage(((Village) p).getPort().getImage().getImage(), ((Village) p).getPort().getEmplacement().getX()-((Village) p).getPort().getImage().getIconWidth()/2, ((Village) p).getPort().getEmplacement().getY()-((Village) p).getPort().getImage().getIconHeight()/2, this);
		}
		
    	pieces=p.getPiecesPoser();
    	for(Piece p : pieces){
    		if(pieceAnimation2==null || p.getX() != pieceAnimation2.getX() || p.getY() != pieceAnimation2.getY()){
	    		if(p instanceof Village)
	    			g2d.drawImage(p.getImage().getImage(), p.getX()-p.getImage().getIconWidth()/2, p.getY()-p.getImage().getIconHeight()/2, this);
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
    	}
        g2d.drawImage(voleurImage.getImage(), p.getVoleur().getX()-voleurImage.getIconWidth()/2, p.getVoleur().getY()-voleurImage.getIconHeight()/2, this);
    	
        //Afficher le nombre de cartes de dev
        int marge = 0;
        for(int i=0;i<5;i++){
        	if(f.getController().getJoueurs()[Controller.idJoueurHumain].getNombreCartesDev(i)>0){
                if(f.getController().getJoueurs()[Controller.idJoueurHumain].getNombreCartesDev(i)>1){
                	g2d.setFont(new Font("Arial", Font.PLAIN, 40));
                	FontMetrics fontMetrics = g2d.getFontMetrics();
            		g2d.drawString(f.getController().getJoueurs()[Controller.idJoueurHumain].getNombreCartesDev(i)+"", CARTE_DEV_X+marge+widthCarteDev/2-fontMetrics.getLeading(), heightCarteDev+CARTE_DEV_Y+fontMetrics.getAscent());
            	}
            	marge+= 10 + widthCarteDev;
        	}
        }
        
    	//Afficher les ressources
    	g2d.setFont(new Font("Arial", Font.PLAIN, 40));
    	FontMetrics fontMetrics = g2d.getFontMetrics();
    	g2d.drawImage(cartes[0].getImage().getImage(), CARTE_ARGILE_X, CARTE_TOP_Y, widthCarte, heightCarte, this);
    	g2d.drawString(f.getController().getJoueurs()[Controller.idJoueurHumain].getNombreDeCarteType(cartes[0].getType())+"", margeGauche+5*widthCase+widthCarte-fontMetrics.getLeading(), 140+heightCarte+fontMetrics.getAscent());
    	g2d.drawImage(cartes[1].getImage().getImage(), CARTE_BOIS_X, CARTE_TOP_Y, widthCarte, heightCarte, this);
    	g2d.drawString(f.getController().getJoueurs()[Controller.idJoueurHumain].getNombreDeCarteType(cartes[1].getType())+"", margeGauche+5*widthCase+2*widthCarte+40-fontMetrics.getLeading(), 140+heightCarte+fontMetrics.getAscent());
    	g2d.drawImage(cartes[2].getImage().getImage(), CARTE_BLE_X, CARTE_TOP_Y, widthCarte, heightCarte, this);
    	g2d.drawString(f.getController().getJoueurs()[Controller.idJoueurHumain].getNombreDeCarteType(cartes[2].getType())+"", margeGauche+5*widthCase+3*widthCarte+80-fontMetrics.getLeading(), 140+heightCarte+fontMetrics.getAscent());
    	g2d.drawImage(cartes[3].getImage().getImage(), CARTE_MOUTON_X, CARTE_BAS_Y, widthCarte, heightCarte, this);
    	g2d.drawString(f.getController().getJoueurs()[Controller.idJoueurHumain].getNombreDeCarteType(cartes[3].getType())+"", margeGauche+6*widthCase+20+widthCarte/2-fontMetrics.getAscent()/4, 140+heightCarte*2+50+fontMetrics.getAscent());
    	g2d.drawImage(cartes[4].getImage().getImage(), CARTE_PIERRE_X, CARTE_BAS_Y, widthCarte, heightCarte, this);
    	g2d.drawString(f.getController().getJoueurs()[Controller.idJoueurHumain].getNombreDeCarteType(cartes[4].getType())+"", margeGauche+6*widthCase+80+3*widthCarte/2-fontMetrics.getAscent()/4, 140+heightCarte*2+50+fontMetrics.getAscent());

    	if(rotation!=null){
    		Piece p = pieceAnimation2;
    		if(p.getJoueur().getId()==0)
    			g2d.setPaint(Color.red);
    		else if(p.getJoueur().getId()==1)
    			g2d.setPaint(Color.blue);
    		else if(p.getJoueur().getId()==2)
    			g2d.setPaint(Color.green);
    		else if(p.getJoueur().getId()==3)
    			g2d.setPaint(Color.yellow);

    		g2d.setStroke(new BasicStroke(4.0f));
	        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparence));
			g2d.draw(new Ellipse2D.Double(p.getX()-(circleSize-transparence*5)/2, p.getY()-(circleSize-transparence*5)/2, circleSize-transparence*5, circleSize-transparence*5));
		
    		g2d.setStroke(new BasicStroke(2.0f));
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    		g2d.draw(new Ellipse2D.Double(p.getX()-7, p.getY()-7, 14, 14));
    		
    		g2d.drawImage(pieceAnimation.getImage(), rotation, null);
    	}
	}
	
	public void updateStats(){
		Joueur[] j = f.getController().getJoueurs();
        for(int i=0;i<4;i++){
	    	pointJoueurs[i].setText(""+j[i].getPoints());
	    	nbCarteJoueurs[i].setText(""+j[i].getNombreCartes());
	    	nbRouteJoueurs[i].setText(""+j[i].getRouteLongue());
	    	nbCarteDevJoueurs[i].setText(""+j[i].getNombreCartesDev());
	    	nbChevalierJoueurs[i].setText(""+j[i].getNombreChevalier());
	    }
	}
	
	public void setCarteDev(){
        int marge = 0;
        for(int i=0;i<5;i++){
        	remove(allLabCarteDev[i]);
        	allLabCarteDev[i] = new JLabel();
        	if(f.getController().getJoueurs()[Controller.idJoueurHumain].getNombreCartesDev(i)>0){
                allLabCarteDev[i].setIcon(new ImageIcon(carteDev[i].getImage().getImage().getScaledInstance(widthCarteDev, heightCarteDev, Image.SCALE_DEFAULT)));
                allLabCarteDev[i].setBounds(CARTE_DEV_X+marge, CARTE_DEV_Y, widthCarteDev, heightCarteDev);
                add(allLabCarteDev[i]);
                allLabCarteDev[i].addMouseListener(this);
            	marge+= 10 + widthCarteDev;
        	}
        }
        repaint();
	}
	
	
	public void carteAnimation(Object o, boolean ajouter){
	    Thread t = new Thread() {
            public void run() {
            	Carte c = (Carte) o;
                ImageIcon image = new ImageIcon(c.getImage().getImage().getScaledInstance(widthCarte, heightCarte, Image.SCALE_DEFAULT));
                JLabel labCarte = new JLabel();
                labCarte.setIcon(image);
            	add(labCarte);
            	int xCoord=WIDTH;
            	int yCoord=HEIGHT;
    		    double rate=2;
    		    
    		    int xFinal;
    		    int yFinal = CARTE_TOP_Y;
    		    if(c.getType().equals("argile")){
    		    	xFinal = CARTE_ARGILE_X;
    		    }
    		    else if(c.getType().equals("bois")){
    		    	xFinal = CARTE_BOIS_X;
    		    }
    		    else if(c.getType().equals("ble")){
    		    	xFinal = CARTE_BLE_X;
    		    }
    		    else if(c.getType().equals("pierre")){
    		    	xFinal = CARTE_PIERRE_X;
    		    	yFinal = CARTE_BAS_Y;
    		    }
    		    else{
    		    	xFinal = CARTE_MOUTON_X;
    		    	yFinal = CARTE_BAS_Y;
    		    }
    		    
    		    //Si on retire la carte, on inverse le mouvement
    		    if(!ajouter){
    		    	int temp = xFinal;
    		    	xFinal = xCoord;
    		    	xCoord = temp;
    		    	temp = yFinal;
    		    	yFinal = yCoord;
    		    	yCoord = temp;
    		    }
            	labCarte.setBounds(xCoord, yCoord, widthCarte, heightCarte);
    		    f.repaint();
    		    
    		    if(ajouter){
	            	while(xCoord>xFinal || yCoord>yFinal){
	            		double a=Math.atan2(WIDTH-xFinal, HEIGHT-yFinal);
	        		    xCoord=(int) (xCoord - rate* Math.sin(a)-1);
	        		    yCoord=(int) (yCoord - rate* Math.cos(a)-1);
	        		    if(xCoord<xFinal)
	        		    	xCoord = xFinal;
	        		    if(yCoord<yFinal)
	        		    	yCoord=yFinal;
	                	labCarte.setBounds(xCoord, yCoord, widthCarte, heightCarte);
	                	rate+=0.05;
	                	try {
							sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	                	f.repaint();
	            	}
    		    }
            	else{
	            	while(xCoord<xFinal || yCoord<yFinal){
	            		double a=Math.atan2(WIDTH-xCoord, HEIGHT-yCoord);
	        		    xCoord=(int) (xCoord + rate* Math.sin(a)+1);
	        		    yCoord=(int) (yCoord + rate* Math.cos(a)+1);
	        		    if(xCoord>xFinal)
	        		    	xCoord = xFinal;
	        		    if(yCoord>yFinal)
	        		    	yCoord=yFinal;
	                	labCarte.setBounds(xCoord, yCoord, widthCarte, heightCarte);
	                	rate+=0.07;
	                	try {
							sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	                	f.repaint();
	            	}
            	}
            }
          };
          t.start();
	}
	
	public void lancerPieceAnimation(Piece p){
		Thread t = new Thread(){
			public void run(){
				pieceAnimation(p);
			}
		};
		t.start();
	}
	
	public synchronized void pieceAnimation(Piece p){
    	pieceAnimation2 = p;
    	transparence=1.0f;
		boolean flag = true;
        pieceAnimation = p.getImage();
    	int xCoord=WIDTH-400;
    	int yCoord=HEIGHT;
	    double rate=0;
	    
	    int angle = 80;
	    int xFinal = p.getX()-pieceAnimation.getIconWidth()/2;
	    int yFinal = p.getY()-pieceAnimation.getIconHeight()/2;
	    
		rotation = AffineTransform.getTranslateInstance(xCoord, yCoord);
		if(p instanceof Route){
			if(((Route) p).getOrientation().equals("est")){
    	    	rotation.rotate(Math.toRadians(120), p.getImage().getIconWidth()/2, p.getImage().getIconHeight()/2);
			}
			else if(((Route) p).getOrientation().equals("ouest")){
				rotation.rotate(Math.toRadians(-120), p.getImage().getIconWidth()/2, p.getImage().getIconHeight()/2);
			}
			else{
    	    	rotation.rotate(Math.toRadians(0), p.getImage().getIconWidth()/2, p.getImage().getIconHeight()/2);
			}
		}
		else{
	    	rotation.rotate(Math.toRadians(0), p.getImage().getIconWidth()/2, p.getImage().getIconHeight()/2);
		}
	    
	    f.repaint();
	    
    	while(xCoord>xFinal || yCoord>yFinal){
    		double a=Math.atan2(WIDTH-xFinal, HEIGHT-yFinal);
		    xCoord=(int) (xCoord - rate* Math.sin(a)-1);
		    yCoord=(int) (yCoord - rate* Math.cos(a)-1);
		    if(xCoord<xFinal)
		    	xCoord = xFinal;
		    if(yCoord<yFinal)
		    	yCoord=yFinal;

		    rotation = AffineTransform.getTranslateInstance(xCoord, yCoord);
    		if(p instanceof Route){
    			if(((Route) p).getOrientation().equals("est")){
	    	    	rotation.rotate(Math.toRadians((120+angle)%360), p.getImage().getIconWidth()/2, p.getImage().getIconHeight()/2);
    			}
    			else if(((Route) p).getOrientation().equals("ouest")){
    				rotation.rotate(Math.toRadians((-120+angle)%360), p.getImage().getIconWidth()/2, p.getImage().getIconHeight()/2);
    			}
    			else{
	    	    	rotation.rotate(Math.toRadians(angle), p.getImage().getIconWidth()/2, p.getImage().getIconHeight()/2);
    			}
    		}
    		else{
    	    	rotation.rotate(Math.toRadians(angle), p.getImage().getIconWidth()/2, p.getImage().getIconHeight()/2);
    		}
    		if(angle<360)
    			angle++;
        	rate+=0.008;
        	
        	if(flag)
        		transparence-=0.01f;
        	else
        		transparence+=0.01f;
        	if(transparence<=0.3f || transparence >=1)
        		flag=!flag;

        	try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	f.repaint();
    	}

        rotation = null;
        pieceAnimation2 = null;
        f.repaint();
	}

	
	public void afficherPiecesAchetable(){
		labRessources.removeAll();
		Route r = new Route();
		if(r.getPositionDisponible(f.getController().getPlateau(), f.getController().getJoueurs()[Controller.idJoueurHumain]).size()>0 && f.getController().getJoueurs()[Controller.idJoueurHumain].possedeRessourceSuffisanteRoute()){
			labRoute.setBackground(new Color(0, 1, 0, (float)0.2));
		}
		else{
			labRoute.setBackground(new Color(1, 0, 0, (float)0.2));
		}
    	labRoute.setOpaque(true);
    	labRoute.setBounds(20, 50, 210, 65);
    	labRessources.add(labRoute);
    	Village v = new Village();
		if(v.getPositionDisponible(f.getController().getPlateau(), f.getController().getJoueurs()[Controller.idJoueurHumain]).size()>0 && f.getController().getJoueurs()[Controller.idJoueurHumain].possedeRessourceSuffisanteVillage()){
			labVillage.setBackground(new Color(0, 1, 0, (float)0.2));
		}
		else{
			labVillage.setBackground(new Color(1, 0, 0, (float)0.2));
		}
		labVillage.setOpaque(true);
		labVillage.setBounds(20, 115, 210, 65);
    	labRessources.add(labVillage);
    	v = new Village(true);
		if(v.getPositionDisponible(f.getController().getPlateau(), f.getController().getJoueurs()[Controller.idJoueurHumain]).size()>0 && f.getController().getJoueurs()[Controller.idJoueurHumain].possedeRessourceSuffisanteVille()){
			labVille.setBackground(new Color(0, 1, 0, (float)0.2));
		}
		else{
			labVille.setBackground(new Color(1, 0, 0, (float)0.2));
		}
		labVille.setOpaque(true);
		labVille.setBounds(20, 180, 210, 65);
    	labRessources.add(labVille);
    	
    	
    	CarteDeveloppement c = new CarteDeveloppement("chevalier");
		if(f.getController().getJoueurs()[Controller.idJoueurHumain].possedeRessourceSuffisanteCarteDev()){
			labCarteDev.setBackground(new Color(0, 1, 0, (float)0.2));
		}
		else{
			labCarteDev.setBackground(new Color(1, 0, 0, (float)0.2));
		}
		labCarteDev.setOpaque(true);
		labCarteDev.setBounds(20, 245, 210, 65);
    	labRessources.add(labCarteDev);
	}
	

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == labRessources){
	    	labRessources.setBounds(WIDTH-500, HEIGHT-350, 250, 350);
			afficherPiecesAchetable();
		}
		else if(e.getSource() == labBoutonDes){
			setIconBoutonDes(etatBouton[1]);
	        f.repaint();
		}
		else if(e.getSource() == labBoutonEchange){
			setIconBoutonEchange("echangeEntered");
	        f.repaint();
		}
		else if(e.getSource() == labBoutonEchangeBanque){
			setIconBoutonEchangeBanque("echangeBanqueEntered");
	        f.repaint();
		}
		for(int i=0;i<5;i++)
			if(e.getSource() == allLabCarteDev[i])
                allLabCarteDev[i].setBounds(allLabCarteDev[i].getX(), CARTE_DEV_Y-50, widthCarteDev, heightCarteDev);
	}

	public void mouseExited(MouseEvent e) {
		if(e.getSource() == labRessources){
			if(e.getX()<0 || e.getX()>250 || e.getY()<0){
		    	labRessources.setBounds(WIDTH-500, HEIGHT-50, 250, 350);
		    	labRessources.removeAll();
		    	f.repaint();
			}
		}
		else if(e.getSource() == labBoutonDes){
			setIconBoutonDes(etatBouton[0]);
	        f.repaint();
		}
		else if(e.getSource() == labBoutonEchange){
			setIconBoutonEchange("echange");
	        f.repaint();
		}
		else if(e.getSource() == labBoutonEchangeBanque){
			setIconBoutonEchangeBanque("echangeBanque");
	        f.repaint();
		}
		for(int i=0;i<5;i++)
			if(e.getSource() == allLabCarteDev[i])
                allLabCarteDev[i].setBounds(allLabCarteDev[i].getX(), CARTE_DEV_Y, widthCarteDev, heightCarteDev);
	}

	public void mousePressed(MouseEvent e) {
		if(e.getSource() == labBoutonDes && desEnable){
			setIconBoutonDes(etatBouton[2]);
			f.repaint();
		}
		else if(e.getSource() == labBoutonEchange){
			setIconBoutonEchange("echangePressed");
	        f.repaint();
		}
		else if(e.getSource() == labBoutonEchangeBanque){
			setIconBoutonEchangeBanque("echangeBanquePressed");
	        f.repaint();
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(e.getSource() == labBoutonDes && (desEnable || !etatBouton[0].equals("images/bouton/des.png"))){
			if(etatBouton[0].equals("images/bouton/des.png")){
				roulementDes();
				changerBouton();
			}
			else if(!bloquerFin){
				f.getController().jouerTour();
				changerBouton();
            }
			setIconBoutonDes(etatBouton[0]);
	        f.repaint();
		}
		else if(achatEnable && f.getController().getIdJoueur()==Controller.idJoueurHumain && f.getController().getJoueurs()[Controller.idJoueurHumain].possedeRessourceSuffisanteRoute() && e.getSource() == labRoute && !etatBouton[0].equals("images/bouton/des.png")){
			f.getController().acheterPiece(new Route());
	    	labRessources.setBounds(WIDTH-500, HEIGHT-50, 250, 350);
	    	labRessources.removeAll();
	    	f.repaint();
		}
		else if(achatEnable && f.getController().getIdJoueur()==Controller.idJoueurHumain && f.getController().getJoueurs()[Controller.idJoueurHumain].possedeRessourceSuffisanteVillage() && e.getSource() == labVillage && !etatBouton[0].equals("images/bouton/des.png")){
			f.getController().acheterPiece(new Village());
	    	labRessources.setBounds(WIDTH-500, HEIGHT-50, 250, 350);
	    	labRessources.removeAll();
	    	f.repaint();
		}
		else if(achatEnable && f.getController().getIdJoueur()==Controller.idJoueurHumain && f.getController().getJoueurs()[Controller.idJoueurHumain].possedeRessourceSuffisanteVille() && e.getSource() == labVille && !etatBouton[0].equals("images/bouton/des.png")){
			f.getController().acheterPiece(new Village(true));
	    	labRessources.setBounds(WIDTH-500, HEIGHT-50, 250, 350);
	    	labRessources.removeAll();
	    	f.repaint();
		}
		else if(achatEnable && f.getController().getIdJoueur()==Controller.idJoueurHumain && f.getController().getJoueurs()[Controller.idJoueurHumain].possedeRessourceSuffisanteCarteDev() && e.getSource() == labCarteDev && !etatBouton[0].equals("images/bouton/des.png")){
			f.getController().acheterCarteDev();
	    	labRessources.setBounds(WIDTH-500, HEIGHT-50, 250, 350);
	    	labRessources.removeAll();
	    	f.repaint();
		}
		else if(e.getSource() == labBoutonEchange){
			setIconBoutonEchange("echange");
			if(echangeEnable && !etatBouton[0].equals("images/bouton/des.png")){
				new EchangeFenetre(f.getController().getJoueurs()[Controller.idJoueurHumain], f.getController());
		        f.repaint();
			}
		}
		else if(e.getSource() == labBoutonEchangeBanque){
			setIconBoutonEchangeBanque("echangeBanque");
			if(echangeEnable && !etatBouton[0].equals("images/bouton/des.png")){
				new EchangeFenetreBanque(f.getController().getJoueurs()[Controller.idJoueurHumain], f.getController());
		        f.repaint();
			}
		}
		else if(achatEnable && f.getController().getIdJoueur()==Controller.idJoueurHumain && !etatBouton[0].equals("images/bouton/des.png")){
			for(int i=0;i<5;i++){
				if(e.getSource() == allLabCarteDev[i]){
					f.getController().getJoueurs()[Controller.idJoueurHumain].retirerCartesDev(f.getController(), i);
					break;
				}
			}
		}
	}
	
	public void changerBouton(){
		if(etatBouton[0].equals("images/bouton/des.png")){
			etatBouton[0]="images/bouton/fin.png";
			etatBouton[1]="images/bouton/finEntered.png";
			etatBouton[2]="images/bouton/finPressed.png";
		}
		else if((f.getController().getIdJoueur() <= Controller.nombreJoueurHumain-1)){
			etatBouton[0]="images/bouton/des.png";
			etatBouton[1]="images/bouton/desEntered.png";
			etatBouton[2]="images/bouton/desPressed.png";
		}
		else{
			labBoutonDes.removeMouseListener(this);
			for(int i=0;i<3;i++)
				etatBouton[i]="images/bouton/"+(f.getController().getIdJoueur())+".png";
		}
		setIconBoutonDes(etatBouton[0]);
        f.repaint();
	}
	
	public Thread roulementDes(){
		bloquerFin=true;
		setIconBoutonDes(etatBouton[2]);
        f.repaint();
	    Thread t = new Thread() {
	    	public void run(){
	    		roulementDesAnimation();
	    	}
          };
          t.start();
          return t;
	}
	
	public void setIconBoutonEchange(String state){
		labBoutonEchange.setIcon(new ImageIcon(new ImageIcon("images/bouton/"+state+".png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
	}
	
	public void setIconBoutonEchangeBanque(String state){
		labBoutonEchangeBanque.setIcon(new ImageIcon(new ImageIcon("images/bouton/"+state+".png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
	}
	
	public void setIconBoutonDes(String etat){
	    labBoutonDes.setIcon(new ImageIcon(new ImageIcon(etat).getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
	}
	
    public synchronized void roulementDesAnimation() {
    	JLabel des = new JLabel();
    	JLabel des2 = new JLabel();
    	int d1=0, d2=0;
    	add(des);
    	add(des2);
    	for(int i=0;i<20;i++){
    		d1=((int)(Math.random()*6)+1);
    		d2=((int)(Math.random()*6)+1);
	        des.setIcon(new ImageIcon(new ImageIcon("images/des/"+d1+".png").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
	        des.setBounds(WIDTH/2-350, HEIGHT/2-200, 200, 200);
	        des2.setIcon(new ImageIcon(new ImageIcon("images/des/"+d2+".png").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
	        des2.setBounds(WIDTH/2-100, HEIGHT/2-200, 200, 200);
        	
        	f.repaint();
        	try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	remove(des);
    	remove(des2);
    	f.repaint();
    	bloquerFin = false;
    	f.getController().distribuerRessources((d1+d2));
	}
    
    public void activerBoutons(){
    	achatEnable = true;
    	echangeEnable = true;
    	desEnable = true;
    }
    
    public void desactiverBoutons(){
    	achatEnable = false;
    	echangeEnable = false;
    	desEnable = false;
    }
	
	public Thread cliquerBouton(){
		return roulementDes();
	}
	
	public void finTour(){
		if((f.getController().getIdJoueur() == 3 && Controller.idJoueurHumain<4) || (f.getController().getIdJoueur() <= Controller.nombreJoueurHumain-2)){
			labBoutonDes.addMouseListener(this);
			etatBouton[0]="images/bouton/des.png";
			etatBouton[1]="images/bouton/desEntered.png";
			etatBouton[2]="images/bouton/desPressed.png";
		}
		else{
			for(int i=0;i<3;i++)
				etatBouton[i]="images/bouton/"+(f.getController().getIdJoueur()+1)+".png";
		}
		setIconBoutonDes(etatBouton[0]);
        f.repaint();
        f.getController().jouerTour();
	}
	
	public void setBloquerFin(boolean b){
		bloquerFin = b;
	}
	
	public static Carte[] getCartes(){
		return cartes;
	}

	public void setFenetre(Fenetre f){
		this.f = f;
	}
}
