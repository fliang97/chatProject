package Monitor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;

import javax.swing.*;

import clientProject.LoginWindow;
import clientProject.UserListPane;
import clientProject.chatClient;
import GUI.SignUpWindow;


public class LoginMonitor extends JFrame implements ActionListener{
	private LoginWindow loginWindow;
	private chatClient client;
	private Connection conn = null;
	private Statement stmt = null;
	
	public Connection getConn() {
		return conn;
	}
	
	public Statement getStmt() {
		return stmt;
	}
	
	public LoginMonitor(LoginWindow loginWindow, chatClient client) {
		this.loginWindow  = loginWindow;
		this.client = client;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		int command = Integer.parseInt(e.getActionCommand());
		switch(command) {
		case 1:
			
			doLogin();
			break;
			
		case 2:
			this.loginWindow.setVisible(false);
			SignUpWindow suw = new SignUpWindow();
			suw.launchSignUpWindow();
			break;
		}


		
	}

	private void doLogin() {
		
		
		
		String login = this.loginWindow.getLoginField().getText();
		String password = this.loginWindow.getJPasswordField().getText();
		
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/cs176bproject?";
		//String url = "jdbc:mysql://169.231.128.1:3306/cs176bproject?";
		String sqlName = "root";
		String sqlPassword = "fujie19970";
		try {
			Class.forName(driver);
			System.out.println("Connecting to database server");
	
			conn =  DriverManager.getConnection(url, sqlName, sqlPassword);
			System.out.println("Connect Successful");
			stmt = conn.createStatement();	
			
			
			//check if the account is existed
			String sql_getAccount = "SELECT Aid, Password FROM ClientInfo";
			int outbreak = 0;
			ResultSet rs1 = stmt.executeQuery(sql_getAccount);
			while(rs1.next()) {
				if(rs1.getString("Aid").equals(login)) {
					outbreak = 1;
				}
			}
			if(outbreak == 0) {
				JOptionPane.showMessageDialog(null, "Invalid account number ", "", JOptionPane.PLAIN_MESSAGE);
				return;
			}

			
			//check if the password is correct
			ResultSet rs2 = stmt.executeQuery(sql_getAccount);
			while(rs2.next()) {
				if(rs2.getString("Aid").equals(login)) {
					if(!password.equals(rs2.getString("Password"))){
						JOptionPane.showMessageDialog(null, "Invalid password number ", "", JOptionPane.PLAIN_MESSAGE);
						return;
					}
				}
			}			
			System.out.println("Login1 Successful");

				
			//here the client is logged in to the server
			String sql_status = "UPDATE ClientInfo C SET C.Status = '1' WHERE C.Aid = '" + login + "'";
			PreparedStatement status = conn.prepareStatement(sql_status);
			status.executeUpdate();
			System.out.println("Login Successful");
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
		
		
		
		try {
			if(client.login(login, password)) {
				UserListPane userListPane = new UserListPane(client, conn, stmt, login);
				userListPane.setPreferredSize(new Dimension(400, 500));
				JFrame frame = new JFrame("Users List");
				frame.setLayout(new FlowLayout());
				JLabel friendList = new JLabel("online friend: ");
				frame.add(friendList);
				frame.setResizable(false);
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(400, 800);
				
				frame.getContentPane().add(userListPane, BorderLayout.CENTER);
				
				JButton listFriend = new JButton("All Friend Status");
				AllFriendStatusMonitor afsw = new AllFriendStatusMonitor(login);
				listFriend.setActionCommand("1");
				listFriend.addActionListener(afsw);
				frame.add(listFriend);
				
				JButton addFriend = new JButton("Add Friend");
				frame.add(addFriend);
				
				JButton profile = new JButton("Edit Profile");
				frame.add(profile);
				
				JButton logOff = new JButton("Log Off");
				LogOffMonitor lm = new LogOffMonitor(client, userListPane, frame, conn, stmt, login);
				logOff.setActionCommand("1");
				logOff.addActionListener(lm);
				frame.add(logOff);
				
				JButton quit = new JButton("Quit");
				quit.setActionCommand("2");
				quit.addActionListener(lm);
				frame.add(quit);
				
				
				
				
				
				
				frame.setVisible(true);
				//bring up the user window
				loginWindow.setVisible(false);
			}else {
				//show err message
				JOptionPane.showMessageDialog(this, "Invalid login/password");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}	
	}
	
	

}
