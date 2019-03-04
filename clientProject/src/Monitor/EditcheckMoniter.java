package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import GUI.AllFriendStatusWindow;
import GUI.EditProfileWindow;
import clientProject.chatClient;

public class EditcheckMoniter implements ActionListener{
	private chatClient client;
	private EditProfileWindow epw;
	
	public EditcheckMoniter(chatClient client, EditProfileWindow epw) {
		this.client = client;
		this.epw = epw;
	}
	
	public void actionPerformed(ActionEvent a) {
		//String Aid = this.epw.getFriendID().getText();
		String msg = "Modification succeed\n"; 
		try {
			this.client.getOutputStream().write(msg.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int command = Integer.parseInt(a.getActionCommand());
		switch(command) {
		case 1:
			//AllFriendStatusWindow afsw = new AllFriendStatusWindow();
			//afsw.launchWindow(account);
			this.epw.setVisible(false);
			break;
			
		case 2:
			this.epw.setVisible(false);
			break;
		}
		
		
	}
}
