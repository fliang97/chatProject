package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class HandleLogOffDB {
	private Connection conn;
	private Statement stmt;
	private String login;

	public HandleLogOffDB(Connection conn, Statement stmt, String login) {
		this.conn = conn;
		this.stmt = stmt;
		this.login = login;
	}
	
	public boolean doLogOff() {
		boolean result = false;
		try {
			String sql_logoff = "UPDATE ClientInfo C SET C.Status = '0' WHERE C.Aid = '" + login + "'";
			PreparedStatement status = conn.prepareStatement(sql_logoff);
			status.executeUpdate();
			result = true;
			System.out.println("Logoff Successful");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}
