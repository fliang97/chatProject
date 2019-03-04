package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;

import GUI.AllFriendStatusWindow;
import clientProject.chatClient;

public class AllFriendStatusMonitor implements ActionListener{
	private String account;
	private AllFriendStatusWindow afsw;
	private chatClient client;

	
	public AllFriendStatusMonitor(String account, chatClient client) {
		this.account = account;
		this.client = client;
	}
	
	public AllFriendStatusMonitor(AllFriendStatusWindow afsw) {
		this.afsw = afsw;
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		int command = Integer.parseInt(a.getActionCommand());
		switch(command) {
		case 1:
			//AllFriendStatusWindow afsw = new AllFriendStatusWindow();
			//afsw.launchWindow(account);
			String msg = "allfriendstatus " + this.account + "\n";
			try {
				this.client.getOutputStream().write(msg.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AllFriendStatusWindow afsw = new AllFriendStatusWindow();
			afsw.launchWindow(account, this.client);
			break;
			
		case 2:
			this.afsw.setVisible(false);
			break;
		}
	}

}
