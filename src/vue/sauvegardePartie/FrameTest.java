package vue.sauvegardePartie;

import javax.swing.JFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;

public class FrameTest extends JFrame {

	public FrameTest() {
        super("Test keyListeners");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        
        JLabel lblAppuyezSurEchap = new JLabel("Appuyez sur Echap");
        lblAppuyezSurEchap.setBounds(164, 84, 137, 38);
        getContentPane().add(lblAppuyezSurEchap);
 
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                System.out.println(evt.getKeyChar()); // affiche la touche appuyée
                switch(evt.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE :
                        new DialogTest();
                        break;
                    case KeyEvent.VK_E :
                        System.out.println("Vous avez appuyé sur 'E'");
                        break;
                }
            }
        });
        this.setVisible(true);
    }
 
    public static void main(String[] args) {
        new FrameTest();
    }
}
