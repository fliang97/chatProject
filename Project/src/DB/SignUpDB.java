package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUpDB {
	private String[] tokens;
	private Connection conn;
	private Statement stmt;

	public SignUpDB(String[] tokens, Connection conn, Statement stmt) {
		this.tokens = tokens;
		this.conn = conn;
		this.stmt = stmt;
	}
	
	public int signUp() throws SQLException {
		int result = 0;

		if(tokens.length == 5) {
			String account = tokens[1];
			String password = tokens[2];
			String userName = tokens[3];
			String profile = tokens[4];
			String sql_getAccount = "SELECT Aid FROM ClientInfo";
			ResultSet rs = stmt.executeQuery(sql_getAccount);
			int outbreak = 0;
			while(rs.next()) {
				if(rs.getString("Aid").equals(account)) {
					outbreak++;
				}
			}
			if(outbreak != 0) {
				result = 2;
				return result;
			}
		
		
			String sql_sign_up = "INSERT INTO ClientInfo (Aid , Password, Status, UserName, Profile) VALUES ('" + account + "', '" + password + "', '0' ,'" + userName + "','" + profile + "')";
			PreparedStatement insert = conn.prepareStatement(sql_sign_up);
			insert.executeUpdate();
			
			result = 1;
		}
		
		
		return result;
	}
}
