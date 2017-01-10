package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CustomOutputStream extends OutputStream {
    private JLabel lab;
    private String text="";
    private JPanel pan;
    private Thread t=null;
    private int x=PartiePanel.WIDTH;
    private int y=50;
    private Color c=Color.blue;
    
    public CustomOutputStream(JLabel lab, Fenetre f) {
        this.lab = lab;
        pan=new JPanel(){
        	public void paintComponent(Graphics g){
            	Graphics2D g2d = (Graphics2D)g;
                g2d.setFont(new Font("Arial", Font.BOLD, 50));
                g2d.setPaint(c);
                g2d.drawString(lab.getText(), x, 50);
                
                if(!text.equals(lab.getText())){
                	text=lab.getText();
                	if(t!=null)
                		t.stop();
	                t = new Thread() {
	    	            public void run(){
	    	            	x=PartiePanel.WIDTH;
	    	            	pan.repaint();
	    	            	while(x>PartiePanel.WIDTH/2){
	    	            		x-=5;
	    	            		try{
	    	            			Thread.sleep(1);
	    	            			pan.repaint();
	    	            		} catch (InterruptedException e) {
		    						e.printStackTrace();
		    					}
	    	            		f.repaint();
	    	            	}
	    	            	while(x>PartiePanel.WIDTH/3){
		                		x--;
	    	            		try {
		    						Thread.sleep(15);
		    					} catch (InterruptedException e) {
		    						e.printStackTrace();
		    					}
		                    	f.repaint();
	    	            	}
	    	            	while(x>-100){
		                		x-=5;
	    	            		try {
		    						Thread.sleep(1);
		    					} catch (InterruptedException e) {
		    						e.printStackTrace();
		    					}
		                    	f.repaint();
	    	            	}
	    	            	text="";
	    	            	lab.setText("");
	                	}
	    	          };
	    	          t.start();
	        	}
        	}
        };
		pan.setBounds(0, 0, PartiePanel.WIDTH, PartiePanel.HEIGHT);
		pan.setOpaque(false);
		f.getContentPane().add(pan);
    }
     
    @Override
    public void write(int b) throws IOException {
        if(b=='\b')
    		lab.setText("");
    	else
    		lab.setText(lab.getText()+String.valueOf((char)b));
        
    }
}