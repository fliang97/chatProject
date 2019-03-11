package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import GUI.MainWindow;
import clientProject.chatClient;

public class HandShakeMonitor implements ActionListener{
	private MainWindow mw;
	private chatClient client;

	public HandShakeMonitor(MainWindow mw, chatClient client) {
		this.mw = mw;
		this.client = client;
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		String msg = "handShake " + this.mw.getLogin() + "\n";
		try {
			this.client.getOutputStream().write(msg.getBytes());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
