package vue;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.Piece;
import model.Plateau;
import model.Ville;

public class PartiePanel extends JPanel{
    public static final int WIDTH=(int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int HEIGHT=(int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static final int widthCase=(int)((double)180/1920*WIDTH);
    public static final int margeGauche=3*widthCase/2+5;
    public static final int margeTop=widthCase/6;
	private int widthJeton=60;
    private Plateau p;
    
    private Thread t=null;
    
    public PartiePanel(Fenetre f) {
    	p=f.getController().getPlateau();

        setLayout(null);
    	/* 
        JButton acheterVille=new JButton("Acheter une ville");
		acheterVille.addActionListener(new ActionListener(){ 
	        public void actionPerformed(ActionEvent e){
	        	if(f.getController().acheterVille())
	        		new PieceState(f);
	        }
	    });
		add(acheterVille);*/
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
			for(int i=0;i<4+(2-k);i++){
		        g2d.drawImage(p.getImageOfCase(k+4, i).getImage(), margeGauche+widthCase*i+k*widthCase/2-widthCase, 3*widthCase+widthCase*3/4*k+margeTop-2, widthCase, widthCase, this);
		        g2d.drawImage(p.getCases()[k+4][i].getJeton().getImage(), margeGauche+widthCase*i+k*widthCase/2-widthCase+widthCase/2-widthJeton/2, 3*widthCase+widthCase*3/4*k+margeTop-2+widthCase/2-widthJeton/2, widthJeton, widthJeton, this);
			}
		}

    	ArrayList<Piece> pieces=p.getListVille();
    	for(Piece p : pieces){
	        g2d.drawImage(p.getImage().getImage(), p.getX()-25, p.getY()-10, 50, 20, this);
    	}
	}
	

}
