package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Server.MultipleConnection;



public class Server extends Thread{
	private final int serverPort;
	private ArrayList<MultipleConnection> connectionList = new ArrayList<>();
	
	public Server(int serverPort) {
		this.serverPort = serverPort;
	}
	
	public List<MultipleConnection> getConnectionList(){
		return connectionList;
	}
	
	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(serverPort);
			while(true) {
				System.out.println("about to accept connection");
				Socket clientSocket = serverSocket.accept();
				System.out.println("Accepted connection from " + clientSocket);
				MultipleConnection mc = new MultipleConnection(this, clientSocket);
				connectionList.add(mc);
				mc.start();
			} 
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public void removeWorker(MultipleConnection multipleConnection) {
		connectionList.remove(multipleConnection);
		
	}
}
