package state;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import model.Piece;
import vue.Fenetre;
import vue.PartiePanel;

public class PieceState extends State{
    private float transparence=1.0f;
    private int circleSize=44;
	private JPanel pan;
	private Thread t;
	private Fenetre f;
    
	public PieceState(Fenetre f){
		super(f);
		this.f=f;
        pan=new JPanel(){
        	public void paintComponent(Graphics g){
            	Graphics2D g2d = (Graphics2D)g;
                g2d.setPaint(Color.white);
                ArrayList<Piece> pieces ;
                if(f.getController().getDebutPartie())
                	pieces = f.getController().getPiece().getPositionDisponibleDebutPartie(f.getController().getPlateau(), f.getController().getJoueur());
                else
            		pieces = f.getController().getPiece().getPositionDisponible(f.getController().getPlateau(), f.getController().getJoueur());
                for(Piece p : pieces){
        			g2d.setStroke(new BasicStroke(4.0f));
        	        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparence));
        			g2d.draw(new Ellipse2D.Double(p.getX()-circleSize/2, p.getY()-circleSize/2, circleSize, circleSize));
        			
        			g2d.setStroke(new BasicStroke(2.0f));
        	        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        			g2d.draw(new Ellipse2D.Double(p.getX()-7, p.getY()-7, 14, 14));
            	}        		
        	}
        };

		pan.setBounds(0, 0, PartiePanel.WIDTH, PartiePanel.HEIGHT);
		pan.setOpaque(false);
		f.getContentPane().add(pan);

	    t = new Thread() {
	    	public void run() {
	            	boolean flag=true;
	            	while(!this.isInterrupted()){
		            	if(flag)
		            		transparence-=0.03f;
		            	else
		            		transparence+=0.03f;
		            	pan.repaint();
		            	if(transparence<=0.4f || transparence >=1)
		            		flag=!flag;
		            	try {
		            		if(transparence >=0.90) Thread.sleep(40);
							Thread.sleep(30);
						} catch (InterruptedException e) {
				            Thread.currentThread().interrupt();
						}
	            	}
	            }
	          };
	          t.start();
	}
	
	public Thread getThread(){
		return t;
	}

	public void mousePressed(MouseEvent e) {

		if(f.getController().getDebutPartie()){
			if(f.getController().poserPieceDebutPartie(e.getX(), e.getY())){
				f.remove(pan);
				t.interrupt();
			}
		}
		else{
			if(f.getController().poserPiece(e.getX(), e.getY())){
				f.remove(pan);
			}
		}
	}
}
