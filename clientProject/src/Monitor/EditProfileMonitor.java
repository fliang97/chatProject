package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import GUI.AddFriendWindow;
import GUI.AllFriendStatusWindow;
import GUI.EditProfileWindow;
//import clientProject.chatClient;
import clientProject.chatClient;

public class EditProfileMonitor implements ActionListener{
	//private String profile;
	private chatClient client;
	
	public EditProfileMonitor(chatClient client) {
		this.client = client;
		//this.profile = profile;
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		EditProfileWindow epw = new EditProfileWindow();
		epw.launchEditProfileWindow(this.client);
	}
		
}

