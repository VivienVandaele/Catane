package vue.sauvegardePartie;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;

public class DialogTest extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the dialog.
	 */
	public DialogTest() {
		
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblVousAvezAppuy = new JLabel("Vous avez appuy\u00E9 sur echap");
			lblVousAvezAppuy.setBounds(22, 11, 191, 26);
			contentPanel.add(lblVousAvezAppuy);
		}
		
		JButton btnNewButton = new JButton("Menu principal");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(131, 48, 171, 23);
		contentPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Options");
		btnNewButton_1.setBounds(131, 82, 171, 23);
		contentPanel.add(btnNewButton_1);
		
		JButton btnQuitterLeMenu = new JButton("Quitter le menu");
		btnQuitterLeMenu.setBounds(131, 184, 171, 23);
		contentPanel.add(btnQuitterLeMenu);
		
		JButton btnNewButton_2 = new JButton("Sauvegarder");
		btnNewButton_2.setBounds(131, 116, 171, 23);
		contentPanel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Charger");
		btnNewButton_3.setBounds(131, 150, 171, 23);
		contentPanel.add(btnNewButton_3);
		
		btnNewButton_2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new JDialogSave();
			}
		});
		
		btnNewButton_3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//new JDialogLoad();
				Importer_fichier test = new Importer_fichier("Charger","Charger une partie");
int result=test.p.showOpenDialog(null);
				
				if(result==test.p.APPROVE_OPTION)
				{
					
						File f=test.p.getSelectedFile();
						
						load(f);		
			}
			}
		});
		
		btnQuitterLeMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			dispose();	
			}
		});		
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		try {
			DialogTest dialog = new DialogTest();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void load (File f){
		
		try
		{
		    ObjectInputStream ois = new ObjectInputStream (new FileInputStream (f));
		    String s2 = (String) ois.readObject();
		    ois.close();
		 
		    System.out.println (s2);
		}
		catch (ClassNotFoundException exception)
		{
		    System.out.println ("Impossible de lire l'objet : " + exception.getMessage());
		}
		catch (IOException exception)
		{
		    System.out.println ("Erreur lors de l'écriture : " + exception.getMessage());
		}
	}
	
}