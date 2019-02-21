package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import GUI.AllFriendStatusWindow;

public class AllFriendStatusMonitor implements ActionListener{
	private String account;
	private AllFriendStatusWindow afsw;
	
	public AllFriendStatusMonitor(String account) {
		this.account = account;
	}
	
	public AllFriendStatusMonitor(AllFriendStatusWindow afsw) {
		this.afsw = afsw;
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		int command = Integer.parseInt(a.getActionCommand());
		switch(command) {
		case 1:
			AllFriendStatusWindow afsw = new AllFriendStatusWindow();
			afsw.launchWindow(account);
			break;
		case 2:
			this.afsw.setVisible(false);
			break;
		}
	}

}
