package vue.sauvegardePartie;

import java.io.File;

import javax.swing.JFileChooser;

public class Importer_fichier {

	public JFileChooser p;
	public File f;
	
	public Importer_fichier(String n,String titre)
	{
		JFileChooser filechoose = new JFileChooser();
		filechoose.setCurrentDirectory(new File("."));  /* ouvrir la boite de dialogue dans répertoire courant */
		filechoose.setDialogTitle(titre); /* nom de la boite de dialogue */
		 
		filechoose.setFileSelectionMode(JFileChooser.FILES_ONLY); /* pour afficher seulement les répertoires */
		 
		String approve = new String(n); /* Le bouton pour valider l’enregistrement portera la mention Enregistrer */
		
		p=filechoose;
	}
	
}