package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class GetClientInfoDB {
	private String login;
	private Connection conn;

	public GetClientInfoDB(Connection conn) {
		this.conn = conn;
	}
	
	public String getLogin(String userName) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql_getLogin = "SELECT * FROM ClientInfo WHERE UserName = '" + userName + "'";
		ResultSet rs = stmt.executeQuery(sql_getLogin);
		while(rs.next()) {
			this.login = rs.getString("Aid");
		}
		
		return this.login;
	}
	
	public String getuserName(String Aid) throws SQLException{
		Statement stmt2 = conn.createStatement();
		String sql_getUserName = "SELECT * FROM ClientInfo WHERE Aid = '" + Aid + "'";
		ResultSet rs = stmt2.executeQuery(sql_getUserName);
		while(rs.next()) {
			this.login = rs.getString("userName");
		}
		
		return this.login;
	}
	
	public ArrayList<String> getAllFriendStatus(String account) throws SQLException{
		ArrayList<String> friendList = new ArrayList<String>();
		ArrayList<String> statusList = new ArrayList<String>();
		
		Statement stmt3 = conn.createStatement();
		Statement stmt4 = conn.createStatement();
		String sql = "SELECT Aid_2 FROM FriendStatus WHERE Aid_1 = '" + account + "'";
		ResultSet rs = stmt3.executeQuery(sql);
		while(rs.next()) {
			friendList.add(rs.getString("Aid_2"));
		}
		
		for(int i = 0; i < friendList.size(); i++) {
			String sql_getStatus = "SELECT UserName, Status, Profile From ClientInfo WHERE Aid = '" + friendList.get(i) + "'";
			ResultSet rs2 = stmt4.executeQuery(sql_getStatus);
			while(rs2.next()) {
				if(rs2.getString("Status").equals("1")) {
					statusList.add("online  " + rs2.getString("UserName") + "  " + rs2.getString("Profile"));
				}else if(rs2.getString("Status").equals("0")) {
					statusList.add("offline  " + rs2.getString("UserName") + "  " + rs2.getString("Profile"));
				}
					
			}
		}
		
		return statusList;
	}
}
