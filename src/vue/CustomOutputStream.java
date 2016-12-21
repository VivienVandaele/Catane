package vue;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Arete;
import model.Noeud;
import model.Plateau;
import model.Position;

public class CustomOutputStream extends OutputStream {
    private JLabel lab;
    private JPanel pan; 
    
    public CustomOutputStream(JLabel lab, Fenetre f) {
        this.lab = lab;
		
        pan=new JPanel(){
        	public void paintComponent(Graphics g){
            	Graphics2D g2d = (Graphics2D)g;
                g2d.setPaint(Color.gray);
                g2d.setFont(new Font("Arial", Font.BOLD, 40));
                g2d.drawString(lab.getText(), 400, 400);        		
        	}
        };
		lab.setFont(new Font("Arial", Font.BOLD, 40));

        pan.repaint();
        pan.revalidate();

		pan.setBounds(0, 0, PartiePanel.WIDTH, PartiePanel.HEIGHT);
		pan.setOpaque(false);
		f.getContentPane().add(pan);


    }
     
    @Override
    public void write(int b) throws IOException {
        if(b=='\b')
    		lab.setText("");
        else if(b=='\n'){
	        Thread t = new Thread() {
	            public void run() {
            		lab.setForeground(Color.green);
            		try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                	lab.setForeground(Color.orange);
                	try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                	lab.setVisible(false);
            	}
	          };
	          t.start();
        }
    	else
    		lab.setText(lab.getText()+String.valueOf((char)b));
        
    }
}