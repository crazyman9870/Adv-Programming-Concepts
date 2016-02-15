package Shared.CommunicatingClasses;

import Shared.ModelClasses.Credentials;

/**
 * This class is packages the information from the client
 * Used by the server to return information about the fields in the project
 * @author aconstan
 *
 */

public class GetFieldsIn {
	
	private String username;
	private String password;
	private int projectKey;
	
	public GetFieldsIn (String username, String password, int projectKey)
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
}
