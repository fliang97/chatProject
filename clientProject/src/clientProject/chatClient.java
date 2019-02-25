package clientProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import org.apache.commons.lang3.*;

import Listener.AddFriendRequestListener;
import Listener.AddFriendWindowListener;
import Listener.AllFriendStatusListener;
import Monitor.LogOffMonitor;

public class chatClient {
	private final String serverName;
	private final int serverPort;
	private Socket socket;
	private InputStream serverIn;
	private OutputStream serverOut;
	private BufferedReader bufferedIn;
	private String result = "";
	
	private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
	private ArrayList<MessageListener> messageListeners = new ArrayList<>();
	private ArrayList<AllFriendStatusListener>  allFriendStatusListener = new ArrayList<>();
	private ArrayList<AddFriendWindowListener>  addFriendWindowListener = new ArrayList<>();
	private ArrayList<AddFriendRequestListener>  addFriendRequestListener = new ArrayList<>();
	
	
	
	public InputStream getServerIn() {
		return serverIn;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public String getResult() {
		return result;
	}
	
	public OutputStream getOutputStream() {
		return serverOut;
	}
	
	public BufferedReader getBufferedReader() {
		return this.bufferedIn;
	} 
	
	public chatClient(String serverName, int serverPort) {
		this.serverName = serverName;
		this.serverPort = serverPort;
	}
	
	public static void main(String[] args) throws IOException {
		chatClient client = new chatClient("localhost", 8818);
		client.addUserStatusListener(new UserStatusListener(){
			@Override
			public void online(String login) {
				System.out.println("ONLINE " + login);
			}
			
			@Override
			public void offline(String login) {
				System.out.println("OFFLINE " + login);
			}
		});

		client.addMessageListener(new MessageListener(){
			@Override
			public void onMessage(String fromLogin, String msgBody) {
				System.out.println("you got the message from " + fromLogin + " ====> " + msgBody);
			}
		});
		
		client.addAllFriendStatusListener(new AllFriendStatusListener() {
			@Override
			public void showFriendStatus(String information) {
				System.out.println("Client Information: " + information);
			}
		});
		
		client.addAddFriendWindowListener(new AddFriendWindowListener() {
			@Override
			public void showWindow(String result) {}
		});
		
		client.addAddFriendRequestListener(new AddFriendRequestListener(){
			@Override
			public void evokeRequestWindow(String fromlogin) {}
			
			public void evokeDeleteResultWindow(String result) {}
		});
		
		if(!client.connect()) {
			System.out.println("connection failed");
		}else {
			System.err.println("Login Failed");	
			//client.logoff();
		}
	}
	
	public void msg(String sendTo, String message) throws IOException {
		String cmd = "msg " + sendTo + " " + message + "\n"; 
		serverOut.write(cmd.getBytes());
	}
	
	public void logoff() throws IOException{
		String cmd = "logoff\n";
		serverOut.write(cmd.getBytes());
				
	}
	

	
	public boolean login(String login, String password) throws IOException {
		// TODO Auto-generated method stub
		String cmd = "login " + login + " " + password + "\n";
		serverOut.write(cmd.getBytes());
		
		String response = bufferedIn.readLine();
		System.out.println("Response Line:" + response);
		
		if("ok login".equalsIgnoreCase(response)) {
			startMessageReader();
			return true;
		}else {
			return false;
		}
	}

	public void startMessageReader() {
		// TODO Auto-generated method stub
		Thread t = new Thread() {
			@Override
			public void run() {
				readMessageLoop();
			}
		}; 
		t.start();
	}
	
	
	public void readMessageLoop() {
		try {
			String line;
			while((line = bufferedIn.readLine()) != null) {
				System.out.println("Received: " + line);
				String[] tokens = StringUtils.split(line);
				if(tokens != null && tokens.length > 0) {
					String cmd = tokens[0];
					if("online".equalsIgnoreCase(cmd)) {
						handleOnline(tokens);
					}else if("offline".equalsIgnoreCase(cmd)) {
						handleOffline(tokens);
					}else if("msg".equalsIgnoreCase(cmd)) {
						System.out.println("received message");
						String[] tokensMsg = StringUtils.split(line, null, 4);
						handleMessage(tokensMsg);
					}else if("succeed".equalsIgnoreCase(cmd)) {
						break;
					}else if("getfriendoffline".equalsIgnoreCase(cmd)) {
						this.result = tokens[1];
					}else if("allfriendstatus".equalsIgnoreCase(cmd)) {
						String[] tokensMsg = StringUtils.split(line, null, 4);
						HandleAllFriendStatus(tokensMsg);
					}else if("Addfriendresult".equalsIgnoreCase(cmd)) {
						returnAddFriendResult(tokens);
					}else if("AddFriendRequest".equalsIgnoreCase(cmd)) {
						returnIfFriend(tokens);
					}else if("deletefriendresult".equalsIgnoreCase(cmd)) {
						promtSucceedPane(tokens);
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			try {
				socket.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	


	private void promtSucceedPane(String[] tokens) {
		for(AddFriendRequestListener listener : addFriendRequestListener) {
			listener.evokeDeleteResultWindow(tokens[1]);
		}
		
	}

	private void returnIfFriend(String[] tokens) {
		for(AddFriendRequestListener listener : addFriendRequestListener) {
			listener.evokeRequestWindow(tokens[1]);
		}
	}

	private void returnAddFriendResult(String[] tokens) {
		String result = "";
		if(tokens.length == 1) {
			result = "****fail****";
		}else {
			result = tokens[1];
		}
		for(AddFriendWindowListener listener : addFriendWindowListener) {
			listener.showWindow(result);
		}
		
	}

	private void HandleAllFriendStatus(String[] tokensMsg) {
		// TODO Auto-generated method stub
		String login = tokensMsg[1];
		login = login + " " + tokensMsg[2] + " " + tokensMsg[3];
		System.out.println("login: " + login);
		System.out.println("AllFriendStatus size: " + allFriendStatusListener.size());
		for(AllFriendStatusListener listener : allFriendStatusListener) {
			listener.showFriendStatus(login);
		}
		
	}

	private void handleMessage(String[] tokensMsg) {
		String login = tokensMsg[1];
		String userName = tokensMsg[2];
		String msg = tokensMsg[3];
		//String msg = tokensMsg[2];
		
		for(MessageListener listener : messageListeners) {
			listener.onMessage(userName, msg);
		}
	}
	
	private void handleOnline(String[] tokens) {
		String login = tokens[1];
		for(UserStatusListener listener : userStatusListeners) {
			listener.online(login);
		}
	}
	
	private void handleOffline(String[] tokens) {
		String userName = tokens[2];
		for(UserStatusListener listener : userStatusListeners) {
			listener.offline(userName);
		}
	}

	public boolean connect() {
		try {
			this.socket = new Socket(serverName, serverPort);
			System.out.println("Client port is " + socket.getLocalPort());
			this.serverOut = socket.getOutputStream();
			this.serverIn = socket.getInputStream();
			this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
			return true;
		} catch(IOException e){
			e.printStackTrace();
		}
		return false;
	}
		
	public void addUserStatusListener(UserStatusListener listener) {
		userStatusListeners.add(listener);
	}
	
	public void removeUserStatusListener(UserStatusListener listener) {
		userStatusListeners.remove(listener);
	}
	
	public void addMessageListener(MessageListener listener) {
		messageListeners.add(listener);
	}
	
	public void removeMessageListener(MessageListener listener) {
		messageListeners.remove(listener);
	}
	
	public void addAllFriendStatusListener(AllFriendStatusListener listener) {
		allFriendStatusListener.add(listener);
	}
	
	public void removeAllFriendStatusListener(AllFriendStatusListener listener) {
		allFriendStatusListener.remove(listener);
	}
	
	public void addAddFriendWindowListener(AddFriendWindowListener listener) {
		addFriendWindowListener.add(listener);
	}
	
	public void removeAddFriendWindowListener(AddFriendWindowListener listener) {
		addFriendWindowListener.remove(listener);
	}
	
	public void addAddFriendRequestListener(AddFriendRequestListener listener) {
		addFriendRequestListener.add(listener);
	}
	
	public void removeAddFriendRequestListener(AddFriendRequestListener listener) {
		addFriendRequestListener.remove(listener);
	}

}
