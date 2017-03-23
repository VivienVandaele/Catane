package vue.sauvegardePartie;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Voleur;
import vue.Fenetre;

public class JDialogSave extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private Fenetre fenetre;

	/**
	 * Create the dialog.
	 */
	public JDialogSave(Fenetre f) {
		this.fenetre = f;
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblEntrerLeNom = new JLabel("Entrer le nom de la sauvegarde");
		lblEntrerLeNom.setBounds(112, 26, 194, 23);
		contentPanel.add(lblEntrerLeNom);
		
		textField = new JTextField();
		textField.setBounds(112, 77, 135, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				
				okButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
					save(textField.getText());
					dispose();
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				
				cancelButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
					dispose();
					}
				});
			}
			
		}
				
		this.setVisible(true);
	}
	public void save (String s){
		String sTest = new String("Voici un test");
		File f = new File (s+".txt");
		
		try
		{
		    ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (f));
		    oos.writeObject (fenetre.getController());
		    oos.writeObject (fenetre.getPartiePanel());
		    oos.writeObject (Voleur.emplacements);
		    oos.writeObject (Voleur.position);
		    oos.close();
		}
		catch (IOException exception)
		{
		    System.out.println ("Erreur lors de l'écriture : " + exception.getMessage());
		}
	
	}
	
}

