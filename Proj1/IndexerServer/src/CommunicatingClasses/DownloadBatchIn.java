package CommunicatingClasses;

/**
 * This object stores the client's request to get a batch from the server
 * 
 * @author aconstan
 *
 */

public class DownloadBatchIn {
	
	private String username;
	private String password;
	private String projectKey;
	
	public DownloadBatchIn(String username, String password, String projectKey)
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

	public String getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}

}
