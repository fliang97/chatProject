package Monitor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

import javax.swing.*;

import org.apache.commons.lang3.StringUtils;

import clientProject.LoginWindow;
import clientProject.UserListPane;
import clientProject.chatClient;
import GUI.MainWindow;
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
			suw.launchSignUpWindow(client);
			break;
		}


		
	}

	private void doLogin() {
		
		String login = this.loginWindow.getLoginField().getText();
		String password = this.loginWindow.getJPasswordField().getText();	
		int accountlen = login.length();
		int passlen = password.length();
		if(accountlen < 8 || accountlen > 10) {
			JOptionPane.showMessageDialog(this, "Account length should be between 8 and 10, please retry");
			return;
		}
		if(!(login.matches("[0-9]+"))){
			JOptionPane.showMessageDialog(this, "Account should be composed with all digitals, please retry");
			return;
		}
		if(passlen < 8 || passlen >10) {
			JOptionPane.showMessageDialog(this, "Password length should be between 8 and 10, please retry");
			return;
		}
		try {
			
			if(client.login(login, password)) {
				MainWindow mw = new MainWindow(this.loginWindow, client, login);
				mw.launchMainWindow();
			}else {
				//show err message
				JOptionPane.showMessageDialog(this, "Invalid login/password");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}	
	}
	
	

}
