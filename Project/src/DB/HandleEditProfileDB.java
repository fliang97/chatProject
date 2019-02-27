package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HandleEditProfileDB {
	private String[] tokens;
	private Connection conn;
	private String password;
	private String userName;
	private String profile;
	private String account;


	public HandleEditProfileDB(String[] tokens, Connection conn) {
		this.tokens = tokens;
		this.conn = conn;
		this.account = tokens[1];
		this.password = tokens[2];
		this.userName = tokens[3];
		this.profile = tokens[4];
	}
	
	public boolean editProfileResult() {
		boolean result = true;
	
		try {			
			
			//change password
			if(!password.equals("00000000000")) {
				String sql_1 = "UPDATE ClientInfo C SET Password = '" + this.password + "' WHERE Aid = '" + this.account + "'";
				PreparedStatement insert_1 = conn.prepareStatement(sql_1);
				insert_1.executeUpdate();
			}
			
			//change userName
			if(!userName.equals("00000000000")) {
				String sql_check = "SELECT UserName FROM ClientInfo";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql_check);
				int outbreak = 0;
				while(rs.next()) {
					if(rs.getString("UserName").equals(this.userName)) {
						outbreak++;
					}
				}
				if(outbreak != 0) {
					result = false;
					return result; 
				}
				else if(outbreak == 0) {
					String sql_2 = "UPDATE ClientInfo C SET UserName = '" + this.userName + "'WHERE Aid = '" + this.account + "'";
					PreparedStatement insert_2 = conn.prepareStatement(sql_2);
					insert_2.executeUpdate();
				}
			}
			
			//change profile
			if(!profile.equals("no.profile.no.profile")) {
				String sql_3 = "UPDATE ClientInfo C SET Profile = '" + this.profile + "' WHERE Aid = '" + this.account + "'";
				PreparedStatement insert_3 = conn.prepareStatement(sql_3);
				insert_3.executeUpdate();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
}
