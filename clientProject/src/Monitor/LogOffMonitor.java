package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import clientProject.LoginWindow;
import clientProject.UserListPane;
import clientProject.chatClient;
import clientProject.ReadMessageLoop;

public class LogOffMonitor implements ActionListener{
	private chatClient client;
	private UserListPane usp;
	private JFrame frame;
	private String login;
	private String signal = "succeed";

	public LogOffMonitor(chatClient client, UserListPane usp, JFrame frame, String login) {
		this.client = client;
		this.usp = usp;
		this.frame = frame;
		this.login = login;
	}
	

	
	@Override
	public void actionPerformed(ActionEvent a){
		int command = Integer.parseInt(a.getActionCommand());
		switch (command) {
		case 1:
			try{
				//this.client.logoff();

				String msg = "logoff\n";
				this.client.getOutputStream().write(msg.getBytes());
				
				this.signal = "succeed";
				
				try {
					String line;
					
					/*BufferedReader bufferedIn = new BufferedReader(new InputStreamReader(this.client.getServerIn()));
					while((line = bufferedIn.readLine()) != null ) {
						String[] tokens = StringUtils.split(line);
						if(tokens != null && tokens.length > 0) {
							String cmd = tokens[0];
							System.out.println(line +line);
							if("succeed".equalsIgnoreCase(cmd)) {
								System.out.println("gun");
								this.signal = "succeed";	
								break;
							}
						}
					}*/


				}catch(Exception ex){
					ex.printStackTrace();
					try {
						this.client.getSocket().close();
					}catch(IOException e){
						e.printStackTrace();
					}
				}
				if(this.signal.equalsIgnoreCase("succeed")){
					this.frame.setVisible(false);
					LoginWindow lw = new LoginWindow();
				}else {
					JOptionPane.showMessageDialog(null, "logOff failed! ", "", JOptionPane.PLAIN_MESSAGE);
				}
				
			}catch(IOException e) {
				e.printStackTrace();
			}

			break;
			
		case 2:
			
			try {
				this.client.logoff();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.frame.setVisible(false);
			System.exit(0);
			break;
		}
	}
	
	
}
	
	