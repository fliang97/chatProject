package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.CreateJoinGroupWindow;
import GUI.GroupListPane;
import GUI.GroupManageWindow;
import GUI.MainWindow;
import clientProject.chatClient;

public class GroupMonitor implements ActionListener{
	private GroupManageWindow gmw;
	private chatClient client;
	
	public GroupMonitor(GroupManageWindow gmw, chatClient client) {
		this.gmw = gmw;
		this.client = client;
	}
	
	public void actionPerformed(ActionEvent a) {
		int command = Integer.parseInt(a.getActionCommand());
		switch(command) {
		case 1:
			CreateJoinGroupWindow cgw = new CreateJoinGroupWindow(client, this.gmw.getLogin());
			cgw.launchCreaGroupWindow();
			break;
		case 2:
			CreateJoinGroupWindow cgw_2 = new CreateJoinGroupWindow(client, this.gmw.getLogin());
			cgw_2.launchJoinGroupWindow();

			break;
		case 3:
			client.removeGroupListListener(this.gmw.getGroupListPane());
			this.gmw.setVisible(false);
			break;
		}
	}

}
