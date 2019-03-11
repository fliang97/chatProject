package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import Listener.AddFriendRequestListener;
import Listener.SendFileListener;
import Monitor.AddFriendMonitor;
import Monitor.AllFriendStatusMonitor;
import Monitor.DeleteFriendMonitor;
import Monitor.EditAccountInfoMonitor;
import Monitor.GroupActionMonitor;
import Monitor.HandShakeMonitor;
import Monitor.LogOffMonitor;
import Monitor.SendFileMonitor;
import clientProject.LoginWindow;
import clientProject.UserListPane;
import clientProject.chatClient;


public class MainWindow extends JFrame implements AddFriendRequestListener, SendFileListener{
	private LoginWindow lw;
	private chatClient client;
	private String login;
	private String file_path;
	private String receiveFilePath = "/Users/liangfujie/Downloads/cs176b/receive/";

	public MainWindow(LoginWindow lw, chatClient client, String login) {
		this.lw = lw;
		this.client = client;
		this.login = login;
	}
	
	public void setFile_path(String s_1) {
		file_path = s_1;
	}
	
	public LoginWindow getLoginWindow() {
		return this.lw;
	}
	public String getLogin() {
		return login;
	}
	
	public void launchMainWindow(){
		
		UserListPane userListPane = new UserListPane(this.client, login);
		userListPane.setPreferredSize(new Dimension(400, 500));
		this.setTitle("Users List");
		this.setLayout(new FlowLayout());
		JLabel friendList = new JLabel("online friend: ");
		this.add(friendList);
		this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 800);
		
		this.getContentPane().add(userListPane, BorderLayout.CENTER);
		
		client.addAddFriendRequestListener(this);
		client.addSendFileListener(this);
		
		JButton listFriend = new JButton("All Friend Status");
		AllFriendStatusMonitor afsw = new AllFriendStatusMonitor(login, client);
		listFriend.setActionCommand("1");
		listFriend.addActionListener(afsw);
		this.add(listFriend);
		
		JButton handShake = new JButton("HandShake Now!");
		HandShakeMonitor hsm = new HandShakeMonitor(this, this.client);
		handShake.addActionListener(hsm);
		this.add(handShake);
		
		JButton group = new JButton("Group");
		GroupActionMonitor gam = new GroupActionMonitor(this, client);
		group.addActionListener(gam);
		this.add(group);
		
		
		JButton addFriend = new JButton("Add Friend");
		AddFriendMonitor afm = new AddFriendMonitor(client);
		addFriend.addActionListener(afm);
		this.add(addFriend);
		
		JButton deleteFriend = new JButton("Delete Friend");
		DeleteFriendMonitor dfm = new DeleteFriendMonitor(userListPane, client, this.login);
		deleteFriend.addActionListener(dfm);
		this.add(deleteFriend);
		
		JButton sendFile = new JButton("Send File");
		SendFileMonitor sfm = new SendFileMonitor(userListPane, client, this.login, this);
		sendFile.addActionListener(sfm);
		this.add(sendFile);
		
		JButton profile = new JButton("Edit Account Info");
		EditAccountInfoMonitor eaim = new EditAccountInfoMonitor(this, this.client);
		profile.addActionListener(eaim);
		this.add(profile);
		
		
		JButton logOff = new JButton("Log Off");
		LogOffMonitor lm = new LogOffMonitor(this.client, userListPane, this, login);
		logOff.setActionCommand("1");
		logOff.addActionListener(lm);
		this.add(logOff);
		
		JButton quit = new JButton("Quit");
		quit.setActionCommand("2");
		quit.addActionListener(lm);
		this.add(quit);
	
		

		
		
		
		
		this.setVisible(true);
		//bring up the user window
		this.lw.setVisible(false);
	}
	
	@Override
	public void evokeRequestWindow(String fromlogin) {
		System.out.println("hahaha i am here");
		String msg = fromlogin + " is sending a friend request to you, do you want to be friend with him/her?";
		int reply = JOptionPane.showConfirmDialog(null,
				msg, "Friend Request", JOptionPane.YES_NO_OPTION);
		String outmsg = "becomefriend fail\n";;
		if(reply == JOptionPane.YES_OPTION) {
			 outmsg = "becomefriend succeed " + fromlogin + " " + this.login + "\n";
		}
		try {
			this.client.getOutputStream().write(outmsg.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void evokeDeleteResultWindow(String result) {
		if(result.equalsIgnoreCase("succeed")) {
			JOptionPane.showMessageDialog(this, "Delete Successful");
		}else {
			JOptionPane.showMessageDialog(this, "Delete Failed");
		}
	}
	
	@Override 
	public void sendFileRequest(String token) {
	}
	
	@Override 
	public void returnFileRequest(String[] tokens) {
		String msg = tokens[1] + " is trying to send a file to you. Accept? ";
		int reply = JOptionPane.showConfirmDialog(null,
				msg, "SendFile", JOptionPane.YES_NO_OPTION);
		if(reply == JOptionPane.YES_OPTION) {
			
			/*JFileChooser fileChooser = new JFileChooser();
			JDialog dialog = new JDialog();  

			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			              fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			              int result = fileChooser.showOpenDialog(dialog);
			              if (result == JFileChooser.APPROVE_OPTION) {
			                  File selectedFile = fileChooser.getSelectedFile();
			                  receiveFilePath = selectedFile.getAbsolutePath()+"/";
			              }else{
			                  System.out.println("Cancelled");
			              }*/
			/*JButton open = new JButton();
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("SELECT YOUT FILE TO BE SENT");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
				
			}
			receiveFilePath = fc.getSelectedFile().getAbsolutePath();*/
			
			//JFileChooser fc = new JFileChooser();
			//fc.setDialogTitle("SELECT YOUT FILE TO BE SENT");
			/*JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Choose a directory to save your file: ");
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			int returnValue = jfc.showSaveDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				if (jfc.getSelectedFile().isDirectory()) {
					System.out.println("You selected the directory: " + jfc.getSelectedFile());
				}
			}*/
			
			
			msg = "confirmsend " + this.login + " " + tokens[2] + " " + tokens[3] + "\n";
			try {
				this.client.getOutputStream().write(msg.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void startSendFile() {
		int SOCKET_PORT = 8000;
		String SERVER = "127.0.0.1";
	    FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    OutputStream os = null;
	    Socket sock = null;
	    try {
	        System.out.println("Waiting...");
	        try {
	          sock = new Socket(SERVER, SOCKET_PORT);
	          System.out.println("Accepted connection : " + sock);
	          // send file
	          File myFile = new File (file_path);
	          byte [] mybytearray  = new byte [(int)myFile.length()];
	          fis = new FileInputStream(myFile);
	          bis = new BufferedInputStream(fis);
	          bis.read(mybytearray,0,mybytearray.length);
	          os = sock.getOutputStream();
	          System.out.println("Sending " + file_path + "(" + mybytearray.length + " bytes)");
	          os.write(mybytearray,0,mybytearray.length);
	          os.flush();
	          System.out.println("Done.");
	        }
	        finally {
	          if (bis != null) bis.close();
	          if (os != null) os.close();
	          if (sock!=null) sock.close();
	        }
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	@Override
	public void startReceiveFile(String tokens[]) {
		int bytesRead;
	    int current = 0;
	    FileOutputStream fos = null;
	    BufferedOutputStream bos = null;
	    Socket sock = null;
	    int FILE_SIZE = 95551830;
	    receiveFilePath = receiveFilePath  + "test" + tokens[1];
	    try {
	        sock = new Socket("127.0.0.1", 8000);
	        System.out.println("Connecting...");
	        byte [] mybytearray  = new byte [FILE_SIZE];
	        InputStream is = sock.getInputStream();
	        fos = new FileOutputStream(receiveFilePath);
	        bos = new BufferedOutputStream(fos);
	        bytesRead = is.read(mybytearray,0,mybytearray.length);
	        current = bytesRead;

	        do {
	           bytesRead =
	              is.read(mybytearray, current, (mybytearray.length-current));
	           if(bytesRead >= 0) current += bytesRead;
	        } while(bytesRead > -1);

	        bos.write(mybytearray, 0 , current);
	        bos.flush();
	        System.out.println("File " + receiveFilePath
	            + " downloaded (" + current + " bytes read)");
	      }catch(Exception e){
	    	  e.printStackTrace();
	      }finally {
	    	  try {
	    		  if (fos != null) fos.close();
	    		  if (bos != null) bos.close();
	    		  if (sock != null) sock.close();
	    	  }catch(IOException ex) {
	    		  ex.printStackTrace();
	    	  }
	        }
	}
	
	@Override
	public void HandShake(String UserName, String Nation, String PrefLang, String aid) {
		String msg = UserName + " from " + Nation + " is handshaking with you.\nHe/She speaks " + PrefLang + "\n"; 
		int reply = JOptionPane.showConfirmDialog(null,
				msg, "HandShake", JOptionPane.YES_NO_OPTION);
		if(reply == JOptionPane.YES_OPTION) {
			msg = "addfriend " + aid + "\n";
			try {
				this.client.getOutputStream().write(msg.getBytes());
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
