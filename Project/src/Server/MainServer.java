package Server;

import Server.Server;

public class MainServer {

	public static void main(String[] args) {
		int portnum = 8818;
		Server server = new Server(portnum);
		
		server.start();

	}


}
