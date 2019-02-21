package Monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import GUI.SignUpWindow;
import clientProject.LoginWindow;


public class SignUpMonitor implements ActionListener{
	private LoginWindow lw;
	private SignUpWindow swu;
	
	public SignUpMonitor(SignUpWindow swu) {
		this.swu = swu;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int command = Integer.parseInt(e.getActionCommand());
		switch (command) {
		case 1:
			//TO DO SIGN UP BUTTON
			String account = swu.getAccount().getText();
			String password = swu.getPassword().getText();
			String userName = swu.getUserName().getText();
			String profile = swu.getProfile().getText();

			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://127.0.0.1:3306/cs176bproject?";
			String sqlName = "root";
			String sqlPassword = "fujie19970";
			try {
				
				//TO DO
				// account should only be numbers only. 
				// account and password field should not be longer than 10 characters
				// account should be 8 <= account number <= 10.
				Class.forName(driver);
				System.out.println("Connecting to database server");
			
				Connection conn =  DriverManager.getConnection(url, sqlName, sqlPassword);
				System.out.println("Connect Successful");
				Statement stmt = conn.createStatement();	
				
				
				String sql_getAccount = "SELECT Aid FROM ClientInfo";
				ResultSet rs = stmt.executeQuery(sql_getAccount);
				int outbreak = 0;
				while(rs.next()) {
					if(rs.getString("Aid").equals(account)) {
						JOptionPane.showMessageDialog(null, "Account Already Existed! ", "", JOptionPane.PLAIN_MESSAGE);
						outbreak++;
					}
				}
				if(outbreak != 0) break;
				
				
				String sql_sign_up = "INSERT INTO ClientInfo (Aid , Password, Status, UserName, Profile) VALUES ('" + account + "', '" + password + "', '0' ,'" + userName + "','" + profile + "')";
				PreparedStatement insert = conn.prepareStatement(sql_sign_up);
				insert.executeUpdate();
				JOptionPane.showMessageDialog(null, "Sign Up Successful! ", "", JOptionPane.PLAIN_MESSAGE);
			}catch(Exception ex) {
				System.out.println(ex);
			}finally {
				//conn.close();
			}
			
			
		case 2:
			this.swu.setVisible(false);
			this.lw = new LoginWindow();
		}
	}
}
