package vue;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.Piece;
import model.Plateau;
import model.Ville;
import state.PieceState;

public class PartiePanel extends JPanel{
    public static final int WIDTH=(int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int widthCase=(int)((double)180/1920*WIDTH);
    private int margeGauche;
    private int margeTop;
    private Plateau p;

    public PartiePanel(Fenetre f) {
    	p=f.getController().getPlateau();
        margeGauche=3*widthCase/2+5;
    	margeTop=widthCase/6;
        
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
    	
    	for(int k=0;k<4;k++){
			for(int i=0;i<4+k;i++){
		        g2d.drawImage(p.getImageOfCase(k, i).getImage(), margeGauche+widthCase*i-k*widthCase/2, widthCase*3/4*k+margeTop, widthCase, widthCase, this);
			}
    	}
		for(int k=0;k<3;k++){
			for(int i=0;i<4+(2-k);i++){
		        g.drawImage(p.getImageOfCase(k+4, i).getImage(), margeGauche+widthCase*i+k*widthCase/2-widthCase, 3*widthCase+widthCase*3/4*k+margeTop-2, widthCase, widthCase, this);
			}
		}

    	ArrayList<Piece> pieces=p.getListVille();
    	for(Piece p : pieces){
	        g2d.drawImage(p.getImage().getImage(), p.getX()-25, p.getY()-10, 50, 20, this);
    	}
	}
}
