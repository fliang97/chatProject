package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.EditAccountWindow;
import GUI.MainWindow;
import clientProject.chatClient;

public class EditAccountInfoMonitor implements ActionListener{
	private MainWindow mw;
	private chatClient client;
	
	
	public EditAccountInfoMonitor(MainWindow mw, chatClient client) {
		this.mw = mw;
		this.client = client;
	}
	
	public void actionPerformed(ActionEvent a) {
		EditAccountWindow eaw = new EditAccountWindow(this.mw, this.client);
		eaw.launchEditAccountWindow();
	}
}
