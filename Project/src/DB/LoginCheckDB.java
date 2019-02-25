package DB;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginCheckDB {
	private OutputStream outputStream;
	private String[] tokens;
	private Connection conn;
	private Statement stmt;

	public LoginCheckDB(OutputStream outputStream, String[] tokens, Connection conn, Statement stmt) {
		this.outputStream = outputStream;
		this.tokens = tokens;
		this.conn = conn;
		this.stmt = stmt;
	}
	
	@SuppressWarnings("finally")
	public boolean handleLogin() throws IOException, SQLException{
		
		String login = this.tokens[1];
		String password = this.tokens[2];	
		
		boolean result = true;
		try {
			//check if the account is existed
			String sql_getAccount = "SELECT Aid, Password FROM ClientInfo";
			int outbreak = 0;
			ResultSet rs1 = stmt.executeQuery(sql_getAccount);
			while(rs1.next()) {
				if(rs1.getString("Aid").equals(login)) {
					outbreak = 1;
				}
			}
			if(outbreak == 0) {
				//System.out.println("Login fail");
				rs1.close();
				result = false;
				//return false;

			}
			rs1.close();

			
			//check if the password is correct
			ResultSet rs2 = stmt.executeQuery(sql_getAccount);
			while(rs2.next()) {
				if(rs2.getString("Aid").equals(login)) {
					if(!password.equals(rs2.getString("Password"))){
						//System.out.println("Login fail");
						rs2.close();
						result = false;
						//return false;
					}
				}
			}		
			rs2.close();

				
			//here the client is logged in to the server
			if(result) {
				String sql_status = "UPDATE ClientInfo C SET C.Status = '1' WHERE C.Aid = '" + login + "'";
				PreparedStatement status = conn.prepareStatement(sql_status);
				status.executeUpdate();
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(result) System.out.println("login successful");
			else System.out.println("gameover");
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	public boolean validateAccount(String login, String password) throws SQLException {
		try {
			//check if the account is existed
			String sql_getAccount = "SELECT Aid, Password FROM ClientInfo";
			int outbreak = 0;
			ResultSet rs1 = stmt.executeQuery(sql_getAccount);
			while(rs1.next()) {
				if(rs1.getString("Aid").equals(login)) {
					outbreak = 1;
				}
			}
			if(outbreak == 0) {
				return false;
			}

		
			//check if the password is correct
			ResultSet rs2 = stmt.executeQuery(sql_getAccount);
			while(rs2.next()) {
				if(rs2.getString("Aid").equals(login)) {
					if(!password.equals(rs2.getString("Password"))){
						return false;
					}
				}
			}			
			System.out.println("Customer " + login + " Login Successful");

			
			//here the client is logged in to the server
			String sql_status = "UPDATE ClientInfo C SET C.Status = '1' WHERE C.Aid = '" + login + "'";
			PreparedStatement status = conn.prepareStatement(sql_status);
			status.executeUpdate();
			System.out.println("Login Successful");
		
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			return true;
		}
	}
}
