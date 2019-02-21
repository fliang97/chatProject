package clientProject;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;

import Monitor.LoginMonitor;

public class LoginWindow extends JFrame{
	private static final long serialVersionUID = 1L;

	private chatClient client;
	private JTextField loginField;// = new JTextField();
	private JPasswordField passwordField;// = new JPasswordField();
	private JButton loginButton = new JButton("login");
	
	public JTextField getLoginField() {
		return loginField;
	}
	
	public JPasswordField getJPasswordField() {
		return passwordField;
	}
	
	public LoginWindow() {
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		this.setTitle("chatMate");
		this.setLayout(new FlowLayout());
		this.setSize(500, 500);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		JLabel userLabel = new JLabel("Account: ");
		JLabel pwdLabel = new JLabel("Password: ");
		this.add(userLabel);
		Dimension dim = new Dimension(160, 20);
		this.loginField = new JTextField();
		this.loginField.setPreferredSize(dim);
		this.add(this.loginField);
		this.add(pwdLabel);
		this.passwordField = new JPasswordField();
		this.passwordField.setPreferredSize(dim);
		this.add(this.passwordField);
		
		
		this.client = new chatClient("localhost", 8818);
		client.connect();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//JPanel p = new JPanel();
		//p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		//p.add(loginField);
		//p.add(passwordField);	
		
		LoginMonitor lm = new LoginMonitor(this, client);
		loginButton.setActionCommand("1");
		loginButton.addActionListener(lm);
		this.add(loginButton);
		
		JButton signUpButton = new JButton("Sign up");
		signUpButton.setActionCommand("2");
		signUpButton.addActionListener(lm);
		this.add(signUpButton);
		//p.add(loginButton);
		
		/*loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doLogin();
			}
			
		});*/
		
		//getContentPane().add(p, BorderLayout.CENTER);
		
		pack();
		
		setVisible(true);
	}
	
/*	public void doLogin() {
		String login = loginField.getText();
		String password = passwordField.getText();
		
		try {
			if(client.login(login, password)) {
				UserListPane userListPane = new UserListPane(client);
				JFrame frame = new JFrame("User List");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(400, 600);
				
				frame.getContentPane().add(userListPane, BorderLayout.CENTER);
				frame.setVisible(true);
				//bring up the user window
				setVisible(false);
			}else {
				//show err message
				JOptionPane.showMessageDialog(this, "Invalid login/password");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}*/

	public static void main(String[] args) {
		LoginWindow lw = new LoginWindow();
		lw.setVisible(true);
	}
}
