package chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Serveur_Console_Chat {
	private static int uniqueId;
	private ArrayList<ClientThread> al;
	private Serveur_Fentre_Chat sg;
	private SimpleDateFormat sdf;
	private static int port = 1099;
	private boolean keepGoing;
	String host = "10.6.1.73";
	
	public Serveur_Console_Chat(int port) {
		this(port, null);
	}
	
	public Serveur_Console_Chat(int port, Serveur_Fentre_Chat sg) {
		this.sg = sg;
		Serveur_Console_Chat.port = port;
		sdf = new SimpleDateFormat("HH:mm:ss");
		al = new ArrayList<ClientThread>();
	}
	
	public void start() {
		keepGoing = true;
		try 
		{
			ServerSocket serverSocket = new ServerSocket(port);

			while(keepGoing) 
			{
				display("Le serveur attend les clients sur le port " + port + ".");
				
				Socket socket = serverSocket.accept();  	
				if(!keepGoing)
					break;
				ClientThread t = new ClientThread(socket);  
				al.add(t);									
				t.start();
			}
			try {
				serverSocket.close();
				for(int i = 0; i < al.size(); ++i) {
					ClientThread tc = al.get(i);
					try {
					tc.sInput.close();
					tc.sOutput.close();
					tc.socket.close();
					}
					catch(IOException ioE) {
					}
				}
			}
			catch(Exception e) {
				display("Exception fermeture clients et serveur: " + e);
			}
		}
		catch (IOException e) {
            String msg = sdf.format(new Date()) + " Exception sur new ServerSocket: " + e + "\n";
			display(msg);
		}
	}		

	@SuppressWarnings("resource")
	protected void stop() {
		keepGoing = false;
	
		try {
			new Socket(host, port);
		}
		catch(Exception e) {
		}
	}

	private void display(String msg) {
		String time = sdf.format(new Date()) + " " + msg;
		if(sg == null)
			System.out.println(time);
		else
			sg.appendEvent(time + "\n");
	}
	
	private synchronized void broadcast(String message) {
		String time = sdf.format(new Date());
		String messageLf = time + " " + message + "\n";
		if(sg == null)
			System.out.print(messageLf);
		else
			sg.appendRoom(messageLf);  
	
		for(int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
			if(!ct.writeMsg(messageLf)) {
				al.remove(i);
				display("Deconnecté Client " + ct.username + " retiré de la liste.");
			}
		}
	}

	synchronized void remove(int id) {
		for(int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);
			if(ct.id == id) {
				al.remove(i);
				return;
			}
		}
	}
	

	public static void main(String[] args) {
		int portNumber = port;
		switch(args.length) {
			case 1:
				try {
					portNumber = Integer.parseInt(args[0]);
				}
				catch(Exception e) {
					System.out.println("Port invalide.");
					System.out.println("Recommencer avec le format: > java Server [portNumber]");
					return;
				}
			case 0:
				break;
			default:
				System.out.println("Recommencer avec le format: > java Server [portNumber]");
				return;
				
		}
		Serveur_Console_Chat server = new Serveur_Console_Chat(portNumber);
		server.start();
	}

	/** One instance of this thread will run for each client */
	class ClientThread extends Thread {
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		int id;
		String username;
		Message cm;
		String date;

		ClientThread(Socket socket) {
			id = ++uniqueId;
			this.socket = socket;
			System.out.println("Thread crée Object Input/Output Streams");
			try
			{
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput  = new ObjectInputStream(socket.getInputStream());
				username = (String) sInput.readObject();
				display(username + " just connected.");
			}
			catch (IOException e) {
				display("Exception création new Input/output Streams: " + e);
				return;
			}

			catch (ClassNotFoundException e) {
			}
            date = new Date().toString() + "\n";
		}

		public void run() {
			boolean keepGoing = true;
			while(keepGoing) {
				try {
					cm = (Message) sInput.readObject();
				}
				catch (IOException e) {
					display(username + " Exception lecture Streams: " + e);
					break;				
				}
				catch(ClassNotFoundException e2) {
					break;
				}
				String message = cm.getMessage();

				switch(cm.getType()) {

				case Message.MESSAGE:
					broadcast(username + ": " + message);
					break;
				case Message.LOGOUT:
					display(username + " deconnecté avec LOGOUT.");
					keepGoing = false;
					break;
				case Message.WHOISIN:
					writeMsg("Liste des utilisateurs connectés à " + sdf.format(new Date()) + "\n");
					for(int i = 0; i < al.size(); ++i) {
						ClientThread ct = al.get(i);
						writeMsg((i+1) + ") " + ct.username + " depuis " + ct.date);
					}
					break;
				}
			}

			remove(id);
			close();
		}
		
		private void close() {
			try {
				if(sOutput != null) sOutput.close();
			}
			catch(Exception e) {}
			try {
				if(sInput != null) sInput.close();
			}
			catch(Exception e) {};
			try {
				if(socket != null) socket.close();
			}
			catch (Exception e) {}
		}


		private boolean writeMsg(String msg) {
			if(!socket.isConnected()) {
				close();
				return false;
			}
			try {
				sOutput.writeObject(msg);
			}
			catch(IOException e) {
				display("Erreur envoie message à " + username);
				display(e.toString());
			}
			return true;
		}
	}
}

