package Listener;

public interface GroupListListener {
	public void showGroup(String groupName);
	public void showCreateGroupResult(String result);
	public void showJoinGroupResult(String result, String groupName);
	public void showLeaveGroupResult(String result, String groupName);
}