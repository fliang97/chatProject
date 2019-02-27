package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Listener.EditProfileResultListener;
import Monitor.ConfirmEditInfoMonitor;
import clientProject.chatClient;

public class EditAccountWindow extends JFrame implements EditProfileResultListener{
	private MainWindow mw;
	private chatClient client;
	private JTextField passwordField, userNameField, profileField;
	
	public JTextField getPassword() {
		return passwordField;
	}
	
	public JTextField getUserName() {
		return userNameField;
	}
	
	public JTextField getProfileField() {
		return profileField;
	}
	
	public MainWindow getMainWindow() {
		return this.mw;
	}
	
	public EditAccountWindow(MainWindow mw, chatClient client) {
		this.mw = mw;
		this.client = client;
	}
	
	public void launchEditAccountWindow() {
		this.setTitle("Edit your information");
		this.setLayout(new FlowLayout());
		JLabel password = new JLabel("Password: (LEAVE IT BLANK IF NO CHANGE) ");
		this.add(password);
		passwordField = new JTextField();
		passwordField.setPreferredSize(new Dimension(250, 20));
		this.add(passwordField);
		JLabel userName = new JLabel("User name: (LEAVE IT BLANK IF NO CHANGE)");
		this.add(userName);
		userNameField = new JTextField();
		userNameField.setPreferredSize(new Dimension(250, 20));
		this.add(userNameField);
		JLabel profile = new JLabel("Profile: (LEAVE IT BLANK IF NO CHANGE)");
		this.add(profile);
		profileField = new JTextField();
		profileField.setPreferredSize(new Dimension(250, 20));
		this.add(profileField);
		
		ConfirmEditInfoMonitor cfim = new ConfirmEditInfoMonitor(this.client, this);
		
		JButton confirm = new JButton("confirm");
		confirm.setActionCommand("1");
		confirm.addActionListener(cfim);
		this.add(confirm);
		
		JButton back = new JButton("back");
		back.setActionCommand("2");
		back.addActionListener(cfim);
		this.add(back);
	
		this.client.addEditProfileResultListener(this);
		
		
		this.setResizable(false);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);
		
		
		
		
		this.setVisible(true);
	}
	
	@Override
	public void returnResult(String result) {
		if(result.equals("succeed")) {
			this.setVisible(false);
			JOptionPane.showMessageDialog(this, "Edit Profile Successful");
		}else {
			JOptionPane.showMessageDialog(this, "UserName Already Existed");
		}
	}
	
}