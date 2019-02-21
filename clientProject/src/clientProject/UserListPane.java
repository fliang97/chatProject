package clientProject;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class UserListPane extends JPanel implements UserStatusListener{
	private chatClient client;
	private JList<String> userListUI;
	private DefaultListModel<String> userListModel;
	private Connection conn = null;
	private Statement stmt = null;
	private String Aid_1 = null;
	
	public UserListPane(chatClient client, Connection conn, Statement stmt, String Aid_1) {
		this.client = client;
		this.client.addUserStatusListener(this);
		this.conn = conn;
		this.stmt = stmt;
		this.Aid_1 = Aid_1;
		
		userListModel = new DefaultListModel<>();
		userListUI = new JList<>(userListModel);
		setLayout(new BorderLayout());
		add(new JScrollPane(userListUI), BorderLayout.CENTER);
		
		userListUI.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if(e.getClickCount() > 1) {
						String login = null;
						String sql_getAccount = "SELECT Aid FROM ClientInfo WHERE UserName = '" + userListUI.getSelectedValue() + "'";
						ResultSet rs = stmt.executeQuery(sql_getAccount);
						while(rs.next()) {
							login = rs.getString("Aid");
						}
						//String login = userListUI.getSelectedValue();
						MessagePane messagePane = new MessagePane(client, login, userListUI.getSelectedValue());
					
						JFrame f = new JFrame("Message " + userListUI.getSelectedValue());
						f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						f.setSize(500, 500);
						f.getContentPane().add(messagePane, BorderLayout.CENTER);
					
						f.setVisible(true);
					}
					
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
			
		});
	}

	/*public static void main(String[] args) {
		 chatClient client = new chatClient("localhost", 8818);
		 
		 UserListPane userListPane = new UserListPane(client);
		 JFrame frame = new JFrame("User List");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setSize(400, 400);
		 
		 frame.getContentPane().add(userListPane, BorderLayout.CENTER);
		 frame.setVisible(true);
		 

	}*/
	
	@Override
	public void online(String login) {
		
		try {
			System.out.println("NIce work");
			String sql_checkIfFriend  = "SELECT * FROM FriendStatus WHERE Aid_1 = '" + Aid_1 + "'"; 
			ResultSet rs2 = stmt.executeQuery(sql_checkIfFriend);
			while(rs2.next()) {
				if(rs2.getString("Aid_2").equals(login)) {
					String sql_getUserName = "SELECT * FROM ClientInfo WHERE Aid = '" + login + "'";
					ResultSet rs3 = stmt.executeQuery(sql_getUserName);
					rs3.next();
					userListModel.addElement(rs3.getString("UserName"));
					

				}
			System.out.println("done");

			}

		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			
		}
		//userListModel.addElement(login);
	}
	
	@Override
	public void offline(String login) {
		try {
			String sql_getUserName = "SELECT * FROM ClientInfo WHERE Aid = '" + login + "'";
			ResultSet rs2 = stmt.executeQuery(sql_getUserName);
			rs2.next();
			login = rs2.getString("UserName");
			for(int i = 0; i < userListModel.size(); i++) {
				if(login.equals(userListModel.elementAt(i))){
					userListModel.removeElement(login);
					System.out.println("remove Successful");
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		//userListModel.removeElement(login);
	}
	
}
