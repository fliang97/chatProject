package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.*;
import Server.Server;


public class MultipleConnection extends Thread{
	private Socket clientSocket;
	private Server server;
	private String login = null;
	private OutputStream outputStream;
	private HashSet<String> topicSet = new HashSet<>();
	
	MultipleConnection(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	public MultipleConnection(Server server, Socket clientSocket) {
		this.server = server;
		this.clientSocket = clientSocket;
	}
	
	public String getLogin() {
		return login;
	}
	
	private void handleLogoff() throws IOException {
		server.removeWorker(this);
		
		//send other online users current user's status
		List<MultipleConnection> connectionList = server.getConnectionList();
		String onlineMsg = "offline " + login + "\n";
		for(MultipleConnection mc : connectionList) {
			if(!login.equals(mc.getLogin())) {
				mc.send(onlineMsg);
			}
		}
		clientSocket.close();
	}
	
	@Override
	public void run() {
		try {
			enabledMultipleConnection();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void enabledMultipleConnection() throws IOException, InterruptedException {
		InputStream inputStream = clientSocket.getInputStream();
		this.outputStream = clientSocket.getOutputStream();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String input;
		while( (input = reader.readLine()) != null) {
			String[] tokens = StringUtils.split(input);
			if(tokens != null && tokens.length > 0) {
				String cmd = tokens[0];
				if("quit".equalsIgnoreCase(input) || "logoff".equalsIgnoreCase(input)) {
					handleLogoff();
					break;
				}else if("login".equalsIgnoreCase(cmd)){
					handleLogin(outputStream, tokens);
				}else if("msg".equalsIgnoreCase(cmd)) {
					String[] tokensMsg = StringUtils.split(input, null, 3);
					handleMessage(tokensMsg);
				}else if("join".equalsIgnoreCase(cmd)) {
					handleJoin(tokens);
				}else if("leave".equalsIgnoreCase(cmd)) {
					handleLeave(tokens);
				}
				else{
					String msg = "unkown" + cmd + "\n";
					outputStream.write(msg.getBytes());
				}
			//String msg = "you type: " + input + "\n";
			//outputStream.write(msg.getBytes());
			}
		}
		//clientSocket.close();
	}
	

	public boolean isMemberOfTopic(String topic) {
		return topicSet.contains(topic);
	}
	
	private void handleLeave(String[] tokens) {
		if(tokens.length > 1) {
			String topic = tokens[1];
			topicSet.remove(topic);
		}	
	}
	
	
	private void handleJoin(String[] tokens) {
		// TODO Auto-generated method stub
		if(tokens.length > 1) {
			String topic = tokens[1];
			topicSet.add(topic);
		}
	}

	//format: "msg" "login" "message"
	//format: "msg" "#topic" "body"
	private void handleMessage(String[] tokens) throws IOException{
		String sendTo = tokens[1];
		String body = tokens[2];
		
		boolean isTopic = sendTo.charAt(0) == '#';
		
		List<MultipleConnection> connectionList = server.getConnectionList();
		for(MultipleConnection mc : connectionList) {
			if(isTopic) {
				if(mc.isMemberOfTopic(sendTo)) {
					String outMsg = "msg " + sendTo + ":" + login + " " + body + "\n";
					mc.send(outMsg);
				}
			}else {
				if(sendTo.equalsIgnoreCase(mc.getLogin())) {
					String outMsg = "msg " + login + " " + body + "\n";
					mc.send(outMsg);
				}
			}
		}
	}
	

	private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException{
		if(tokens.length == 3) {
			String login = tokens[1];
			String password = tokens[2];
			
			//if(login.equals("guest1") && password.equals("guest1") || (login.equals("jim1") && password.equals("jim1"))) {
				String msg = "ok login\n";
				outputStream.write(msg.getBytes());
				this.login = login;
				System.out.println("User logged in successfully: + " + login);
				
				List<MultipleConnection> connectionList = server.getConnectionList();
				
				//send current user all other online users
				for(MultipleConnection mc : connectionList ) {
					if(mc.getLogin() != null) {
						if(!login.equals(mc.getLogin())) {
							String msg2 = "online " + mc.getLogin() + "\n";
							send(msg2);
						}
					}
				}
				
				String onlineMsg = "online " + login + "\n"; 
				for(MultipleConnection mc : connectionList) {
					if(!login.equals(mc.getLogin())) {
						mc.send(onlineMsg);
					}
				}
				
			/*}else {
				String msg = "error login\n";
				outputStream.write(msg.getBytes());
				System.err.println("Login failed for " + login);
			}*/
			
		}
	}
	
	public void send(String onlineMsg) throws IOException{
		if(login != null) {
			//if(!login.equals(currentUser)) {
				outputStream.write(onlineMsg.getBytes());
			//}
		}
	}
}
