package CommunicatingClasses;

/**
 * This class takes the information from the client to get a sample image from the database 
 * @author aconstan
 *
 */

public class SampleImageIn {
	
	private String username;
	private String password;
	private String projectKey;
	
	public SampleImageIn(String username, String password, String projectKey)
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
