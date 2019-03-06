package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import Listener.AddFriendRequestListener;
import Listener.SendFileListener;
import Monitor.AddFriendMonitor;
import Monitor.AllFriendStatusMonitor;
import Monitor.GroupMonitor;
import Monitor.DeleteFriendMonitor;
import Monitor.EditAccountInfoMonitor;
import Monitor.GroupActionMonitor;
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
}
