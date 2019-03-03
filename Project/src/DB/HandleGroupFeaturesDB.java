package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HandleGroupFeaturesDB {
	private Connection conn;
	private String[] tokens;
	
	public HandleGroupFeaturesDB(Connection conn, String[] tokens) {
		this.conn = conn;
		this.tokens = tokens;
	}
	
	public boolean createGroupResult() {
		try {
			String groupName = this.tokens[1];
			String account = this.tokens[2];
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM GroupName";
			ResultSet rs1 = stmt.executeQuery(sql);
			while(rs1.next()) {
				if(rs1.getString("GroupName").equals(groupName)) {
					return false;
				}
			}
			

			
			String sql_createGroup = "INSERT INTO GroupName (GroupName) VALUES ('" + groupName + "')";
			PreparedStatement insert_1 = conn.prepareStatement(sql_createGroup);
			insert_1.executeUpdate();
			
			String table = "UserGroupList_" + account;
			String sql_insertUserGroup = "INSERT INTO " + table + " (GroupName) VALUES ('" + groupName + "')";
			PreparedStatement insert_2 = conn.prepareStatement(sql_insertUserGroup);
			insert_2.executeUpdate();
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	
	public int joinGroupResult() {
		int result = 1;
		try {
			String groupName = this.tokens[1];
			String account = this.tokens[2];
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM GroupName";
			ResultSet rs1 = stmt.executeQuery(sql);
			
			//checking if group exists;
			int end_func = 0;
			while(rs1.next()) {
				if(rs1.getString("GroupName").equals(groupName)) {
					end_func++; 
				}
			}
			if(end_func == 0) return 2;
			
			Statement stmt2 = conn.createStatement();
			String tableName = "UserGroupList_" + account;
			String sql_2 = "SELECT * FROM " + tableName;
			ResultSet rs2 = stmt2.executeQuery(sql_2);
			
			//checking if already in;
			end_func = 0;
			while(rs2.next()) {
				if(rs2.getString("GroupName").equals(groupName)) {
					end_func++;
				}
			}
			if(end_func > 0) return 3;
			
			//here successful all!
			String sql_insertUserGroup = "INSERT INTO " + tableName + " (GroupName) VALUES ('" + groupName + "')";
			PreparedStatement insert_2 = conn.prepareStatement(sql_insertUserGroup);
			insert_2.executeUpdate();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
		
		return result;
	}
	
	public int leaveGroupResult() {		
		try {
			String groupName = tokens[1];
			String account = tokens[2];
			String tableName = "UserGroupList_" + account;
			
			String sql = "SELECT * FROM " + tableName;
			Statement stmt = conn.createStatement();
			ResultSet rs1 = stmt.executeQuery(sql);
			
			int end_func = 0;
			while(rs1.next()) {
				if(rs1.getString("GroupName").equals(groupName)) {
					end_func++;
				}
			}
			if(end_func == 0) return 2;
			
			//here delete row
			String sql_leaveUserGroup = "DELETE FROM " + tableName + " WHERE GroupName = '" + groupName + "'"; 
			PreparedStatement delete = conn.prepareStatement(sql_leaveUserGroup);
			delete.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
}
