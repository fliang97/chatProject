package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import GUI.SignUpWindow;
import clientProject.LoginWindow;
import clientProject.chatClient;


public class SignUpMonitor implements ActionListener{
	private LoginWindow lw;
	private SignUpWindow swu;
	private chatClient client;
	
	public SignUpMonitor(SignUpWindow swu, chatClient client) {
		this.swu = swu;
		this.client = client;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int command = Integer.parseInt(e.getActionCommand());
		switch (command) {
		case 1:
			//TO DO SIGN UP BUTTON
			String account = swu.getAccount().getText();
			String password = swu.getPassword().getText();
			String userName = swu.getUserName().getText();
			String profile = swu.getProfile().getText();
			
			String tokens = "signup " + account + " " + password + " " + userName + " " + profile + "\n";
			try {
				int accountlen = account.length();
				int passlen = password.length();
				if(accountlen < 8 || accountlen > 10) {
					JOptionPane.showMessageDialog(null, "Account length should be between 8 and 10, please retry");
					return;
				}
				if(!(account.matches("[0-9]+"))){
					JOptionPane.showMessageDialog(null, "Account should be composed with all digitals, please retry");
					return;
				}
				if(passlen < 8 || passlen >10) {
					JOptionPane.showMessageDialog(null, "Password length should be between 8 and 10, please retry");
					return;
				}
				if(!(password.matches("[0-9]+"))){
					JOptionPane.showMessageDialog(null, "Password should be composed with all digitals, please retry");
					return;
				}
				this.client.getOutputStream().write(tokens.getBytes());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		case 2:
			this.swu.setVisible(false);
			this.lw = new LoginWindow();
		}
	}
}
