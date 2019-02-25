package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Monitor.SignUpMonitor;
import clientProject.chatClient;

public class SignUpWindow extends JFrame{
	private JTextField account;
	private JTextField password;
	private JTextField userName;
	private JTextField profile;
	
	public JTextField getAccount() {
		return account;
	}
	
	public JTextField getPassword() {
		return password;
	}
	
	public JTextField getUserName() {
		return userName;
	}
	
	public JTextField getProfile() {
		return profile;
	}
	
	public void launchSignUpWindow(chatClient client) {
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		this.setTitle("chatMate");
		this.setLayout(new FlowLayout());
		this.setSize(200, 270);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		JLabel userLabel = new JLabel("Account: ");
		JLabel pwdLabel = new JLabel("Password: ");
		this.add(userLabel);
		Dimension dim = new Dimension(160, 20);
		this.account = new JTextField();
		this.account.setPreferredSize(dim);
		this.add(this.account);
		this.add(pwdLabel);
		this.password = new JPasswordField();
		this.password.setPreferredSize(dim);
		this.add(this.password);
		JLabel nameLabel = new JLabel("UserName: ");
		JLabel profileLabel = new JLabel("Profile: ");
		this.userName = new JTextField();
		this.profile = new JTextField();
		userName.setPreferredSize(dim);
		profile.setPreferredSize(dim);
		this.add(nameLabel);
		this.add(userName);
		this.add(profileLabel);
		this.add(profile);
		
		JButton signUp = new JButton("Confirm");
		SignUpMonitor sum = new SignUpMonitor(this, client);
		signUp.setActionCommand("1");
		signUp.addActionListener(sum);
		this.add(signUp);
		JButton back = new JButton("Back");
		back.setActionCommand("2");
		back.addActionListener(sum);
		this.add(back);
		
		this.setVisible(true);
	}
}
