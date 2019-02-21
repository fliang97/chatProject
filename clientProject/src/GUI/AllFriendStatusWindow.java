package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import Monitor.AllFriendStatusMonitor;
import clientProject.UserListPane;

public class AllFriendStatusWindow extends JFrame{


	
	public void launchWindow(String account) {
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		this.setTitle("Friend Status");
	
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/cs176bproject?";
		String sqlName = "root";
		String sqlPassword = "fujie19970";
		Connection conn = null;
		Statement stmt = null;
		
		OnlineFriendStatusPane afsp = null;
		
		try {
			Class.forName(driver);
			System.out.println("Connecting to database server");
	
			conn =  DriverManager.getConnection(url, sqlName, sqlPassword);
			System.out.println("Connect Successful");
			stmt = conn.createStatement();	
		
			afsp = new OnlineFriendStatusPane(account, conn, stmt);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		afsp.setPreferredSize(new Dimension(400, 500));
		
		this.setLayout(new FlowLayout());
		this.setSize(500, 800);
		
		this.getContentPane().add(afsp, BorderLayout.CENTER);

		this.setResizable(false);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		JButton back = new JButton("back");
		back.setActionCommand("2");
		AllFriendStatusMonitor afsm = new AllFriendStatusMonitor(this);
		back.addActionListener(afsm);
		this.add(back);
		
		this.setVisible(true);
	}
}
