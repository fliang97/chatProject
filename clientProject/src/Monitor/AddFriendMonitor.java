package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.AddFriendWindow;
import clientProject.chatClient;

public class AddFriendMonitor implements ActionListener{
	private chatClient client;
	
	public AddFriendMonitor(chatClient client) {
		this.client = client;
	}
	
	@Override
	public void actionPerformed(ActionEvent a){
		AddFriendWindow afw = new AddFriendWindow();
		afw.launchAddFriendWindow(this.client);
	}

}
