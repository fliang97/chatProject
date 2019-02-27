package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HandleFriendOfflineDB {
	private Connection conn;
	private String login;
	
	public HandleFriendOfflineDB(String login, Connection conn) {
		this.conn = conn;
		this.login = login;
	}
	
	public String getUserName() throws SQLException {
		Statement stmt = conn.createStatement();
		String sql_getUserName = "SELECT * FROM ClientInfo WHERE Aid = '" + this.login + "'";
		ResultSet rs2 = stmt.executeQuery(sql_getUserName);
		rs2.next();
		login = rs2.getString("UserName");

		return login;
	} 
}
