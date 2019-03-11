package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Monitor.ConfirmCreateJoinGroupMonitor;
import clientProject.chatClient;

public class CreateJoinGroupWindow extends JFrame{
	private chatClient client;
	private String login;
	private JTextField groupNameField;
	
	public CreateJoinGroupWindow(chatClient client, String login) {
		this.client = client;
		this.login = login;
	}
	
	public JTextField getGroupNameField() {
		return this.groupNameField;
	}
	
	public void launchCreaGroupWindow() {
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		this.setTitle("Create Your Own Group Chat");
		this.setLayout(new FlowLayout());
		this.setSize(300, 100);
		this.setResizable(false);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		JLabel groupNameLabel = new JLabel("Enter your group name: ");
		this.add(groupNameLabel);
		
		groupNameField = new JTextField();
		groupNameField.setPreferredSize(new Dimension(280, 20));
		this.add(groupNameField);
		
		
		ConfirmCreateJoinGroupMonitor ccgm = new ConfirmCreateJoinGroupMonitor(this, this.client, this.login);
		JButton confirm = new JButton("confirm");
		confirm.setActionCommand("1");
		confirm.addActionListener(ccgm);
		this.add(confirm);
		
		JButton back = new JButton("back");
		back.setActionCommand("3");
		back.addActionListener(ccgm);
		this.add(back);
		
		
		
		
		this.setVisible(true);
	}
	
	
	public void launchJoinGroupWindow() {
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		this.setTitle("Enter the Group Name: ");
		this.setLayout(new FlowLayout());
		this.setSize(300, 100);
		this.setResizable(false);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		JLabel groupNameLabel = new JLabel("Enter your group name: ");
		this.add(groupNameLabel);
		
		groupNameField = new JTextField();
		groupNameField.setPreferredSize(new Dimension(280, 20));
		this.add(groupNameField);
		
		ConfirmCreateJoinGroupMonitor ccgm = new ConfirmCreateJoinGroupMonitor(this, this.client, this.login);
		JButton confirm = new JButton("confirm");
		confirm.setActionCommand("2");
		confirm.addActionListener(ccgm);
		this.add(confirm);
		
		JButton back = new JButton("back");
		back.setActionCommand("3");
		back.addActionListener(ccgm);
		this.add(back);
		
		this.setVisible(true);
	}

}