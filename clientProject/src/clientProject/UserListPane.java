package clientProject;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import org.apache.commons.lang3.StringUtils;

public class UserListPane extends JPanel implements UserStatusListener{
	private chatClient client;
	private JList<String> userListUI;
	private DefaultListModel<String> userListModel;
	private String Aid_1 = null;
	
	public JList<String> getUserListUI(){
		return this.userListUI;
	}

	
	public UserListPane(chatClient client, String login) {
		this.client = client;
		this.client.addUserStatusListener(this);
		this.Aid_1 = login;

		userListModel = new DefaultListModel<>();
		userListUI = new JList<>(userListModel);
		setLayout(new BorderLayout());
		add(new JScrollPane(userListUI), BorderLayout.CENTER);
		
		System.out.println("Aid_1 is: " + Aid_1);
		
		userListUI.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() > 1) {
					//String login = userListUI.getSelectedValue();
					MessagePane messagePane = new MessagePane(client, userListUI.getSelectedValue());
					
					
					JFrame f = new JFrame("Message " + userListUI.getSelectedValue());
					f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					f.setSize(500, 500);
					f.getContentPane().add(messagePane, BorderLayout.CENTER);
					
					f.setVisible(true);
				}
					
				
			}
		});
	}
	
	@Override
	public void online(String login) {
		
		try {
			String msg = "getfriend " + Aid_1 + " " + login + "\n";
			this.client.getOutputStream().write(msg.getBytes());
			
			System.out.println("NIce work");
			
			String line;
			BufferedReader bufferedIn = new BufferedReader(new InputStreamReader(this.client.getServerIn()));
			while((line = bufferedIn.readLine()) != null ) {
				String[] tokens = StringUtils.split(line);
				if(tokens != null && tokens.length > 0) {
					String cmd = tokens[0];
					
					if("getfriendonline".equalsIgnoreCase(cmd)) {
						if(tokens[1].equalsIgnoreCase("error")) {
							break;
						}else {
							System.out.println(line);
							userListModel.addElement(tokens[1]);
							break;
						}
					}
				}
			}
			
	
		}catch(IOException e){
			e.printStackTrace();
		}finally {
			
		}
		//userListModel.addElement(login);
	}
	
	@Override
	public void offline(String login) {
		//try {
			

			
			/*String line;
			BufferedReader bufferedIn = new BufferedReader(new InputStreamReader(this.client.getServerIn()));
			while((line = bufferedIn.readLine()) != null ) {
				String[] tokens = StringUtils.split(line);
				if(tokens != null && tokens.length > 0) {
					String cmd = tokens[0];
					if("getfriendoffline".equalsIgnoreCase(cmd)) {
						userListModel.removeElement(tokens[1]);
					}
				}
			}*/
			
			/*String sql_getUserName = "SELECT * FROM ClientInfo WHERE Aid = '" + login + "'";
			ResultSet rs2 = stmt.executeQuery(sql_getUserName);
			rs2.next();
			login = rs2.getString("UserName");
			for(int i = 0; i < userListModel.size(); i++) {
				if(login.equals(userListModel.elementAt(i))){
					userListModel.removeElement(login);
					System.out.println("remove Successful");
				}
			}*/
			userListModel.removeElement(login);
		//}catch(IOException e) {
		//	e.printStackTrace();
		//}
		//userListModel.removeElement(login);
	}
	
}
