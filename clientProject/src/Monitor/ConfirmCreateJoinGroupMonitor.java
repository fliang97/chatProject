package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import GUI.CreateJoinGroupWindow;
import clientProject.chatClient;

public class ConfirmCreateJoinGroupMonitor implements ActionListener{
	private CreateJoinGroupWindow cgw;
	private chatClient client;
	private String login;
	
	public ConfirmCreateJoinGroupMonitor(CreateJoinGroupWindow cgw, chatClient client, String login) {
		this.cgw = cgw;
		this.client = client;
		this.login = login;
	}
	
	public void actionPerformed(ActionEvent a) {
		int command = Integer.parseInt(a.getActionCommand());
		switch(command) {
		// create confirm button
		case 1:
			String groupName = this.cgw.getGroupNameField().getText();
			String msg = "creategroup #" + groupName + " " + this.login + "\n";
			try {
				this.client.getOutputStream().write(msg.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.cgw.setVisible(false);
			break;
		// join confirm button
		case 2:
			String groupName_1 = this.cgw.getGroupNameField().getText();
			String msg_1 = "joingroup #" + groupName_1 + " " + this.login + "\n";
			try {
				this.client.getOutputStream().write(msg_1.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 3:
			this.cgw.setVisible(false);
			break;
		}
		
	}
	

}
