package GUI;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddFriendWindow extends JFrame{
	
	public void launchAddFriendWindow() {
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		this.setTitle("Add Friend");
		this.setLayout(new FlowLayout());
		this.setSize(200, 270);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		JLabel searchFriend = new JLabel("search your Friend");
		this.add(searchFriend);
		JTextField friendID = new JTextField();
		this.add(friendID);
		JButton search = new JButton ("search");
		
	}

}
