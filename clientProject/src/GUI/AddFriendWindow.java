package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Listener.AddFriendWindowListener;
import Monitor.SendRequestFriendMonitor;
import clientProject.chatClient;

public class AddFriendWindow extends JFrame implements AddFriendWindowListener{
	private JTextField friendID;
	
	public JTextField getFriendID() {
		return friendID;
	}
	
	public void launchAddFriendWindow(chatClient client) {
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		this.setTitle("Add Friend");
		this.setLayout(new FlowLayout());
		this.setSize(300, 100);
		this.setResizable(false);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		client.addAddFriendWindowListener(this);
		
		JLabel searchFriend = new JLabel("search your Friend");
		//searchFriend.setPreferredSize(new Dimension(300, 100));
		this.add(searchFriend);
		friendID = new JTextField("Enter friend's Account Number");
		friendID.setPreferredSize(new Dimension(250, 20));
		this.add(friendID);
		JButton search = new JButton ("send request");
		SendRequestFriendMonitor sfm = new SendRequestFriendMonitor(client, this);
		search.addActionListener(sfm);
		this.add(search);
		
		
		this.setVisible(true);
	}
	
	@Override
	public void showWindow(String result) {
		if(result.equals("****fail****")) {
			JOptionPane.showMessageDialog(null, "Account NOT EXIST!", "Request failed", JOptionPane.ERROR_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(this, "request sent");
		}
	}

}
