package Shared.CommunicatingClasses;

/**
 * This object stores the client's request to get a batch from the server
 * 
 * @author aconstan
 *
 */

public class GetBatchIn {
	
	private String username;
	private String password;
	private int projectKey;
	
	public GetBatchIn(String username, String password, int projectKey)
	{
		this.username = username;
		this.password = password;
		this.projectKey = projectKey;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(int projectKey) {
		this.projectKey = projectKey;
	}

}
