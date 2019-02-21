package clientProject;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class MessagePane extends JPanel implements MessageListener{
	private chatClient client;
	private String login;
	private String userName;
	
	private DefaultListModel<String> listModel = new DefaultListModel<>();
	private JList<String> messageList = new JList<>(listModel);
	private JTextField inputField = new JTextField();
	

	public MessagePane(chatClient client, String login, String userName) {
		this.client = client;
		this.login = login;
		this.userName = userName;
		
		client.addMessageListener(this);
		
		setLayout(new BorderLayout());
		add(new JScrollPane(messageList), BorderLayout.CENTER);
		add(inputField, BorderLayout.SOUTH);
		
		inputField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String text = inputField.getText();
					client.msg(login, text);
					listModel.addElement("You: " + text);
					inputField.setText("");
					
				}catch(IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
	}
	
	@Override
	public void onMessage(String fromLogin, String msgBody) {
		if(login.equalsIgnoreCase(fromLogin)) {
			String line = userName + ": " + msgBody;
			listModel.addElement(line);
		}
	}


}
