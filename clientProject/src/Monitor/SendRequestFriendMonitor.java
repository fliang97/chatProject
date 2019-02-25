package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import GUI.AddFriendWindow;
import clientProject.chatClient;

public class SendRequestFriendMonitor implements ActionListener{
	private chatClient client;
	private AddFriendWindow afw;
	
	public SendRequestFriendMonitor(chatClient client, AddFriendWindow afw) {
		this.client = client;
		this.afw = afw;
	}
	
	public void actionPerformed(ActionEvent a) {
		String Aid = this.afw.getFriendID().getText();
		String msg = "addfriend " + Aid + "\n"; 
		try {
			this.client.getOutputStream().write(msg.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}