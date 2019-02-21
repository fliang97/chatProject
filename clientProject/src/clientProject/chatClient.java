package clientProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import org.apache.commons.lang3.*;

public class chatClient {
	private final String serverName;
	private final int serverPort;
	private Socket socket;
	private InputStream serverIn;
	private OutputStream serverOut;
	private BufferedReader bufferedIn;
	
	private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
	private ArrayList<MessageListener> messageListeners = new ArrayList<>();
	
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
	
	private void readMessageLoop() {
		try {
			String line;
			while((line = bufferedIn.readLine()) != null) {
				String[] tokens = StringUtils.split(line);
				if(tokens != null && tokens.length > 0) {
					String cmd = tokens[0];
					if("online".equalsIgnoreCase(cmd)) {
						handleOnline(tokens);
					}else if("offline".equalsIgnoreCase(cmd)) {
						handleOffline(tokens);
					}else if("msg".equalsIgnoreCase(cmd)) {
						String[] tokensMsg = StringUtils.split(line, null, 3);
						handleMessage(tokensMsg);
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
	
	private void handleMessage(String[] tokensMsg) {
		String login = tokensMsg[1];
		String msg = tokensMsg[2];
		
		for(MessageListener listener : messageListeners) {
			listener.onMessage(login, msg);
		}
	}
	
	private void handleOnline(String[] tokens) {
		String login = tokens[1];
		for(UserStatusListener listener : userStatusListeners) {
			listener.online(login);
		}
	}
	
	private void handleOffline(String[] tokens) {
		String login = tokens[1];
		for(UserStatusListener listener : userStatusListeners) {
			listener.offline(login);
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
}
