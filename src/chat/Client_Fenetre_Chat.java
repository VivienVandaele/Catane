package chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Client_Fenetre_Chat extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3132149233039179287L;
	private JLabel label;
	String textLabel1 = "Veuillez entrer votre pseudo ci-dessous: ";
	String textLabel2 = "Tapez votre message ci-dessous: ";
	String msgAccueil1 = "Bienvenue sur le chat de Catane!!\nEntrez votre pseudo et connectez-vous\n\n";
	
	private JTextField tf;
	String textTf1 = "Joueur Anonyme";
	
	private JTextField tfServer, tfPort;
	private JButton login, logout, whoIsIn;
	private JTextArea ta;
	private boolean connected = false;
	private Client_Console_Chat client;
		private static int defaultPort = 1099;
			
	private static String defaultHost = "localhost";
	
	Client_Fenetre_Chat(String host, int port) {

		super("Catane Chat");
		defaultPort = port;
		defaultHost = host;
		
				JPanel serverAndPort = new JPanel(new GridLayout(1,5, 1, 3));
				tfServer = new JTextField(host);
				tfPort = new JTextField("" + port);
				tfPort.setHorizontalAlignment(SwingConstants.RIGHT);

				serverAndPort.add(new JLabel("Adresse du serveur :  "));
				serverAndPort.add(tfServer);
				serverAndPort.add(new JLabel("Numéro du port: "));
				serverAndPort.add(tfPort);
				serverAndPort.add(new JLabel(""));
				add(serverAndPort, BorderLayout.NORTH);
				

				ta = new JTextArea(msgAccueil1, 50, 50);
				JPanel centerPanel = new JPanel(new GridLayout(1,1));
				centerPanel.add(new JScrollPane(ta));
				ta.setEditable(false);
				add(centerPanel, BorderLayout.CENTER);

				login = new JButton("Se connecter");
				login.addActionListener(this);
				logout = new JButton("Se déconnecter");
				logout.addActionListener(this);
				logout.setEnabled(false);		
				whoIsIn = new JButton("Liste d'utilisateurs connectés");
				whoIsIn.addActionListener(this);
				whoIsIn.setEnabled(false);		

				JPanel southPanel = new JPanel(new GridLayout(3,1));
				label = new JLabel(textLabel1, SwingConstants.CENTER);
				southPanel.add(label);
				tf = new JTextField(textTf1);
				tf.setBackground(Color.WHITE);
				southPanel.add(tf);
				
				JPanel jPbutton = new JPanel(new GridLayout(1,3));
				
				jPbutton.add(login);
				jPbutton.add(logout);
				jPbutton.add(whoIsIn);
				southPanel.add(jPbutton);
				add(southPanel, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
		tf.requestFocus();

	}

	void append(String str) {
		ta.append(str);
		ta.setCaretPosition(ta.getText().length() - 1);
	}

	void connectionFailed() {
		login.setEnabled(true);
		logout.setEnabled(false);
		whoIsIn.setEnabled(false);
		label.setText(textLabel1);
		tf.setText(textTf1);
		tfPort.setText("" + defaultPort);
		tfServer.setText(defaultHost);
		tfServer.setEditable(false);
		tfPort.setEditable(false);
		tf.removeActionListener(this);
		connected = false;
	}
		

	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o == logout) {
			client.sendMessage(new Message(Message.LOGOUT, ""));
			return;
		}
		if(o == whoIsIn) {
			client.sendMessage(new Message(Message.WHOISIN, ""));				
			return;
		}

		if(connected) {
			client.sendMessage(new Message(Message.MESSAGE, tf.getText()));				
			tf.setText("");
			return;
		}
		

		if(o == login) {
			String username = tf.getText().trim();
			ta.setText("Bienvenue "+username+"!!\n");
			if(username.length() == 0)
				return;
			String server = tfServer.getText().trim();
			if(server.length() == 0)
				return;
			String portNumber = tfPort.getText().trim();
			if(portNumber.length() == 0)
				return;
			int port = 0;
			try {
				port = Integer.parseInt(portNumber);
			}
			catch(Exception en) {
				return;   
			}

			client = new Client_Console_Chat(server, port, username, this);
			if(!client.start()) 
				return;
			tf.setText("");
			label.setText(textLabel2);
			connected = true;
			
			login.setEnabled(false);
			logout.setEnabled(true);
			whoIsIn.setEnabled(true);
			tfServer.setEditable(false);
			tfPort.setEditable(false);
			tf.addActionListener(this);
		}

	}

	public static void main(String[] args) {
		new Client_Fenetre_Chat(defaultHost, defaultPort);
	}
}
