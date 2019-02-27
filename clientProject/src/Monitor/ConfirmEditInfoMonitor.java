package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import GUI.EditAccountWindow;
import clientProject.chatClient;

public class ConfirmEditInfoMonitor implements ActionListener{
	private chatClient client;
	private EditAccountWindow eaw;
	
	public ConfirmEditInfoMonitor(chatClient client, EditAccountWindow eaw) {
		this.client = client;
		this.eaw = eaw;
	}
	
	public void actionPerformed(ActionEvent a) {
		int command = Integer.parseInt(a.getActionCommand());
		switch(command) {
		case 1:
			String password = "00000000000", userName = "00000000000", profile = "no.profile.no.profile";

			String account = this.eaw.getMainWindow().getLogin();
			
			if(!this.eaw.getPassword().getText().equals("")) {
				password = this.eaw.getPassword().getText();
			}
			if(!this.eaw.getUserName().getText().equals("")) {
				userName = this.eaw.getUserName().getText();
			}
			if(!this.eaw.getProfileField().getText().equals("")) {
				profile = this.eaw.getProfileField().getText();
			}
			String msg = "editprofile " + account + " " + password + " " + userName + " " + profile + "\n";
			try {
				this.client.getOutputStream().write(msg.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 2:
			this.eaw.setVisible(false);		
			break;
		}
	}

}
