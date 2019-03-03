package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HandleGetGroupDB {
	private Connection conn;
	private String[] tokens;
	private ArrayList<String> result;
	
	public HandleGetGroupDB(String[] tokens, Connection conn) {
		this.conn = conn;
		this.tokens = tokens;
		result = new ArrayList<String>();
	}
	
	public ArrayList<String> run() throws SQLException{
		Statement stmt = conn.createStatement();
		
		String tableName = "UserGroupList_" + this.tokens[1];
		
		String sql_getGroup = "SELECT * FROM " + tableName;
		ResultSet rs1 = stmt.executeQuery(sql_getGroup);
		while(rs1.next()) {
			this.result.add(rs1.getString("GroupName"));
		}
		
		return result;
	}
}
