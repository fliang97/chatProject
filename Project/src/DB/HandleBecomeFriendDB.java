package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HandleBecomeFriendDB {
	private String Aid_1, Aid_2;
	private Connection conn;
	
	public HandleBecomeFriendDB(String Aid_1, String Aid_2, Connection conn) {
		this.Aid_1 = Aid_1;
		this.Aid_2 = Aid_2;
		this.conn = conn;
	}
	
	public void run() throws SQLException {
		String sql_1 = "INSERT INTO FriendStatus (Aid_1, Aid_2) VALUES ('" + Aid_1 + "', '" + Aid_2 + "')";
		PreparedStatement insert_1 = conn.prepareStatement(sql_1);
		insert_1.executeUpdate();
		String sql_2 = "INSERT INTO FriendStatus (Aid_1, Aid_2) VALUES ('" + Aid_2 + "', '" + Aid_1 + "')";
		PreparedStatement insert_2 = conn.prepareStatement(sql_2);
		insert_2.executeUpdate();
				

	}
	
}
