package state;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

import model.Arete;
import model.Noeud;
import model.Plateau;
import model.Position;
import vue.Fenetre;
import vue.PartiePanel;

public class PieceState extends State{
	private Thread t=null;
    private float transparence=1.0f;
    private int circleSize=44;
	private JPanel pan;
    
	public PieceState(Fenetre f){
		super(f);
		
        pan=new JPanel(){
        	public void paintComponent(Graphics g){
            	Graphics2D g2d = (Graphics2D)g;
                g2d.setPaint(Color.white);
                Arete a=new Arete(1, 1);
                for(Position p : Plateau.positions){
                	if(p instanceof Noeud || p instanceof Arete){
        			g2d.setStroke(new BasicStroke(4.0f));
        	        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparence));
        			g2d.draw(new Ellipse2D.Double(p.getX()-circleSize/2, p.getY()-circleSize/2, circleSize, circleSize));
        			
        			g2d.setStroke(new BasicStroke(2.0f));
        	        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        			g2d.draw(new Ellipse2D.Double(p.getX()-7, p.getY()-7, 14, 14));
                	}
                }        		
        	}
        };
        pan.repaint();
        pan.revalidate();

		pan.setBounds(0, 0, PartiePanel.WIDTH, PartiePanel.HEIGHT);
		pan.setOpaque(false);
		f.getContentPane().add(pan);

        if(t==null){
	        t = new Thread() {
	            public void run() {
	            	boolean flag=true;
	            	while(transparence>0){
	            	if(flag){
	            		transparence-=0.03f;
	            	}
	            	else{
	            		transparence+=0.03f;
	            	}
	            	pan.repaint();
	            	if(transparence<=0.4f || transparence >=1) flag=!flag;
	            	try {
	            		if(transparence >=0.90) Thread.sleep(40);
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            }}
	          };
	          t.start();
        }
		
	}

	public void mousePressed(MouseEvent e) {
		if(f.getController().poserPiece(e.getX(), e.getY())){
			f.getContentPane().remove(pan);
			f.setState(new NormalState(f));
		}
	}
}
