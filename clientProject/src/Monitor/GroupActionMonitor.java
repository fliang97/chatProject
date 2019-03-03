package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import GUI.GroupManageWindow;
import GUI.MainWindow;
import clientProject.chatClient;

public class GroupActionMonitor implements ActionListener{
	private MainWindow mw;
	private chatClient client;
	
	public GroupActionMonitor(MainWindow mw, chatClient client) {
		this.mw = mw;
		this.client = client;
	}
	
	public void actionPerformed(ActionEvent a) {
		GroupManageWindow gmw = new GroupManageWindow(client, mw.getLogin());
		gmw.launchGroupManageWindow();
		
		String msg = "getGroup " + mw.getLogin() + "\n";
		try {
			this.client.getOutputStream().write(msg.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
