package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HandleGetFriendDB {
	private Connection conn;
	private String[] tokens;
	private Statement stmt;
	private Statement stmt2;

	public HandleGetFriendDB(String[] tokens, Connection conn) {
		this.tokens = tokens;
		this.conn = conn;
	}
	
	@SuppressWarnings("finally")
	public String friendUserName() throws SQLException {
		this.stmt = conn.createStatement();
		this.stmt2 = conn.createStatement();
		String userName = "";
		try {
			
			for(int i = 0; i < tokens.length; i++) System.out.println(tokens[i]);
			
			String Aid_1 = tokens[1];
			String login = tokens[2];

	
			String sql_checkIfFriend  = "SELECT * FROM FriendStatus WHERE Aid_1 = '" + Aid_1 + "'"; 
			ResultSet rs21 = stmt.executeQuery(sql_checkIfFriend);
			while(rs21.next()) {
				if(rs21.getString("Aid_2").equals(login)) {
					String sql_getUserName = "SELECT * FROM ClientInfo WHERE Aid = '" + login + "'";
					ResultSet rs3 = stmt2.executeQuery(sql_getUserName);
					rs3.next();
					userName = rs3.getString("UserName");
					//userListModel.addElement(rs3.getString("UserName"));
					rs3.close();
				}
			}
			rs21.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(userName.equalsIgnoreCase("")) return "error";
			else return userName;
		}
	}
}
