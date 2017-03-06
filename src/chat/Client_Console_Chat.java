package chat;

import java.net.*;
import java.io.*;
import java.util.*;


public class Client_Console_Chat  {

	private ObjectInputStream sInput;	
	private ObjectOutputStream sOutput;	
	private Socket socket;

	private Client_Fenetre_Chat cg;
	
	private String server;
	private String username;
	private static int port = 1099;
	static String host = "10.6.1.73";

	Client_Console_Chat(String server, int port, String username) {
		this(server, port, username, null);
	}


	Client_Console_Chat(String server, int port, String username, Client_Fenetre_Chat cg) {
		this.server = server;
		Client_Console_Chat.port = port;
		this.username = username;
		this.cg = cg;
	}
	
	public boolean start() {
		try {
			socket = new Socket(server, port);
		} 
		catch(Exception ec) {
			display("Erreur de connexion au serveur:" + ec);
			return false;
		}
		
		String msg = "Connexion acceptée sur " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);
	
		try
		{
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			display("Exception creation new Input/output Streams: " + eIO);
			return false;
		}

		new ListenFromServer().start();
		
		try
		{
			sOutput.writeObject(username);
		}
		catch (IOException eIO) {
			display("Exceptionlrs du login : " + eIO);
			disconnect();
			return false;
		}
		return true;
	}

	private void display(String msg) {
		if(cg == null)
			System.out.println(msg);      
		else
			cg.append(msg + "\n");		
	}
	
	
	void sendMessage(Message msg) {
		try {
			sOutput.writeObject(msg);
		}
		catch(IOException e) {
			display("Exception écriture au serveur: " + e);
		}
	}

	
	private void disconnect() {
		try { 
			if(sInput != null) sInput.close();
		}
		catch(Exception e) {} 
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(Exception e) {} 
        try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {} 
		
		if(cg != null)
			cg.connectionFailed();
			
	}
	
	public static void main(String[] args) {
		int portNumber = port;
		String serverAddress = host;
		String userName = "Joueur Anonyme";

		switch(args.length) {
			case 3:
				serverAddress = args[2];
			case 2:
				try {
					portNumber = Integer.parseInt(args[1]);
				}
				catch(Exception e) {
					System.out.println("Port invalide.");
					System.out.println("Recommencer avec le format suivant: > java Client [username] [portNumber] [serverAddress]");
					return;
				}
			case 1: 
				userName = args[0];
			case 0:
				break;
			default:
				System.out.println("Recommencer avec le format suivant: > java Client [username] [portNumber] {serverAddress]");
			return;
		}
		Client_Console_Chat client = new Client_Console_Chat(serverAddress, portNumber, userName);
	
		if(!client.start())
			return;
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		while(true) {
			System.out.print("> ");
			String msg = scan.nextLine();
			if(msg.equalsIgnoreCase("LOGOUT")) {
				client.sendMessage(new Message(Message.LOGOUT, ""));
				break;
			}
			else if(msg.equalsIgnoreCase("WHOISIN")) {
				client.sendMessage(new Message(Message.WHOISIN, ""));				
			}
			else {				
				client.sendMessage(new Message(Message.MESSAGE, msg));
			}
		}
		client.disconnect();	
	}

	class ListenFromServer extends Thread {

		public void run() {
			while(true) {
				try {
					String msg = (String) sInput.readObject();
					if(cg == null) {
						System.out.println(msg);
						System.out.print("> ");
					}
					else {
						cg.append(msg);
					}
				}
				catch(IOException e) {
					display("Le serveur a arrété l'échange: " + e);
					if(cg != null) 
						cg.connectionFailed();
					break;
				}
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}
