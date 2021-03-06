package GUI;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Listener.AllFriendStatusListener;
import clientProject.chatClient;

public class OnlineFriendStatusPane extends JPanel implements AllFriendStatusListener{
	private DefaultListModel<String> onlineListModel = new DefaultListModel<>();
	private JList<String> onlineFriendList = new JList<>(onlineListModel);
	private String account; 
	
	
	public OnlineFriendStatusPane(chatClient client) {
		
		client.addAllFriendStatusListener(this);
		
		System.out.println("I am here!");
		/*ArrayList<String> friendList = new ArrayList<String>();
	
		try {
			String sql_getFriend = "SELECT Aid_2 FROM FriendStatus WHERE Aid_1 = '" + account + "'";
			ResultSet rs1 = stmt.executeQuery(sql_getFriend);
			while(rs1.next()) {
				friendList.add(rs1.getString("Aid_2"));
			}
			for(int i = 0; i < friendList.size(); i++) {
				String sql_getStatus = "SELECT UserName, Status, Profile From ClientInfo WHERE Aid = '" + friendList.get(i) + "'";
				ResultSet rs2 = stmt.executeQuery(sql_getStatus);
				while(rs2.next()) {
					if(rs2.getString("Status").equals("1")) {
						onlineListModel.addElement("online  " + rs2.getString("UserName") + "  " + rs2.getString("Profile"));
					}else if(rs2.getString("Status").equals("0")) {
						onlineListModel.addElement("offline  " + rs2.getString("UserName") + "  " + rs2.getString("Profile"));
					}
						
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}*/
		

		
		setLayout(new BorderLayout());
		add(new JScrollPane(onlineFriendList), BorderLayout.CENTER);
		
	}
	
	@Override 
	public void showFriendStatus(String information) {

		onlineListModel.addElement(information);
	}
}
