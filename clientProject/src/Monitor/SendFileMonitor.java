package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import GUI.MainWindow;
import clientProject.UserListPane;
import clientProject.chatClient;

public class SendFileMonitor implements ActionListener{
	private UserListPane ulp;
	private chatClient client;
	private String login;
	private MainWindow mw;
	
	public SendFileMonitor(UserListPane ulp, chatClient client, String login, MainWindow mw) {
		this.ulp = ulp;
		this.client = client;
		this.login = login;
		this.mw = mw;
	}
	

	public void actionPerformed(ActionEvent a) {
		String sendTo = this.ulp.getUserListUI().getSelectedValue();
		
		int reply = JOptionPane.showConfirmDialog(null,
				"Do you want to send a file to " + sendTo + "?", "SendFile", JOptionPane.YES_NO_OPTION);
		if(reply == JOptionPane.YES_OPTION) {
			JButton open = new JButton();
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("SELECT YOUT FILE TO BE SENT");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
				
			}
			String pwd = fc.getSelectedFile().getAbsolutePath();
				
			int index = pwd.indexOf(".");
			String format = pwd.substring(index);
			this.mw.setFile_path(pwd);	
			
			String msg = "sendfile " + sendTo + " " + this.login + " " + format + " sendfilerequest\n";
			try {
				this.client.getOutputStream().write(msg.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

			
			
		}
	}
	
}
