package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;


import clientProject.LoginWindow;
import clientProject.UserListPane;
import clientProject.chatClient;

public class LogOffMonitor implements ActionListener{
	private chatClient client;
	private UserListPane usp;
	private JFrame frame;
	private Connection conn;
	private Statement stmt;
	private String login;

	public LogOffMonitor(chatClient client, UserListPane usp, JFrame frame, Connection conn, Statement stmt, String login) {
		this.client = client;
		this.usp = usp;
		this.frame = frame;
		this.conn = conn;
		this.stmt = stmt;
		this.login = login;
	}
	
	@Override
	public void actionPerformed(ActionEvent a){
		int command = Integer.parseInt(a.getActionCommand());
		switch (command) {
		case 1:
			try{
				logOff();
			}catch(IOException e) {
				e.printStackTrace();
			}catch(SQLException e1) {
				e1.printStackTrace();
			}
			this.frame.setVisible(false);
			LoginWindow lw = new LoginWindow();
			break;
			
		case 2:
			try{
				logOff();
			}catch(IOException e) {
				e.printStackTrace();
			}catch(SQLException e1) {
				e1.printStackTrace();
			}
			this.frame.setVisible(false);
			//System.exit(0);
			break;
		}
	}
	
	public void logOff() throws IOException, SQLException{
		this.client.logoff();
		
		try {
			String sql_logoff = "UPDATE ClientInfo C SET C.Status = '0' WHERE C.Aid = '" + login + "'";
			PreparedStatement status = conn.prepareStatement(sql_logoff);
			status.executeUpdate();
			System.out.println("Logoff Successful");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
	}
}
