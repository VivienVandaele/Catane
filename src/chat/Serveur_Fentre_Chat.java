package chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Serveur_Fentre_Chat extends JFrame implements ActionListener, WindowListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1879607109486402443L;
	private JButton stopStart;
	private JTextArea chat, event;
	private JTextField tPortNumber;
	private Serveur_Console_Chat server;
	String stopStartPos1 = "Démarrer le serveur";
	String stopStartPos2 = "Arrêter le serveur";

	
	
	Serveur_Fentre_Chat(int port) {
		super("Catane - Serveur de Chat");
		server = null;
		JPanel north = new JPanel();
		north.add(new JLabel("Numéro du port: "));
		tPortNumber = new JTextField("  " + port);
		north.add(tPortNumber);
		stopStart = new JButton(stopStartPos1);
		stopStart.addActionListener(this);
		north.add(stopStart);
		add(north, BorderLayout.NORTH);
		
		JPanel center = new JPanel(new GridLayout(2,1));
		chat = new JTextArea(80,80);
		chat.setEditable(false);
		appendRoom("Chat:\n");
		center.add(new JScrollPane(chat));
		event = new JTextArea(80,80);
		event.setEditable(false);
		appendEvent("Evenements:\n");
		center.add(new JScrollPane(event));	
		add(center);
		
		addWindowListener(this);
		setSize(400, 600);
		setVisible(true);
	}		


	void appendRoom(String str) {
		chat.append(str);
		chat.setCaretPosition(chat.getText().length() - 1);
	}
	void appendEvent(String str) {
		event.append(str);
		event.setCaretPosition(chat.getText().length() - 1);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(server != null) {
			server.stop();
			server = null;
			tPortNumber.setEditable(true);
			stopStart.setText(stopStartPos1);
			return;
		}
		int port;
		try {
			port = Integer.parseInt(tPortNumber.getText().trim());
		}
		catch(Exception er) {
			appendEvent("Numéro de port invalide");
			return;
		}
		server = new Serveur_Console_Chat(port, this);
		new ServerRunning().start();
		stopStart.setText(stopStartPos2);
		tPortNumber.setEditable(false);
	}
	
	public static void main(String[] arg) {
		new Serveur_Fentre_Chat(1099);
	}

	
	public void windowClosing(WindowEvent e) {
		if(server != null) {
			try {
				server.stop();			
			}
			catch(Exception eClose) {
			}
			server = null;
		}
		dispose();
		System.exit(0);
	}
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

	class ServerRunning extends Thread {
		public void run() {
			server.start();        
			stopStart.setText(stopStartPos1);
			tPortNumber.setEditable(true);
			appendEvent("Le serveur s'est arrêté\n");
			server = null;
		}
	}

}
