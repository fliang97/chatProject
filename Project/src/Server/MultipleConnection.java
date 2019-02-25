package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.*;

import DB.GetClientInfoDB;
import DB.HandleAddFriendDB;
import DB.HandleBecomeFriendDB;
import DB.HandleDeleteFriendDB;
import DB.HandleFriendOfflineDB;
import DB.HandleGetFriendDB;
import DB.HandleLogOffDB;
import DB.LoginCheckDB;
import DB.SignUpDB;
import Server.Server;


public class MultipleConnection extends Thread{
	private Socket clientSocket;
	private Server server;
	private String login = null;
	private OutputStream outputStream;
	private HashSet<String> topicSet = new HashSet<>();
	private Connection conn;
	private Statement stmt;
	
	MultipleConnection(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	public MultipleConnection(Server server, Socket clientSocket, Connection conn, Statement stmt) {
		this.server = server;
		this.clientSocket = clientSocket;
		this.conn = conn;
		this.stmt = stmt;
	}
	
	public String getLogin() {
		return login;
	}
	
	public Socket getClientSocket() {
		return this.clientSocket;
	}
	
	private void handleLogoff() throws IOException, SQLException {

		
		HandleFriendOfflineDB hgfo = new HandleFriendOfflineDB(this.login, this.conn);
		HandleLogOffDB hdlo = new HandleLogOffDB(conn, stmt, this.login);
		if(hdlo.doLogOff()) {
			//send other online users current user's status
			List<MultipleConnection> connectionList = server.getConnectionList();
			String onlineMsg = "offline " + login + " " + hgfo.getUserName() + "\n";
			for(MultipleConnection mc : connectionList) {
				if(!login.equals(mc.getLogin())) {
					mc.send(onlineMsg);
				}
			}
			this.outputStream = clientSocket.getOutputStream();

			String msg = "succeed\n";
			outputStream.write(msg.getBytes());
			server.removeWorker(this);
			clientSocket.close();
		}else {
			String error = "System error, logoff failed.";
			outputStream.write(error.getBytes());
		}
	}
	
	@Override
	public void run() {
		try {
			enabledMultipleConnection();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void enabledMultipleConnection() throws IOException, InterruptedException, SQLException {
		InputStream inputStream = clientSocket.getInputStream();
		this.outputStream = clientSocket.getOutputStream();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String input;
		while( (input = reader.readLine()) != null) {
			System.out.println(input);
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
				}else if("signup".equalsIgnoreCase(cmd)) {
					handleSignUp(tokens);
				}else if("getfriend".equalsIgnoreCase(cmd)) {
					handleGetFriend(tokens);
				}else if("getfriendoffline".equalsIgnoreCase(cmd)) {
					handleGetFriendOffline(tokens);
				}else if("allfriendstatus".equalsIgnoreCase(cmd)) {
					handleAllFriendStatus(tokens);
				}else if("addfriend".equalsIgnoreCase(cmd)) {
					handleAddFriend(tokens);
				}else if("becomefriend".equalsIgnoreCase(cmd)) {
					handleBecomeFriend(tokens);
				}else if("deleteFriend".equalsIgnoreCase(cmd)) {
					handleDeleteFriend(tokens);
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
	
	private void handleDeleteFriend(String[] tokens) throws IOException {
		HandleDeleteFriendDB hdf = new HandleDeleteFriendDB(tokens, this.conn);
		boolean result = hdf.run();
		String msg = "";
		if(result == true) {
			msg = "deletefriendresult succeed\n";
		}else {
			msg = "deletefriendresult fail\n";
		}
		outputStream.write(msg.getBytes());
		
	}

	private void handleBecomeFriend(String[] tokens) throws SQLException {
		if(tokens[1].equalsIgnoreCase("succeed")) {
			HandleBecomeFriendDB hbf = new HandleBecomeFriendDB(tokens[2], tokens[3], this.conn);
			hbf.run();
		}
	}

	private void handleAddFriend(String[] tokens) throws SQLException, IOException {
		HandleAddFriendDB haf = new HandleAddFriendDB(tokens, this.conn);
		String result = haf.run();
		String result_1 = haf.run();
		if(result.equalsIgnoreCase("")) {
			result = "Addfriendresult\n";
		}else {
			result = "Addfriendresult " + result + "\n";
		}
		List<MultipleConnection> connectionList = server.getConnectionList();
		for(MultipleConnection mc : connectionList) {
			if(result_1.equalsIgnoreCase(mc.getLogin())) {
				String msg = "AddFriendRequest " + this.getLogin() + "\n";
				mc.send(msg);
			}
		}
		
		for(MultipleConnection mc : connectionList) {
			if(this.clientSocket == mc.getClientSocket())
			mc.send(result);
		}
		
	}

	private void handleAllFriendStatus(String[] tokens) throws SQLException, IOException {
		GetClientInfoDB gci = new GetClientInfoDB(this.conn);
		ArrayList<String> friendStatus = gci.getAllFriendStatus(tokens[1]);
		for(int i = 0; i < friendStatus.size(); i++) {
			String msg = "allfriendstatus " + friendStatus.get(i) + "\n";
			outputStream.write(msg.getBytes());
		}
		
	}

	private void handleGetFriendOffline(String[] tokens) throws IOException, SQLException{
		//HandleFriendOfflineDB hgfo = new HandleFriendOfflineDB(tokens, this.conn);
		//String msg = "getfriendoffline " + hgfo.getUserName() + "\n";
		//outputStream.write(msg.getBytes());
	}
	

	private void handleGetFriend(String[] tokens) throws IOException {
		HandleGetFriendDB hgf = new HandleGetFriendDB(tokens, this.conn);
		try {
			String userName = hgf.friendUserName();
			String msg = "getfriendonline " + userName + "\n";
			outputStream.write(msg.getBytes());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void handleSignUp(String[] tokens) throws SQLException, IOException {
		SignUpDB sudb = new SignUpDB(tokens, this.conn, this.stmt);
		int signal = sudb.signUp();
		if(signal == 0 ) {
			String msg = "signup 0";
			outputStream.write(msg.getBytes());
		}else if(signal == 1) {
			String msg = "signup 1";
			outputStream.write(msg.getBytes());
		}else if(signal == 2) {
			String msg = "signup 2";
			outputStream.write(msg.getBytes());
		}
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
		if(tokens.length > 1) {
			String topic = tokens[1];
			topicSet.add(topic);
		}
	}

	//format: "msg" "login" "message"
	//format: "msg" "#topic" "body"
	private void handleMessage(String[] tokens) throws IOException, SQLException{
		String sendTo = tokens[1];
		String body = tokens[2];
		System.out.println("sendto is: " + sendTo);
		
		boolean isTopic = sendTo.charAt(0) == '#';
		
		List<MultipleConnection> connectionList = server.getConnectionList();
		for(MultipleConnection mc : connectionList) {
			if(isTopic) {
				if(mc.isMemberOfTopic(sendTo)) {
					//String outMsg = "msg " + sendTo + ":" + login + " " + body + "\n";
					//System.out.println("1");
					//GetClientInfoDB gci = new GetClientInfoDB(sendTo, this.conn);
					//String login_1 = gci.getLogin();
					//String outMsg = "msg " + login_1 + " " + body + "\n";
					//System.out.println(outMsg);
					//mc.send(outMsg);
				}
			}else {
				GetClientInfoDB gci = new GetClientInfoDB(this.conn);
				String login_1 = gci.getLogin(sendTo);
				String sendFrom = gci.getuserName(login);
				if(login_1.equalsIgnoreCase(mc.getLogin())) {
					System.out.println("3");
					String outMsg = "msg " + login_1 + " " + sendFrom + " " + body + "\n";
					System.out.println(outMsg);
					mc.send(outMsg);
				}
			}
		}
	}
	

	private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException, SQLException{
		if(tokens.length == 3) {
			String login = tokens[1];
			String password = tokens[2];
			LoginCheckDB ldb = new LoginCheckDB(outputStream, tokens, this.conn, this.stmt);
			if(ldb.handleLogin()) {
			
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
				
			}else {
				String msg = "error login\n";
				outputStream.write(msg.getBytes());
				System.err.println("Login failed for " + login);
			}
			
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
