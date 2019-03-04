package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Monitor.AllFriendStatusMonitor;
import Listener.EditProfileListener;
import Monitor.EditProfileMonitor;
import Monitor.EditcheckMoniter;
import clientProject.chatClient;

public class EditProfileWindow extends JFrame {
	private JTextField profile;
	//private chatClient client;
	
	public JTextField getprofile() {
		return profile;
	}
	
	public void launchEditProfileWindow(chatClient client) {
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		this.setTitle("Edit Profile");
		this.setLayout(new FlowLayout());
		this.setSize(400, 300);
		this.setResizable(false);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);

		
		profile = new JTextField("Modify your profile");
		profile.setPreferredSize(new Dimension(400, 220));
		this.add(profile);
		
		JButton editProfile = new JButton("Confirm");
		EditcheckMoniter ecm = new EditcheckMoniter(client,this);
		editProfile.setActionCommand("1");
		editProfile.addActionListener(ecm);
		this.add(editProfile);
		
		JButton back = new JButton("Back");
		back.setActionCommand("2");
		back.addActionListener(ecm);
		this.add(back);
		
		this.setVisible(true);
	}
	
	
}
