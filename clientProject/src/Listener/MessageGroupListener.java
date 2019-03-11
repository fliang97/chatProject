package Listener;

public interface MessageGroupListener {
	public void onMessage(String fromLogin, String toLogin, String msgBody, String userName);
}
