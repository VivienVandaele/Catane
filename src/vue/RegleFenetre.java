package vue;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class RegleFenetre extends JDialog {
    public static final int WIDTH=((int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth())*2/3;
    public static final int HEIGHT=(int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private final JPanel contentPanel = new JPanel();
	private JButton menuBoutton;

	public RegleFenetre() {
		setBounds(100, 100, WIDTH+20, HEIGHT-100);
		setModal(true);
		setLocationRelativeTo(null);
		setTitle("Règles");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		



        ImageIcon page = new ImageIcon(new ImageIcon("images/regles/reglespage1.jpg").getImage().getScaledInstance(WIDTH, (int)((double)(WIDTH)*1.311)*4, Image.SCALE_DEFAULT));
        
		JLabel lab = new JLabel();
		lab.setIcon(page);
		lab.setBounds(200, 0, 1000, 500);
		
	    JScrollPane scroll = new JScrollPane();
	    scroll.setViewportView(lab);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(0, 0, WIDTH, HEIGHT-100);

	    //Add Textarea in to middle panel
	    contentPanel.add(scroll);
		
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}