package GUI;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Listener.GroupListListener;
import clientProject.MessagePane;
import clientProject.chatClient;

public class GroupListPane extends JPanel implements GroupListListener{
	private chatClient client;
	private JList<String> userListUI;
	private DefaultListModel<String> userListModel;
	private String Aid_1 = null;
	
	public JList<String> getUserListUI(){
		return this.userListUI;
	}
	
	public GroupListPane(chatClient client, String login) {
		this.client = client;
		//this.client.addUserStatusListener(this);
		this.Aid_1 = login;

		userListModel = new DefaultListModel<>();
		userListUI = new JList<>(userListModel);
		setLayout(new BorderLayout());
		add(new JScrollPane(userListUI), BorderLayout.CENTER);
		
		client.addGroupListListener(this);
		
		
		userListUI.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() > 1) {
					//String login = userListUI.getSelectedValue();
					MessageGroupPane messagePane = new MessageGroupPane(client, userListUI.getSelectedValue(), login);
					
					
					JFrame f = new JFrame("Group: " + userListUI.getSelectedValue());
					f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					f.setSize(500, 500);
					f.getContentPane().add(messagePane, BorderLayout.CENTER);
					
					f.setVisible(true);
				}
					
				
			}
		});
	}
	
	@Override
	public void showGroup(String groupName) {
		userListModel.addElement(groupName);
	}
	
	@Override
	public void showCreateGroupResult(String result) {
		if(result.equalsIgnoreCase("failed")) {
			JOptionPane.showMessageDialog(null, "Group Already Existed!\n Please Enter Create a New Group", "Request failed", JOptionPane.ERROR_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(this, "Group Created! Please Refresh the Window!");
		}
	}
	
	@Override 
	public void showJoinGroupResult(String result, String groupName) {
		if(result.equalsIgnoreCase("notexist")) {
			JOptionPane.showMessageDialog(null, "Group Not Existed!\n Please Enter a Existed Group Name", "Request failed", JOptionPane.ERROR_MESSAGE);
		}else if(result.equalsIgnoreCase("alreadyin")) {
			JOptionPane.showMessageDialog(null, "You are already in this group", "Request failed", JOptionPane.ERROR_MESSAGE);
		}else if(result.equalsIgnoreCase("successful")) {
			JOptionPane.showMessageDialog(this, "Join Successful!");
			userListModel.addElement(groupName);
		}else {
			JOptionPane.showMessageDialog(this, "Server is busy!\n Please Try Again.");
		}
	}	
	
	@Override 
	public void showLeaveGroupResult(String result, String groupName) {
		if(result.equalsIgnoreCase("alreadyleft")) {
			JOptionPane.showMessageDialog(null, "You already left this group", "Request failed", JOptionPane.ERROR_MESSAGE);
		}else if(result.equalsIgnoreCase("servererror")) {
			JOptionPane.showMessageDialog(null, "Server is busy!\nPlease try again", "Request failed", JOptionPane.ERROR_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(this, "You have left this group!");
			userListModel.removeElement(groupName);
		}
	}
	
}
