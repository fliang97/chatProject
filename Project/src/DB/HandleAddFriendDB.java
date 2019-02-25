package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HandleAddFriendDB {
	private String[] tokens;
	private Connection conn;

	public HandleAddFriendDB(String[] tokens, Connection conn) {
		this.tokens = tokens;
		this.conn = conn;
	}
	
	public String run() throws SQLException {
		Statement stmt = conn.createStatement();
		String sql_search = "SELECT Aid FROM ClientInfo WHERE Aid = '" + this.tokens[1] + "'";
		ResultSet rs = stmt.executeQuery(sql_search);
		String result = "";
		while(rs.next()) {
			result = rs.getString("Aid");
		}
		
		return result;
	}
}
