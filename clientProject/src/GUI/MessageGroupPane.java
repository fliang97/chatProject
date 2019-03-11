package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Listener.MessageGroupListener;
import clientProject.MessageListener;
import clientProject.chatClient;

public class MessageGroupPane extends JPanel implements MessageGroupListener{
	private chatClient client;
	private String groupName;
	private String login;
	
	private DefaultListModel<String> listModel = new DefaultListModel<>();
	private JList<String> messageList = new JList<>(listModel);
	private JTextField inputField = new JTextField();
	

	public MessageGroupPane(chatClient client, String groupName, String login) {
		this.client = client;
		this.groupName = groupName;
		this.login = login;
		
		client.addMessageGroupListener(this);
		
		setLayout(new BorderLayout());
		add(new JScrollPane(messageList), BorderLayout.CENTER);
		add(inputField, BorderLayout.SOUTH);
		
		inputField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String text = inputField.getText();
					client.msg(groupName, text);
					listModel.addElement("You: " + text);
					inputField.setText("");
					
				}catch(IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
	}
	
	@Override
	public void onMessage(String fromLogin, String toLogin, String msgBody, String userName) {
		if(groupName.equalsIgnoreCase(fromLogin)) {
			if(!toLogin.equalsIgnoreCase(this.login)) {
				String line = userName + ": " + msgBody;
				listModel.addElement(line);
			}
		}
	}

}