package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import Monitor.GroupMonitor;
import clientProject.chatClient;

public class GroupManageWindow extends JFrame{
	private chatClient client;
	private String login;
	private GroupListPane glp;
	
	public String getLogin() {
		return this.login;
	}
	
	public GroupListPane getGroupListPane() {
		return this.glp;
	}
	
	public GroupManageWindow(chatClient client, String login) {
		this.client = client;
		this.login = login;
	}
	
	public void launchGroupManageWindow() {
		glp = new GroupListPane(this.client, this.login);
		glp.setPreferredSize(new Dimension(380, 300));
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		this.getContentPane().add(glp, BorderLayout.CENTER);
		this.setTitle("Group List");
		this.setLayout(new FlowLayout());
		this.setSize(400, 500);
		this.setResizable(false);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		JButton createGroup = new JButton("Create Group Chat");
		GroupMonitor cgm = new GroupMonitor(this, client);
		createGroup.setActionCommand("1");
		createGroup.addActionListener(cgm);
		this.add(createGroup);
		
		JButton joinGroup = new JButton("Join Group Chat");
		joinGroup.setActionCommand("2");
		joinGroup.addActionListener(cgm);
		this.add(joinGroup);
		
		JButton back = new JButton("Back");
		back.setActionCommand("3");
		back.addActionListener(cgm);
		this.add(back);
		

		
		this.setVisible(true);
	}
	
}
