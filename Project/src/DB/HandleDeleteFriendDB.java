package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HandleDeleteFriendDB {
	private String[] tokens;
	private Connection conn;
	
	public HandleDeleteFriendDB(String[] tokens, Connection conn) {
		this.tokens = tokens;
		this.conn = conn;
	}
	
	public boolean run() {
		boolean result = false;
		try {
			String sql_get_id = "SELECT Aid FROM ClientInfo WHERE UserName = '" + tokens[1] + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql_get_id);
			String account_id = "";
			while(rs.next()) {
				account_id = rs.getString("Aid");
			}
		
			String sql_1 = "DELETE FROM FriendStatus WHERE Aid_1 = '" + account_id + "' AND Aid_2 = '" + tokens[2] + "'";
			String sql_2 = "DELETE FROM FriendStatus WHERE Aid_1 = '" + tokens[2] + "' AND Aid_2 = '" + account_id + "'";
			PreparedStatement delete_1 = conn.prepareStatement(sql_1);
			delete_1.executeUpdate();
			PreparedStatement delete_2 = conn.prepareStatement(sql_2);
			delete_2.executeUpdate();
			result = true;
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			return result;
		}
		
	}
	
}
